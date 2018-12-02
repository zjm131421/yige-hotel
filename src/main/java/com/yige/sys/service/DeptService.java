package com.yige.sys.service;

import com.yige.common.base.CoreService;
import com.yige.common.domain.Tree;
import com.yige.sys.domain.DeptDO;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author zoujm
 * @since 2018/12/1 15:44
 */
@Service
public interface DeptService extends CoreService<DeptDO> {

    Tree<DeptDO> getTree();

    boolean checkDeptHasUser(Long deptId);

    Optional<DeptDO> get(Serializable id);

}
