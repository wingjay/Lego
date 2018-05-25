package com.wingjay.lego;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import static com.wingjay.lego.LegoViewHolderProcessor.APT_KEY_MODULE_NAME;

/**
 * LegoViewHolderProcessor
 *
 * @author wingjay
 * @date 2018/05/25
 */
@AutoService(Processor.class)
@SupportedOptions(APT_KEY_MODULE_NAME)
public class LegoViewHolderProcessor extends AbstractProcessor {

    static final String APT_KEY_MODULE_NAME = "moduleName";
    private String moduleName = "App";
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        // Attempt to get user configuration [moduleName]
        Map<String, String> options = processingEnv.getOptions();
        if (options != null && options.containsKey(APT_KEY_MODULE_NAME)) {
            // read from build.gradle/android/apt/argument
            moduleName = options.get(APT_KEY_MODULE_NAME);
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(LegoViewHolder.class);
        Set<? extends TypeElement> typeElements = ElementFilter.typesIn(elements);

        LegoViewHolderElement targetElement = new LegoViewHolderElement();

        for (TypeElement element : typeElements) {
            ClassName currentType = ClassName.get(element);
            // Get the annotation element from the type element
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                // Get the ExecutableElement:AnnotationValue pairs from the annotation element
                // because Annotation cannot support Object.class directly, so we have to use TypeMirror.
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues =
                    annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    Object value = entry.getValue().getValue();
                    switch (key) {
                        case "id":
                            String id = (String) value;
                            try {
                                targetElement.addIdToClassName(id, currentType);
                            } catch (IllegalAccessException e) {
                                mMessager.printMessage(Kind.ERROR, e.getMessage());
                            }
                            break;
                        case "bean":
                            TypeMirror className = (TypeMirror) value;
                            try {
                                targetElement.addClassToClassName(className.toString(), currentType);
                            } catch (IllegalAccessException e) {
                                mMessager.printMessage(Kind.ERROR, e.getMessage());
                            }
                            break;
                        default: break;
                    }
                }
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE,
            String.format("generate Lego%sViewHolder class:",targetElement.getGeneratedClassName(moduleName)));

        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        TypeSpec targetTypeSpec = TypeSpec.classBuilder(targetElement.getGeneratedClassName(moduleName))
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(targetElement.superInterfaceName())
            .addMethod(targetElement.idToVHMapMethod())
            .addMethod(targetElement.classToVHMapMethod())
            .addJavadoc("This file was generated by apt. Do not modify!\n\n")
            .addJavadoc("@date " + dateFormat.format(new Date()) + "\n")
            .addJavadoc("@author 冲灵 (yj142679@alibaba-inc.com)\n")
            .build();

        JavaFile javaFile = JavaFile.builder(targetElement.getPackageName(), targetTypeSpec)
            .addFileComment("This file was generated by apt. Do not modify!\n")
            .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            //mMessager.printMessage(Diagnostic.Kind.ERROR,
            //    String.format("Generate file failed, reason: %s", e.getMessage()));
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(LegoViewHolder.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
