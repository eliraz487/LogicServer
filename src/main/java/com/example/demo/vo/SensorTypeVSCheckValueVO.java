package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data

public class SensorTypeVSCheckValueVO implements Serializable {



    private Long id;

    private String check;

    private double value;


    private LocalDateTime valueTime;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "SensorTypeVSCheckValueVO{" +
                "id=" + id +
                ", check=" + check +
                ", value=" + value +
                ", valueTime=" + valueTime.format(formatter) +
                '}';
    }
}
