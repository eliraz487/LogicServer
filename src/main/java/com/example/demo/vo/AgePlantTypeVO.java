package com.example.demo.vo;


import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

@Data
public class AgePlantTypeVO implements Serializable {
 
    private Long id;

    private Long plantTypeID;

    private Date fromDate;

    private Date toDate;

    private Long ages;

}
