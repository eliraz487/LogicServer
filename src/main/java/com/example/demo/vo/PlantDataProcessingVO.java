package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class PlantDataProcessingVO implements Serializable {
    private Integer id;

    private Long plantAgePlantTypeID;

    private Long checkID;

    private Float rangeOfChange;

    private Float lossingWater;

    private Float waterConsumption;

}
