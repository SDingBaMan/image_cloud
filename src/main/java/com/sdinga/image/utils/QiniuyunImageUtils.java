package com.sdinga.image.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sdinga.image.Enum.ZoneQ;
import com.sdinga.image.bean.ImageCloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 特殊的工具类,给ImageCofig对象使用的特殊
 */
public class QiniuyunImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiniuyunImageUtils.class);

    private final ImageCloud imageCloud;

    public QiniuyunImageUtils(ImageCloud imageCloud) {
        this.imageCloud = imageCloud;
    }

    public static QiniuyunImageUtils getQiniuyunUtils(ImageCloud imageCloud) {
        return new QiniuyunImageUtils(imageCloud);
    }

    private Auth authInstance() {
        return Auth.create(getAk(), getSk());
    }

    /**
     * 普通上传 get token
     *
     * @return token
     */
    public String getUpToken() {
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize),\"mimeType\":$(mimeType)}");
        return authInstance().uploadToken(getBuckename(), null, 3600L, putPolicy);
    }

    /**
     * 更新上传 get token
     *
     * @return update token
     */
    private String getUpTokenUpdate(String key) {
        return authInstance().uploadToken(getBuckename(), key);
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @param key  file key
     * @return info
     */
    public String upload(byte[] file, String key) {
        Configuration cfg = new Configuration(getZone());
        UploadManager uploadManager = new UploadManager(cfg);// 创建上传对象
        try {
            Response res = uploadManager.put(file, key, getUpToken());
            return res.bodyString();
        } catch (QiniuException e) {
            LOGGER.error("上传七牛云异常:", e);
        }
        return "";
    }

    public String upload(File file, String key) {
        Configuration cfg = new Configuration(getZone());
        UploadManager uploadManager = new UploadManager(cfg);// 创建上传对象
        try {
            Response res = uploadManager.put(file, key, getUpToken());
            return res.bodyString();
        } catch (QiniuException e) {
            LOGGER.error("上传七牛云异常:", e);
        }
        return "";
    }

    /**
     * 删除一个文件
     */
    public void deleteImg(String key) {
        // 实例化一个 bucketManage
        Configuration cfg = new Configuration(getZone());
        BucketManager bucketManager = new BucketManager(authInstance(), cfg);
        try {
            bucketManager.delete(getBuckename(), key);
        } catch (QiniuException e) {
            LOGGER.error("qiniu_delete_Exception", e);
        }
    }

    /**
     * 通过 key 获取 hash 值
     */
    public String getHashToKey(String key) {
        Configuration cfg = new Configuration(getZone());
        BucketManager bucketManager = new BucketManager(authInstance(), cfg);
        try {
            FileInfo info = bucketManager.stat(getBuckename(), key);
            return info.hash;
        } catch (QiniuException e) {
            LOGGER.error("文件删除错误", e);
        }
        return "";
    }

    // 无损压缩 图片文件
    public static String handleImageSizeSlim(String imageUrl) {
        return imageUrl + "?imageslim";
    }

    public String getBuckename() {
        return this.imageCloud.getConfig().getTable();
    }

    public String getAccount() {
        return this.imageCloud.getConfig().getAccount();
    }

    private String getAk() {
        return this.imageCloud.getConfig().getAk();
    }

    private String getSk() {
        return this.imageCloud.getConfig().getSk();
    }

    private String getZoneName() {
        return this.imageCloud.getConfig().getZone();
    }

    private Zone getZone() {
        return ZoneQ.getZone(getZoneName()).getZone();
    }

    public String getBaseUrl() {
        return this.imageCloud.getConfig().getBaseUrl();
    }

}
