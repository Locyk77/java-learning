package org.open.code.learning.base.design_01.observe_mode;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体主题：气象数据
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
// 具体主题：气象数据
public class WeatherData implements Subject {
    private List<Observer> observers;
    private float temperature;  // 温度
    private float humidity;     // 湿度
    private float pressure;     // 气压

    public WeatherData() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    // 当气象数据更新时，调用此方法
    public void measurementsChanged() {
        notifyObservers();
    }

    // 设置气象数据
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
