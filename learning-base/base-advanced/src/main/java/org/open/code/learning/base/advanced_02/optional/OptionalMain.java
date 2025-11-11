package org.open.code.learning.base.advanced_02.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 1-1-2、掌握函数式编程在Java中的应用
 *@author: Locyk
 *@time: 2025/9/8
 *
 */
public class OptionalMain {
    public static void main(String[] args) {
        Optional<User> user1 = Optional.ofNullable(new User("Locyk", new Address("wuhan")));

        String city1 = user1
                .map(User::getAddress)
                .map(Address::getCity)
                .orElseGet(() -> "默认城市1");

        System.out.println(city1);

        Optional<User> user2 = Optional.ofNullable(new User("Locyk", null));


        String city2 = user2
                .map(User::getAddress)
                .map(Address::getCity)
                .orElseGet(() -> "默认城市2");

        System.out.println(city2);

        Optional<User> user3 = Optional.ofNullable(null);


        String city3 = user3
                .map(User::getAddress)
                .map(Address::getCity)
                .orElseGet(() -> "默认城市3");

        System.out.println(city3);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String name;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String city;
    }
}
