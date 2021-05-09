package com.example.amoy_interest.dao;
import com.example.amoy_interest.dto.SimUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SimUserDao {
    Page<SimUserDTO> getSimUserUsingUser_id(Integer my_user_id, Integer user_id, Pageable pageable);
}

