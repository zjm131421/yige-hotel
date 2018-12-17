package com.yige.hotel.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.exception.GeneralException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/12 0:44
 */
@Service
public class RoomOrderServiceImpl extends CoreServiceImpl<RoomOrderDao, RoomOrderDO> implements RoomOrderService {

    @Autowired
    private RoomBookServiceImpl roomBookService;

    public RoomOrderDO require(Long id) {
        return get(id).orElseThrow(() -> new GeneralException(String.format("未查询到订单信息:%d", id)));
    }

    public List<RoomOrderDO> listByBookId(Long bookId){
        Map<String,Object> param = new HashMap<>();
        param.put("book_id",bookId);
        return this.baseMapper.selectByMap(param);
    }

    public Optional<RoomOrderDO> get(Long id) {
        if (Objects.isNull(id)) {
            throw new GeneralException("订单id未空");
        }
        return Optional.ofNullable(this.selectById(id));
    }

    @Transactional
    public RoomOrderDO add(RoomOrderDO roomOrderDO, RoomDO roomDO) {
        init(roomOrderDO, roomDO);
        this.baseMapper.insert(roomOrderDO);
        roomBookService.checkIn(roomOrderDO);
        this.baseMapper.updateById(roomOrderDO);
        return roomOrderDO;
    }

    @Transactional
    public RoomOrderDO change(RoomOrderDO roomOrderDO, RoomOrderDO data, RoomDO roomDO) {
        doChange(roomOrderDO);
        RoomOrderDO newOrder = initChange(roomOrderDO, data);
        add(newOrder, roomDO);
        roomBookService.change(roomOrderDO.getBookId(), roomOrderDO.getId());
        return null;
    }

    @Transactional
    public RoomOrderDO checkOut(RoomOrderDO roomOrderDO,RoomOrderDO data){
        initCheckOut(roomOrderDO,data);
        this.baseMapper.updateById(roomOrderDO);
        List<RoomOrderDO> orders = listByBookId(roomOrderDO.getBookId());
        orders.stream()
                .filter(order -> !order.getId().equals(roomOrderDO.getId()))
                .forEach(order -> {
                    order.setStatus(RoomOrderStatus.WJZT.getCode());
                    this.baseMapper.updateById(order);
                });

        roomBookService.checkOut(roomOrderDO);
        return roomOrderDO;
    }

    private void initCheckOut(RoomOrderDO roomOrderDO, RoomOrderDO data) {
        roomOrderDO.setNetPrice(data.getNetPrice());
        roomOrderDO.setPayment(data.getPayment());
        roomOrderDO.setCheckOutDate(data.getCheckOutDate());
        roomOrderDO.setNetDay((int) roomOrderDO.getCheckInDate().until(roomOrderDO.getCheckOutDate(),ChronoUnit.DAYS));
        roomOrderDO.setStatus(RoomOrderStatus.WJZT.getCode());
    }

    private RoomOrderDO initChange(RoomOrderDO roomOrderDO, RoomOrderDO data) {
        RoomOrderDO newOrder = new RoomOrderDO();
        newOrder.setRoomId(roomOrderDO.getRoomId());
        newOrder.setBookId(roomOrderDO.getBookId());
        newOrder.setCustomerName(roomOrderDO.getCustomerName());
        newOrder.setCustomerGender(roomOrderDO.getCustomerGender());
        newOrder.setCustomerMobile(roomOrderDO.getCustomerMobile());
        newOrder.setCustomerNumberId(roomOrderDO.getCustomerNumberId());
        newOrder.setForegift(roomOrderDO.getForegift());
        newOrder.setExpectCheckInDate(data.getExpectCheckInDate());
        newOrder.setCheckInDate(data.getExpectCheckInDate());
        newOrder.setExpectCheckOutDate(data.getExpectCheckOutDate());
        newOrder.setExpectDay((int) data.getExpectCheckInDate().until(data.getExpectCheckOutDate(), ChronoUnit.DAYS));
        newOrder.setRemark(data.getRemark());
        return newOrder;
    }

    private void doChange(RoomOrderDO roomOrderDO) {
        roomOrderDO.setCheckInDate(roomOrderDO.getExpectCheckInDate());
        roomOrderDO.setCheckOutDate(DateHelpers.today());
        roomOrderDO.setNetDay((int) roomOrderDO.getCheckInDate().until(roomOrderDO.getCheckOutDate(),ChronoUnit.DAYS));
        roomOrderDO.setNetPrice(roomOrderDO.getNetDay()*roomOrderDO.getUnitPrice());
        roomOrderDO.setStatus(RoomOrderStatus.HFZT.getCode());
        this.baseMapper.updateById(roomOrderDO);
    }

    private void init(RoomOrderDO roomOrderDO, RoomDO roomDO) {
        roomOrderDO.setRoomId(roomDO.getId());
        roomOrderDO.setRoomName(roomDO.getName());
        roomOrderDO.setUnitPrice(roomDO.getPrice());
        roomOrderDO.setExpectCheckInDate(roomOrderDO.getExpectCheckOutDate().minusDays(roomOrderDO.getExpectDay()));
        roomOrderDO.setCheckInDate(roomOrderDO.getExpectCheckInDate());
        roomOrderDO.setExpectPrice(roomOrderDO.getExpectDay() * roomOrderDO.getUnitPrice());
        roomOrderDO.setStatus(RoomOrderStatus.CSZT.getCode());
        roomOrderDO.setCreateTime(DateHelpers.now());
        roomOrderDO.setUpdateTime(DateHelpers.now());
    }

}
