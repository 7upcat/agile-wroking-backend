package org.catframework.agileworking.web.support;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class AccessControlAllowOriginFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) rsp;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(req, rsp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
