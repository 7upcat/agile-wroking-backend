package org.catframework.agileworking.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 会议室实体.
 * 
 * @author devzzm
 */
@Entity
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })  
public class MeetingRoom implements Serializable {

	private static final long serialVersionUID = -1701470269000866582L;

	@Id
	@GeneratedValue
	private Long id;

	/** 会议室房间编号,唯一. */
	@Column(nullable = false,unique = true)
	private String roomNo;

	/** 会议室大小: 大/中/小. */
	@Column(nullable = false)
	private String size;

	/** 会议室类型: 视频/其他. */
	@Column(nullable = false)
	private String type;

	/** 会议室视频设备的厂商,可选:华为/思科. */
	private String vnedor;

	/** 会议室视频设备的ip,可选. */
	private String ip;

	/** 会议室视频设备终端 id,可选. */
	private String terminalId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVnedor() {
		return vnedor;
	}

	public void setVnedor(String vnedor) {
		this.vnedor = vnedor;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

}
