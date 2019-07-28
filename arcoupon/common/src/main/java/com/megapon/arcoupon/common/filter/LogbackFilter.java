package com.megapon.arcoupon.common.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {
	@Override
	public FilterReply decide(ILoggingEvent event) {
		//System.out.printf("FilterReply -> [%s][%b]", event.getMessage(), event.getThreadName().contains("DiscoveryClient"));
		if (event.getThreadName().contains("DiscoveryClient")) {
			return FilterReply.DENY;
		} else {
			return FilterReply.ACCEPT;
		}
	}
}