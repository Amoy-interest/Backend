package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.RedisService;
import com.example.amoy_interest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserCountDao userCountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserFollowDao userFollowDao;
    @Autowired
    private UserBanDao userBanDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserReportDao userReportDao;

    @Override
    public UserAuth findUserAuthById(Integer id) {
        return userAuthDao.findUserById(id);
    }

    @Override
    public UserAuth findUserAuthByUsername(String username) {
        return userAuthDao.findUserByUsername(username);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public UserInfoDTO register(RegisterDTO registerDTO) {
        UserAuth userAuth = new UserAuth(registerDTO.getUsername(), registerDTO.getPassword(), 0, 0, 0);
        userAuth = userAuthDao.insert(userAuth);
        Integer user_id = userAuth.getUser_id();
        User user = new User(user_id, registerDTO.getNickname(), registerDTO.getEmail(), registerDTO.getSex(),
                registerDTO.getAddress(), 100, "这个人很懒，什么都没留下", null
        );
        userDao.insert(user);
        UserCount userCount = new UserCount(user_id, 0, 0, 0,0);
        userCountDao.insert(userCount);
        user.setUserAuth(userAuth);
        UserRole userRole = new UserRole();
        userRole.setRole_name("user");
        userRole.setUsername(registerDTO.getUsername());
        roleDao.insert(userRole);
        return new UserInfoDTO(user, false);
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean follow(Integer user_id, Integer follow_id) {
        UserFollow userFollow = new UserFollow(user_id, follow_id);
        Optional<UserFollow> userFollow1 = userFollowDao.findByUser_idAndFollow_id(user_id, follow_id);
        if(userFollow1.isPresent()) {
            return true;
        }else {
            userFollowDao.insert(userFollow);
            if(redisService.getUserFollowCountFromRedis(user_id) == null) {
                UserCount userCount = userCountDao.getByUserID(user_id);
                redisService.setUserFollowCount(user_id,userCount.getFollow_count());
            }
            if(redisService.getUserFanCountFromRedis(follow_id) == null) {
                UserCount userCount = userCountDao.getByUserID(follow_id);
                redisService.setUserFanCount(follow_id,userCount.getFan_count());
            }
            redisService.incrementUserFollowCount(user_id);
            redisService.incrementUserFanCount(follow_id);
        }
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean ban(UserCheckDTO userCheckDTO) {
        Date endTime = new Date(System.currentTimeMillis() + userCheckDTO.getTime() * 1000);
        Integer user_id = userCheckDTO.getUser_id();
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_ban(1);
        userAuthDao.update(userAuth);
        Optional<UserBan> userBanOptional = userBanDao.findUserBanById(user_id);
        UserBan userBan = null;
        if (!userBanOptional.isPresent()) {
            userBan = new UserBan(user_id, endTime, null);
        } else {
            userBan = userBanOptional.get();
            userBan.setBan_time(endTime);
        }
        userBanDao.insert(userBan);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean unban(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_ban(0);
        userAuthDao.update(userAuth);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean forbid(UserCheckDTO userCheckDTO) {
        Date endTime = new Date(System.currentTimeMillis() + userCheckDTO.getTime() * 1000);
        Integer user_id = userCheckDTO.getUser_id();
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_forbidden(1);
        userAuthDao.update(userAuth);
        Optional<UserBan> userBanOptional = userBanDao.findUserBanById(user_id);
        UserBan userBan = null;
        if (!userBanOptional.isPresent()) {
            userBan = new UserBan(user_id, null, endTime);
        } else {
            userBan = userBanOptional.get();
            userBan.setForbidden_time(endTime);
        }
        userBanDao.insert(userBan);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean permit(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_forbidden(0);
        userAuthDao.update(userAuth);
        return true;
    }

//    @Override
//    public List<UserReportDTO> getReportedUsers() {
////        List<User> userList = userDao.getReportedUsers();
////        List<UserReportDTO> userReportDTOList = new ArrayList<>();
////        for (User user : userList) {
////            UserReportDTO userReportDTO = new UserReportDTO(user);
////            userReportDTOList.add(userReportDTO);
////        }
////        return userReportDTOList;
//        return null;
//    }

    @Override
    public User findUserById(Integer user_id) {
        return userDao.getById(user_id);
    }


    @Override
    public Page<UserReportDTO> getReportedUsersPage(Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
////            sort = Sort.by(Sort.Direction.DESC,"");
//        }else if(orderType == 2){
//            sort = Sort.by(Sort.Direction.ASC, "credits");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "credits");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userDao.getReportedUsersPage(pageable);
    }

    @Override
    public Page<UserInfoDTO> getUserFollowPage(Integer my_user_id, Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<UserFollow> userFollowPage = userFollowDao.findFollowPageByUser_id(user_id, pageable);
        List<UserFollow> userFollowList = userFollowPage.getContent();
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        if (my_user_id == user_id) {
            for (UserFollow userFollow : userFollowList) {
                User user = userDao.getById(userFollow.getFollow_id());
                userInfoDTOList.add(new UserInfoDTO(user, true));
            }
        } else {
            for (UserFollow userFollow : userFollowList) {
                Integer follow_id = userFollow.getFollow_id();
                User follow = userDao.getById(follow_id);
                Optional<UserFollow> userFollow1 = userFollowDao.findByUser_idAndFollow_id(user_id, follow_id);
//                User user = userDao.getById(userFollow.getFollow_id());
                if (userFollow1.isPresent())
                    userInfoDTOList.add(new UserInfoDTO(follow, true));
                else
                    userInfoDTOList.add(new UserInfoDTO(follow, false));
            }
        }
        return new PageImpl<>(userInfoDTOList, userFollowPage.getPageable(), userFollowPage.getTotalElements());
    }

    @Override
    public Page<UserInfoDTO> getUserFanPage(Integer my_user_id, Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<UserFollow> userFanPage = userFollowDao.findFollowPageByFollow_id(user_id, pageable);
        List<UserFollow> userFollowList = userFanPage.getContent();
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        for (UserFollow userFollow : userFollowList) {
            Integer fan_id = userFollow.getUser_id();
            User fan = userDao.getById(fan_id);
            Optional<UserFollow> userFollow1 = userFollowDao.findByUser_idAndFollow_id(my_user_id, fan_id);
            if (userFollow1.isPresent())
                userInfoDTOList.add(new UserInfoDTO(fan, true));
            else
                userInfoDTOList.add(new UserInfoDTO(fan, false));
        }
        return new PageImpl<>(userInfoDTOList, userFanPage.getPageable(), userFanPage.getTotalElements());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean unfollow(Integer user_id, Integer unfollow_id) {
        UserFollow userFollow = new UserFollow(user_id, unfollow_id);
        Optional<UserFollow> userFollow1 = userFollowDao.findByUser_idAndFollow_id(user_id, unfollow_id);
        if(!userFollow1.isPresent()) {
            return true;
        }else {
            userFollowDao.delete(userFollow);
            if(redisService.getUserFollowCountFromRedis(user_id) == null) {
                UserCount userCount = userCountDao.getByUserID(user_id);
                redisService.setUserFollowCount(user_id,userCount.getFollow_count());
            }
            if(redisService.getUserFanCountFromRedis(unfollow_id) == null) {
                UserCount userCount = userCountDao.getByUserID(unfollow_id);
                redisService.setUserFanCount(unfollow_id,userCount.getFan_count());
            }
            redisService.decrementUserFollowCount(user_id);
            redisService.decrementUserFanCount(unfollow_id);
        }
        return true;
    }

    @Override
    public Page<UserReportDTO> searchReportedUsersPage(String keyword, Integer pageNum, Integer pageSize, Integer orderType) {
//        Sort sort = null;
//        if(orderType == 1) {
////            sort = Sort.by(Sort.Direction.DESC,"");
//        }else if(orderType == 2){
//            sort = Sort.by(Sort.Direction.ASC, "credits");
//        }else {
//            sort = Sort.by(Sort.Direction.DESC, "credits");
//        }
//        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userDao.searchReportedUsersPage(keyword, pageable);
    }

    @Override
    public UserInfoDTO getUserInfo(Integer user_id1, Integer user_id2) {
        User user = userDao.getById(user_id2);
        Optional<UserFollow> userFollow = userFollowDao.findByUser_idAndFollow_id(user_id1, user_id2);
        if (userFollow.isPresent())
            return new UserInfoDTO(user, true);
        else
            return new UserInfoDTO(user, false);
    }

    @Override
    public UserDTO getUserDTO(Integer user_id1, Integer user_id2) {
        User user = userDao.getById(user_id2);
        Optional<UserFollow> userFollow = userFollowDao.findByUser_idAndFollow_id(user_id1, user_id2);
        UserInfoDTO userInfoDTO = null;
        if (userFollow.isPresent())
            userInfoDTO = new UserInfoDTO(user, true);
        else
            userInfoDTO = new UserInfoDTO(user, false);
        UserCountDTO userCountDTO = new UserCountDTO(getUserCount(user_id2));
        return new UserDTO(userInfoDTO, userCountDTO);
    }

    @Override
    public Page<UserDTO> searchUsersPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<User> userPage = userDao.searchUsersPage(keyword, pageable);
        List<User> userList = userPage.getContent();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserInfoDTO userInfoDTO = new UserInfoDTO(user, false);
            UserCountDTO userCountDTO = new UserCountDTO(getUserCount(user.getUser_id()));
            userDTOList.add(new UserDTO(userInfoDTO, userCountDTO));
        }
        return new PageImpl<>(userDTOList, userPage.getPageable(), userPage.getTotalElements());
    }

    @Override
    public Page<UserBanResult> getUserBanPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userBanDao.getUserBanPage(pageable, new Date());
    }

    @Override
    public Page<UserForbiddenResult> getUserForbidPage(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userBanDao.getUserForbidPage(pageable, new Date());
    }

    @Override
    public Page<UserBanResult> searchUserBanPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userBanDao.searchUserBanPage(keyword, pageable, new Date());
    }

    @Override
    public Page<UserForbiddenResult> searchUserForbidPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userBanDao.searchUserForbidPage(keyword, pageable, new Date());
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public boolean modifyUser(UserModifyParam userModifyParam) {
        User user = userDao.getById(userModifyParam.getUser_id());
        if(user == null) {
            return false;
        }
        user.setAddress(userModifyParam.getAddress());
        user.setAvatar_path(userModifyParam.getAvatar());
        user.setIntroduction(userModifyParam.getIntroduction());
        user.setSex(userModifyParam.getSex());
        userDao.update(user);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NOT_SUPPORTED,readOnly = false)
    public void ReportUser(UserReportParam userReportParam) {
        Integer user_id = userReportParam.getUser_id();
        Integer reporter_id = userReportParam.getReporter_id();
        String report_reason = userReportParam.getReport_reason();
        if(redisService.userIsReported(user_id,reporter_id)) {
            redisService.saveUserReport2Redis(user_id,reporter_id,report_reason);
        }else {
            //redis没有记录，去数据库查
            UserReport userReport = userReportDao.getByUserIdAndReporterId(user_id,reporter_id);
            //刷新举报原因和时间，但是重复举报不增加举报数
            redisService.saveUserReport2Redis(user_id,reporter_id,report_reason);
            if(userReport != null) {
                redisService.incrementUserReportCount(user_id);
            }
        }
    }
    private UserCount getUserCount(Integer user_id) {
        Integer follow = redisService.getUserFollowCountFromRedis(user_id);
        Integer fan = redisService.getUserFanCountFromRedis(user_id);
        Integer blog = redisService.getUserBlogCountFromRedis(user_id);
//        Integer report = redisService.getUserReportCountFromRedis(user_id);
        if(fan == null || follow == null || blog == null ) {
            UserCount userCount = userCountDao.getByUserID(user_id);
            if(fan == null) {
                fan = userCount.getFan_count();
                redisService.setUserFanCount(user_id,fan);
            }
            if(follow == null) {
                follow = userCount.getFollow_count();
                redisService.setUserFollowCount(user_id,follow);
            }
            if(blog == null) {
                blog = userCount.getBlog_count();
                redisService.setUserBlogCount(user_id,blog);
            }
//            if(report == null) {
//                report = userCount.getReport_count();
//                redisService.setUserReportCount(user_id,report);
//            }
        }
        return new UserCount(user_id,follow,fan,blog,0);
    }


}
