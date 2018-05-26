package com.wingjay.lego;

import java.util.Map;


/**
 * IViewHolderMapper, Every sdk has its own ViewHolderMapper,
 * name is "LegoXXXViewHolderMapper", XXX is moduleName configured in
 * "build.gradle/android/defaultConfig/javaCompileOptions/annotationProcessorOptions/arguments['moduleName']"
 *
 * @author wingjay
 */
public interface IViewHolderMapper {
    /**
     * use Id to find ViewHolder class name({@link Class#getName()}).
     * Note: this id is configured in {@link LegoViewHolder#id()}.
     *
     * @return ViewHolder className.
     */
    Map<String, Class> idToVHMap();

    /**
     * Use Object class to find ViewHolder. Remember: This object class cannot map to two or more ViewHolder.
     *
     * @return ViewHolder className.
     */
    Map<String, Class> classToVHMap();

}
