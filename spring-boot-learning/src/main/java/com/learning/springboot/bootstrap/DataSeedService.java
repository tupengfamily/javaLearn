package com.learning.springboot.bootstrap;

import com.learning.springboot.entity.Permission;
import com.learning.springboot.entity.Role;
import com.learning.springboot.entity.User;
import com.learning.springboot.repository.PermissionRepository;
import com.learning.springboot.repository.RoleRepository;
import com.learning.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据种子 Service
 * <p>
 * 使用 JPA 保存主表数据,用 JdbcTemplate 显式写入多对多/一对多关系表,
 * 绕开 JPA + SQLite 在 @ManyToMany / @OneToMany 上静默不写关系表的问题。
 */
@Service
public class DataSeedService {

    private static final Logger log = LoggerFactory.getLogger(DataSeedService.class);

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JdbcTemplate jdbc;

    private static final List<String[]> PERMISSION_SEEDS = List.of(
        new String[]{"user:list",    "用户查看", "MENU"},
        new String[]{"user:edit",    "用户编辑", "BUTTON"},
        new String[]{"role:list",    "角色查看", "MENU"},
        new String[]{"role:edit",    "角色编辑", "BUTTON"},
        new String[]{"dict:list",    "字典查看", "MENU"},
        new String[]{"dict:edit",    "字典编辑", "BUTTON"},
        new String[]{"log:list",     "日志查看", "MENU"},
        new String[]{"dashboard:view","仪表盘",  "MENU"}
    );

    private static final List<String[]> DICT_SEEDS = List.of(
        // typeCode, typeName, description, [items...]
        new String[]{"user_status", "用户状态", "启用/禁用", "1", "启用", "0", "禁用"},
        new String[]{"gender",      "性别",     "用户性别", "M", "男",   "F", "女"}
    );

    @Transactional(rollbackFor = Exception.class)
    public void seedPermissions() {
        for (String[] p : PERMISSION_SEEDS) {
            String code = p[0];
            boolean exists = permissionRepository.findAll().stream()
                .anyMatch(x -> code.equals(x.getCode()));
            if (!exists) {
                permissionRepository.save(new Permission(code, p[1], p[2]));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void seedRoles() {
        if (!roleRepository.existsByCode("ADMIN")) {
            jdbc.update(
                "INSERT INTO t_role (code, name, description, create_time, update_time) VALUES (?, ?, ?, datetime('now', 'localtime'), datetime('now', 'localtime'))",
                "ADMIN", "管理员", "系统管理员,拥有全部权限");
            Long adminId = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
            for (String code : List.of("user:list", "user:edit", "role:list", "role:edit",
                                       "dict:list", "dict:edit", "log:list", "dashboard:view")) {
                Long permId = jdbc.queryForObject(
                    "SELECT id FROM t_permission WHERE code = ?", Long.class, code);
                jdbc.update("INSERT OR IGNORE INTO t_role_permission (role_id, permission_id) VALUES (?, ?)",
                    adminId, permId);
            }
        }
        if (!roleRepository.existsByCode("USER")) {
            jdbc.update(
                "INSERT INTO t_role (code, name, description, create_time, update_time) VALUES (?, ?, ?, datetime('now', 'localtime'), datetime('now', 'localtime'))",
                "USER", "普通用户", "默认注册用户,只读权限");
            Long userId = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
            for (String code : List.of("user:list", "dict:list", "dashboard:view")) {
                Long permId = jdbc.queryForObject(
                    "SELECT id FROM t_permission WHERE code = ?", Long.class, code);
                jdbc.update("INSERT OR IGNORE INTO t_role_permission (role_id, permission_id) VALUES (?, ?)",
                    userId, permId);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void seedDictTypes() {
        for (String[] ds : DICT_SEEDS) {
            String typeCode = ds[0];
            if (!existsDictType(typeCode)) {
                jdbc.update(
                    "INSERT INTO t_dict_type (type_code, type_name, description, status, create_time, update_time) VALUES (?, ?, ?, 1, datetime('now', 'localtime'), datetime('now', 'localtime'))",
                    ds[0], ds[1], ds[2]);
                Long typeId = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
                // items: ds[3..]
                int sort = 1;
                for (int i = 3; i < ds.length; i += 2) {
                    jdbc.update(
                        "INSERT INTO t_dict_item (type_id, type_code, item_code, item_value, sort, status, create_time, update_time) VALUES (?, ?, ?, ?, ?, 1, datetime('now', 'localtime'), datetime('now', 'localtime'))",
                        typeId, ds[0], ds[i], ds[i + 1], sort++);
                }
            }
        }
    }

    private boolean existsDictType(String typeCode) {
        Integer c = jdbc.queryForObject(
            "SELECT COUNT(*) FROM t_dict_type WHERE type_code = ?", Integer.class, typeCode);
        return c != null && c > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void seedDefaultUsers() {
        Role adminRole = roleRepository.findByCode("ADMIN").orElseThrow();
        Role userRole = roleRepository.findByCode("USER").orElseThrow();

        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin",
                passwordEncoder.encode("admin123"),
                "admin@example.com", 30, "系统");
            admin.setStatus(1);
            admin.setRoles(new HashSet<>(Set.of(adminRole)));
            User saved = userRepository.save(admin);
            // JPA 已自动写 t_user_role,无需 JDBC 干预
        }
        if (!userRepository.existsByUsername("user")) {
            User u = new User("user",
                passwordEncoder.encode("user123"),
                "user@example.com", 25, "默认账号");
            u.setStatus(1);
            u.setRoles(new HashSet<>(Set.of(userRole)));
            userRepository.save(u);
        }
    }
}