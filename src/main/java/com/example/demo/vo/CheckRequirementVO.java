package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class WateringRequirementVO implements Serializable {
    private Long id;

    private Long agePlantTypeID;

    private Long checkID;

    private double maxRecommendValue;

    private double minRecommendValue;

    private double maxValidValue;

    private double minValidValue;

}
