package com.sdinga.image.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sdinga.image.bean.Config;
import com.sdinga.image.bean.ImageCloud;
import com.sdinga.image.config.ImageCloudConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ImageCloudServer implements InitializingBean {

    private static List<ImageCloud> imageClouds = Lists.newArrayList();
    private static List<Integer> weightList = Lists.newArrayList();

    @Resource
    private ImageCloudConfig imageCloudConfig;

    public List<ImageCloud> getImageClouds() {
        return imageClouds;
    }

    public Map<Integer, ImageCloud> getImageCloudMap() {
        Map<Integer, ImageCloud> imageCloudMap = Maps.newHashMap();
        for (ImageCloud imageCloud : imageClouds) {
            imageCloudMap.put(imageCloud.getId(), imageCloud);
        }
        return imageCloudMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        String configString = imageCloudConfig.getImageCloud();
        if (StringUtils.isNotEmpty(configString)) {
            JSONObject object = JSONObject.parseObject(configString);
            JSONArray jsonArray = object.getJSONArray("imagecloud");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject bean = jsonArray.getJSONObject(i);
                ImageCloud imageCloud = new ImageCloud();
                Integer id = bean.getInteger("id");
                imageCloud.setId(id);
                Integer weight = bean.getInteger("weight");
                for (Integer integer = 0; integer < weight; integer++) {
                    weightList.add(id);
                }
                imageCloud.setWeight(weight);
                Config config = new Config();
                config.setAk(bean.getString("ak"));
                config.setSk(bean.getString("sk"));
                config.setDb(bean.getString("db"));
                config.setTable(bean.getString("table"));
                config.setBaseUrl(bean.getString("baseUrl"));
                imageCloud.setConfig(config);
                imageClouds.add(imageCloud);
            }
        }
    }
}
