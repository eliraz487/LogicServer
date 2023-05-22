package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class SensorDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long typeID;

    private ArrayList<SensorTypeVSCheckValueVO> checkValues;

}
