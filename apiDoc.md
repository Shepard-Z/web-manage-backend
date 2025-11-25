# 接口总览

- [游戏模块](#游戏模块)
- [角色模块](#角色模块)
- [团队成员模块](#团队成员模块)
- [关于我们模块](#关于我们模块)

---

### 说明
- 此文档仅适用于site模块
- 所有接口均使用表头Accept-Language字段添加语言代号，否则默认返回中文
- **语言代号**:zh,en,ja
- **备注**：本文档内所有接口均为GET，无需登录，仅限只读数据库访问用于静态展示。

---

## 游戏模块

### 获取游戏列表

- **接口地址**：`GET /api/game`
- **描述**：获取所有游戏基础信息，用于游戏页面初级展示
- **请求参数**：通用表头语言
- **请求示例**：

```http
GET /api/game
```

- **响应示例**：

```json
{
    "status": 1,
    "message": "success",
    "data": [
        {
            "id": 10001,
            "releaseDate": "2023-01-01",
            "downloadUrl": "http://example.com/game1",
            "imgUrl": "img/game1.jpg",
            "langCode": "ja",
            "name": "ファンタジー・レルム",
            "category": "アドベンチャー",
            "introduction": "冒険の旅",
            "description": "これは冒険ゲームです。",
            "series": "シリーズA",
            "projectType": "インディー",
            "releaseStatus": "リリース済み"
        },
        {
            "id": 10002,
            "releaseDate": "2023-02-01",
            "downloadUrl": "http://example.com/game2",
            "imgUrl": "img/game2.jpg",
            "langCode": "ja",
            "name": "シャドウの契約",
            "category": "パズル",
            "introduction": "謎を解く",
            "description": "これはパズルゲームです。",
            "series": "シリーズB",
            "projectType": "インディー",
            "releaseStatus": "開発中"
        },
        {
            "id": 10003,
            "releaseDate": "2023-03-01",
            "downloadUrl": "http://example.com/game3",
            "imgUrl": "img/game3.jpg",
            "langCode": "ja",
            "name": "終焉のエコー",
            "category": "アクション",
            "introduction": "激しい戦闘",
            "description": "これはアクションゲームです。",
            "series": "シリーズC",
            "projectType": "インディー",
            "releaseStatus": "計画中"
        }
    ]
}
```

### 获取游戏详情

- **接口地址**：`GET /api/game/{gameId}`
- **描述**：获取指定游戏详情以及关联角色信息，用于游戏详情页展示
- **请求参数**：通用表头语言


| 参数名 | 类型 | 是否必填 | 默认值 | 说明   |
| ------ | ---- | -------- | ------ | ------ |
| gameId | Long | 是       | 无     | 游戏id |

- **请求示例**：

```http
GET /api/game/10001
```

- **响应示例**：

```json
{
    "status": 1,
    "message": "success",
    "data": {
        "gameVO": {
            "id": 10001,
            "releaseDate": "2023-01-01",
            "downloadUrl": "http://example.com/game1",
            "imgUrl": "img/game1.jpg",
            "langCode": "en",
            "name": "Fantasy Realm",
            "category": "Adventure",
            "introduction": "An epic journey",
            "description": "This is an adventure game.",
            "series": "Series A",
            "projectType": "Indie",
            "releaseStatus": "Released"
        },
        "characterList": [
            {
                "id": 20001,
                "gameId": null,
                "imgUrl": "http://localhost:8087/uploads/character/Ellie.jpg",
                "langCode": "en",
                "name": "Ellie"
            },
            {
                "id": 20002,
                "gameId": null,
                "imgUrl": "http://localhost:8087/uploads/character/Lina.jpg",
                "langCode": "en",
                "name": "Lina"
            },
            {
                "id": 20003,
                "gameId": null,
                "imgUrl": "http://localhost:8087/uploads/character/Ivy.jpg",
                "langCode": "en",
                "name": "Ivy"
            },
            {
                "id": 20004,
                "gameId": null,
                "imgUrl": "img/chara1_4.jpg",
                "langCode": "en",
                "name": "Carl"
            }
        ]
    }
}
```

---

## 角色模块

### 获取角色列表

- **接口地址**：`GET /api/character`
- **描述**：获取全部角色与基础信息，用于角色页面展示
- **请求参数**：通用表头语言
- **请求示例**：

```http
GET /api/characters
```

- **响应示例**：

```json
{
  "code": 0,
  "message": "success",
  "data": {
    "total": 25,
    "items": [
      {
        "id": 1001,
        "name": "ユウナ",
        "description": "召喚士",
        "gender": "女性",
        "image_url": "https://example.com/image/yuna.jpg",
        "game_id": 123,
        "game_name": "ファイナルファンタジーX"
      }
    ]
  }
}
```

---

## 团队成员模块

### 获取团队成员列表

- **接口地址**：`GET /api/team`
- **描述**：获取团队展示页成员信息
- **请求参数**：无
- **响应字段**：`id`, `name`, `role`, `avatar_url`, `bio`

---

## 关于我们模块

### 获取团队信息

---
