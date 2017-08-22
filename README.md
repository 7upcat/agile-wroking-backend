# agile-wroking-backend

*AgileWorking* 应用后端程序，基于 *SpringBoot* ，项目属于玩票 **性质**，并未使用高级特性，主要是配合项目组进行小程序的技术验证。

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

## 接口清单

接口设计遵循 *Restful* 风格的  **API**，设计，支持以下功能：

- 查询团队列表，返回所有的团队 `/agileworking/teams`

- 查询指定 `openId` 的用户是否有加入指定的团队  **GET** `/agileworking/team/{teamId}/user/{openId}`，如果加入则返回 `User` 信息

- 加入指定的团队  **POST** `/agileworking/team/{id}/join`
 + id/要加入的团队 id 
 + name/姓名
 + mobileNo/手机号
 + openId/微信 openId
 + nickName/微信昵称
 + avatarUrl/微信头像 url 

- 查询指定团队下的所有会议室列表  **GET** `/agileworking/meetingRooms/{teamId}`

- 创建/修改排期  **POST** `/agileworking/meetingRooms/{id}/schedule`
  + id/排期id（可选，创建排期为空）
  + title/标题
  + date/日期（yyyy-MM-dd）
  + startTime/开始时间(hh:min)
  + endTime/结束时间(hh:min)
  + creatorOpenId/创建人微信 openId
  + creatorNickName/创建人微信昵称
  + creatorAvatarUrl/创建人微信头像URL
  + repeatMode/会议重复模式（N-不重复/W-每周）
- 取消排期  **DELETE** `/agileworking/meetingRooms/schedule/{id}`
- 查询指定会议室指定日期的排期  **GET** `/agileworking/meetingRooms/{id}/schedule?date=yyyyMMdd`
- 接受会议邀请  **POST** `/agileworking/schedules/{id}/join`
  + openId/接受邀请人微信 openId
  + nickName/接受邀请人微信昵称
  + avatarUrl/接受邀请人微信头像URL

- 根据 `id` 查询指定的排期  **GET** `/agileworking/schedules/{id}`，含排期的参与人
  
- 查询加入的会议  **GET** `/agileworking/participant/{openId}?date=yyyyMMdd`
  + scheduleId/排期id
  + meetingRoomId/会议室 id
  + title/会议主题
  + openId/参会人的微信 openId
  + roomNo/会议室
  + startTime/开始时间
  + endTime/结束时间 
