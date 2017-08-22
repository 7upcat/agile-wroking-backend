-- 组别初始化数据
INSERT INTO team(id,name,team_desc,token) VALUES (1,'深研二部','一个敏捷的团队~','123456');

-- 会议室初始化数据
INSERT INTO meeting_room (ip, room_no, size, terminal_id, type, vnedor,team_id) VALUES ('182.207.96.163', '3201', '大', '21169', '视频', '思科',1);
INSERT INTO meeting_room (ip, room_no, size, terminal_id, type, vnedor,team_id) VALUES ('182.207.96.166', '3202', '中', '120706', '视频', '华为',1);
INSERT INTO meeting_room (ip, room_no, size, terminal_id, type, vnedor,team_id) VALUES (null, '3203', '小', null, '普通', null,1);
INSERT INTO meeting_room (ip, room_no, size, terminal_id, type, vnedor,team_id) VALUES (null, '3207', '小', null, '普通', null,1);
INSERT INTO meeting_room (ip, room_no, size, terminal_id, type, vnedor,team_id) VALUES (null, '3403', '小', null, '普通', null,1);


