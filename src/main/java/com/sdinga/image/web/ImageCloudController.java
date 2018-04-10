package com.sdinga.image.web;

import com.alibaba.fastjson.JSONObject;
import com.sdinga.image.bean.Result;
import com.sdinga.image.server.QiniuyunServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

@RestController
public class ImageCloudController {

    @Resource
    private QiniuyunServer qiniuyunServer;

    @RequestMapping("/image/cloud/getToken/db")
    public Result getToken(@RequestParam(value = "db", required = false) String db) {
        Result resultCode = new Result();
        String token = qiniuyunServer.getToken(db);
        JSONObject object = new JSONObject();
        object.put("token", token);
        object.put("db", db);
        resultCode.setData(object);
        return resultCode;
    }

    @RequestMapping("/image/cloud/getToken/id")
    public Result getTokenById(@RequestParam(value = "cloudId", required = false) Integer cloudId) {
        Result resultCode = new Result();
        String token = qiniuyunServer.getToken(cloudId);
        JSONObject object = new JSONObject();
        object.put("token", token);
        object.put("cloudId", cloudId);
        resultCode.setData(object);
        return resultCode;
    }

    @RequestMapping("test")
    public void test() {
        File file = new File("/home/xiong/图片/1231.jpg");
        for (int i = 0; i < 5; i++) {
            System.out.println(qiniuyunServer.upload(file, "nihaowoshixinren" + i, "db3"));
        }
    }
}
