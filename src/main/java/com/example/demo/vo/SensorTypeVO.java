package org.example.models;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data

public class SensorTypeVO implements Serializable {
    private Long id;

    private String type;

    private ArrayList<SensorTypeVSCheckVO>Checks;

}
