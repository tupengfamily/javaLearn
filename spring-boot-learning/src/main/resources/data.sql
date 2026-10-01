-- 初始数据
-- Spring Boot 启动时自动执行

INSERT INTO t_user (username, email, age, address, create_time, update_time) VALUES
('张三', 'zhangsan@example.com', 25, '北京', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('李四', 'lisi@example.com', 30, '上海', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('王五', 'wangwu@example.com', 22, '广州', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('赵六', 'zhaoliu@example.com', 35, '深圳', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('钱七', 'qianqi@example.com', 28, '杭州', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);