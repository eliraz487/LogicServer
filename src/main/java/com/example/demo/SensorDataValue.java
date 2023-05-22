package com.example.demo;

import com.example.demo.vo.CheckRequirementVO;
import com.example.demo.vo.SensorTypeVSCheckValueVO;
import com.example.demo.vo.SensorVO;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SensorDataValue implements OnAverageReceive {


    Map<Long, LocalDateTime>riseFactorAverageForPlantTimeMap=new HashMap<>();

    Map<Long, Map<String, Double>> calculateRiseFactorAverageForPlant = new HashMap<>();
    private Map<Long, Map<String, CheckRequirementVO>> checkRequirementVOS;
    private Map<Long, CalculateRiseFactor> calculate_rise_factor_check_for_Plant = new HashMap<>();

    public  void receiveSensorData(SensorVO sensor) {

        Long plantID = sensor.getId();//todo change getId() to get plant id
        Map<String, CheckRequirementVO> plantCheckRequest = DataProject.getCheckRequirement(plantID);//todo make it use common list cash


        for (SensorTypeVSCheckValueVO checkValueVO : sensor.getCheckValues()) {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
             checkValueVO.setValue( Double.parseDouble(decimalFormat.format(checkValueVO.getValue())));
            CheckRequirementVO checkRequirement = plantCheckRequest.get(checkValueVO.getCheck());
            if (checkValueVO.getValue() >= checkRequirement.getMaxValidValue() || checkValueVO.getValue() <= checkRequirement.getMinValidValue()) {
                sensorDamageAlert();
                return;
            }
            calculateRiseFactor(plantID, checkValueVO);
        }

    }


    private void calculateRiseFactor(Long plantID, SensorTypeVSCheckValueVO checkValueVO) {
        if (!calculate_rise_factor_check_for_Plant.containsKey(plantID))
        {
            CalculateRiseFactor calculateRiseFactor = new CalculateRiseFactor(plantID,  this);
            calculate_rise_factor_check_for_Plant.put(plantID, calculateRiseFactor);
        }
        CalculateRiseFactor calculateRiseFactor = calculate_rise_factor_check_for_Plant.get(plantID);
        calculateRiseFactor.addNewCheckValue(checkValueVO);
    }

    // 3.1. Sensor damage alert
    public void sensorDamageAlert() {

        // Raise alert for sensor damage
        // ...
    }



    @Override
    public void ReceiveCoefficientOfIncreaseAverage(Long plantID, SensorTypeVSCheckValueVO checkValueVO, double coefficient_of_increase_average) {

        System.out.println(plantID +" : "+checkValueVO.getCheck()+" average  is : " +coefficient_of_increase_average);
        Map<String, Double> CalculateRiseFactorCheckMap = calculateRiseFactorAverageForPlant.get(plantID);
        if (CalculateRiseFactorCheckMap == null) {
            riseFactorAverageForPlantTimeMap=new HashMap<>();
            CalculateRiseFactorCheckMap = new HashMap<>();
        }
        if(CalculateRiseFactorCheckMap.size()==0){
            riseFactorAverageForPlantTimeMap.put(plantID,LocalDateTime.now());
        }
        CalculateRiseFactorCheckMap.put(checkValueVO.getCheck(), coefficient_of_increase_average);
        calculateRiseFactorAverageForPlant.put(plantID, CalculateRiseFactorCheckMap);
        Map<String, CheckRequirementVO> checkRequirementVOS=DataProject.getCheckRequirement(plantID);
        double check_calculate_rise_factor_average_exist_percent=(checkRequirementVOS.size()/calculateRiseFactorAverageForPlant.get(plantID).size())*100;
        if(LocalDateTime.now().isAfter(riseFactorAverageForPlantTimeMap.get(plantID).plus(5, ChronoUnit.MINUTES))) {
            if (check_calculate_rise_factor_average_exist_percent > 80) {
                makeChoice(plantID);
                //make a choice by your data
                return;
            }
            if (check_calculate_rise_factor_average_exist_percent > 60) {
                //try collect more data by same plant
                //how many you have if you don't have more than 80 try collect more data by same check
                //if you still don't have more than 60 alert for the manager
                //make a choice by your data
            }
            //todo remove data
            riseFactorAverageForPlantTimeMap.remove(plantID);
        }
        calculate_rise_factor_check_for_Plant.get(plantID);
    }

    private void makeChoice(Long plantID) {
        Map<String ,Double> check_rise_factor_average_map= calculateRiseFactorAverageForPlant.get(plantID);
        for (Map.Entry<String,Double> check_rise_factor_average:check_rise_factor_average_map.entrySet()) {
            //calculate
            //remove data
            check_rise_factor_average_map.remove(check_rise_factor_average.getKey());
        }
        calculateRiseFactorAverageForPlant.put(plantID,check_rise_factor_average_map);
    }
}

