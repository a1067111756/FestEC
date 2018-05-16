package com.ali.latte.ec.main.sort;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.ali.latte.delegates.LatteDelagate;
import com.ali.latte.ec.R;
import com.ali.latte.ec.main.sort.content.ContentDelegate;
import com.ali.latte.ui.recycle.ItemType;
import com.ali.latte.ui.recycle.MuitipleViewHolder;
import com.ali.latte.ui.recycle.MultipleFields;
import com.ali.latte.ui.recycle.MultipleItemEntity;
import com.ali.latte.ui.recycle.MultipleRecyleAdatper;
import java.util.List;


/**
 * Created by 澄鱼 on 2018/5/11.
 */

public class SortRecyclerAdapter extends MultipleRecyleAdatper{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    public SortRecyclerAdapter(List<MultipleItemEntity> data, SortDelegate mDelegate) {
        super(data);
        this.DELEGATE = mDelegate;

        // 添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MuitipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);

        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isChecked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            // 还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyDataSetChanged();

                            // 更新选中的Item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            // 切换左侧content视图
                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if (!isChecked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name, text);
                break;

            default:
                break;
        }
    }

    private void showContent(int contentId) {
        final ContentDelegate delegate = ContentDelegate.newInstanc(contentId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate) {
        final LatteDelagate contentDelegate = DELEGATE.findChildFragment(ContentDelegate.class);
        if (contentDelegate != null) {
            contentDelegate.replaceFragment(delegate, false);
        }
    }
}
