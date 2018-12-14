package com.yige.hotel.controller;

import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.utils.Result;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.domain.RoomOrderDO;
import com.yige.hotel.service.impl.RoomOrderServiceImpl;
import com.yige.hotel.service.impl.RoomServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zoujm
 * @since 2018/12/12 0:45
 */
@RequestMapping("/hotel/room/order")
@Controller
public class RoomOrderController extends AdminBaseController {

    @Autowired
    private RoomOrderServiceImpl roomOrderService;
    @Autowired
    private RoomServiceImpl roomService;

    /**
     * 下单
     */
    @Log("入住下单")
    @ResponseBody
    @PostMapping("/add/{roomId}")
    @RequiresPermissions("hotel:room:in")
    public Result<String> add(@PathVariable("roomId") Long roomId, RoomOrderDO roomOrderDO) {
        try{
            RoomDO roomDO = roomService.require(roomId);
            roomOrderService.add(roomOrderDO, roomDO);
        }catch (Exception e){
            return Result.build(Result.fail().getCode(),String.format("入住异常:%s",e.getMessage()));
        }

        return Result.ok();
    }

}
