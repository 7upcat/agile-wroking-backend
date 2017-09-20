package org.catframework.agileworking.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.catframework.agileworking.vo.ScheduleVO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

	@Autowired
	private EntityManager entityManager;

	/**
	 * 自定义 native query 查询 {@link ScheduleVO} ，略繁琐但是好像没有更好的办法.
	 */
	@Transactional
	@Override
	public List<ScheduleVO> findByOpenIdAndDate(String openId, Date date) {
		String sql = "select  t.schedule_id as scheduleId  ,t.date,t.meeting_room_id  as meetingRoomId,t.title,t.open_id as openId,m.room_no as roomNo,t.start_time as startTime,t.end_time as endTime, t.repeat_mode as repeatMode   from (select  p.schedule_id,p.date,s.meeting_room_id,s.title,p.open_id,s.start_time,s.end_time,s.repeat_mode from participant p left join  schedule  s on    p.schedule_id = s.id  ) as t left join meeting_room m on t.meeting_room_id  = m.id  where (t.open_id=? and t.date=?) or (t.open_id=? and repeat_mode='W')";
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<ScheduleVO> scheduleVOs = query.setResultTransformer(Transformers.aliasToBean(ScheduleVO.class))
				.setParameter(0, openId).setParameter(1, date).setParameter(2, openId).list();
		return scheduleVOs.stream().filter(s -> s.isNeedInclude(date)).map(s -> {
			s.setDate(date);
			return s;
		}).sorted().collect(Collectors.toList());
	}


	@Transactional
	@Override
	public List<ScheduleVO> findByDate(Date date) {
		String sql = "select  t.schedule_id as scheduleId  ,t.date,t.meeting_room_id  as meetingRoomId,t.title,t.open_id as openId,m.room_no as roomNo,t.start_time as startTime,t.end_time as endTime, t.repeat_mode as repeatMode   from (select  p.schedule_id,p.date,s.meeting_room_id,s.title, s.creator_open_id open_id,s.start_time,s.end_time,s.repeat_mode from participant p left join  schedule  s on    p.schedule_id = s.id  ) as t left join meeting_room m on t.meeting_room_id  = m.id  where (t.date=?) or (repeat_mode='W')";
		Session session = entityManager.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<ScheduleVO> scheduleVOs = query.setResultTransformer(Transformers.aliasToBean(ScheduleVO.class))
				.setParameter(0, date).list();
		return scheduleVOs.stream().filter(s -> s.isNeedInclude(date)).map(s -> {
			s.setDate(date);
			return s;
		}).sorted().collect(Collectors.toList());

	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
