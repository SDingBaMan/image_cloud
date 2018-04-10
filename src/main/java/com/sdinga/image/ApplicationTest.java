package com.sdinga.image;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ApplicationTest {
    public static void main(String[] args) {
        JSONObject object = JSONObject.parseObject("{\"imagecloud\":[{\"id\":111,\"db\":\"db1\",\"config\":{\"table\":\"table1\",\"ak\":\"ak\",\"sk\":\"sk\",\"baseUrl\":\"url\"},\"weight\":1},{\"id\":222,\"db\":\"db2\",\"config\":{\"table\":\"table2\",\"ak\":\"ak\",\"sk\":\"sk\",\"baseUrl\":\"url\"},\"weight\":1},{\"id\":333,\"db\":\"db3\",\"config\":{\"table\":\"table3\",\"ak\":\"ak\",\"sk\":\"sk\",\"baseUrl\":\"url\"},\"weight\":2}]}");
        JSONArray jsonArray = object.getJSONArray("imagecloud");
    }
}
