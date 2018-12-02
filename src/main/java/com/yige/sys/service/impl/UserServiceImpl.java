package com.yige.sys.service.impl;

import com.yige.common.base.CoreServiceImpl;
import com.yige.common.domain.Tree;
import com.yige.common.enums.EnumErrorCode;
import com.yige.common.exception.GeneralException;
import com.yige.common.utils.BuildTree;
import com.yige.common.utils.MD5Utils;
import com.yige.sys.dao.DeptDao;
import com.yige.sys.dao.UserDao;
import com.yige.sys.dao.UserRoleDao;
import com.yige.sys.domain.DeptDO;
import com.yige.sys.domain.UserDO;
import com.yige.sys.domain.UserRoleDO;
import com.yige.sys.service.DeptService;
import com.yige.sys.service.UserService;
import com.yige.sys.vo.UserVO;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zoujm
 * @since 2018/12/1 13:28
 */
@Transactional
@Service
public class UserServiceImpl extends CoreServiceImpl<UserDao,UserDO> implements UserService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private DeptService deptService;
//    @Autowired
//    private FileService sysFileService;

    @Override
    public UserDO selectById(Serializable id) {
        List<Long> roleIds = userRoleDao.listRoleId(id);
        UserDO user = baseMapper.selectById(id);
        user.setDeptName(deptService.get(user.getDeptId()).map(DeptDO::getName).orElse(""));
        user.setRoleIds(roleIds);
        return user;
    }

    /**
     *
     * @param userId
     * @param roleIds
     * @return
     */
    private List<UserRoleDO> handleRoles(Long userId,List<Long> roleIds){
        return roleIds.stream().map(roleId -> {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            return ur;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean insert(UserDO user) {
        int count = baseMapper.insert(user);
        Long userId = user.getId();
        List<Long> roles = user.getRoleIds();
        userRoleDao.removeByUserId(userId);
        List<UserRoleDO> list = handleRoles(userId,roles);
        if (list.size() > 0) {
            userRoleDao.batchSave(list);
        }
        return retBool(count);
    }

    @Override
    public boolean updateById(UserDO user) {
        int r = baseMapper.updateById(user);
        Long userId = user.getId();
        List<Long> roles = user.getRoleIds();
        userRoleDao.removeByUserId(userId);
        List<UserRoleDO> list = handleRoles(userId,roles);
        if (list.size() > 0) {
            userRoleDao.batchSave(list);
        }
        return retBool(r);
    }

    @Override
    public boolean deleteById(Serializable userId) {
        userRoleDao.removeByUserId(userId);
        return retBool(baseMapper.deleteById(userId));
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        return retBool(baseMapper.selectByMap(params).size());
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserVO userVO, UserDO userDO) {
        if (Objects.equals(userVO.getUserDO().getId(), userDO.getId())) {
            if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
                return baseMapper.updateById(userDO);
            } else {
                throw new GeneralException("输入的旧密码有误！");
            }
        } else {
            throw new GeneralException("你修改的不是你登录的账号！");
        }
    }

    @Override
    public int adminResetPwd(UserVO userVO) {
        UserDO userDO = selectById(userVO.getUserDO().getId());
        if ("admin".equals(userDO.getUsername())) {
            throw new GeneralException(EnumErrorCode.userUpdatePwd4adminNotAllowed.getCodeStr());
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return baseMapper.updateById(userDO);
    }

    @Transactional
    @Override
    public boolean deleteBatchIds(List<? extends Serializable> idList) {
        int count = baseMapper.deleteBatchIds(idList);
        userRoleDao.deleteBatchIds(idList);
        return retBool(count);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptDao.selectList(null);
        Long[] pDepts = deptDao.listParentDept();
        Long[] uDepts = baseMapper.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = baseMapper.selectList(null);
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        return BuildTree.build(trees);
    }

    @Override
    public int updatePersonal(UserDO userDO) {
        return baseMapper.updateById(userDO);
    }

    /**
     *修改头像
     * @param file
     *            图片
     * @param avatar_data
     *            裁剪信息
     * @param userId
     *            用户ID
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        return null;
    }
}
