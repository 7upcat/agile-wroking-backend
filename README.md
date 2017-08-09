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

- 查询所有会议室列表  **GET** `/agileworking/meetingRooms`
- 创建排期  **POST** `/agileworking/meetingRooms/{id}/schedule`
  + title/标题
  + date/日期（yyyy-MM-dd）
  + startTime/开始时间(hh:min)
  + endTime/结束时间(hh:min)
  + creatorNickName/创建人微信昵称
  + creatorAvatarUrl/创建人微信头像URL
  + repeatMode/会议重复模式（N-不重复/W-每周）
- 取消排期  **DELETE** `/agileworking/meetingRooms/schedule/{id}`
- 查询指定会议室指定日期的排期  **GET** `/agileworking/meetingRooms/{id}/schedule?date=yyyyMMdd`
- 接受会议邀请  **POST** `/agileworking/schedules/{id}/join`
  + nickName/接受邀请人微信昵称
  + avatarUrl/接受邀请人微信头像URL