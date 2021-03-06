package com.yige.hotel.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author zoujm
 * @since 2018/12/9 21:57
 */
@TableName("room_bookings")
@Data
public class RoomBookDO extends Model<RoomBookDO> implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Long roomId;
    private Long orderId;
    private String customerName;
    private String customerMobile;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime keepTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;
    private Integer sumtimes;
    private String remark;
    private Integer enabled;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
