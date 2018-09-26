package com.example.apilibrary;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */
public class BufferViewBinder {

    private static final ActivityViewFinder FINDER = new ActivityViewFinder(); // 创建ActivityViewFinder实例
    private static final Map<String, ViewBinder> BINDER_MAP = new HashMap<>();  // 创建缓存Map

    public static void bind(Activity activity) {
        bind(activity, activity, FINDER);
    } // 提供的绑定接口

    private static void bind(Object host, Object o, ViewFinder finder) {
        String className = host.getClass().getName();  // 获取绑定的类名
        ViewBinder viewBinder = BINDER_MAP.get(className);  // 查看是否有缓存
        if (viewBinder == null) {
            try {
                Class<?> aClass = Class.forName(className + "$$ViewBinder"); // 根据类名反射获取自动生成的类
                try {
                    viewBinder = (ViewBinder) aClass.newInstance(); // 创建自动生成的类的实例
                    BINDER_MAP.put(className, viewBinder);  // 添加到缓存
                    if (viewBinder != null) {
                        viewBinder.bindView(host, o, finder); // 调用绑定的方法
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static void unBind(Object host) {
        String classname = host.getClass().getName();
        ViewBinder viewBinder = BINDER_MAP.get(classname);
        if (viewBinder != null) {
            viewBinder.unBindView(host); // 解除绑定
        }
    }
}
