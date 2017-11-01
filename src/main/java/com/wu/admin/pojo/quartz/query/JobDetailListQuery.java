package com.wu.admin.pojo.quartz.query;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 27 下午5:19
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobDetailListQuery {

    private Integer pageSize;

    private Integer pageIndex;

    private String description;

    private Integer state;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
