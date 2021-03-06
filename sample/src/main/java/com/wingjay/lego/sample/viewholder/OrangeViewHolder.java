package com.wingjay.lego.sample.viewholder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wingjay.lego.ILegoViewHolder;
import com.wingjay.lego.LegoViewHolder;
import com.wingjay.lego.sample.R;

/**
 * OrangeViewHolder
 *
 * @author wingjay
 * @date 2018/05/25
 */
@LegoViewHolder(id = "orange")
public class OrangeViewHolder implements ILegoViewHolder {
    @Override
    public View initView(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orange, parent, false);
    }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) {

    }
}
