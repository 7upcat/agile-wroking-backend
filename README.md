# agile-wroking-backend

 此项目是  *real world* 小程序 *AgileWorking* 应用的后端，*AgileWorking* 是一个用于部门内部会议室及团队管理的小程序。

## 特色

- 全面使用 *Java8* 函数式编码风格，代码简洁、高效，易读
- 采用 [领域模型设计](https://en.wikipedia.org/wiki/Domain-driven_design) 的风格组织代码，应对复杂逻辑
- 使用 *SpringBoot* 构建，*Convension Over Configuration*，零配置
- 提供  *Restful* 风格的  API 供访问
- 支持小程序的会话管理及接口调用的安全控制
  
## 构建

### 前置条件

- Jdk1.8+
- 安装配置 [Maven](http://maven.apache.org/install.html)
- 安装 [Git](https://git-scm.com/downloads)

### 构建步骤

- git clone https://github.com/7upcat/agile-wroking-backend.git
- cd agile-working-backend
- mvn package
- java -jar target\agile-working-backend-0.0.1-SNAPSHOT.jar

## 单元测试

- 进行单元测试前在 *src/main/resources/application.properties*  中设置 profile  `spring.profiles.active=dev`，将会自动连接测试的数据库，并在每个
案例执行前重新建表
- 进行单元测试 `mvn test`

## 接口清单

接口设计遵循 *Restful* 风格的  **API**，**公开** 标签下的服务可以直接访问，**私有** 标签的服务必须在  http header 中指定  `Authorization`（Token） 及 `Subject`（微信 openId）：

- 【公开】通过 `jsCode` 获取用户的  `openId`  `/agileworking/wechat/openid/{jsCode}`，成功查询 payload 中返回的即是 openId

- 【公开】查询团队列表，返回所有的团队 `/agileworking/teams`

- 【公开】查询指定 `openId` 的用户是否有加入指定的团队  **GET** `/agileworking/team/{teamId}/user/{openId}`，如果加入则返回 `User` 信息及 `token`

- 【公开】加入指定的团队  **POST** `/agileworking/team/{id}/join` ，加入成功会返回  `User` 及 `token`

  + name/姓名
  + mobileNo/手机号
  + openId/微信 openId
  + nickName/微信昵称
  + avatarUrl/微信头像 url
  + token/团队的加入口令 

- 【私有】查询指定团队下的所有会议室列表  **GET** `/agileworking/meetingRooms/{teamId}`

- 【私有】创建/修改排期  **POST** `/agileworking/meetingRooms/{id}/schedule?formId=?`
  + id/排期id（可选，创建排期为空）
  + title/标题
  + date/日期 `yyyy-MM-dd` 格式
  + startTime/开始时间(hh:min)
  + endTime/结束时间(hh:min)
  + creatorOpenId/创建人微信 openId
  + creatorNickName/创建人微信昵称
  + creatorAvatarUrl/创建人微信头像URL
  + repeatMode/会议重复模式（N-不重复/W-每周）

- 【私有】取消排期  **DELETE** `/agileworking/meetingRooms/schedule/{id}`

- 【私有】查询指定会议室指定日期的排期  **GET** `/agileworking/meetingRooms/{id}/schedule?date=yyyyMMdd`

- 【私有】接受会议邀请  **POST** `/agileworking/schedules/{id}/join`

  + openId/接受邀请人微信 openId
  + nickName/接受邀请人微信昵称
  + avatarUrl/接受邀请人微信头像URL
  + formId/表单 id ，用于后续的微信消息通知

- 【私有】根据 `id` 查询指定的排期  **GET** `/agileworking/schedules/{id}`，含排期的参与人
  
- 【私有】查询加入的会议  **GET** `/agileworking/participant/{openId}?date=yyyyMMdd`

  +  scheduleId/排期id
  +  meetingRoomId/会议室 id
  +  date/排期的日期
  +  title/会议主题
  +  openId/参会人的微信 openId
  +  roomNo/会议室
  +  start_time/开始时间
  +  endTime/结束时间 
  +  repeatMode/会议重复模式（N-不重复/W-每周）  
