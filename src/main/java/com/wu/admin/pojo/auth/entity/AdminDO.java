package com.wu.admin.pojo.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author : wubo
 *         Date : 2017/10/10
 *         Time : 下午5:39
 */
@Entity
@Table(name = "auth_admin")
@DynamicUpdate(value = true)
@DynamicInsert(value = true)
public class AdminDO implements Serializable {
    @Id
    @Column(name = "uid")
    @GenericGenerator(name = "uid", strategy = "assigned")
    @GeneratedValue(generator = "uid")
    private String uid;

    @Column(name = "user_name")
    @NotEmpty(message = "账号不能为空")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "salt")
    private String salt;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "type")
    private Integer type;

    @Column(name = "edit_enable")
    private Boolean editEnable;

    @Column
    @org.hibernate.annotations.CreationTimestamp
    private Date createdAt;

    @Column
    @org.hibernate.annotations.UpdateTimestamp
    private Date updatedAt;

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";

    /**
     * optional表示的是该属性是否允许为null,默认为true，即允许为null，一个管理员不可以没有角色，故此处设置optional=false；
     * 注意：如果延迟加载，必须设置为false。
     * fetch表示的是加载策略/读取策略,有EAGER和LAZY两种,分别表示主支抓取和延迟加载,默认为EAGER；
     * cascade：表示默认的级联操作方式。有ALL(all)、PERSIST(persist)、MERGE(merge)、REFRESH(refresh)和REMVOE(remove)中的若干组合，默认为无级联操作。
     * CascadeType各个值的含义：
     * CascadeType.REFRESH(refresh):表示的是级联刷新，当多个用户同时操作一个实体，为了用户取到的数据是实时的，在用实体中的数据之前就可以调用一下refresh()方法！
     * CsacadeType.REMOVE(remove):级联删除，当调用remove()方法删除Order实体时会先级联删除OrderItem的相关数据
     * CascadeType.MERGE(merge):级联更新，当调用了Merger()方法，如果Order中的数据改变了会相应的更新到OrderItem中的数据
     * CascadeType.ALL：包含以上所有级联属性。。上面的哈
     * (注：以上几种级联操作，只能实在满足数据库的约束时才能生效，比如上边的Order和OrderItem存在主外键关联，所以执行REMOVE()方法时是不能实现级联删除的)
     * CascadeType.PERSIST(persist)：级联保存，当调用了Persist()方法，会级联保存相应的数据。
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "role_id")
    private RoleDO role;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getEditEnable() {
        return editEnable;
    }

    public void setEditEnable(Boolean editEnable) {
        this.editEnable = editEnable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSort() {
        if (StringUtils.isEmpty(sort)) {
            return "createdAt";
        } else {
            return sort;
        }
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        if (StringUtils.isEmpty(sort)) {
            return "desc";
        } else {
            return order;
        }
    }

    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 密码盐.
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.userName + this.salt;
    }

    public RoleDO getRole() {
        return role;
    }

    public void setRole(RoleDO role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "uid='" + uid + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", role=" + role +
                '}';
    }
}
