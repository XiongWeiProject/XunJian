package com.zhhome.xunjian.model;

import org.devio.takephoto.model.TImage;

import java.io.Serializable;
import java.util.List;

public class VideoJumpModel implements Serializable {
    private String serial_id;
    private String mobile;
    private String action;
    private String filevideo;
    private String isimage;
    private String nul;
    private List<TImage> images;

    public String getNul() {
        return nul;
    }

    public void setNul(String nul) {
        this.nul = nul;
    }

    public String getSerial_id() {
        return serial_id;
    }

    public void setSerial_id(String serial_id) {
        this.serial_id = serial_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFilevideo() {
        return filevideo;
    }

    public void setFilevideo(String filevideo) {
        this.filevideo = filevideo;
    }

    public String getIsimage() {
        return isimage;
    }

    public void setIsimage(String isimage) {
        this.isimage = isimage;
    }

    public List<TImage> getImages() {
        return images;
    }

    public void setImages(List<TImage> images) {
        this.images = images;
    }
}
