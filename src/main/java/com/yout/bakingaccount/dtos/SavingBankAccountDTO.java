package com.yout.bakingaccount.dtos;

import com.yout.bakingaccount.entities.BankAccount;
import com.yout.bakingaccount.enums.AccountStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


import java.util.Date;



@Data


public  class SavingBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private CustomerDTO customerDTO;
    private double intereRate;

}
