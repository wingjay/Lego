package com.wingjay.lego;

/**
 * DuplicateLegoKeyException
 *
 * @author wingjay
 */
public class DuplicateLegoKeyException extends IllegalArgumentException {
    public DuplicateLegoKeyException(String key, String viewHolder1, String viewHolder2) {
        super(String.format("Duplicate LegoViewHolder id: %s, ViewHolder 1: %s, ViewHolder 2: %s",
            key, viewHolder1, viewHolder2));
    }
}
