package com.ali.latte.ec.mvp;

/**
 * Created by 澄鱼 on 2018/4/8.
 * 基础View, 持有presenter. 可以将加载的loading，以及加载错误页面，
 * 加载失败页面等操作放在BaseView里面，这是每个View都会有的
 */

public interface BaseView<T> {
    /**
     * 使用fragment作为view时，将activity中的presenter传递给fragment
     * @param presenter
     */
    void setPresenter(T presenter);
}
