package com.yige.hotel.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.helper.DateHelpers;
import com.yige.hotel.dao.RoomOrderDao;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.domain.RoomOrderDO;
import com.yige.hotel.enums.RoomOrderStatus;
import com.yige.hotel.service.RoomOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

/**
 * @author zoujm
 * @since 2018/12/12 0:44
 */
@Service
public class RoomOrderServiceImpl extends CoreServiceImpl<RoomOrderDao,RoomOrderDO> implements RoomOrderService {

    @Autowired
    private RoomBookServiceImpl roomBookService;

    @Transactional
    public RoomOrderDO add(RoomOrderDO roomOrderDO, RoomDO roomDO){
        init(roomOrderDO,roomDO);
        roomBookService.checkIn(roomOrderDO);
        this.baseMapper.insert(roomOrderDO);
        return roomOrderDO;
    }

    private void init(RoomOrderDO roomOrderDO,RoomDO roomDO) {
        roomOrderDO.setRoomId(roomDO.getId());
        roomOrderDO.setUnitPrice(roomDO.getPrice());
        roomOrderDO.setExpectCheckInDate(roomOrderDO.getExpectCheckOutDate().minusDays(roomOrderDO.getExpectDay()));
        roomOrderDO.setExpectPrice(roomOrderDO.getExpectDay()*roomOrderDO.getUnitPrice());
        roomOrderDO.setStatus(RoomOrderStatus.CSZT.getCode());
        roomOrderDO.setCreateTime(DateHelpers.now());
        roomOrderDO.setUpdateTime(DateHelpers.now());
    }

}
