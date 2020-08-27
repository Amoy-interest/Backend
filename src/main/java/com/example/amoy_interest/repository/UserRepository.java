package com.example.amoy_interest.repository;

import com.example.amoy_interest.dto.UserReportDTO;
import com.example.amoy_interest.entity.User;
import com.example.amoy_interest.entity.UserFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "from User where credits < 150")
    List<User> getReportedUsers();

    @Query(value = "SELECT new com.example.amoy_interest.dto.UserReportDTO(u.user_id,u.nickname,uc.report_count) FROM User as u join UserCount as uc on uc.user_id = u.user_id WHERE uc.report_count >= 10",
            countQuery = "SELECT count(u.user_id) From User as u left join UserCount as uc on uc.user_id = u.user_id WHERE uc.report_count >= 10"
    )
    Page<UserReportDTO> findReportedUsersPage(Pageable pageable);

    @Query(value = "SELECT new com.example.amoy_interest.dto.UserReportDTO(u.user_id,u.nickname,uc.report_count) From User as u left join UserCount as uc on uc.user_id = u.user_id WHERE uc.report_count >= 10 and u.nickname like %?1%",
            countQuery = "SELECT count(u.user_id) From User as u left join UserCount as uc on uc.user_id = u.user_id WHERE uc.report_count >= 10 and u.nickname like %?1%")
    Page<UserReportDTO> searchReportedUsersPage(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM user_info WHERE nickname like %?1%",
            countQuery = "SELECT count(*) From user_info WHERE nickname like %?1%",
            nativeQuery = true)
    Page<User> searchUsersPage(String keyword, Pageable pageable);
}
