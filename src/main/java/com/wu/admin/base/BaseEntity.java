package com.wu.admin.base;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:39
 */
public class BaseEntity implements Serializable{
    @Transient
    private Integer offset = 0;

    @Transient
    private Integer limit = 10;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
