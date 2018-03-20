package com.ali.latte.app;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 澄鱼 on 2018/3/14.
 * 使用静态内部类实现线程安全的懒汉单利模式
 */

public class Configurator {

    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();


    private Configurator() {
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    // 获取Configurator单例
    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public  final void configure() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    // 配置项检查
    private void checkConfiguration() {
        final boolean isReady = (boolean)LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw  new RuntimeException("Configuration is not ready, call configure");
        }
    }

    // 获取配置对象
    final HashMap<String, Object> getConfigurations() {
        return Configurator.LATTE_CONFIGS;
    }

    // 配置api
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
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

    // 加入新的字体图标
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    // 获取配置项
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }

}
