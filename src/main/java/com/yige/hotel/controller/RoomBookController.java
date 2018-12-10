package com.yige.hotel.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.utils.Result;
import com.yige.hotel.domain.RoomBookDO;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.dto.RoomDTO;
import com.yige.hotel.service.impl.RoomBookServiceImpl;
import com.yige.hotel.service.impl.RoomServiceImpl;
import com.yige.hotel.vo.RoomVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zoujm
 * @since 2018/12/9 21:56
 */
@RequestMapping("/hotel/room/book")
@Controller
public class RoomBookController extends AdminBaseController {

    @Autowired
    private RoomBookServiceImpl roomBookService;
    @Autowired
    private RoomServiceImpl roomService;

    private static final String PREFIX = "hotel/room";
    /**
     * 预定
     */
    @Log("预定房间")
    @ResponseBody
    @PostMapping("/add/{id}")
    @RequiresPermissions("hotel:room:book")
    public Result<String> add(@PathVariable("id") Long id, RoomBookDO roomBookDO) {
        try{
            RoomDO roomDO = roomService.require(id);
            roomBookService.booking(roomBookDO, roomDO);
        }catch (Exception e){
            return Result.build(Result.fail().getCode(),String.format("预定异常:%s",e.getMessage()));
        }

        return Result.ok();
    }

    @Log("进入预定页面")
    @GetMapping("/get/{id}")
    @RequiresPermissions("hotel:room:book")
    String get(@PathVariable("id") Long id, Model model) {
        RoomDO roomDO = roomService.require(id);
        model.addAttribute("room", roomDO);
        return PREFIX + "/book";
    }

    @Log("查询房间列表")
    @RequiresPermissions("hotel:room:book")
    @RequestMapping("/list")
    @ResponseBody
    Result<Page<RoomVO>> list(RoomDTO dto) {
        Page<RoomVO> page = roomBookService.listBook(getPage(RoomVO.class),dto);
        return Result.ok(page);
    }

}
