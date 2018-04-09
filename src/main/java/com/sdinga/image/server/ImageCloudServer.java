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
import java.util.Random;

@Service
public class ImageCloudServer implements InitializingBean {

    private static List<ImageCloud> imageClouds = Lists.newArrayList();
    private static List<Integer> weightList = Lists.newArrayList();

    @Resource
    private ImageCloudConfig imageCloudConfig;

    public List<ImageCloud> getImageClouds() {
        return imageClouds;
    }

    /**
     * 通过 权重获取仓库信息
     *
     * @return 图片仓库配置信息
     */
    public ImageCloud getImageCloudByWeight() {
        Random random = new Random();
        Integer id = weightList.get(random.nextInt());
        return getImageCloudMap().get(id);
    }

    /**
     * 通过 Id 获取仓库信息
     *
     * @return 图片仓库配置信息
     */
    public ImageCloud getImageCloudById(Integer id) {
        return getImageCloudMap().get(id);
    }

    public List<Integer> getWeightList() {
        return weightList;
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
                imageCloud.setDb(bean.getString("db"));

                JSONObject configObject = bean.getJSONObject("config");
                Integer weight = configObject.getInteger("weight");
                for (Integer j = 0; j < weight; j++) {
                    weightList.add(id);
                }
                imageCloud.setWeight(weight);
                Config config = new Config();
                config.setAk(configObject.getString("ak"));
                config.setSk(configObject.getString("sk"));
                config.setTable(configObject.getString("table"));
                config.setAccount(configObject.getString("account"));
                config.setBaseUrl(configObject.getString("baseUrl"));
                imageCloud.setConfig(config);
                imageClouds.add(imageCloud);
            }
        }
    }
}
