package com.ali.latte.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ali.latte.delegates.LatteDelagate;

import java.util.EventListener;

/**
 * Created by 澄鱼 on 2018/5/1.
 * 底部导航栏 --- 具体每一个页面fragment
 */

public abstract class BottomItemDelegate extends LatteDelagate implements View.OnKeyListener{
    private final static int EXIT_TIME = 2000;
    private long mExit_time =  0;

    /* 重写导航栏Item页面的返回键监听逻辑 */
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExit_time) > EXIT_TIME) {
                Toast.makeText(getProxyActivity(), "双击退出", Toast.LENGTH_LONG).show();
                mExit_time = System.currentTimeMillis();
            } else {
                _mActivity.finish();
                if (mExit_time != 0) {
                    mExit_time = 0;
                }
            }
        }
        return true;
    }
}
