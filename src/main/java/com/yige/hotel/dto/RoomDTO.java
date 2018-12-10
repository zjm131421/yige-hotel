package com.yige.hotel.dto;

import com.yige.hotel.domain.RoomDO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author zoujm
 * @since 2018/12/10 22:47
 */
@Data
public class RoomDTO extends RoomDO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookDate;
    private Integer status;

}
