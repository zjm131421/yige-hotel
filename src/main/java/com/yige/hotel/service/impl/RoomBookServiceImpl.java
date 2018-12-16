package com.yige.hotel.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.base.CoreServiceImpl;
import com.yige.common.domain.DictDO;
import com.yige.common.exception.GeneralException;
import com.yige.common.helper.DateHelpers;
import com.yige.common.service.impl.DictServiceImpl;
import com.yige.hotel.dao.RoomBookDao;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.domain.RoomOrderDO;
import com.yige.hotel.dto.RoomDTO;
import com.yige.hotel.enums.Enabled;
import com.yige.hotel.enums.RoomBookStatus;
import com.yige.hotel.service.RoomBookService;
import com.yige.hotel.vo.RoomVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/9 22:09
 */
@Service
public class RoomBookServiceImpl extends CoreServiceImpl<RoomBookDao,RoomBookDO> implements RoomBookService {

    @Autowired
    private DictServiceImpl dictService;
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

    public Optional<RoomBookDO> getByBookDate(Long roomId, String bookDate){
        RoomDTO dto = new RoomDTO();
        dto.setRoomId(roomId);
        dto.setBookDate(bookDate);
        dto.setEnabled(Enabled.YX.getCode());
        return Optional.ofNullable(this.baseMapper.getByDto(dto));
    }

    /**
     * 预定
     * @param roomBookDO 预订入参
     * @param roomDO 房间
     * @return RoomBookDO
     */
    public RoomBookDO booking(RoomBookDO roomBookDO,RoomDO roomDO){
        initBook(roomBookDO,roomDO);
        validateBook(roomBookDO);
        insert(roomBookDO);
        return roomBookDO;
    }

    public RoomBookDO change(Long id,Long orderId){
        RoomBookDO roomBookDO = require(id);
        roomBookDO.setOrderId(orderId);
        this.updateById(roomBookDO);
        return roomBookDO;
    }

    public RoomBookDO checkOut(RoomOrderDO roomOrderDO){
        RoomBookDO roomBookDO = require(roomOrderDO.getBookId());
        roomBookDO.setDepartureTime(DateHelpers.now());
        roomBookDO.setSumtimes((int) roomBookDO.getArrivalTime().until(roomBookDO.getDepartureTime(),ChronoUnit.HOURS));
        roomBookDO.setEnabled(Enabled.WX.getCode());
        roomBookDO.setStatus(RoomBookStatus.YTF.getCode());
        this.baseMapper.updateById(roomBookDO);
        return roomBookDO;
    }

    public RoomBookDO checkIn(RoomOrderDO roomOrderDO){
        Optional<RoomBookDO> optionalBookDO = get(roomOrderDO.getBookId());
        return optionalBookDO.map(roomBookDO -> {
            roomBookDO.setArrivalTime(DateHelpers.now());
            roomBookDO.setOrderId(roomOrderDO.getId());
            roomBookDO.setRoomId(roomOrderDO.getRoomId());
            roomBookDO.setStatus(RoomBookStatus.YRZ.getCode());
            this.updateById(roomBookDO);
            return roomBookDO;
        }).orElseGet(() -> {
            RoomBookDO roomBookDO = initCheckIn(roomOrderDO);
            validateBook(roomBookDO);
            this.baseMapper.insert(roomBookDO);
            roomOrderDO.setBookId(roomBookDO.getId());
            return roomBookDO;
        });
    }

    private RoomBookDO initCheckIn(RoomOrderDO roomOrderDO) {
        RoomBookDO roomBookDO = new RoomBookDO();
        roomBookDO.setId(roomOrderDO.getBookId());
        roomBookDO.setRoomId(roomOrderDO.getRoomId());
        roomBookDO.setOrderId(roomOrderDO.getId());
        roomBookDO.setCustomerName(roomOrderDO.getCustomerName());
        roomBookDO.setCustomerMobile(roomOrderDO.getCustomerMobile());
        roomBookDO.setBookDate(roomOrderDO.getExpectCheckInDate());
        roomBookDO.setKeepTime(roomOrderDO.getExpectCheckInDate().atTime(DateHelpers.now().toLocalTime()));
        roomBookDO.setArrivalTime(DateHelpers.now());
        roomBookDO.setDepartureTime(roomOrderDO.getExpectCheckOutDate().atTime(DateHelpers.parseTime("14:00")));
        roomBookDO.setEnabled(Enabled.YX.getCode());
        roomBookDO.setRemark(roomOrderDO.getRemark());
        roomBookDO.setStatus(RoomBookStatus.YRZ.getCode());
        roomBookDO.setCreateTime(DateHelpers.now());
        return roomBookDO;
    }

    private void validateBook(RoomBookDO roomBookDO) {
        Optional<RoomBookDO> optionalBookDO = getByBookDate(roomBookDO.getRoomId(), roomBookDO.getBookDate().toString());
        optionalBookDO.ifPresent(bookDo -> {
            if(RoomBookStatus.YYD.getCode() == bookDo.getStatus()){
                    throw new GeneralException(String.format("该房间在%s已被%s预定",bookDo.getBookDate(),bookDo.getCustomerName()));
            }
            if(RoomBookStatus.YRZ.getCode() == bookDo.getStatus()){
                throw new GeneralException("该房间已有人入住");
            }
        });

    }

    public RoomBookDO noshow(RoomBookDO roomBookDO){
        roomBookDO.setEnabled(Enabled.WX.getCode());
        roomBookDO.setStatus(RoomBookStatus.WRZ.getCode());
        this.updateById(roomBookDO);
        return roomBookDO;
    }

    public RoomBookDO clear(RoomBookDO roomBookDO){
        roomBookDO.setEnabled(Enabled.WX.getCode());
        this.updateById(roomBookDO);
        return roomBookDO;
    }

    private void initBook(RoomBookDO roomBookDO,RoomDO roomDO) {
        roomBookDO.setStatus(RoomBookStatus.YYD.getCode());
        roomBookDO.setCreateTime(DateHelpers.now());
        roomBookDO.setBookDate(roomBookDO.getArrivalTime().toLocalDate());
        roomBookDO.setEnabled(Enabled.YX.getCode());
        roomBookDO.setRoomId(roomDO.getId());
    }

    @SuppressWarnings("unchecked")
    public Page<RoomVO> listBook(Page page,RoomDTO dto) {
        if(StringUtils.isBlank(dto.getBookDate())){
            dto.setBookDate(DateHelpers.today().toString());
        }
        List<RoomVO> vos = this.baseMapper.listBook(page,dto);
        vos.forEach(roomVO -> {
            DictDO dictDO = dictService.selectById(roomVO.getType());
            roomVO.setTypeName(dictDO.getName());
            Optional<RoomBookDO> optionalBookDO = getByBookDate(roomVO.getRoomId(), dto.getBookDate());
            optionalBookDO.ifPresent(roomBookDO ->
                roomVO.setStatus(roomBookDO.getStatus())
            );
            RoomDO roomDO = roomService.require(roomVO.getRoomId());
            roomVO.setPrice(roomDO.getPrice());
        });
        page.setRecords(vos);
        return page;
    }
}
