package com.wu.admin.utils;


import com.wu.admin.pojo.auth.entity.MenuDO;
import com.wu.admin.pojo.auth.entity.RoleMenuDO;

import java.util.*;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/12
 * Time : 下午5:16
 */
public class MenuTreeUtil {

    private List<MenuDO> nodes;

    private List<RoleMenuDO> checknodes;


    /**
     * 创建一个新的实例 Tree.
     *
     * @param nodes   将树的所有节点都初始化进来。
     */
    public MenuTreeUtil(List<MenuDO> nodes, List<RoleMenuDO> checknodes){
        this.nodes = nodes;
        this.checknodes = checknodes;
    }


    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<Map<String, Object>> buildTree(){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(MenuDO node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (Objects.equals(node.getParentId(), "0")) {
                //System.out.println("node"+node.getMenuName());
                Map<String, Object> map = buildTreeChildsMap(node);
                list.add(map);
            }
        }
        return list;
    }


    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<Map<String, Object>> buildTreeChilds(MenuDO node){
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        List<MenuDO> childNodes = getChilds(node);
        for(MenuDO childNode : childNodes){
            //System.out.println("childNode"+childNode.getMenuName());
            Map<String, Object> map = buildTreeChildsMap(childNode);
            list.add(map);
        }
        return list;
    }

    /**
     * buildChildMap
     * 描述:生成Map节点
     * @param childNode
     * @return Map<String, Object>
     */
    private Map<String, Object> buildTreeChildsMap(MenuDO childNode){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> stateMap = new HashMap<>();
        stateMap.put("checked", false);
        for(RoleMenuDO checknode : checknodes){
            if(checknode.getMenuId().equals(childNode.getMenuId())){
                stateMap.put("checked", true);
            }
        }
        stateMap.put("disabled", false);
        stateMap.put("expanded", false);
        stateMap.put("selected", false);
        map.put("id", childNode.getMenuId());
        map.put("text", childNode.getMenuName());
        map.put("url", childNode.getMenuUrl());
        map.put("state", stateMap);
        List<Map<String, Object>> childs = buildTreeChilds(childNode);
        if(childs.isEmpty() || childs.size() == 0){
            //map.put("state","open");
        }else{
            map.put("nodes", childs);
        }
        return map;
    }


    /**
     * getChilds
     * 描述:  获取子节点
     * @param parentNode
     * @return List<Resource>
     * @exception
     * @since  1.0.0
     */
    public List<MenuDO> getChilds(MenuDO parentNode) {
        List<MenuDO> childNodes = new ArrayList<MenuDO>();
        for(MenuDO node : nodes){
//            System.out.println(node.getParentId()+"-------"+parentNode.getMenuId());
            if (Objects.equals(node.getParentId(), parentNode.getMenuId())) {
                childNodes.add(node);
            }
        }
        return childNodes;
    }

    /**
     * buildTree
     * 描述:  创建树
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    public List<MenuDO> buildTreeGrid(){
        List<MenuDO> list = new ArrayList<MenuDO>();
        for(MenuDO node : nodes) {
            //这里判断父节点，需要自己更改判断
            if (Objects.equals(node.getParentId(), "0")) {
                List<MenuDO> childs = buildTreeGridChilds(node);
                node.setChildren(childs);
                list.add(node);
            }
        }
        return list;
    }

    /**
     * buildChilds
     * 描述:  创建树下的节点。
     * @param node
     * @return List<Map<String,Object>>
     * @exception
     * @since  1.0.0
     */
    private List<MenuDO> buildTreeGridChilds(MenuDO node){
        List<MenuDO> list = new ArrayList<MenuDO>();
        List<MenuDO> childNodes = getChilds(node);
        for(MenuDO childNode : childNodes){
            //System.out.println("childNode"+childNode.getMenuName());
            List<MenuDO> childs = buildTreeGridChilds(childNode);
            childNode.setChildren(childs);
            list.add(childNode);
        }
        return list;
    }




}
