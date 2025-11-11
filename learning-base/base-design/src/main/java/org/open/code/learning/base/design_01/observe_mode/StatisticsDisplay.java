package org.open.code.learning.base.design_01.observe_mode;

/**
 * 具体观察者：天气统计显示器
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class StatisticsDisplay implements Observer {
    private float maxTemp = 0.0f;
    private float minTemp = 200;
    private float tempSum = 0.0f;
    private int numReadings;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        tempSum += temperature;
        numReadings++;

        if (temperature > maxTemp) {
            maxTemp = temperature;
        }

        if (temperature < minTemp) {
            minTemp = temperature;
        }

        display();
    }

    public void display() {
        System.out.println("温度统计: 平均 = " + (tempSum / numReadings) +
                "°C, 最高 = " + maxTemp + "°C, 最低 = " + minTemp + "°C");
    }
}

