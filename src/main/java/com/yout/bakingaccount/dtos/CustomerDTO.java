package com.yout.bakingaccount.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yout.bakingaccount.entities.BankAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;
    private String nom;
    private String gmail;

}
