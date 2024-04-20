package com.yout.bakingaccount.dtos;

import lombok.Data;

@Data
public class DebitDTO {
    private String acountid;
    private double mount;
    private String description;
}
