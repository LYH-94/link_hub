-- ==========================================
-- LinkHub Database Initialization Script
-- ==========================================

-- 建立資料庫
CREATE DATABASE IF NOT EXISTS linkhub_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_0900_ai_ci;

-- 建立使用者
CREATE USER 'linkhub_user'@'%'
IDENTIFIED BY 'linkhub_user123';

GRANT ALL PRIVILEGES
ON linkhub_db.*
TO 'linkhub_user'@'%';

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
