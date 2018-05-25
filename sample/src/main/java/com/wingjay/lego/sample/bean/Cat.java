package com.wingjay.lego.sample.bean;

import android.content.Context;
import android.widget.Toast;
import com.wingjay.lego.LegoBean;
import com.wingjay.lego.sample.viewholder.CatViewHolder;

/**
 * Cat
 *
 * @author wingjay
 * @date 2018/05/25
 */
@LegoBean(vhClass = CatViewHolder.class)
public class Cat {
    public void say(Context context) {
        Toast.makeText(context, "MiaoWu!", Toast.LENGTH_SHORT).show();
    }
}
