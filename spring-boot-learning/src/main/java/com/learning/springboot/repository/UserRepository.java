package com.learning.springboot.repository;

import com.learning.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 用户 Repository(数据访问层)
 * <p>
 * Spring Data JPA 的强大之处:
 * - 继承 JpaRepository 自动获得 CRUD 方法
 * - 方法命名遵循约定即可自动生成 SQL
 * <p>
 * 自动提供的方法:
 * - save(entity): 保存/更新
 * - findById(id): 按 ID 查
 * - findAll(): 查全部
 * - deleteById(id): 按 ID 删
 * - count(): 统计
 * - existsById(id): 判断是否存在
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 按用户名查询(方法命名约定: findBy + 属性名)
     */
    Optional<User> findByUsername(String username);

    /**
     * 按邮箱查询
     */
    Optional<User> findByEmail(String email);

    /**
     * 按年龄范围查询
     */
    List<User> findByAgeBetween(Integer startAge, Integer endAge);

    /**
     * 按地址模糊查询(Containing 等价于 SQL LIKE '%keyword%')
     */
    List<User> findByAddressContaining(String keyword);

    /**
     * 自定义 JPQL 查询
     */
    @Query("SELECT u FROM User u WHERE u.age >= :minAge ORDER BY u.age ASC")
    List<User> findByMinAge(@Param("minAge") Integer minAge);

    /**
     * 自定义 SQL 查询(nativeQuery = true)
     */
    @Query(value = "SELECT * FROM t_user WHERE username LIKE %:keyword% ORDER BY id DESC",
            nativeQuery = true)
    List<User> searchByUsernameLike(@Param("keyword") String keyword);

    /**
     * 按用户名统计
     */
    long countByUsername(String username);
}