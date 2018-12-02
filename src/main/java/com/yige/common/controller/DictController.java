package com.yige.common.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.yige.common.annotation.Log;
import com.yige.common.base.AdminBaseController;
import com.yige.common.domain.DictDO;
import com.yige.common.service.DictService;
import com.yige.common.utils.Result;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典
 * @author zoujm
 * @since 2018/12/2 11:44
 */
@Controller
@RequestMapping("/common/sysDict")
public class DictController extends AdminBaseController {

    @Autowired
    private DictService sysDictService;

    @Log("进入数据字典列表页面")
    @GetMapping()
    @RequiresPermissions("common:sysDict:sysDict")
    String sysDict() {
        return "common/sysDict/sysDict";
    }

    @Log("查询数据字典列表")
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:sysDict:sysDict")
    public Result<Page<DictDO>> list(DictDO dictDTO) {
        // 查询列表数据
        Page<DictDO> page = sysDictService.selectPage(getPage(DictDO.class), sysDictService.convertToEntityWrapper("name", dictDTO.getName(), "type", dictDTO.getType()));
        return Result.ok(page);
    }

    @Log("进入数据字典添加页面")
    @GetMapping("/add")
    @RequiresPermissions("common:sysDict:add")
    String add() {
        return "common/sysDict/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:sysDict:edit")
    String edit(@PathVariable("id") Long id, Model model) {
        DictDO sysDict = sysDictService.selectById(id);
        model.addAttribute("sysDict", sysDict);
        return "common/sysDict/edit";
    }

    /**
     * 保存
     */
    @Log("添加数据字典")
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:sysDict:add")
    public Result<String> save(DictDO sysDict) {
        sysDictService.insert(sysDict);
        return Result.ok();
    }

    /**
     * 修改
     */
    @Log("更新数据字典")
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:sysDict:edit")
    public Result<String> update(DictDO sysDict) {
        sysDictService.updateById(sysDict);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除数据字典")
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:remove")
    public Result<String> remove(Long id) {
        sysDictService.deleteById(id);
        return Result.ok();
    }

    /**
     * 删除
     */
    @Log("删除数据字典")
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:sysDict:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
        sysDictService.deleteBatchIds(Arrays.asList(ids));
        return Result.ok();
    }

    @Log("查询数据字典key列表")
    @GetMapping("/type")
    @ResponseBody
    public List<DictDO> listType() {
        return sysDictService.listType();
    };

    // 类别已经指定增加
    @Log("进入数据字典添加页面")
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysDict:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysDict/add";
    }

    @Log("根据key查询数据字典信息")
    @ResponseBody
    @GetMapping("/list/{type}")
    public List<DictDO> listByType(@PathVariable("type") String type) {
        // 查询列表数据
        Map<String, Object> map = new HashMap<>(16);
        map.put("type", type);
        List<DictDO> dictList = sysDictService.selectByMap(map);
        return dictList;
    }

}
