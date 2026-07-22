-- ==========================================
-- LinkHub Database Initialization Script
-- ==========================================


-- 建立資料庫
CREATE DATABASE IF NOT EXISTS linkhub_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;


-- 使用資料庫
USE linkhub_db;


-- ==========================================
-- 1. 閱讀狀態表
-- ==========================================

CREATE TABLE link_status (
    id TINYINT NOT NULL COMMENT '狀態編號',
    name VARCHAR(20) NOT NULL COMMENT '狀態名稱',

    PRIMARY KEY (id),
    UNIQUE KEY uk_link_status_name (name)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- 初始化狀態資料

INSERT INTO link_status (id, name)
VALUES
    (0, '未讀'),
    (1, '閱讀中'),
    (2, '已讀');



-- ==========================================
-- 2. 連結紀錄表
-- ==========================================

CREATE TABLE link_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '流水號',
    title VARCHAR(255) NOT NULL COMMENT '連結標題',
    url VARCHAR(2048) NOT NULL COMMENT '連結網址',
    url_hash BINARY(32) NOT NULL COMMENT 'SHA-256(url)',
    status_id TINYINT NOT NULL DEFAULT 0 COMMENT '閱讀狀態',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '建立時間',
    last_edited_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最後修改時間',
    
    UNIQUE KEY uk_link_record_url_hash (url_hash),
    PRIMARY KEY (id),
    CONSTRAINT fk_link_record_status FOREIGN KEY (status_id) REFERENCES link_status(id) ON UPDATE RESTRICT ON DELETE RESTRICT
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 範例資料

INSERT INTO link_record
(title, url, url_hash, status_id)
VALUES
(
    'Spring Boot 官方文件',
    'https://docs.spring.io/spring-boot/index.html',
    UNHEX(SHA2('https://docs.spring.io/spring-boot/index.html', 256)),
    1
),
(
    'MyBatis 官方文件',
    'https://mybatis.org/mybatis-3/',
    UNHEX(SHA2('https://mybatis.org/mybatis-3/', 256)),
    2
),
(
    'Docker 官方文件',
    'https://docs.docker.com/',
    UNHEX(SHA2('https://docs.docker.com/', 256)),
    1
),
(
    'OpenAPI Specification',
    'https://spec.openapis.org/oas/latest.html',
    UNHEX(SHA2('https://spec.openapis.org/oas/latest.html', 256)),
    0
),
(
    'Redis 官方文件',
    'https://redis.io/docs/latest/',
    UNHEX(SHA2('https://redis.io/docs/latest/', 256)),
    2
),
(
    'MDN Web Docs',
    'https://developer.mozilla.org/',
    UNHEX(SHA2('https://developer.mozilla.org/', 256)),
    1
),
(
    'Vue.js 官方文件',
    'https://vuejs.org/guide/introduction.html',
    UNHEX(SHA2('https://vuejs.org/guide/introduction.html', 256)),
    1
),
(
    'Java 21 Documentation',
    'https://docs.oracle.com/en/java/javase/21/',
    UNHEX(SHA2('https://docs.oracle.com/en/java/javase/21/', 256)),
    2
);

-- ==========================================
-- 3. 標籤表
-- ==========================================

CREATE TABLE tag (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '流水號',
    name VARCHAR(50) NOT NULL COMMENT '標籤名稱',

    PRIMARY KEY (id),
    UNIQUE KEY uk_tag_name (name)
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 範例資料

INSERT INTO tag (name)
VALUES
    ('Java'),
    ('Spring Boot'),
    ('Backend'),
    ('Database'),
    ('Docker'),
    ('Frontend'),
    ('Vue');

-- ==========================================
-- 4. 連結與標籤關聯表
-- ==========================================

CREATE TABLE link_record_tag (
    link_record_id BIGINT NOT NULL COMMENT '連結流水號',
    tag_id BIGINT NOT NULL COMMENT '標籤流水號',

    PRIMARY KEY (link_record_id, tag_id),
    CONSTRAINT fk_link_record_tag_link_record FOREIGN KEY (link_record_id) REFERENCES link_record(id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_link_record_tag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON UPDATE CASCADE ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- 範例資料

INSERT INTO link_record_tag
(link_record_id, tag_id)
VALUES
    -- Spring Boot 官方文件
    (17, 1),
    (17, 2),
    (17, 3),

    -- MyBatis 官方文件
    (18, 1),
    (18, 3),
    (18, 4),

    -- Docker 官方文件
    (19, 5),
    (19, 3),

    -- MySQL Reference Manual
    (20, 4),
    (20, 3),

    -- Vue.js 官方文件
    (21, 6),
    (21, 7);