package com.yige.hotel.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.DictDO;
import com.yige.common.utils.Result;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.domain.RoomOrderDO;
import com.yige.hotel.service.impl.RoomOrderServiceImpl;
import com.yige.hotel.service.impl.RoomServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Log("进入酒店订单列表页面")
    @GetMapping()
    @RequiresPermissions("hotel:order:order")
    String order() {
        return "hotel/order/order";
    }

    @Log("查询订单列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("hotel:order:order")
    public Result<Page<RoomOrderDO>> list(RoomOrderDO orderDTO) {
        // 查询列表数据
        Page<RoomOrderDO> page = roomOrderService.selectPage(getPage(RoomOrderDO.class), roomOrderService.convertToEntityWrapper("id", orderDTO.getId(), "status", orderDTO.getStatus()));
        return Result.ok(page);
    }

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

    @Log("换房")
    @ResponseBody
    @PostMapping("/chage/{orderId}")
    @RequiresPermissions("hotel:room:in")
    public Result<String> chage(@PathVariable("orderId") Long orderId,RoomOrderDO data){
        try {
            RoomOrderDO roomOrderDO = roomOrderService.require(orderId);
            RoomDO roomDO = roomService.require(data.getRoomId());
            roomOrderService.change(roomOrderDO, data,roomDO);
        }catch (Exception e){
            return Result.build(Result.fail().getCode(),String.format("换房异常:%s",e.getMessage()));
        }
        return Result.ok();
    }

    @Log("退房")
    @ResponseBody
    @PostMapping("/checkOut/{orderId}")
    @RequiresPermissions("hotel:room:in")
    public Result<String> checkOut(@PathVariable("orderId") Long orderId,RoomOrderDO data){
        try {
            RoomOrderDO roomOrderDO = roomOrderService.require(orderId);
            roomOrderService.checkOut(roomOrderDO, data);
        }catch (Exception e){
            return Result.build(Result.fail().getCode(),String.format("退房异常:%s",e.getMessage()));
        }
        return Result.ok();
    }

}
