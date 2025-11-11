package org.open.code.learning.base.design121.observe_mode;

/**
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public class DesignMain121 {
    public static void main(String[] args) {
        // 创建主题
        WeatherData weatherData = new WeatherData();

        // 创建观察者并注册到主题
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay();
        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statisticsDisplay);

        // 模拟气象数据更新
        System.out.println("--- 第一次数据更新 ---");
        weatherData.setMeasurements(25.5f, 65f, 1013.1f);

        System.out.println("\n--- 第二次数据更新 ---");
        weatherData.setMeasurements(26.2f, 63f, 1012.9f);

        System.out.println("\n--- 第三次数据更新 ---");
        weatherData.setMeasurements(24.8f, 68f, 1013.5f);
    }
}
