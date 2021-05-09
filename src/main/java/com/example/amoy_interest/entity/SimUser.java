package com.example.amoy_interest.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sim_user")
@IdClass(SimUserPK.class)
public class SimUser {
    @Id
    private int user_id;
    @Id
    private int sim_id;
}
