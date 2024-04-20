package com.yout.bakingaccount.dtos;

import lombok.Data;

@Data
public class TransferDTO {
    private String AcountIdSource;
    private String AcountIdDestination;
    private double mount;

}
