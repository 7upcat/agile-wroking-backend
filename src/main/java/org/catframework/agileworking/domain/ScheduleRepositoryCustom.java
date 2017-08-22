package org.catframework.agileworking.domain;

import java.util.Date;
import java.util.List;

import org.catframework.agileworking.vo.ScheduleVO;

/**
 * 自定义 {@link ScheduleRepository} 接口
 * 
 * @author devzzm
 *
 */
public interface ScheduleRepositoryCustom {

	/**
	 * 查询指定日期指定 openId 的排期
	 * 
	 * @param openId 微信 openId
	 * @param date 查询的日期
	 * @return 符合条件的排期值对象
	 */
	List<ScheduleVO> findScheules(String openId, Date date);
}
