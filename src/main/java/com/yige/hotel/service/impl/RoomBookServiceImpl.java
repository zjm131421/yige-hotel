package com.yige.hotel.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Map<String,Object> map = new HashMap<>();
        map.put("room_id",roomBookDO.getRoomId());
        map.put("enabled",1);
        map.put("book_date",roomBookDO.getBookDate());
        RoomBookDO exists = selectByMap(map).stream().findFirst().orElse(null);
        if(Objects.nonNull(exists)){
            throw new GeneralException(String.format("该房间状态不正确：%s,无法预订",exists.getStatus()));
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
        List<RoomVO> vos = this.baseMapper.listBook(page,dto);
        vos.forEach(roomVO -> {
            DictDO dictDO = dictService.selectById(roomVO.getType());
            roomVO.setTypeName(dictDO.getName());
        });
        page.setRecords(vos);
        return page;
    }
}
