package com.ali.latte.ec.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.ali.latte.ec.R;
import com.ali.latte.utils.storage.LattePreference;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by 澄鱼 on 2018/3/24.
 */

public class BannerHolder implements Holder<Integer> {

    private View itemView;

    @Override
    public View createView(Context context) {
        itemView = LayoutInflater.from(context).inflate(R.layout.layout_gaide, null);
        return itemView;
    }

    @Override
    public void UpdateUI(Context context, final int position, Integer data) {
         RelativeLayout relativeLayout = (RelativeLayout)itemView.findViewById(R.id.wapper);
         relativeLayout.setBackgroundResource(data);

         if (data == R.drawable.launcher_last) {
             AppCompatButton button =  relativeLayout.findViewById(R.id.but_gaide_start);
             button.setVisibility(View.VISIBLE);
             button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     LattePreference.setAppFlag(LauncherTag.IS_FIRST_LAUNCHER_APP.name(), false);
                 }
             });
         }
    }

}
