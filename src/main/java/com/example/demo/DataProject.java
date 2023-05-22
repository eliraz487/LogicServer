package com.example.demo;

import com.example.demo.vo.CheckRequirementVO;
import com.example.demo.vo.PlantDataProcessingVO;

import java.util.HashMap;
import java.util.Map;

public class DataProject {


    public static Map<String, CheckRequirementVO> getCheckRequirement(Long PlantID) {
        Map<String, CheckRequirementVO> wateringRequirementVOS = new HashMap<>();

        CheckRequirementVO wateringRequirement = new CheckRequirementVO();
        wateringRequirement = new CheckRequirementVO();
        wateringRequirement.setMaxRecommendValue(5.0);
        wateringRequirement.setMinRecommendValue(2.5);
        wateringRequirement.setMaxValidValue(7.2);
        wateringRequirement.setMinValidValue(1.7);
        wateringRequirementVOS.put("PH", wateringRequirement);
        return wateringRequirementVOS;
    }
    public static Map<String, PlantDataProcessingVO> dataProcessingMap(Long plantID){
        Map<String, PlantDataProcessingVO> dataProcessingVOMap=new HashMap<>();
        //todo go to the database
        return dataProcessingVOMap;
    }
}
