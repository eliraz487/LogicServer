package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class SensorVSPlantsVO implements Serializable {
    
    private Long id;

    private Long plantID;

    private Long sensorID;

}
