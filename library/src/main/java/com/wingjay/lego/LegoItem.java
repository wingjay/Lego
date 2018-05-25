package com.wingjay.lego;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * LegoItem
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class LegoItem {
    private String viewHolderId;
    private Object data;
    /**
     * attachment data used by ViewHolder
     */
    private Bundle bundle;
    private LegoItem(@Nullable String viewHolderId,
                     @Nullable Object data,
                     @Nullable Bundle bundle) {
        this.viewHolderId = viewHolderId;
        this.data = data;
        this.bundle = bundle;
    }

    /**
     * Want to display a ViewHolder in RecyclerView? Do as below:
     * 1. Create/Find the viewHolder class you want;
     * 2. For this ViewHolder class, there should be an Annotation: {@link LegoViewHolder};
     * 3. {@link LegoViewHolder#id()} is ViewHolderId;
     * 4. {@link LegoViewHolder#bean()} is your Data class, default to be Object.class.
     *
     * Example:
     * There is a ViewHolder used to display cat's info.
     *```
     *| @LegoViewHolder(id='CatViewHolder', bean = Cat.class)
     *| public class CatViewHolder implements ILegoViewHolder {
     *|     @Override
     *|     public initView(...) {}
     *|
     *|     @Override
     *|     public bindData(Object data) {
     *|         Cat cat = (Cat) data;
     *|         display(cat);
     *|     }
     *| }
     *```
     * If you want to use CatViewHolder, then your LegoItem is:
     * ```
     * Cat cat = new Cat();
     * LegoItem.create(cat, "CatViewHolder");
     * adapter.setData(legoItemList);
     * ```
     *
     * @param viewHolderId the id of LegoViewHolder.
     * @param data Data your LegoViewHolder wants, if it allows null data, you can use null.
     */
    public static LegoItem create(@NonNull String viewHolderId,
                                  @Nullable Object data) {
        return new LegoItem(viewHolderId, data, null);
    }

    /**
     * Create a LegoItem with bundle, which is used for data-transmission between <b>Atlas Bundles</b>.
     *
     * @param bundle this bundle will be taken to {@link ILegoViewHolder#bindData(Object, int, Bundle)}
     */
    public static LegoItem create(@Nullable String viewHolderId,
                                  @Nullable Object data,
                                  @NonNull Bundle bundle) {
        return new LegoItem(viewHolderId, data, bundle);
    }

    @Nullable String getViewHolderId() {
        return viewHolderId;
    }

    public Object getData() {
        return data;
    }

    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Handy method to transfer List<Object> to List<LegoItem>
     */
    public static List<LegoItem> toList(@NonNull String viewHolderId,
                                        List<? extends Object> rawData) {
        return toList(viewHolderId, rawData, null);
    }

    /**
     * Handy method to transfer List<Object> to List<LegoItem>.
     * This bundle will be put into each LegoItem instance.
     */
    public static List<LegoItem> toList(@NonNull String viewHolderId,
                                        List<? extends Object> rawData,
                                        @Nullable Bundle bundle) {
        if (rawData == null || rawData.size() <= 0) {
            return new ArrayList<>(0);
        }

        List<LegoItem> itemList = new ArrayList<>(rawData.size());
        for (Object o : rawData) {
            if (bundle == null) {
                itemList.add(LegoItem.create(viewHolderId, o));
            } else {
                itemList.add(LegoItem.create(viewHolderId, o, bundle));
            }
        }
        return itemList;
    }
}
