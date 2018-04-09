package com.sdinga.image.server;

import com.sdinga.image.bean.ImageCloud;
import com.sdinga.image.bean.ImageResult;
import com.sdinga.image.utils.QiniuyunImageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
public class QiniuyunServer {

    @Resource
    private ImageCloudServer imageCloudServer;

    public String upload(byte[] file, String key) {
        return QiniuyunImageUtils.getQiniuyunUtils(imageCloudServer.getImageCloudByWeight()).upload(file, key);
    }

    public String upload(File file, String key) {
        return QiniuyunImageUtils.getQiniuyunUtils(imageCloudServer.getImageCloudByWeight()).upload(file, key);
    }

    /**
     * delete
     *
     * @param imageCloudId 自定义的id
     * @param key          file key
     */
    public void delete(Integer imageCloudId, String key) {
        QiniuyunImageUtils.getQiniuyunUtils(imageCloudServer.getImageCloudById(imageCloudId)).deleteImg(key);
    }

    private ImageResult packageResult(ImageCloud imageCloud) {
        ImageResult imageResult = new ImageResult();
        imageResult.setId(imageCloud.getId());
        imageResult.setAccount(imageCloud.getConfig().getAccount());
        imageResult.setTable(imageCloud.getConfig().getTable());
        imageResult.setBaseUrl(imageCloud.getConfig().getBaseUrl());
        return imageResult;
    }
}
