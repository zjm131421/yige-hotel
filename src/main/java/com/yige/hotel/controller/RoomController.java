package com.yige.hotel.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.DictDO;
import com.yige.common.service.impl.DictServiceImpl;
import com.yige.common.utils.Result;
import com.yige.hotel.domain.RoomDO;
import com.yige.hotel.service.impl.RoomServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zoujm
 * @since 2018/12/2 19:12
 */
@RequestMapping("/hotel/room")
@Controller
public class RoomController extends AdminBaseController {

    @Autowired
    private RoomServiceImpl roomService;
    @Autowired
    private DictServiceImpl dictService;

    private static final String PREFIX = "hotel/room";

    @GetMapping
    @Log("进入部门页面")
    @RequiresPermissions("hotel:room:room")
    String room() {
        return PREFIX + "/room";
    }

    @Log("查询房间列表")
    @RequiresPermissions("hotel:room:room")
    @RequestMapping("/list")
    @ResponseBody
    Result<Page<RoomDO>> list(RoomDO roomDto) {
        Page<RoomDO> page = roomService.selectPage(getPage(RoomDO.class), new EntityWrapper<>(roomDto));
        page.getRecords().forEach(roomDO -> {
            DictDO dictDO = dictService.selectById(roomDO.getType());
            roomDO.setTypeName(Objects.isNull(dictDO) ? "" : dictDO.getName());
        });
        return Result.ok(page);
    }

    @Log("进入房间添加页面")
    @GetMapping("/add")
    @RequiresPermissions("hotel:room:add")
    String add() {
        return "hotel/room/add";
    }

    @Log("进入房间修改页面")
    @GetMapping("/edit/{id}")
    @RequiresPermissions("hotel:room:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        RoomDO roomDO = roomService.require(id);
        model.addAttribute("room", roomDO);
        return "hotel/room/edit";
    }

    /**
     * 保存
     */
    @Log("新增房间")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("hotel:room:add")
    public Result<String> save(RoomDO roomDO) {
        roomService.add(roomDO);
        return Result.ok();
    }

    /**
     * 修改
     */
    @Log("更新房间")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("hotel:room:edit")
    public Result<String> update(RoomDO roomDO) {
        RoomDO require = roomService.require(roomDO.getId());
        roomService.update(roomDO, require);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除房间")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("hotel:room:remove")
    public Result<String> remove(Long id) {
        roomService.deleteById(id);
        return Result.ok();
    }

    @Log("校验房间名称")
    @PostMapping("/validName")
    @ResponseBody
    boolean validName(@RequestParam Map<String, Object> params) {
        return !roomService.validNmae(params);
    }
}
