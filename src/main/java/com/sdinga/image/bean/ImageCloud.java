package com.sdinga.image.bean;

import com.sdinga.image.Enum.MainConstants;

public class ImageCloud {
    public ImageCloud() {
    }

    /**
     * id
     */
    private Integer id;
    /**
     * 相当于数据库，用于处理一个项目下 同一个账户多个 bucket （table） 空间
     */
    private String db = MainConstants.DEFAULT_DB;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 配置参数
     */
    private Config config;

    public Integer getId() {
        return id;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
