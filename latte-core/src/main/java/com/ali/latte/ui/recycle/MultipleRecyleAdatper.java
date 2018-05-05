package com.ali.latte.ui.recycle;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.ali.latte.R;
import com.ali.latte.glide.GlideApp;
import com.ali.latte.ui.banner.BannerCreator;
import com.ali.latte.ui.banner.BannerHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;

import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 澄鱼 on 2018/5/5.
 */

public class MultipleRecyleAdatper extends BaseMultiItemQuickAdapter<MultipleItemEntity, MuitipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener {


    private boolean mIsInitBanner = false;
    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected MultipleRecyleAdatper(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    @Override
    protected void convert(MuitipleViewHolder holder, MultipleItemEntity entity) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImage;

        switch (holder.getItemViewType()) {
            case ItemType.TEXT:
                text = entity.getField(MultipleFields.TEXT);
                holder.setText(R.id.text_sigle, text);
                break;

            case ItemType.IMAGE:
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into((ImageView) holder.getView(R.id.image_sigle));
                break;

            case ItemType.TEXT_IMAGE:
                text = entity.getField(MultipleFields.TEXT);
                imageUrl = entity.getField(MultipleFields.IMAGE_URL);
                holder.setText(R.id.tv_multiple, text);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(REQUEST_OPTIONS)
                        .into((ImageView) holder.getView(R.id.img_multiple));
                break;

            // 确保banner只加载一次,防止重复加载
            case ItemType.BANNER:
                if (!mIsInitBanner) {
                    bannerImage = entity.getField(MultipleFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = holder.getView(R.id.banner_recycle_item);
                    BannerCreator.setDefault(convenientBanner, bannerImage, this);
                    mIsInitBanner = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    protected MuitipleViewHolder createBaseViewHolder(View view) {
        return MuitipleViewHolder.create(view);
    }

    public static MultipleRecyleAdatper create(List<MultipleItemEntity> data) {
        return new MultipleRecyleAdatper(data);
    }

    public static MultipleRecyleAdatper create(DataConverter converter) {
        return new MultipleRecyleAdatper(converter.convert());
    }

    private void init() {
        // 设置不同的布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multiple_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multiple_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multiple_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        //打开加载动画效果
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    public void onItemClick(int position) {

    }
}
