package org.example.models;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data

public class SensorTypeVSCheckValueVO implements Serializable {



    private Long id;

    private CheckTableVO check;

    private Float value;


    private LocalDateTime valueTime;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "SensorTypeVSCheckValueVO{" +
                "id=" + id +
                ", check=" + check.getTitle() +
                ", value=" + value +
                ", valueTime=" + valueTime.format(formatter) +
                '}';
    }
}
