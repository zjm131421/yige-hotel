package com.yige.hotel.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 房间
 * @author zoujm
 * @since 2018/12/2 17:35
 */
@TableName("rooms")
@Data
public class RoomDO extends Model<RoomDO> implements Serializable {

    // 主键
    @TableId
    private Long id;
    private String name; //房间名称
    private Long type;  //房间类型
    @TableField(exist = false)
    private String typeName; //类型名称
    private Integer bedNumber; //床数量
    private Integer airConditioned; //是否有空调
    private Integer windowed; //是否有窗
    private Integer televioned; //是否有电话
    private Integer hasToilet; //是否有厕所
    private Long price; //价格
    private String remark;
    private Integer enabled; //是否有效 0 无效 1有效
    private LocalDateTime crateTime;
    private LocalDateTime updateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
