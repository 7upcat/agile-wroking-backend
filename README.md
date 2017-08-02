# agile-wroking-backend
AgileWorking 应用端程序

## 接口清单

接口设计遵循 *Restful* 风格的  **API**，设计，支持以下功能：

- 查询所有会议室列表  **GET** `/meetingRooms`
- 创建排期  **POST** `/meetingRooms/{id}/schedule`
  + title/标题
  + date/日期（yyyy-MM-dd）
  + startTime/开始时间(hh:min)
  + endTime/结束时间(hh:min)
  + creatorNickName/创建人微信昵称
  + creatorAvatarUrl/创建人微信头像URL
  + repeatMode/会议重复模式（N-不重复/W-每周）
- 取消排期  **DELETE** `/meetingRooms/schedule/{id}`
- 查询指定日期区间排期  **GET** `/meetingRooms/{id}/schedule?from=yyyyMMdd&to=yyyyMMdd`
- 接受会议邀请  **POST** `/schedules/{id}/join`
  + nickName/接受邀请人微信昵称
  + avatarUrl/接受邀请人微信头像URL