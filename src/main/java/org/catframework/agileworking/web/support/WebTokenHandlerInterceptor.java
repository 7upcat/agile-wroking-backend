package org.catframework.agileworking.web.support;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.catframework.agileworking.service.WebTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义拦截器实现了 WebToken 的校验.
 * 
 * @author devzzm
 */
public class WebTokenHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private WebTokenService webTokenService;

	private String[] ignoreUriPatterns;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private String tokenKey = "Authorization";

	private String subjectKey = "Subject";

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		if (isIgnoreUri(req.getRequestURI())) {
			return true;
		} else {
			String token = req.getHeader(tokenKey);
			Assert.notNull(token, "Authorization 不可为空.");
			String subject = req.getHeader(subjectKey);
			Assert.notNull(subject, "Subject 不可为空.");
			Assert.isTrue(webTokenService.verify(subject, token), "Web Token 校验失败.");
			return true;
		}
	}

	private boolean isIgnoreUri(String uri) {
		if (null == ignoreUriPatterns)
			return false;
		return Arrays.asList(ignoreUriPatterns).stream().anyMatch(p -> pathMatcher.match(p, uri));
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e)
			throws Exception {
		// do nothing
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView m)
			throws Exception {
		// do nothing
	}

	public void setWebTokenService(WebTokenService webTokenService) {
		this.webTokenService = webTokenService;
	}

	@Value("${web.token.ignore.uri.pattern}")
	public void setIgnoreUripattern(String ignoreUripattern) {
		if (!StringUtils.isEmpty(ignoreUripattern))
			ignoreUriPatterns = StringUtils.tokenizeToStringArray(ignoreUripattern, ",");
	}
}
