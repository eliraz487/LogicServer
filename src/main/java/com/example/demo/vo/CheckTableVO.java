package org.example.models;


import lombok.Data;

import java.io.Serializable;


@Data

public class CheckTableVO implements Serializable {
    private static final long serialVersionUID = 1L;

 
    private Long id;

  
    private String title;

}
