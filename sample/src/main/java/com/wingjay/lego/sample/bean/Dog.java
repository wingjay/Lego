package com.wingjay.lego.sample.bean;

import android.content.Context;
import android.widget.Toast;
import com.wingjay.lego.LegoBean;
import com.wingjay.lego.sample.viewholder.DogViewHolder;

/**
 * Dog
 *
 * @author wingjay
 * @date 2018/05/25
 */
@LegoBean(vhClass = DogViewHolder.class)
public class Dog {
    public void say(Context context) {
        Toast.makeText(context, "WangWang!", Toast.LENGTH_SHORT).show();
    }
}
