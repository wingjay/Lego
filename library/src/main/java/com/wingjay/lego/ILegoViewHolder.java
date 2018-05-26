package com.wingjay.lego;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

/**
 * ILegoViewHolder.
 * As a ViewHolder, it has only two responsibilities:
 * 1. create its view;
 * 2. bind data.
 *
 * @author wingjay
 */
public interface ILegoViewHolder {
    /**
     * Create your view.
     *
     * @param parent parent view
     * @return the view you want to show
     */
    View initView(ViewGroup parent);

    /**
     * Display the data at the specified position.
     *
     * @param data Your data. you can specify its type within Annotation {@link LegoViewHolder#bean()}
     * @param position The position of the item within the adapter's data set.
     * @param argument Data used to transfer between multi bundles.
     */
    void bindData(Object data, int position, @Nullable Bundle argument);
}
