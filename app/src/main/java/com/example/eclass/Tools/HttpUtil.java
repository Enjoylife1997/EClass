package com.example.eclass.Tools;

import com.example.eclass.tableClass.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Enjoy life on 2017/10/7.
 */
/*
   将通用的网络操作提取到一个类里，并提供静态方法
   发送请求
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);//OkHttp的enqueue()方法内部会自动开好子线程

    }

}
