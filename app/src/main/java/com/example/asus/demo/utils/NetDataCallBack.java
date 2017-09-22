package com.example.asus.demo.utils;


public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon,String str);
}
