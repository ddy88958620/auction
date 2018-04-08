package com.trump.auction.back.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cf.common.util.page.PageUtils;
import com.cf.common.util.page.Paging;
import com.trump.auction.back.sys.dao.read.RoleModuleReadDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.trump.auction.back.sys.dao.read.RoleReadDao;
import com.trump.auction.back.sys.dao.read.UserRoleReadDao;
import com.trump.auction.back.sys.dao.write.RoleDao;
import com.trump.auction.back.sys.dao.write.RoleModuleDao;
import com.trump.auction.back.sys.dao.write.UserRoleDao;
import com.trump.auction.back.sys.model.Role;
import com.trump.auction.back.sys.model.SysUser;
import com.trump.auction.back.sys.model.UserRole;
import com.trump.auction.back.sys.model.ZTree;
import com.trump.auction.back.util.sys.RequestUtils;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleReadDao roleReadDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserRoleReadDao userRoleReadDao;
    @Autowired
    private RoleModuleDao roleModuleDao;
    @Autowired
    private RoleModuleReadDao roleModuleReadDao;

    @Override
    public List<ZTree> findRoleTree(HashMap<String, Object> params) {
        if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
            return roleReadDao.findAdminRoleTree(params);
        } else {
            return roleReadDao.findUserRoleTree(params);
        }
    }

    @Override
    public List<Role> findRoleList(HashMap<String, Object> params) {
        if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
            return roleReadDao.findAdminAll(params);
        } else {
            return roleReadDao.findUserAll(params);
        }
    }

    @Override
    public Paging<Role> findPage(HashMap<String, Object> params) {
        PageHelper.startPage(Integer.valueOf(String.valueOf(params.get("page") == null ? 1 : params.get("page"))),
                Integer.valueOf(String.valueOf(params.get("limit") == null ? 10 : params.get("limit"))));
        Paging<Role> pageConfig = new Paging<Role>();
        if (params.containsKey("userId") && SysUser.isAdmin(String.valueOf(params.get("userId")))) {
            pageConfig = PageUtils.page(roleReadDao.findAdminAll(params));
        } else {
            pageConfig = PageUtils.page(roleReadDao.findUserAll(params));
        }
        return pageConfig;

    }

    @Override
    public int deleteById(String[] ids) {
        int count = roleDao.deleteById(ids);
        // 删除这个角色的授予的用户记录
        userRoleDao.deleteByRoleId(ids);
        // 删除这个角色被赋予的菜单
        roleModuleDao.deleteByRoleId(ids);
        if (count > 0) {
            // 如果这些角色被删掉了,还要继续删除这些角色的子角色,以及子角色对应的用户授权、菜单授权记录
            for (String id : ids) {
                List<String> list = roleReadDao.findIdsByParentId(Integer.valueOf(id));
                if (list != null && list.size() > 0) {
                    String[] strings = new String[list.size()];
                    list.toArray(strings);
                    count += roleDao.deleteById(strings);
                    userRoleDao.deleteByRoleId(strings);
                    roleModuleDao.deleteByRoleId(strings);
                }
            }
        }
        return count;
    }

    @Override
    public Role findById(Integer id) {
        return roleReadDao.findById(id);
    }

    @Override
    public void insert(Role role, Integer userId) {
        roleDao.insert(role);
        // 插入角色以后把当前操作人和该角色关联起来,超级管理员不关联,认为超级管理员能看所有
        if (!SysUser.isAdmin(String.valueOf(userId))) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(userId);
            userRoleDao.inserUserRole(userRole);
        }
    }

    @Override
    public void updateById(Role role) {
        roleDao.updateById(role);
    }

    @Override
    public List<String> showUserListByRoleId(Integer id) {
        return roleReadDao.showUserListByRoleId(id);
    }

    @Override
    public List<ZTree> getTreeByRoleId(Integer roleId,Integer parentId) {
        return roleReadDao.getTreeByRoleId(roleId,parentId);
    }

    @Override
    public int addRoleModule(String roleId, List<String> ids) {
        int count = 0;
        roleModuleDao.deleteByRoleId(new String[]{roleId});
        if (null != ids && ids.size() > 0) {
            count = roleModuleDao.insertModuleRole(roleId, ids);
        }
        return count;
    }

    @Override
    public List<String> findHaveIds(Boolean isAdd, String[] ids, Integer userId) {
        List<String> realId = new ArrayList<>();
        if (SysUser.isAdmin(userId + "")) {
            realId = Arrays.asList(ids);
        } else {
            Set<String> set = new HashSet<>();
            String[] userFirstRoles = userRoleReadDao.findRoleIdByUserId(userId, null);
            if (userFirstRoles != null && userFirstRoles.length > 0) {
                for (String roleId : userFirstRoles) {
                    List<String> list2 =   roleReadDao.findIdsByParentId(Integer.valueOf(roleId));
                    if (!isAdd) {
                        list2.remove(roleId);
                    }
                    set.addAll(list2);
                }
            }
            if (set != null && set.size() > 0) {
                for (String id : ids) {
                    for (String id2 : set) {
                        if (id.equals(id2)) {
                            realId.add(id);
                        }
                    }
                }
            }
        }
        return realId;
    }
    @Override
    public List<String> findHaveModules(String[] ids, Integer userId) {
        List<String> realId = new ArrayList<>();
        if (SysUser.isAdmin(userId + "")) {
            realId = Arrays.asList(ids);
        } else {
            Set<String> set = new HashSet<>();
            //查询某个用户的角色
            String[] userFirstRoles = userRoleReadDao.findRoleIdByUserId(userId, null);
            for (String roleId : userFirstRoles) {
                //查询某个角色的无限子角色
                set.addAll(roleReadDao.findIdsByParentId(Integer.valueOf(roleId)));
            }
            if (set != null && set.size() > 0) {
                List<String> list= roleModuleReadDao.findModuleByRoleIds(StringUtils.join(set, ","));
                for (String id : ids) {
                    for (String id2 : list) {
                        if (id.equals(id2)) {
                            realId.add(id);
                            break;
                        }
                    }
                }
            }
        }
        return realId;
    }
}
