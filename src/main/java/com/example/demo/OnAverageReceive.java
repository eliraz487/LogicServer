package com.example.demo;

import com.example.demo.vo.SensorTypeVSCheckValueVO;

public interface OnAverageReceive {
    void
    ReceiveCoefficientOfIncreaseAverage(Long plantID,SensorTypeVSCheckValueVO checkValueVO, double coefficient_of_increase_average);


}
