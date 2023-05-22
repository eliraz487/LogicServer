package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;


@Data

public class SensorTypeVSCheckVO implements Serializable {



    private Long id;


    private CheckTableVO checkID;


    private Long sensorTypeID;


}
