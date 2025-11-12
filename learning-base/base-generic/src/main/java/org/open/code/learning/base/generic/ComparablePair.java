package org.open.code.learning.base.generic;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class ComparablePair<T extends Comparable<T>> {
    private T first;
    private T second;

    public ComparablePair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    private T getMax() {
        return first.compareTo(second) >= 0 ? first : second;
    }

    static class User {
        private String name;

        public User(String name) {
            this.name = name;
        }
    }

    static class Team implements Comparable<Team> {
        private String name;

        public Team(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Team o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        ComparablePair<Integer> pair = new ComparablePair<>(10, 20);
        ComparablePair<String> stringPair = new ComparablePair<>("apple", "banana");
        System.out.println(pair.getMax());
        System.out.println(stringPair.getMax());
        //TODO 泛型类中不能使用非 Comparable 的类型,User未实现 Comparable 接口，因此IDEA代码检查会报错（有一个误区，IDEA检查报错不是编译报错，IDEA检查报错不是不能执行代码，执行代码的时候会进行编译，这个时候出现错误就是编译错误，当然可以理解成，绝大部分IDEA检查报错，执行的时候都会出现编译报错）
//        User user1 = new User("apple");
//        User user2 = new User("banana");
//        ComparablePair<User> userPair = new ComparablePair<>(user1, user2);
        Team team1 = new Team("banana");
        Team team2 = new Team("apple");
        ComparablePair<Team> teamPair = new ComparablePair<>(team1, team2);
        System.out.println(teamPair.getMax().name);
    }
}
