package com.wu.admin.pojo.auth.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Description : Created by intelliJ IDEA
 * @author  : wubo
 * @version  : 2017/10/11上午9:57
 *
 */

@Entity
@Table(name = "auth_role_menu")
public class RoleMenuDO implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "menu_id")
    private String menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "RoleMenuDO{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}
