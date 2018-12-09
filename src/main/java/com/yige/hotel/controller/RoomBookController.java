package com.yige.hotel.controller;

import com.yige.common.base.AdminBaseController;
import com.yige.hotel.service.impl.RoomBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zoujm
 * @since 2018/12/9 21:56
 */
@RequestMapping("/hotel/room/book")
@Controller
public class RoomBookController extends AdminBaseController {

    @Autowired
    private RoomBookServiceImpl roomBookService;


}
