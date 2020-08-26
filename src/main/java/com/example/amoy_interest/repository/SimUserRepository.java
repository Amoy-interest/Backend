package com.example.amoy_interest.repository;

import com.example.amoy_interest.dto.SimUserDTO;
import com.example.amoy_interest.entity.SimUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SimUserRepository extends JpaRepository<SimUser, Integer> {
    @Query(value = "select new com.example.amoy_interest.dto.SimUserDTO(u.nickname,u.avatar_path) from SimUser as su left join UserFollow as uf  on (su.sim_id = uf.follow_id and uf.user_id = :my_user_id) join User as u on su.sim_id = u.user_id where su.user_id = :user_id and uf.follow_id is null",
            countQuery = "select count(u.user_id) from SimUser as su left join UserFollow as uf  on (su.sim_id = uf.follow_id and uf.user_id = :my_user_id) join User as u on su.sim_id = u.user_id where su.user_id = :user_id and uf.follow_id is null")
    Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, Pageable pageable);
}
