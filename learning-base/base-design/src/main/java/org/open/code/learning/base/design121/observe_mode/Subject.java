package org.open.code.learning.base.design121.observe_mode;

/**
 * 主题接口
 *
 *@author: Locyk
 *@time: 2025/9/9
 *
 */
public interface Subject {
    // 注册观察者
    void registerObserver(Observer observer);

    // 移除观察者
    void removeObserver(Observer observer);

    // 通知所有观察者
    void notifyObservers();
}
