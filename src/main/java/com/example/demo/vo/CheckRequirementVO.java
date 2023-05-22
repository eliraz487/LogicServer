package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class CheckRequirementVO implements Serializable {
    private Long id;

    private Long agePlantTypeID;

    private CheckTableVO checkID;

    private double maxRecommendValue;

    private double minRecommendValue;

    private double maxValidValue;

    private double minValidValue;

}
