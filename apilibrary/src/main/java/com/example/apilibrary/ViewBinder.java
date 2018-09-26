package com.example.apilibrary;

/**
 * @author : Alex
 * @version : V 2.0.0
 * @date : 2018/09/17
 */
public interface ViewBinder<T> {

    void bindView(T host, Object o,ViewFinder viewFinder);

    void unBindView(T host);
}
