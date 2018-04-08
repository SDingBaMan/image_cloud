package com.sdinga.image.bean;

public class ImageCloud {
    public ImageCloud() {
    }

    private Integer id;
    private Integer weight;
    private Config config;



    public Integer getId() {
        return id;
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
