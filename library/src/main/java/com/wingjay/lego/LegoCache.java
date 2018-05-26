package com.wingjay.lego;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.wingjay.lego.inner.LViewHolderFactory;
import com.wingjay.lego.inner.LegoLogger;
import com.wingjay.lego.inner.LegoViewHolderDelegate;

/**
 * LegoCache: ViewHolder cache. Pre inflate ViewHolder before data fetched from server.
 *
 * Note:
 *  1. No need to inflate viewHolder in worker thread, due to that cost more time to switch thread.
 *
 * @author wingjay
 */
@SuppressWarnings("WeakerAccess")
public class LegoCache {

    private Context context;
    private ViewGroup listView;
    private final Map<Class, Integer> cacheCapacityMap;
    private final Map<Class, Stack<LegoViewHolderDelegate>> viewHolderStackCache;

    private LegoCache(ViewGroup listView,
                      @NonNull Map<Class, Integer> cacheCapacityMap) {
        this.context = listView.getContext();
        this.listView = listView;
        this.cacheCapacityMap = cacheCapacityMap;
        this.viewHolderStackCache = new HashMap<>(cacheCapacityMap.size());

        buildCache();
    }

    /**
     * Pop first cached viewHolder from stack.
     *
     * @return the viewHolder used by {@link LegoRecyclerAdapter}, maybe null when stack is empty.
     */
    @Nullable
    LegoViewHolderDelegate acquireCachedViewHolder(Class<?> viewHolderClass) {
        Stack<LegoViewHolderDelegate> stack = viewHolderStackCache.get(viewHolderClass);
        if (stack != null && !stack.empty()) {
            return stack.pop();
        }

        return null;
    }

    private void buildCache() {
        for (Map.Entry<Class, Integer> entry : cacheCapacityMap.entrySet()) {
            try {
                viewHolderStackCache.put(entry.getKey(), buildViewHolderStack(entry.getKey(), entry.getValue()));
            } catch (Exception e) {
                LegoLogger.e("LegoCache build Failure. " + e.getMessage());
            }
        }
    }

    private Stack<LegoViewHolderDelegate> buildViewHolderStack(Class<?> viewHolderClass,
                                                                                                int initialCapacity) throws Exception {
        LegoLogger.i("Start build ViewHolder Stack for " + viewHolderClass.getSimpleName());
        Stack<LegoViewHolderDelegate> stack = new Stack<>();
        for (int i = 0; i < initialCapacity; i++) {
            ILegoViewHolder realViewHolder = LViewHolderFactory.buildViewHolder(viewHolderClass, context);
            stack.push(new LegoViewHolderDelegate(realViewHolder.initView(listView), realViewHolder));
            LegoLogger.i(String.format("create CacheViewHolder %s and push into Stack.",
                realViewHolder.getClass().getSimpleName()));
        }

        return stack;
    }

    public static class Builder {
        private ViewGroup listView;
        private Map<Class, Integer> cacheCapacityMap;
        public Builder(ViewGroup listView) {
            this.listView = listView;
            this.cacheCapacityMap = new HashMap<>();
        }

        /**
         * PreCreate some cache instances of ViewHolderClass to display ui earlier without inflating again.
         * @param viewHolderClass viewHolder class you want to cache
         * @param capacity the number of viewHolder, which will be displayed in first screen.
         * @return Builder
         */
        public Builder cache(Class<?> viewHolderClass, int capacity) {
            cacheCapacityMap.put(viewHolderClass, capacity);
            return this;
        }

        public LegoCache build() {
            return new LegoCache(listView, cacheCapacityMap);
        }
    }

}
