package org.open.code.learning.base.design121.observe_mode;

/**
 * 具体观察者：当前天气状况显示器
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
// 具体观察者：当前天气状况显示器
public class CurrentConditionsDisplay implements Observer {
    private float temperature;
    private float humidity;


    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

    public void display() {
        System.out.println("当前天气状况: " + temperature + "°C, 湿度: " + humidity + "%");
    }
}
