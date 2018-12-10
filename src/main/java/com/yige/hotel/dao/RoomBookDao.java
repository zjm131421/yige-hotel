package com.yige.hotel.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.base.BaseDao;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.dto.RoomDTO;
import com.yige.hotel.vo.RoomVO;

import java.util.List;


public interface RoomBookDao extends BaseDao<RoomBookDO> {

    List<RoomVO> listBook(Page page,RoomDTO dto);

    int countBook(RoomDTO dto);

}
