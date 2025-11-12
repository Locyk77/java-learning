package org.open.code.learning.base.generic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/11/12
 *
 */
public class UserToUserDTO implements Mapper<UserToUserDTO.User, UserToUserDTO.UserDTO> {

    @Override
    public UserDTO map(User source) {
        return new UserDTO(source.getId(), source.getName());
    }

    static class User {
        private int id;
        private String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    class UserDTO {
        private int userId;
        private String userName;

        public UserDTO(int userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        @Override
        public String toString() {
            return "UserDTO{userId=" + userId + ", userName='" + userName + "'}";
        }
    }

    public static void main(String[] args) {
        // 单个User转换
        Mapper<User, UserDTO> userMapper = new UserToUserDTO();
        User user = new User(1001, "张三");
        UserDTO userDTO = userMapper.map(user);
        System.out.println("单个User转换结果：" + userDTO); // UserDTO{userId=1001, userName='张三'}

        // 批量List<User>转换为List<UserDTO>
        List<User> userList = new ArrayList<>();
        userList.add(new User(1002, "李四"));
        userList.add(new User(1003, "王五"));

        Mapper<List<User>, List<UserDTO>> listMapper = new ListMapper<>(userMapper);//TODO 这一步有点复杂，需要细细体会
        List<UserDTO> userDTOList = listMapper.map(userList);
        System.out.println("批量转换结果：" + userDTOList);
        // 输出：[UserDTO{userId=1002, userName='李四'}, UserDTO{userId=1003, userName='王五'}]
    }
}
