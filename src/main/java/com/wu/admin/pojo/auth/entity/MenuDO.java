package com.wu.admin.pojo.auth.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/10
 * Time : 下午5:42
 */
@Entity
@Table(name = "auth_menu")
public class MenuDO  implements Serializable {

    @Id
    @Column(name = "menu_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String menuId;

    @NotEmpty(message = "菜单名称不能为空")
    private String menuName;

    @Column(columnDefinition = "enum('menu','auth','button')")
    private String menuType;

    @NotEmpty(message = "菜单URL不能为空")
    @Column(name = "menu_url")
    private String menuUrl;

    @NotEmpty(message = "菜单标识不能为空")
    @Column
    private String menuCode;

    @NotEmpty(message = "父类ID不能为空")
    @Column
    private String parentId;

    @Column
    private Integer listOrder;

    @Column
    private Boolean editEnable;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Transient
    private List<MenuDO> children;

    @Transient
    private List<RoleDO> roleList;

    @Transient
    private List<AdminDO> adminList;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getListOrder() {
        return listOrder;
    }

    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }

    public Boolean getEditEnable() {
        return editEnable;
    }

    public void setEditEnable(Boolean flag) {
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

    public List<MenuDO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDO> children) {
        this.children = children;
    }

    public List<RoleDO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleDO> roleList) {
        this.roleList = roleList;
    }

    public List<AdminDO> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<AdminDO> adminList) {
        this.adminList = adminList;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuType='" + menuType + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", parentId='" + parentId + '\'' +
                ", listOrder=" + listOrder +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", children=" + children +
                ", roleList=" + roleList +
                ", adminList=" + adminList +
                '}';
    }


}