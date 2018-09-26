package com.example.lib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */


public class AnnotatedClass {

    private TypeElement mTypeElement;
    private Elements mElements;
    private ArrayList<BindViewField> bindViewFields;

    public AnnotatedClass(TypeElement mTypeElement, Elements mElements) {
        this.mTypeElement = mTypeElement;
        this.mElements = mElements;
        bindViewFields = new ArrayList<>();
    }

    public void addFields(BindViewField field) {
        bindViewFields.add(field);
    }

    public JavaFile generatedFile() {
        MethodSpec.Builder bindViewMethod = MethodSpec.methodBuilder("bindView")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "host")
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtil.VIEW_FINDER, "finder");

        for (BindViewField field : bindViewFields) {
            bindViewMethod.addStatement("host.$N = ($T)(finder.findView(source,$L))", field.getFiledName(),
                    ClassName.get(field.getFieldType()), field.getResId()
            );
        }

        MethodSpec.Builder unBindViewMethod = MethodSpec.methodBuilder("unBindView")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "host")
                .addAnnotation(Override.class);
        for (BindViewField field : bindViewFields) {
            unBindViewMethod.addStatement("host.$N = null", field.getFiledName());
        }
        TypeSpec typeSpec = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$ViewBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.BINDER, TypeName.get(mTypeElement.asType())))
                .addMethod(bindViewMethod.build())
                .addMethod(unBindViewMethod.build())
                .build();

        String packName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();
        return JavaFile.builder(packName, typeSpec).build();
    }


    private static class TypeUtil {
        static final ClassName BINDER = ClassName.get("com.example.apilibrary", "ViewBinder");
        static final ClassName VIEW_FINDER = ClassName.get("com.example.apilibrary", "ViewFinder");
    }
}
