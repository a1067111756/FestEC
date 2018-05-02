package com.ali.latte.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;


/**
 * Created by 澄鱼 on 2018/3/14.
 * 使用静态内部类实现线程安全的懒汉单利模式
 */

public class Configurator {

    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();



    private Configurator() {

        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER.name(), HANDLER);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    // 获取Configurator单例
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public  final void configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

    // 配置项检查
    private void checkConfiguration() {
        final boolean isReady = (boolean)LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw  new RuntimeException("Configuration is not ready, call configure");
        }
    }

    // 获取配置对象
    final HashMap<Object, Object> getConfigurations() {
        return Configurator.LATTE_CONFIGS;
    }

    // 字体图标初始化
    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 0; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    // 获取配置项
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigKeys> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }

    // 配置api
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    // 加入新的字体图标
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    // 配置拦截器
    public final Configurator withInterceptors(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    // 配置拦截器
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTORS, INTERCEPTORS);
        return this;
    }

    // 配置微信APP ID
    public final Configurator withWeChatAppId(String appId) {
        LATTE_CONFIGS.put(ConfigKeys.WX_CHAT_APP_ID, appId);
        return this;
    }

    // 配置微信APP Secret
    public final Configurator withWeChatAppSceret(String appSceret) {
        LATTE_CONFIGS.put(ConfigKeys.WX_CHAT_APP_SECRET, appSceret);
        return this;
    }

    // Application全局上下文
    public final Configurator withActivity(Activity activity) {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }
}
