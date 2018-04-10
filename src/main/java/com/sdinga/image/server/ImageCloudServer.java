package com.sdinga.image.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.sdinga.image.Enum.MainConstants;
import com.sdinga.image.bean.Config;
import com.sdinga.image.bean.ImageCloud;
import com.sdinga.image.config.ImageCloudConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ImageCloudServer implements InitializingBean {

    private static Table<String, Integer, ImageCloud> imageCloudTable = HashBasedTable.create();
    private static Map<String, List<Integer>> weightList = Maps.newHashMap();

    @Resource
    private ImageCloudConfig imageCloudConfig;

    /**
     * 获取所有的配置image文件
     *
     * @return list imageCloud
     */
    public List<ImageCloud> getImageClouds() {
        return Lists.newArrayList(imageCloudTable.values());
    }

    /**
     * 通过db 获取 权限id队列
     *
     * @param db 组
     * @return list
     */
    private List<Integer> getWeightList(String db) {
        return MapUtils.getObject(weightList, db, Lists.newArrayList());
    }

    /**
     * *********************** 重要 ***********************
     * 通过 权重获取仓库信息
     * *********************** 重要 ***********************
     *
     * @return 图片仓库配置信息
     */
    public ImageCloud getImageCloudByWeight(String db) {
        Random random = new Random();
        List<Integer> imageCloudList = getWeightList(db);
        if (CollectionUtils.isNotEmpty(imageCloudList)) {
            Integer id = imageCloudList.get(random.nextInt(imageCloudList.size()));
            return getImageCloudMapId().get(id);
        }
        return null;
    }

    public ImageCloud getImageCloudByWeight() {
        return getImageCloudByWeight(MainConstants.DEFAULT_DB);
    }


    /**
     * 获取 db 下所有的配置文件
     *
     * @param db db
     * @return 相同类型的bucket
     */
    public Map<Integer, ImageCloud> getImageCloudTableByDb(String db) {
        Map<String, Map<Integer, ImageCloud>> mapMap = imageCloudTable.rowMap();
        return mapMap.get(db);
    }

    public Map<Integer, ImageCloud> getImageCloudTableByDb() {
        return getImageCloudTableByDb(MainConstants.DEFAULT_DB);
    }

    public Map<String, Map<Integer, ImageCloud>> getImageCloudTableMap() {
        return imageCloudTable.rowMap();
    }

    public Table<String, Integer, ImageCloud> getImageCloudTable() {
        return imageCloudTable;
    }


    /**
     * 通过 Id 获取仓库信息
     *
     * @return 图片仓库配置信息
     */
    public ImageCloud getImageCloudById(Integer id) {
        return getImageCloudMapId().get(id);
    }


    /**
     * 获取 id - imageCloud
     *
     * @return key id, v imageCloud
     */
    public Map<Integer, ImageCloud> getImageCloudMapId() {
        Map<Integer, ImageCloud> imageCloudMap = Maps.newHashMap();
        for (ImageCloud imageCloud : getImageClouds()) {
            imageCloudMap.put(imageCloud.getId(), imageCloud);
        }
        return imageCloudMap;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        String configString = imageCloudConfig.getImageCloud();
        if (StringUtils.isNotEmpty(configString)) {
            // 解析封装参数
            JSONObject object = JSONObject.parseObject(configString);
            JSONArray jsonArray = object.getJSONArray("imagecloud");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject bean = jsonArray.getJSONObject(i);
                ImageCloud imageCloud = new ImageCloud();
                Integer id = bean.getInteger("id");
                imageCloud.setId(id);
                String db = bean.getString("db");
                imageCloud.setDb(db);
                Integer weight = bean.getInteger("weight");
                imageCloud.setWeight(weight);

                JSONObject configObject = bean.getJSONObject("config");
                Config config = new Config();
                config.setAk(configObject.getString("ak"));
                config.setSk(configObject.getString("sk"));
                config.setTable(configObject.getString("table"));
                config.setAccount(configObject.getString("account"));
                config.setBaseUrl(configObject.getString("baseUrl"));
                config.setZone(configObject.getString("zone"));
                imageCloud.setConfig(config);
                imageCloudTable.put(db, id, imageCloud);
            }
            // 初始化 权重
            Map<String, Map<Integer, ImageCloud>> dbKeyMap = imageCloudTable.rowMap();
            if (MapUtils.isNotEmpty(dbKeyMap)) {
                for (String dbKey : imageCloudTable.rowKeySet()) {
                    Map<Integer, ImageCloud> imageCloudMap = dbKeyMap.get(dbKey);
                    List<Integer> idWeight = Lists.newArrayList();
                    for (ImageCloud imageCloud : Lists.newArrayList(imageCloudMap.values())) {
                        for (Integer j = 0; j < imageCloud.getWeight(); j++) {
                            idWeight.add(imageCloud.getId());
                        }
                    }
                    weightList.put(dbKey, idWeight);
                }
            }
        }
    }
}

