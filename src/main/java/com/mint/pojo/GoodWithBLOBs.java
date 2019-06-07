package com.mint.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class GoodWithBLOBs extends Good {
    private String content;

    private String cover;

    private String picture;

    public GoodWithBLOBs(String gid, String title, Integer isused, String ndegree, BigDecimal price, Date ptime, Integer pcount, String uid, Integer issaled, String content, String cover, String picture) {
        super(gid, title, isused, ndegree, price, ptime, pcount, uid, issaled);
        this.content = content;
        this.cover = cover;
        this.picture = picture;
    }

    public GoodWithBLOBs() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }
}