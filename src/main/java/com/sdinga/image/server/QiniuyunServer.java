package com.sdinga.image.server;

import com.alibaba.fastjson.JSONObject;
import com.sdinga.image.Enum.MainConstants;
import com.sdinga.image.bean.ImageCloud;
import com.sdinga.image.bean.ImageResult;
import com.sdinga.image.bean.QiniuyunResult;
import com.sdinga.image.bean.Result;
import com.sdinga.image.utils.QiniuyunEtag;
import com.sdinga.image.utils.QiniuyunImageUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云 api 接口 对外提供访问
 */
@Service
public class QiniuyunServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuyunServer.class);

    @Resource
    private ImageCloudConfigServer imageCloudServer;

    public String upload(byte[] file, String key, String db) {
        ImageCloud imageCloud = imageCloudServer.getImageCloudByWeight(db);
        String result = QiniuyunImageUtils.getQiniuyunUtils(imageCloud).upload(file, key);
        return JSONObject.toJSONString(packageResult(imageCloud, packageImageQiniuyunResult(result)));
    }

    public String upload(File file, String key, String db) {
        ImageCloud imageCloud = imageCloudServer.getImageCloudByWeight(db);
        String result = QiniuyunImageUtils.getQiniuyunUtils(imageCloud).upload(file, key);
        return JSONObject.toJSONString(packageResult(imageCloud, packageImageQiniuyunResult(result)));
    }

    public String upload(InputStream file, String key, String db) throws IOException {
        ImageCloud imageCloud = imageCloudServer.getImageCloudByWeight(db);
        byte[] fileByte = IOUtils.toByteArray(file);
        String result = QiniuyunImageUtils.getQiniuyunUtils(imageCloud).upload(fileByte, key);
        return JSONObject.toJSONString(packageResult(imageCloud, packageImageQiniuyunResult(result)));
    }

    public String uploadDefaultKey(byte[] file, String db) {
        return upload(file, null, db);
    }

    public String uploadDefaultKey(File file, String db) throws IOException {
        return upload(file, null, db);
    }

    public String uploadDefaultKey(InputStream file, String db) throws IOException {
        byte[] fileByte = IOUtils.toByteArray(file);
        return upload(file, null, db);
    }

    public String upload(byte[] file) {
        String key = QiniuyunEtag.data(file);
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    public String upload(File file) throws IOException {
        String key = QiniuyunEtag.file(file);
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    public String upload(InputStream file) throws IOException {
        byte[] fileByte = IOUtils.toByteArray(file);
        String key = QiniuyunEtag.data(fileByte);
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    public String upload(byte[] file, String key) {
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    public String upload(File file, String key) {
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    public String upload(InputStream file, String key) throws IOException {
        return upload(file, key, MainConstants.DEFAULT_DB);
    }

    /**
     * 获取 上传 token
     *
     * @param db db
     * @return
     */
    public String getToken(String db) {
        ImageCloud imageCloud = imageCloudServer.getImageCloudByWeight(db);
        String token = QiniuyunImageUtils.getQiniuyunUtils(imageCloud).getUpToken();
        ImageResult imageResult = new ImageResult();
        imageResult.setTable(token);
        return JSONObject.toJSONString(packageResult(imageCloud, imageResult));
    }

    public String getToken() {
        return getToken(MainConstants.DEFAULT_DB);
    }

    public String getToken(Integer cloudId) {
        ImageCloud imageCloud = imageCloudServer.getImageCloudById(cloudId);
        String token = QiniuyunImageUtils.getQiniuyunUtils(imageCloud).getUpToken();
        ImageResult imageResult = new ImageResult();
        imageResult.setToken(token);
        return JSONObject.toJSONString(packageResult(imageCloud, imageResult));
    }


    /**
     * delete
     *
     * @param cloudId 自定义的id
     * @param key     file key
     */
    public void delete(Integer cloudId, String key) {
        QiniuyunImageUtils.getQiniuyunUtils(imageCloudServer.getImageCloudById(cloudId)).deleteImg(key);
    }

    private ImageResult packageImageQiniuyunResult(String result) {
        ImageResult imageResult = new ImageResult();
        if (StringUtils.isNotEmpty(result)) {
            try {
                QiniuyunResult qiniuyunResult = JSONObject.parseObject(result, QiniuyunResult.class);
                imageResult.setKey(qiniuyunResult.getKey());
                imageResult.setHashCode(qiniuyunResult.getHash());
                imageResult.setFsize(qiniuyunResult.getFsize());
            } catch (Exception e) {
                LOGGER.error("error_{}", e);
                imageResult.setMessage(result);
            }
        }
        return imageResult;
    }

    private Result packageResult(ImageCloud imageCloud, ImageResult imageResult) {
        Result resultObject = new Result();
        imageResult.setId(imageCloud.getId());
        imageResult.setDb(imageCloud.getDb());
        imageResult.setAccount(imageCloud.getConfig().getAccount());
        imageResult.setTable(imageCloud.getConfig().getTable());
        imageResult.setBaseUrl(imageCloud.getConfig().getBaseUrl());
        imageResult.setZone(imageCloud.getConfig().getZone());
        if (StringUtils.isNotEmpty(imageResult.getKey())) {
            imageResult.setUrl(imageCloud.getConfig().getBaseUrl() + imageResult.getKey());
        }
        resultObject.setImageResult(imageResult);
        return resultObject;
    }
}
