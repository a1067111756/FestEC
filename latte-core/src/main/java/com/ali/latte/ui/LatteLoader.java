package com.ali.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.ali.latte.R;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.Utils;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;

/**
 * Created by 澄鱼 on 2018/3/21.
 */

public class LatteLoader {

    private final static int LOADER_SIZE_SCALE = 8;
    private final static int LOADER_SIZE_OFFSET = 10;

    // 通过创建列表，将开启的loader一次加入列表维护， 在不需要的时候取全不遍历关闭，
    // 会省掉许多同步问题
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    // 展示
    public static void showLoding (String type, Context context) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreator.create(type, context);
        dialog.setContentView(avLoadingIndicatorView);

        // 设置dialog的属性
        int deviceWidth = ScreenUtils.getScreenWidth();
        int deviceHeigth = ScreenUtils.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
            layoutParams.width = deviceWidth / LOADER_SIZE_SCALE;
            layoutParams.height = deviceHeigth / LOADER_SIZE_SCALE;
            layoutParams.height = layoutParams.height + deviceHeigth / LOADER_SIZE_OFFSET;
            layoutParams.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    // 默认样式
    public static void showLoding (Context context) {
        showLoding(DEFAULT_LOADER, context);
    }

    // 默认样式
    public static void showLoding (Context context, Enum<LoaderStyle> type) {
        showLoding(type.name(), context);
    }

    // 取消
    public static void cancelLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }

}
