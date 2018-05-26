package com.wingjay.lego.inner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.wingjay.lego.ILegoViewHolder;

/**
 * LViewHolderFactory: build LegoViewHolder instance from class.
 *
 * @author wingjay
 */
public class LViewHolderFactory {

    /**
     * Build ILegoViewHolder instance from class.
     */
    public static ILegoViewHolder buildViewHolder(Class<?> viewHolderClass, Context context) throws Exception {
        ILegoViewHolder viewHolderObject;
        Class enclosingClass = viewHolderClass.getEnclosingClass();
        if (null != viewHolderClass.getEnclosingClass()) {
            // ViewHolder is inner class
            if (Modifier.isStatic(viewHolderClass.getModifiers())) {
                // static inner class
                viewHolderObject = (ILegoViewHolder) createInstance(viewHolderClass, null, context);
            } else {
                // non-static inner class
                viewHolderObject = (ILegoViewHolder) createInstance(viewHolderClass, enclosingClass, context);
            }
        } else {
            // non-inner class
            viewHolderObject = (ILegoViewHolder) createInstance(viewHolderClass, null, context);
        }

        return viewHolderObject;
    }

    /**
     * Class may have two kinds of constructor for compatibility.
     *  public clazz() {}
     *  public clazz(Context context) {} // from old HolderView
     */
    private static <T> Object createInstance(@NonNull Class<T> clazz,
                                      @Nullable Class enclosingClazz,
                                      Context context) throws Exception {
        try {
            if (enclosingClazz == null) {
                Constructor c = clazz.getDeclaredConstructor();
                c.setAccessible(true);
                return c.newInstance();
            } else {
                Constructor c = clazz.getDeclaredConstructor(enclosingClazz);
                c.setAccessible(true);
                return c.newInstance(enclosingClazz);
            }
        } catch (NoSuchMethodException e) {
            if (enclosingClazz == null) {
                Constructor c = clazz.getDeclaredConstructor(Context.class);
                c.setAccessible(true);
                return c.newInstance(context);
            } else {
                Constructor c = clazz.getDeclaredConstructor(enclosingClazz, Context.class);
                c.setAccessible(true);
                return c.newInstance(enclosingClazz, context);
            }
        }
    }

    /**
     * Generally, the application classLoader is Atlas#DelegateClassLoader, which is able to load class across Bundle.
     */
    private static ClassLoader getDelegateClassLoader(@Nullable ClassLoader defaultClassLoader,
                                                      Context context) {
        if (defaultClassLoader == null) {
            defaultClassLoader = (context.getApplicationContext()).getClassLoader();
        }
        return defaultClassLoader;
    }

}
