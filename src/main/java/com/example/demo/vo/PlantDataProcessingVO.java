package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class PlantDataProcessingDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private Long plantAgePlantTypeID;

    private Long checkID;

    private Float rangeOfChange;

    private Float lossingWater;

    private Float waterConsumption;

}
