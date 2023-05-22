package com.example.demo;

import com.example.demo.vo.SensorTypeVO;
import com.example.demo.vo.SensorTypeVSCheckValueVO;
import com.example.demo.vo.SensorVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestConfiguration
class SensorDataValueTest {

    @Test
    public void checkBySendSenorTime() {
        SensorDataValue sensorDataValue=new SensorDataValue();
        SensorVO sensorVO = new SensorVO();
        sensorVO.setId(1L);
        Random random=new Random();
        ArrayList<SensorTypeVSCheckValueVO> sensorTypeVSCheckValueVOS = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            SensorTypeVSCheckValueVO vo = new SensorTypeVSCheckValueVO();
            vo.setCheck("PH");
            vo.setValue(5.0 + (7.2 - 5.0) * random.nextDouble());
            vo.setValueTime(LocalDateTime.now().plus(i*7,ChronoUnit.MINUTES));
            sensorTypeVSCheckValueVOS.add(vo);
        }
        sensorVO.setCheckValues(sensorTypeVSCheckValueVOS);
        System.out.println(sensorTypeVSCheckValueVOS.size());
        sensorDataValue.receiveSensorData(sensorVO);
    }
    @Test
    public void check2() {
        PriorityQueue<SensorTypeVSCheckValueVO> priorityQueue = new PriorityQueue<>(Comparator.comparing(SensorTypeVSCheckValueVO::getValueTime));
        Random random=new Random();
        for (int i = 0; i < 6; i++) {
            SensorTypeVSCheckValueVO vo = new SensorTypeVSCheckValueVO();
            vo.setCheck("PH");
            vo.setValue(1.7 + (7.2 - 1.7) * random.nextDouble());
            vo.setValueTime(LocalDateTime.now().plus(i*5,ChronoUnit.MINUTES));
            priorityQueue.add(vo);
        }
        System.out.println(priorityQueue.size());
    }

}