package com.yige.sys.domain;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 部门
 * @author zoujm
 * @since 2018/12/1 15:39
 */
@TableName("depts")
public class DeptDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    // 上级部门ID，一级部门为0
    private Long parentId;
    // 部门名称
    private String name;
    // 排序
    private Integer orderNum;
    // 是否删除 -1：已删除 0：正常
    private Integer delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
