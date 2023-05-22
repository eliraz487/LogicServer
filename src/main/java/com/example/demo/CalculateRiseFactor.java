package com.example.demo;

import com.example.demo.vo.CheckRequirementVO;
import com.example.demo.vo.SensorTypeVSCheckValueVO;
import com.fasterxml.jackson.databind.util.internal.PrivateMaxEntriesMap;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalculateRiseFactor {

    private Long plantID;
    private OnAverageReceive averageReceive;


    private Map<String, PriorityQueue<SensorTypeVSCheckValueVO>> map_of_sensor_type_Check_values_by_time = new HashMap<>();


    private Map<String, CheckRequirementVO> checkRequirementVOMap;

    public CalculateRiseFactor(Long plantID,OnAverageReceive averageReceive) {
        checkRequirementVOMap=DataProject.getCheckRequirement(plantID);
        for (Map.Entry<String, CheckRequirementVO> check_key : checkRequirementVOMap.entrySet()) {
            PriorityQueue<SensorTypeVSCheckValueVO> priorityQueue = new PriorityQueue<>(Comparator.comparing(SensorTypeVSCheckValueVO::getValueTime).reversed());
            map_of_sensor_type_Check_values_by_time.put(check_key.getKey(), priorityQueue);
        }
        this.plantID=plantID;
        this.averageReceive=averageReceive;
    }

    public void addNewCheckValue(SensorTypeVSCheckValueVO checkValueVO) {
        if(!map_of_sensor_type_Check_values_by_time.containsKey(checkValueVO.getCheck()))
        {
            checkRequirementVOMap=DataProject.getCheckRequirement(plantID);
            if(!checkRequirementVOMap.containsKey(checkValueVO.getCheck())){
                //todo throw exception of check in plant not exist
                return;
            }
            PriorityQueue<SensorTypeVSCheckValueVO> SensorTypeVSCheckValuesByTime=new PriorityQueue<>(Comparator.comparing(SensorTypeVSCheckValueVO::getValueTime));
            map_of_sensor_type_Check_values_by_time.put(checkValueVO.getCheck(),SensorTypeVSCheckValuesByTime);
        }
        PriorityQueue<SensorTypeVSCheckValueVO> SensorTypeVSCheckValuesByTime = map_of_sensor_type_Check_values_by_time.get(checkValueVO.getCheck());
        if (SensorTypeVSCheckValuesByTime.isEmpty()) {
            SensorTypeVSCheckValuesByTime.add(checkValueVO);
            map_of_sensor_type_Check_values_by_time.put(checkValueVO.getCheck(),SensorTypeVSCheckValuesByTime);
            return;
        }
        System.out.println(SensorTypeVSCheckValuesByTime.size());
        if (SensorTypeVSCheckValuesByTime.size() != 5) {
            SensorTypeVSCheckValueVO lastValue = SensorTypeVSCheckValuesByTime.remove();
            LocalDateTime last_time_five_minute_age =lastValue.getValueTime().plus(5,ChronoUnit.MINUTES);
            LocalDateTime last_time_six_minute_age =lastValue.getValueTime().plus(6,ChronoUnit.MINUTES);
            if(checkValueVO.getValueTime().isAfter(last_time_five_minute_age)&& checkValueVO.getValueTime().isAfter(last_time_six_minute_age)){
                System.out.println("error");
            }
            SensorTypeVSCheckValuesByTime.add(checkValueVO);
            SensorTypeVSCheckValuesByTime.add(lastValue);
            map_of_sensor_type_Check_values_by_time.put(checkValueVO.getCheck(),SensorTypeVSCheckValuesByTime);
//            SensorTypeVSCheckValueVO lastValue = SensorTypeVSCheckValuesByTime.remove();
//            if (tr) {
//               boolean check= SensorTypeVSCheckValuesByTime.add(lastValue);
//                map_of_sensor_type_Check_values_by_time.put(checkValueVO.getCheck(),SensorTypeVSCheckValuesByTime);
//                return;
//            }
//            // todo need to make to choice about watering pool
            return;
        }
        //Coefficient of Increase = (Current Value - Minimum Recommended Value) / Minimum Recommended Value

        double coefficient_of_increase_total = 0;
        double coefficient_of_increase_count = SensorTypeVSCheckValuesByTime.size();
        for (SensorTypeVSCheckValueVO sensor_value : SensorTypeVSCheckValuesByTime) {
            double current_value = sensor_value.getValue();
            double minimum_recommend_value = checkRequirementVOMap.get(sensor_value.getCheck()).getMinRecommendValue();
            double maximum_recommend_value = checkRequirementVOMap.get(sensor_value.getCheck()).getMaxRecommendValue();;
            double coefficient_of_increase = (current_value - minimum_recommend_value) / (maximum_recommend_value - minimum_recommend_value);
            coefficient_of_increase_total = coefficient_of_increase_total + coefficient_of_increase;
        }
        double coefficient_of_increase_average = coefficient_of_increase_total / coefficient_of_increase_count;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        coefficient_of_increase_average = Double.parseDouble(decimalFormat.format(coefficient_of_increase_average));;

        averageReceive.ReceiveCoefficientOfIncreaseAverage(plantID,checkValueVO,coefficient_of_increase_average);
        //remove data
        SensorTypeVSCheckValuesByTime.clear();
        map_of_sensor_type_Check_values_by_time.put(checkValueVO.getCheck(),SensorTypeVSCheckValuesByTime);
    }
}
