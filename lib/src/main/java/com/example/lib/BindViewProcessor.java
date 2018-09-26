package com.example.lib;

import com.example.animal.BindView;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */

public class BindViewProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElements;
    private Messager mMessage;
    private Map<String, AnnotatedClass> annotatedClassMap;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElements = processingEnv.getElementUtils();
        mMessage = processingEnv.getMessager();
        annotatedClassMap = new TreeMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotatedClassMap.clear();
        processBindView(roundEnv);

        for (AnnotatedClass annotatedClass : annotatedClassMap.values()){
            try {
                annotatedClass.generatedFile().writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void processBindView(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            annotatedClass.addFields(new BindViewField(element));
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        String fullName = element.getSimpleName().toString();
        AnnotatedClass annotatedClass = annotatedClassMap.get(fullName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(typeElement, mElements);
            annotatedClassMap.put(fullName, annotatedClass);
        }
        return annotatedClass;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        return types;
    }
}
