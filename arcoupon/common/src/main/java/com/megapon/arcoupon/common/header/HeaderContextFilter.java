package com.megapon.arcoupon.common.header;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
public class HeaderContextFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

		HeaderContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(HeaderContextConst.AUTH_TOKEN));
		HeaderContextHolder.getContext().setTransId(httpServletRequest.getHeader(HeaderContextConst.TRANS_ID));
		HeaderContextHolder.getContext().setUserId(httpServletRequest.getHeader(HeaderContextConst.USER_ID));
		
		if(HeaderContextHolder.getContext().getTransId() != null) {
			log.debug("trans_id: {}", HeaderContextHolder.getContext().getTransId());
		}

		filterChain.doFilter(httpServletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
