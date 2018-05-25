package com.wingjay.lego;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * LegoViewHolderElement
 *
 * @author wingjay
 * @date 2018/05/25
 */
public class LegoViewHolderElement {
    private static final String TARGET_PACKAGE_NAME = "com.wingjay.lego";
    private static final String SUPER_INTERFACE_PACKAGE_NAME = "com.wingjay.lego";
    private static final String SUPER_INTERFACE_NAME = "IViewHolderMapper";

    private Map<String, ClassName> idToVHMap;
    private Map<String, ClassName> classToVHMap;

    LegoViewHolderElement() {
        idToVHMap = new HashMap<>();
        classToVHMap = new HashMap<>();
    }

    void addIdToClassName(String key, ClassName className) throws IllegalAccessException {
        if (idToVHMap.containsKey(key)) {
            throw new IllegalArgumentException(
                String.format("Duplicate LegoKey. key: %s, viewHolder1: %s, viewHolder2: %s",
                    key, idToVHMap.get(key), className));
        }
        idToVHMap.put(key, className);
    }

    void addClassToClassName(String clazz, ClassName className) throws IllegalAccessException {
        if (classToVHMap.containsKey(clazz)) {
            throw new IllegalArgumentException(
                String.format("Duplicate LegoClass. clazz: %s, viewHolder1: %s, viewHolder2: %s",
                    clazz, classToVHMap.get(clazz), className));
        }
        classToVHMap.put(clazz, className);
    }

    String getPackageName() {
        return TARGET_PACKAGE_NAME;
    }

    String getGeneratedClassName(String moduleName) {
        return "Lego" + moduleName + "ViewHolderMapper";
    }

    ClassName superInterfaceName() {
        return ClassName.get(SUPER_INTERFACE_PACKAGE_NAME, SUPER_INTERFACE_NAME);
    }

    MethodSpec classToVHMapMethod() {
        return innerMapMethod("classToVHMap", "classToVHMap", classToVHMap);
    }

    MethodSpec idToVHMapMethod() {
        return innerMapMethod("idToVHMap", "idToVHMap", idToVHMap);
    }

    private MethodSpec innerMapMethod(String methodName, String mapName, Map<String, ClassName> dataSource) {
        CodeBlock.Builder builder = CodeBlock.builder();
        builder.addStatement("Map<String, Class> $L = new $T<>()", mapName, HashMap.class);
        for (Entry<String, ClassName> e : dataSource.entrySet()) {
            builder.add("$L.put(\"", mapName);
            builder.add(e.getKey());
            builder.add("\", ");
            builder.add("$T.class", e.getValue());
            builder.addStatement(")");
        }
        builder.addStatement("return $L", mapName);

        return MethodSpec.methodBuilder(methodName)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .returns(ParameterizedTypeName.get(Map.class, String.class, Class.class))
            .addCode(builder.build())
            .build();
    }

}
