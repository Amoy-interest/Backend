package com.example.amoy_interest.config.shiro;

import com.example.amoy_interest.config.shiro.jwt.JwtToken;
import com.example.amoy_interest.constant.Constant;
import com.example.amoy_interest.dao.PermissionDao;
import com.example.amoy_interest.dao.RoleDao;
import com.example.amoy_interest.dao.UserAuthDao;
import com.example.amoy_interest.entity.RolePermission;
import com.example.amoy_interest.entity.UserAuth;
import com.example.amoy_interest.entity.UserRole;
import com.example.amoy_interest.utils.JedisUtil;
import com.example.amoy_interest.utils.JwtUtil;
import com.example.amoy_interest.utils.common.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义Realm
 * @author dolyw.com
 * @date 2018/8/30 14:10
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

//    @Autowired
//    public UserRealm(UserMapper userMapper, RoleMapper roleMapper, PermissionMapper permissionMapper) {
//        this.userMapper = userMapper;
//        this.roleMapper = roleMapper;
//        this.permissionMapper = permissionMapper;
//    }

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String username = JwtUtil.getClaim(principalCollection.toString(), Constant.USERNAME);
//        UserDto userDto = new UserDto();
//        userDto.setAccount(account);
//        // 查询用户角色
//        List<RoleDto> roleDtos = roleMapper.findRoleByUser(userDto);
        List<UserRole> userRoles = roleDao.getRoleByUsername(username);
        for (UserRole role : userRoles) {
            if (role != null) {
                // 添加角色
                simpleAuthorizationInfo.addRole(role.getRole_name());
                // 根据用户角色查询权限
                List<RolePermission> permissions = permissionDao.getPermissionByRoleName(role.getRole_name());
                for (RolePermission permission : permissions) {
                    if (permission != null) {
                        // 添加权限
                        simpleAuthorizationInfo.addStringPermission(permission.getPermission());
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String username = JwtUtil.getClaim(token, Constant.USERNAME);
        // 帐号为空
        if (StringUtil.isBlank(username)) {
            throw new AuthenticationException("Token中帐号为空(The username in Token is empty.)");
        }
        // 查询用户是否存在
//        UserDto userDto = new UserDto();
//        userDto.setAccount(account);
//        userDto = userMapper.selectOne(userDto);
        UserAuth userAuth = userAuthDao.findUserByUsername(username);
        if (userAuth == null) {
            throw new AuthenticationException("该帐号不存在(The username does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + username).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
