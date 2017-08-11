package org.catframework.agileworking.web.support;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.catframework.agileworking.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器，统一将异常转换为 {@link Result}.
 * 
 * @author devzzm
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler({ BusinessException.class, Exception.class })
	@ResponseBody
	ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
		logger.error("handle exception:",ex);
		return new ResponseEntity<>(DefaultResult.newFailResult(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
