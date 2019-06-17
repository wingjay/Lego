package com.wingjay.lego;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.wingjay.lego.inner.LViewHolderFactory;
import com.wingjay.lego.inner.LegoLogger;
import com.wingjay.lego.inner.LegoViewHolderDelegate;

/**
 * LegoRecyclerAdapter:
 * A powerful RecyclerAdapter, it can display multi-type item, configurable ViewHolder-id for each item.
 *
 * Optimize: cache viewHolder before data fetched from server.
 *
 * @author wingjay
 */
public class LegoRecyclerAdapter extends RecyclerView.Adapter<LegoViewHolderDelegate> {

    private LegoCache legoCache;
    private ClassLoader delegateClassLoader;
    private List<Object> dataList = new ArrayList<>();
    /**
     *  IRichViewHolder class name
     */
    private List<Class> viewHolderClassNameList = new ArrayList<>();
    private OnLegoViewHolderListener onLegoViewHolderListener;

    public LegoRecyclerAdapter() {
        this.legoCache = null;
    }

    public LegoRecyclerAdapter(@NonNull LegoCache legoCache) {
        this.legoCache = legoCache;
    }

    public void setLegoCache(@NonNull LegoCache legoCache) {
        this.legoCache = legoCache;
    }

    /**
     * clear existing data, then use new data.
     *
     * @param newDataList brand-new data.
     */
    public void swapData(final List<Object> newDataList) {
        final int oldCount = this.dataList.size();
        this.dataList.clear();
        notifyItemRangeRemoved(0, oldCount);
        innerAppendData(newDataList, 0);
    }

    /**
     * Append new data after existing ones.
     *
     * @param newDataList appended data.
     */
    public void appendData(List<Object> newDataList) {
        int oldCount = this.dataList.size();
        innerAppendData(newDataList, oldCount);
    }

    private void innerAppendData(List<Object> newDataList, int oldDataCount) {
        if (newDataList == null || newDataList.size() <= 0) {
            return;
        }
        this.dataList.addAll(newDataList);
        setupViewHolderClassList();
        notifyItemRangeInserted(oldDataCount, this.dataList.size() - oldDataCount);
    }

    /**
     * When adapter create ViewHolder, we'll notify you by {@link OnLegoViewHolderListener}
     */
    public void setOnLegoViewHolderListener(OnLegoViewHolderListener listener) {
        this.onLegoViewHolderListener = listener;
    }

    public void setupViewHolderClassList() {
        if (dataList == null || dataList.size() <= 0) {
            viewHolderClassNameList.clear();
            return;
        }
        for (Object d : dataList) {
            Class clazz = LegoManager.find(d);
            if (!viewHolderClassNameList.contains(clazz)) {
                viewHolderClassNameList.add(clazz);
            }
        }
    }
    
    @Override
    public int getItemViewType(int position) {
        return viewHolderClassNameList.indexOf(LegoManager.find(dataList.get(position)));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public LegoViewHolderDelegate onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        Class<?> viewHolderClass = viewHolderClassNameList.get(viewType);
        LegoViewHolderDelegate viewHolderDelegate = fetchFromCache(viewHolderClass);
        if (viewHolderDelegate == null) {
            try {
                ILegoViewHolder realViewHolder = LViewHolderFactory.buildViewHolder(viewHolderClass, context);
                viewHolderDelegate = new LegoViewHolderDelegate(realViewHolder.initView(parent), realViewHolder);
                LegoLogger.i("Manually created viewHolder" + viewHolderClass.getSimpleName());
            } catch (Exception e) {
                LegoLogger.e(String.format("onCreateViewHolder Failure for class: %s. Error: %s",
                    viewHolderClass, e.getMessage()));
            }
        } else {
            LegoLogger.i(String.format("Acquired %s from LegoCache.", viewHolderClass.getSimpleName()));
        }

        if (viewHolderDelegate != null
            && onLegoViewHolderListener != null) {
            onLegoViewHolderListener.onCreate(viewHolderDelegate.getRealViewHolder());
        }
        return viewHolderDelegate;
    }

    @Nullable
    private LegoViewHolderDelegate fetchFromCache(Class<?> viewHolderClass) {
        return legoCache != null ? legoCache.acquireCachedViewHolder(viewHolderClass) : null;
    }


    @Override
    public void onBindViewHolder(LegoViewHolderDelegate holder, int position) {
        LegoLogger.i(String.format("bindViewHolder position: %s ,viewholder: %s", position, holder.toString()));
        holder.bindData(dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onViewRecycled(LegoViewHolderDelegate holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(LegoViewHolderDelegate holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(LegoViewHolderDelegate holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(LegoViewHolderDelegate holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public interface OnLegoViewHolderListener {
        /**
         * This is called <b>before</b> {@link ILegoViewHolder#initView(ViewGroup)}.
         * Note: This viewHolder may be <b>reused</b> by different RecyclerView items.
         *
         * @param viewHolder target viewHolder, maybe multi type.
         */
        void onCreate(@NonNull ILegoViewHolder viewHolder);
    }

    public List<Object> getDataList() {
        return dataList;
    }
}
