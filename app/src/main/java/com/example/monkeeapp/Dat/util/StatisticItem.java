package com.example.monkeeapp.Dat.util;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticItem {
    private int image;
    private String title;
    private BigDecimal money;
}
