package com.ali.latte.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;

import com.ali.latte.R;
import com.ali.latte.glide.GlideApp;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by 澄鱼 on 2018/5/5.
 */

public class ImageHolerd implements Holder<String>{

    private AppCompatImageView mImageView = null;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        GlideApp.with(context)
                .load(data)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(mImageView);
    }
}
