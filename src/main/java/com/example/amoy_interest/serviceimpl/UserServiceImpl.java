package com.example.amoy_interest.serviceimpl;

import com.example.amoy_interest.dao.*;
import com.example.amoy_interest.dto.*;
import com.example.amoy_interest.entity.*;
import com.example.amoy_interest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
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

    @Override
    public UserAuth findUserAuthById(Integer id) {
        return userAuthDao.findUserById(id);
    }

    @Override
    public UserAuth findUserAuthByUsername(String username) {
        return userAuthDao.findUserByUsername(username);
    }

    @Override
    @Transactional
    public UserInfoDTO register(RegisterDTO registerDTO) {
        UserAuth userAuth = new UserAuth(registerDTO.getUsername(), registerDTO.getPassword(), 0, 0, 0);
        userAuth = userAuthDao.insert(userAuth);
        Integer user_id = userAuth.getUser_id();
        User user = new User(user_id, registerDTO.getNickname(), registerDTO.getEmail(), registerDTO.getSex(),
                registerDTO.getAddress(), 100, "这个人很懒，什么都没留下", null
        );
        userDao.insert(user);
        UserCount userCount = new UserCount(user_id, 0, 0, 0);
        userCountDao.insert(userCount);
        user.setUserAuth(userAuth);
        UserRole userRole = new UserRole();
        userRole.setRole_name("user");
        userRole.setUsername(registerDTO.getUsername());
        roleDao.insert(userRole);
        return new UserInfoDTO(user, false);
    }

    @Override
    public boolean follow(Integer user_id, Integer follow_id) {
        UserFollow userFollow = new UserFollow(user_id, follow_id);
        userFollowDao.insert(userFollow);
        return true;
    }

    @Override
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
    public boolean unban(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_ban(0);
        userAuthDao.update(userAuth);
        return true;
    }

    @Override
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
    public boolean permit(Integer user_id) {
        UserAuth userAuth = userAuthDao.findUserById(user_id);
        userAuth.setIs_forbidden(0);
        userAuthDao.update(userAuth);
        return true;
    }

    @Override
    public List<UserReportDTO> getReportedUsers() {
        List<User> userList = userDao.getReportedUsers();
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        for (User user : userList) {
            UserReportDTO userReportDTO = new UserReportDTO(user);
            userReportDTOList.add(userReportDTO);
        }
        return userReportDTOList;
    }

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
        Page<User> userPage = userDao.getReportedUsersPage(pageable);
        List<User> userList = userPage.getContent();
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        for (User user : userList) {
            userReportDTOList.add(new UserReportDTO(user));
        }
        return new PageImpl<>(userReportDTOList, userPage.getPageable(), userPage.getTotalElements());
    }

    @Override
    public Page<UserInfoDTO> getUserFollowPage(Integer my_user_id, Integer user_id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<UserFollow> userFollowPage = userFollowDao.findFollowPageByUser_id(user_id, pageable);
        List<UserFollow> userFollowList = userFollowPage.getContent();
        List<UserInfoDTO> userInfoDTOList = new ArrayList<>();
        if(my_user_id == user_id) {
            for (UserFollow userFollow : userFollowList) {
                User user = userDao.getById(userFollow.getFollow_id());
                userInfoDTOList.add(new UserInfoDTO(user, true));
            }
        }else {
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
    @Transactional
    public Page<UserInfoDTO> getUserFanPage(Integer my_user_id,Integer user_id, Integer pageNum, Integer pageSize) {
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
    @Transactional
    public boolean unfollow(Integer user_id, Integer unfollow_id) {
        UserFollow userFollow = new UserFollow(user_id, unfollow_id);
        userFollowDao.delete(userFollow);
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
        Page<User> userPage = userDao.searchReportedUsersPage(keyword, pageable);
        List<User> userList = userPage.getContent();
        List<UserReportDTO> userReportDTOList = new ArrayList<>();
        for (User user : userList) {
            userReportDTOList.add(new UserReportDTO(user));
        }
        return new PageImpl<>(userReportDTOList, userPage.getPageable(), userPage.getTotalElements());
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
        UserCountDTO userCountDTO = new UserCountDTO(userCountDao.getByUserID(user_id2));
        return new UserDTO(userInfoDTO,userCountDTO);
    }

    @Override
    public Page<UserDTO> searchUsersPage(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<User> userPage = userDao.searchUsersPage(keyword, pageable);
        List<User> userList = userPage.getContent();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user:userList) {
            UserInfoDTO userInfoDTO = new UserInfoDTO(user,false);
            UserCountDTO userCountDTO = new UserCountDTO(userCountDao.getByUserID(user.getUser_id()));
            userDTOList.add(new UserDTO(userInfoDTO,userCountDTO));
        }
        return new PageImpl<>(userDTOList, userPage.getPageable(), userPage.getTotalElements());
    }
}
