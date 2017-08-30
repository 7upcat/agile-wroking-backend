package org.catframework.agileworking.service;

/**
 * 小程序的会话使用 WebToken 的方式跟踪，非使用传统的 session ，此服务提供 WebToken 相关的功能.
 * 
 * @author devzzm
 */
public interface WebTokenService {

	/**
	 * 使用指定的主题生成 token
	 * 
	 * @param subject 指定的信息
	 * @return 生成的 token
	 */
	String generate(String subject);

	/**
	 * 校验指定的主题的 token 是否福匹配
	 * 
	 * @param token 被校验的 token
	 * @return 当校验成功时返回 <code>true</code>
	 */
	boolean verify(String subject, String token);
}
