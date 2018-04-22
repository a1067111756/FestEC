package com.ali.latte.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.logging.Filter;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by 澄鱼 on 2018/4/23.
 */

public class EntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void>{

    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setmFiler(Filer filer) {
        this.mFiler = filer;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return p;
    }

    @Override
    public Void visitType(TypeMirror typeMirror, Void p) {
        mTypeMirror = typeMirror;
        generateJavaCode();
        return p;
    }

    private void generateJavaCode() {

        final TypeSpec targetActivity =
                TypeSpec.classBuilder("WXEntryActivity")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(mTypeMirror))
                .build();
        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi", targetActivity)
                .addFileComment("WeiXin Entry file")
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
