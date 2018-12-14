package com.yige.hotel.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoujm
 * @since 2018/12/12 0:31
 */
@TableName("room_orders")
@Data
public class RoomOrderDO extends Model<RoomOrderDO> implements Serializable {

    @TableId
    private Long id;
    private Long roomId;
    private Long bookId;
    private String customerName;
    private String customerGender;
    private String customerMobile;
    private String customerNumberId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkInTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOutTime;
    private Integer netDay;
    private Long netPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectCheckInDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectCheckOutDate;
    private Long expectPrice;
    private Integer expectDay;
    private Long unitPrice;
    private Long foregift;
    private Integer status;
    private String remark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
