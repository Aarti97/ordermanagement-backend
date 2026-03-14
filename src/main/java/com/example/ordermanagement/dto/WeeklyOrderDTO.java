package com.example.ordermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WeeklyOrderDTO {

    private String date;
    private int count;

}
