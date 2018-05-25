package com.wingjay.lego.inner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.wingjay.lego.ILegoViewHolder;
import com.wingjay.lego.LegoItem;

/**
 * IRichRecyclerViewHolder
 *
 * @author wingjay
 * @date 2017/09/22
 */
public class LegoViewHolderDelegate extends ViewHolder {
    private ILegoViewHolder realViewHolder;

    public LegoViewHolderDelegate(@NonNull View itemView,
                                  @NonNull ILegoViewHolder realViewHolder) {
        super(itemView);
        this.realViewHolder = realViewHolder;
    }

    public void bindData(Object data, int position) {
        if (data instanceof LegoItem) {
            LegoItem item = (LegoItem) data;
            realViewHolder.bindData(item.getData(), position, item.getBundle());
        } else {
            realViewHolder.bindData(data, position, null);
        }
    }

    public ILegoViewHolder getRealViewHolder() {
        return realViewHolder;
    }

    @Override
    public String toString() {
        return realViewHolder.getClass().getSimpleName();
    }
}
