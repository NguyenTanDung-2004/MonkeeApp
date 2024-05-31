package com.example.monkeeapp.Dat.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Expense {
    public String id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date date;
    private BigDecimal money;
    private String note;
    private String type;

    public Expense(){
        id = "ep"+ UUID.randomUUID().toString().substring(0,4);
    }
}
