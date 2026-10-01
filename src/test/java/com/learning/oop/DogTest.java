package com.learning.oop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OOP 测试
 */
@DisplayName("Dog 类的测试")
class DogTest {

    private Dog dog;

    @BeforeEach
    void setUp() {
        dog = new Dog("旺财", 4, "柴犬");
    }

    @Test
    @DisplayName("测试继承")
    void testInheritance() {
        // Dog 应该是 Animal 的子类
        assertInstanceOf(Animal.class, dog);
        assertEquals("旺财", dog.getName());
        assertEquals(4, dog.getAge());
    }

    @Test
    @DisplayName("测试子类特有方法")
    void testFetch() {
        assertEquals("柴犬", dog.getBreed());
    }

    @Test
    @DisplayName("测试方法重写")
    void testMakeSound() {
        // 重写父类方法:Dog 的叫声与 Animal 不同
        Animal animal = new Animal("动物", 1);
        Dog dog = new Dog("小狗", 1, "土狗");
        // 子类重写后,toString 应包含子类特有字段
        assertTrue(dog.toString().contains("土狗"));
        assertTrue(animal.toString().contains("动物"));
    }

    @Test
    @DisplayName("测试封装:setter 校验")
    void testEncapsulation() {
        dog.setAge(-1);
        assertEquals(4, dog.getAge()); // 应保留原值
    }

    @Test
    @DisplayName("测试多态")
    void testPolymorphism() {
        Animal animal = new Dog("豆豆", 1, "柯基");
        assertInstanceOf(Dog.class, animal);
        assertEquals("豆豆", animal.getName());
    }

    @Test
    @DisplayName("测试 toString")
    void testToString() {
        String str = dog.toString();
        assertTrue(str.contains("旺财"));
        assertTrue(str.contains("柴犬"));
    }
}