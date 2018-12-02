package com.yige.sys.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.domain.Tree;
import com.yige.common.utils.BuildTree;
import com.yige.sys.dao.DeptDao;
import com.yige.sys.domain.DeptDO;
import com.yige.sys.service.DeptService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/1 15:45
 */
@Service
public class DeptServiceImpl extends CoreServiceImpl<DeptDao, DeptDO> implements DeptService {

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<>();
        List<DeptDO> sysDepts = baseMapper.selectList(null);
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(trees);
    }

    /**
     * 查询部门以及此部门的下级部门
     * @param deptId 部门id
     * @return boolean
     */
    @Override
    public boolean checkDeptHasUser(Long deptId) {
        int result = baseMapper.getDeptUserNumber(deptId);
        return result == 0;
    }

    @Override
    public Optional<DeptDO> get(Serializable id) {
        return Optional.ofNullable(selectById(id));
    }
}
