package com.example.lib;

import com.example.animal.BindView;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */
public class BindViewField {
    private VariableElement variableElement;
    private int resId;

    BindViewField(Element element) {
        if (element.getKind() != ElementKind.FIELD) {
            return;
        }
        variableElement = (VariableElement) element;
        BindView bindView = variableElement.getAnnotation(BindView.class);
        resId = bindView.value();
    }

    Name getFiledName() {
        return variableElement.getSimpleName();
    }

    int getResId() {
        return resId;
    }

    TypeMirror getFieldType() {
        return variableElement.asType();
    }
}
