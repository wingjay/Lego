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
import com.wingjay.lego.sample.bean.Dog;

/**
 * DogViewHolder
 *
 * @author wingjay
 * @date 2018/05/25
 */
@LegoViewHolder(bean = Dog.class)
public class DogViewHolder implements ILegoViewHolder {
    private Button touchMeBtn;

    @Override
    public View initView(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dog, parent, false);
        touchMeBtn = root.findViewById(R.id.touchMe);
        return root;
    }

    @Override
    public void bindData(Object data, int position, @Nullable Bundle argument) {
        final Dog dog = (Dog) data;
        touchMeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dog.say(v.getContext());
            }
        });
    }
}