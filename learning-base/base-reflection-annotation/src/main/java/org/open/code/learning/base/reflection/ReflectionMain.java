package org.open.code.learning.base.reflection;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 *@author: Locyk
 *@time: 2025/9/10
 *
 */
public class ReflectionMain {
    public static void main(String[] args) throws Exception {
        // 示例1: 获取类的基本信息
        getClassInfo();

        // 示例2: 通过反射创建对象（包括私有构造器）
        createObjects();

        // 示例3: 调用对象的方法（包括私有方法）
        invokeMethods();

        // 示例4: 操作对象的字段（包括私有字段）
        accessFields();


        // 示例5: 处理泛型信息
        handleGenerics();
    }

    //示例1：获取类的基本信息
    private static void getClassInfo() {
        System.out.println("=== 示例1: 获取类的基本信息 ===");
        Class<?> userClass = User.class;
        System.out.println("类名: " + userClass.getName());
        System.out.println("类名(简称): " + userClass.getSimpleName());
        System.out.println("父类: " + userClass.getSuperclass().getName());
        System.out.println("实现的接口: " + Arrays.toString(userClass.getInterfaces()));


        //获取构造器信息
        Constructor<?>[] constructors = userClass.getDeclaredConstructors();
        System.out.println("构造器: ");
        for (Constructor<?> c : constructors) {
            System.out.println("  " + c.getName() + "(" + Arrays.toString(c.getParameterTypes()) + ")");
        }

        //获取方法信息
        Method[] methods = userClass.getDeclaredMethods();
        System.out.println("方法: ");
        for (Method m : methods) {
            System.out.println("  " + m.getName() + "(" + Arrays.toString(m.getParameterTypes()) + ")");
        }

        //获取字段信息
        Field[] fields = userClass.getDeclaredFields();
        System.out.println("字段: ");
        for (Field f : fields) {
            System.out.println("  " + f.getName() + ": " + f.getType());
        }
        System.out.println();
    }

    //示例2：通过反射创建对象（包括私有构造器）
    private static void createObjects() throws Exception {
        System.out.println("=== 示例2: 通过反射创建对象 ===");
        Class<?> userClass = Class.forName("org.open.code.learning.base.reflection.User");

        //1. 通过无参构造器创建（public）
        User user1 = (User) userClass.newInstance();
        System.out.println("通过无参构造器创建：" + user1);

        //2. 通过有参构造器创建（private）
        Constructor<?> constructor = userClass.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);//允许访问私有构造器
        User user2 = (User) constructor.newInstance("Locyk", 18);
        System.out.println("通过私有有参构造器创建：" + user2.getName());
        System.out.println();
    }

    // 示例3: 调用对象的方法
    private static void invokeMethods() throws Exception {
        System.out.println("=== 示例3: 调用对象的方法 ===");
        User user = new User();
        Class<?> userClass = user.getClass();

        //1. 调用public方法
        Method sayHelloMethod = userClass.getMethod("sayHello");
        sayHelloMethod.invoke(user);

        //2. 调用private方法
        Method getInfoMethod = userClass.getDeclaredMethod("getInfo",String.class);
        getInfoMethod.setAccessible(true);//允许访问私有方法
        String result = (String) getInfoMethod.invoke(user,"User Info");
        System.out.println("调用私有方法结果：" + result);

        //3. 调用setter方法设置值
        Method setNameMethod = userClass.getDeclaredMethod("setName",String.class);
        setNameMethod.setAccessible(true);
        setNameMethod.invoke(user,"Locyk");
        user.sayHello();//验证修改结果
        System.out.println();
    }

    //示例4: 操作对象的字段
    private static void accessFields() throws Exception {
        System.out.println("=== 示例4: 操作对象字段 ===");
        User user = new User();
        Class<?> userClass = user.getClass();

        //1. 操作public字段
        Field emailField = userClass.getField("email");
        emailField.set(user, "Locyk@example.com");
        System.out.println("public字段email的值：" + emailField.get(user));

        //2. 操作private字段
        Field nameField = userClass.getDeclaredField("name");
        nameField.setAccessible(true);//允许访问私有字段
        nameField.set(user, "Locyk");
        System.out.println("private字段name的值：" + nameField.get(user));

        Field ageField = userClass.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(user, 18);
        System.out.println("private字段age的值：" + ageField.get(user));
        System.out.println();
    }

    //示例5: 处理泛型信息
    private static void handleGenerics() throws Exception {
        System.out.println("=== 示例5: 处理泛型信息 ===");
        class GenericTest{
            public List<String> stringList;
        }
        Field field = GenericTest.class.getField("stringList");
        Type genericType = field.getGenericType();
        if(genericType instanceof ParameterizedType){
            ParameterizedType paramType = (ParameterizedType) genericType;
            System.out.println("字段类型："+paramType.getRawType());
            System.out.println("泛型参数："+Arrays.toString(paramType.getActualTypeArguments()));
        }
    }
}
