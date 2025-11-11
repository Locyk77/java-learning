package org.open.code.learning.base.design121.observe_mode;

/**
 * 观察者接口
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface Observer {
    // 当主题状态改变时，会调用此方法更新观察者
    void update(float temperature, float humidity, float pressure);
}
