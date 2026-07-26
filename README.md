# LinkHub

LinkHub 是一個 URL 連結管理 Web 應用程式，提供連結的增刪改查、標籤過濾、時間排序、分頁查詢，以及貼上 URL 後自動取得網頁標題等功能。

## 功能特色

- **連結 CRUD**：新增、編輯、刪除、查詢連結記錄
- **自動取得元數據**：使用者貼上 URL 後，自動發送 `/url/metadata` 請求獲取網頁標題
- **標籤過濾**：透過下拉選單選擇標籤，快速篩選連結
- **時間排序**：支援依建立時間升降序排列
- **分頁查詢**：每頁顯示 10 筆連結記錄，避免一次查詢過多資料

## 快速啟動（Docker）

本專案提供 Docker 與 Docker Compose 設定，可一句指令啟動完整環境（包含 MySQL 資料庫與應用程式），無需手動建立資料表結構。

### 環境需求

- Docker 20.10+
- Docker Compose 1.29+

### 啟動步驟

1. 確保 Docker 與 Docker Compose 已安裝並啟動
2. 在專案根目錄執行：

```bash
docker-compose up --build
```

3. 等待啟動完成後，開啟瀏覽器前往：
```
http://localhost:8080/pages/linkRecord.html
```

### 說明

- `docker-compose up --build` 會自動完成以下事項：
  - 建置 Spring Boot 應用程式 Docker 映像檔
  - 啟動 MySQL 8.0 容器
  - 自動執行 `docs/database.sql` 建立資料表結構
  - 啟動應用程式容器並連接 MySQL
- MySQL 資料庫資料會持久化儲存在 Docker volume `mysql_data` 中
- 資料庫連線設定已內建在 `docker-compose.yml`，无需額外修改

### 停止與清除

```bash
# 停止服務（保留資料庫資料）
docker-compose down

# 停止服務並刪除資料庫資料（重新初始化）
docker-compose down -v
```

## 開始使用（本機開發模式）

### 1. 環境需求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 2. 資料庫設定

1. 建立資料庫並匯入 `docs/database.sql`
2. 修改 `src/main/resources/application.yml` 中的資料庫連線資訊

### 3. 啟動應用程式

```bash
./mvnw.cmd spring-boot:run
```

### 4. 訪問應用程式

開啟瀏覽器前往：
```
http://localhost:8080/pages/linkRecord.html
```

## 技術堆疊

### 後端
- Java 17
- Spring Boot 3.5.3
- MyBatis 3.5.17
- MySQL
- Maven

### 前端
- HTML5
- CSS3
- Bootstrap 5.3.3
- Vue 3（CDN）
- Fetch API

## 專案結構

```
src/main/java/com/lyh/linkhub/
├── LinkhubApplication.java          # Spring Boot 啟動類別
├── controller/
│   ├── LinkRecordController.java    # 連結記錄 REST API
│   └── UrlMetadataController.java   # URL 元數據 API
├── service/
│   ├── LinkRecordService.java       # 連結記錄業務邏輯
│   └── UrlMetadataService.java      # URL 元數據取得服務
├── pojo/
│   ├── LinkRecord.java
│   ├── LinkRecordPage.java
│   ├── CreateLinkRecordRequest.java
│   ├── UpdateLinkRecordRequest.java
│   ├── UrlMetadataRequest.java
│   └── UrlMetadataResponse.java
└── mapper/
    ├── LinkRecordMapper.java
    ├── LinkRecordMapper.xml
    ├── TagMapper.java
    └── TagMapper.xml

src/main/resources/
├── application.yml                  # 應用程式設定
├── mapper/                          # MyBatis XML 映射檔案
└── static/
    ├── pages/
    │   └── linkRecord.html          # 前端主頁面（含內嵌 Vue3 JavaScript）
    └── styles/
        └── style.css                # 前端樣式

docs/
├── database.sql                     # 資料庫結構與種子資料
└── openapi.yaml                     # API 規格文件
```

## 資料庫設計

請參考 `docs/database.sql` 建立資料庫與資料表。主要表格包括：

- `link_status`：連結閱讀狀態（未讀、閱讀中、已讀）
- `link_record`：連結記錄主表
- `tag`：標籤表
- `link_record_tag`：連結與標籤關聯表

## API 規格

詳細 API 規格請參考 `docs/openapi.yaml`。

### 主要端點

| 方法 | 路徑 | 說明 |
|------|------|------|
| GET | `/linkRecord` | 查詢連結記錄（支援分頁、排序、標籤過濾） |
| POST | `/linkRecord` | 新增連結記錄 |
| PUT | `/linkRecord/{id}` | 更新連結記錄 |
| DELETE | `/linkRecord/{id}` | 刪除連結記錄 |
| GET | `/linkRecord/tags` | 取得所有標籤列表 |
| POST | `/url/metadata` | 從 URL 獲取元數據（網頁標題） |

## AI 輔助開發聲明

本專案由 **AI 輔助開發** 完成。

### 使用工具

- **Kilo**（互動式 CLI AI 程式設計助手）

### 使用模型

- **kilo-auto/free**

### 貢獻歸屬

#### 使用者（專案擁有者）的貢獻

- 專案目錄結構建立與程式碼目錄規劃（`controller`、`service`、`pojo`、`mapper` 等）
- 資料庫表結構設計（`docs/database.sql`）
- Web API 規格設計（`docs/openapi.yaml`）
- 技術堆疊選型與確認（Java 17、Spring Boot 3.5.3、MyBatis、MySQL、Vue3 CDN 等）
- 專案初始化設定（`pom.xml`、`application.yml` 資料庫連線、`LinkhubApplication.java`）
- 前端頁面雛型定義
- 功能需求定義與規格確認
- 關鍵實作決策（例如：metadata 使用 `HttpURLConnection`、tag 僅操作關聯表不自動建立等）
- Docker 容器化部署、volume 配置

#### AI（Kilo / kilo-auto/free）的貢獻

- 根據 `database.sql` 與 `openapi.yaml`，撰寫完整的後端程式碼：
  - POJO 類別（`LinkRecord`、`LinkRecordPage`、Request / Response）
  - MyBatis Mapper 介面與 XML 映射檔案
  - Service 層業務邏輯（CRUD、分頁、排序、Tag 關聯處理）
  - Controller 層 REST API 實作
  - `/url/metadata` 端點實作（使用 `HttpURLConnection` 抓取 `<title>`）
- 前端頁面：
  - 根據雛型定義撰寫前端頁面，內嵌 Vue3（CDN）+ Fetch API
  - 實作 Tag 下拉篩選、閱讀狀態顯示、分頁、排序等功能
- Dockerfile 與 docker-compose.yml 撰寫
- `application.yml` 環境變數化更新
- 程式碼除錯與修正（編譯錯誤、API 規格對齊、生命週期掛鉤位置錯誤等）
- README.md 撰寫與章節重新編排
