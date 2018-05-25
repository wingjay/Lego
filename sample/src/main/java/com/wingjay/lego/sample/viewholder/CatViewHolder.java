package com.wingjay.lego.sample.viewholder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.wingjay.lego.ILegoViewHolder;
import com.wingjay.lego.LegoViewHolder;
import com.wingjay.lego.sample.R;
import com.wingjay.lego.sample.bean.Cat;

/**
 * CatViewHolder
 *
 * @author wingjay
 * @date 2018/05/25
 */
@LegoViewHolder(bean = Cat.class)
public class CatViewHolder implements ILegoViewHolder {

    private Button touchMeBtn;

    @Override
    public View initView(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat, parent, false);
        touchMeBtn = root.findViewById(R.id.touchMe);
        return root;
    }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) {
        final Cat cat = (Cat) data;
        touchMeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cat.say(v.getContext());
            }
        });
    }
}

