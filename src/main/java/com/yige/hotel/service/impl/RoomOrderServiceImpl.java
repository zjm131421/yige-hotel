package com.yige.hotel.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.hotel.dao.RoomOrderDao;
import com.yige.hotel.domain.RoomOrderDO;
import com.yige.hotel.service.RoomOrderService;
import org.springframework.stereotype.Service;

/**
 * @author zoujm
 * @since 2018/12/12 0:44
 */
@Service
public class RoomOrderServiceImpl extends CoreServiceImpl<RoomOrderDao,RoomOrderDO> implements RoomOrderService {
}
