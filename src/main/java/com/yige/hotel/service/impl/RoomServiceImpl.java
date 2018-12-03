package com.yige.hotel.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.base.CoreServiceImpl;
import com.yige.common.exception.GeneralException;
import com.yige.common.helper.DateHelpers;
import com.yige.hotel.RoomStatus;
import com.yige.hotel.dao.RoomDao;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/2 17:47
 */
@Service
public class RoomServiceImpl extends CoreServiceImpl<RoomDao,RoomDO> implements RoomService {

    public boolean validNmae(Map<String,Object> params){
        return retBool(baseMapper.selectByMap(params).size());
    }

    public RoomDO require(Long id){
        if(Objects.isNull(id)){
            throw new GeneralException("房间id未空");
        }
        return get(id).orElseThrow(() -> new GeneralException(String.format("未查询到房间信息：%d",id)));
    }

    public Optional<RoomDO> get(Long id){
        return Optional.ofNullable(selectById(id));
    }

    public RoomDO add(RoomDO data){
        initData(data);
        insert(data);
        return data;
    }

    public RoomDO update(RoomDO data,RoomDO roomDO){
        doUpdate(data,roomDO);
        updateById(roomDO);
        return roomDO;
    }

    private void doUpdate(RoomDO data, RoomDO roomDO) {
        roomDO.setName(data.getName());
        roomDO.setType(data.getType());
        roomDO.setBedNumber(data.getBedNumber());
        roomDO.setAirConditioned(data.getAirConditioned());
        roomDO.setWindowed(data.getWindowed());
        roomDO.setTelevioned(data.getTelevioned());
        roomDO.setHasToilet(data.getHasToilet());
        roomDO.setPrice(data.getPrice());
        roomDO.setRemark(data.getRemark());
        roomDO.setUpdateTime(DateHelpers.now());
    }

    private void initData(RoomDO data) {
        data.setEnabled(1);
        data.setCrateTime(DateHelpers.now());
        data.setStatus(RoomStatus.WRZ.getBh());
    }

}
