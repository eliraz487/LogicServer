package org.example.models;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data
public class SensorVO implements Serializable {



    private Long id;

    private SensorTypeVO type;

    private ArrayList<SensorTypeVSCheckValueVO> checkValues;
}
