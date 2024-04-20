package com.yout.bakingaccount.dtos;

import com.yout.bakingaccount.entities.BankAccount;
import com.yout.bakingaccount.enums.OpertionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data

public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;

    private OpertionType type;

private String description;
}
