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
import com.yige.hotel.dto.RoomDTO;
import com.yige.hotel.enums.Enabled;
import com.yige.hotel.enums.RoomBookStatus;
import com.yige.hotel.service.RoomBookService;
import com.yige.hotel.vo.RoomVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private void validateBook(RoomBookDO roomBookDO) {
        Optional<RoomBookDO> optionalBookDO = getByBookDate(roomBookDO.getRoomId(), roomBookDO.getBookDate().toString());
        if(optionalBookDO.isPresent()){
            throw new GeneralException(String.format("该房间状态不正确：%s,无法预订",optionalBookDO.get().getStatus()));
        }
    }

    public RoomBookDO noshow(RoomBookDO roomBookDO){
        roomBookDO.setEnabled(Enabled.WX.getCode());
        roomBookDO.setStatus(RoomBookStatus.WRZ.getCode());
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
        });
        page.setRecords(vos);
        return page;
    }
}
