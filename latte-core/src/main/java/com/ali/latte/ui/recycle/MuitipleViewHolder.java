package com.ali.latte.ui.recycle;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by 澄鱼 on 2018/5/5.
 */

public class MuitipleViewHolder extends BaseViewHolder{

    protected MuitipleViewHolder(View view) {
        super(view);
    }
    public static MuitipleViewHolder create(View view) {
        return new MuitipleViewHolder(view);
    }

}
