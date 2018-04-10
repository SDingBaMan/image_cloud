package com.sdinga.image.bean;

import java.util.List;

public class Result {
    private Integer code;
    private String message;
    private Object data;
    private ImageResult imageResult;
    private List<ImageResult> imageResultList;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ImageResult getImageResult() {
        return imageResult;
    }

    public void setImageResult(ImageResult imageResult) {
        this.imageResult = imageResult;
    }

    public List<ImageResult> getImageResultList() {
        return imageResultList;
    }

    public void setImageResultList(List<ImageResult> imageResultList) {
        this.imageResultList = imageResultList;
    }
}
