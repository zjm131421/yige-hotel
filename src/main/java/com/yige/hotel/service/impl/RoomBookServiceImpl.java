package com.yige.hotel.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.exception.GeneralException;
import com.yige.common.helper.DateHelpers;
import com.yige.hotel.dao.RoomBookDao;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.enums.Enabled;
import com.yige.hotel.enums.RoomBookStatus;
import com.yige.hotel.enums.RoomStatus;
import com.yige.hotel.service.RoomBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/9 22:09
 */
@Service
public class RoomBookServiceImpl extends CoreServiceImpl<RoomBookDao,RoomBookDO> implements RoomBookService {

    @Autowired
    private RoomServiceImpl roomService;

    public RoomBookDO require(Long id){
        if(Objects.isNull(id)){
            throw new GeneralException("预定id未空");
        }
        return get(id).orElseThrow(() -> new GeneralException(String.format("未查询到预定记录:%d",id)));
    }

    public Optional<RoomBookDO> get(Long id){
        return Optional.ofNullable(selectById(id));
    }

    /**
     * 预定
     * @param roomBookDO
     * @param roomDO
     * @return
     */
    public RoomBookDO booking(RoomBookDO roomBookDO,RoomDO roomDO){
        validateBook(roomBookDO,roomDO);
        initBook(roomBookDO,roomDO);
        insert(roomBookDO);

        roomDO.setStatus(RoomStatus.YYD.getCode());
        roomService.updateById(roomDO);
        return roomBookDO;
    }

    private void validateBook(RoomBookDO roomBookDO, RoomDO roomDO) {
        int roomStatus = RoomStatus.WRZ.getCode();
        if(roomStatus != roomDO.getStatus()){
            throw new GeneralException(String.format("房间状态不正确：%d",roomDO.getStatus()));
        }
    }

    public RoomBookDO noshow(RoomBookDO roomBookDO){
        roomBookDO.setEnabled(Enabled.WX.getCode());
        roomBookDO.setStatus(RoomBookStatus.WRZ.getCode());
        this.updateById(roomBookDO);

        RoomDO roomDO = roomService.require(roomBookDO.getRoomId());
        roomDO.setStatus(RoomStatus.WRZ.getCode());
        roomService.updateById(roomDO);
        return roomBookDO;
    }

    private void initBook(RoomBookDO roomBookDO,RoomDO roomDO) {
        roomBookDO.setStatus(RoomBookStatus.YYD.getCode());
        roomBookDO.setCreateTime(DateHelpers.now());
        roomBookDO.setEnabled(Enabled.YX.getCode());
        roomBookDO.setRoomId(roomDO.getId());
    }

}
