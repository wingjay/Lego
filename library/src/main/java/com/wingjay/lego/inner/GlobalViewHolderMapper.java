package com.wingjay.lego.inner;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.support.annotation.NonNull;
import com.wingjay.lego.DuplicateLegoKeyException;
import com.wingjay.lego.IViewHolderMapper;

/**
 * GlobalViewHolderMapper, live in v5 + bundles.
 *
 * We have two ways to find a ViewHolder:
 * 1. by Id.
 * 2. by Object class. Remember: one Object class can only specify one ViewHolder, otherwise it'll be confusing to choose.
 *
 * @author wingjay
 */
public class GlobalViewHolderMapper {

    private static final HashMap<String, Class> ID_TO_VH_MAP = new HashMap<>();
    private static final HashMap<String, Class> CLASS_TO_VH_MAP = new HashMap<>();

    /**
     * Match ViewHolder by id.
     *
     * @param id viewHolder id
     * @return ViewHolder class
     */
    public static Class matchById(String id) {
        return ID_TO_VH_MAP.get(id);
    }

    /**
     * Match ViewHolder by data clazz
     *
     * @param dataClass data to display
     * @return ViewHolder class
     */
    public static Class matchByClass(Class dataClass) {
        return CLASS_TO_VH_MAP.get(dataClass.getName());
    }

    /**
     * Every id should be unique.
     */
    private static void checkUniqueId(Map<String, Class> newIdToVHMap) {
        if (newIdToVHMap == null) {
            return;
        }
        for (Entry<String, Class> e : newIdToVHMap.entrySet()) {
            if (ID_TO_VH_MAP.containsKey(e.getKey())) {
                throw new DuplicateLegoKeyException(e.getKey(),
                    ID_TO_VH_MAP.get(e.getKey()).toString(), e.getValue().toString());
            }
        }
    }

    /**
     * Every Object class should be unique.
     */
    private static void checkUniqueObjectClass(Map<String, Class> newClassToVHMap) {
        if (newClassToVHMap == null) {
            return;
        }
        for (Entry<String, Class> e : newClassToVHMap.entrySet()) {
            if (CLASS_TO_VH_MAP.containsKey(e.getKey())) {
                throw new DuplicateLegoKeyException(e.getKey(),
                    CLASS_TO_VH_MAP.get(e.getKey()).toString(), e.getValue().toString());
            }
        }
    }

    private static void innerRegister(@NonNull IViewHolderMapper mapper) {
        checkUniqueId(mapper.idToVHMap());
        checkUniqueObjectClass(mapper.classToVHMap());

        if (mapNotEmpty(mapper.idToVHMap())) {
            ID_TO_VH_MAP.putAll(mapper.idToVHMap());
        }
        if (mapNotEmpty(mapper.classToVHMap())) {
            CLASS_TO_VH_MAP.putAll(mapper.classToVHMap());
        }
    }

    public static void register(IViewHolderMapper... mapperList) {
        for (IViewHolderMapper mapper : mapperList) {
            innerRegister(mapper);
        }
    }

    private static boolean mapNotEmpty(Map c) {
        return c != null && c.size() > 0;
    }
}
