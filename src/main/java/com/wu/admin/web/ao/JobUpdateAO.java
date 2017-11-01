package com.wu.admin.web.ao;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 26 下午3:22
 *
 * @author :  wubo
 * @version :  1.0.0
 */
public class JobUpdateAO {
    private Integer id;

    private String newCron;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewCron() {
        return newCron;
    }

    public void setNewCron(String newCron) {
        this.newCron = newCron;
    }

    @Override
    public String toString() {
        return "JobUpdateAO{" +
                "id=" + id +
                ", newCron='" + newCron + '\'' +
                '}';
    }
}
