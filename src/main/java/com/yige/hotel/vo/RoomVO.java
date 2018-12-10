package com.yige.hotel.vo;

import lombok.Data;

/**
 * @author zoujm
 * @since 2018/12/10 22:57
 */
@Data
public class RoomVO {

    private Long roomId;
    private String name;
    private Long type;
    private String typeName;
    private Long bookId;
    private Integer status;

}
