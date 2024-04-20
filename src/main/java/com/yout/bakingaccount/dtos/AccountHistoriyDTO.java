package com.yout.bakingaccount.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoriyDTO {
    private String accountid;
    private double balance;
    private int currentpage;
    private int sizepage;
    private int totalpage;
    private List<AccountOperationDTO> accountHistoriyDTOS;
}
