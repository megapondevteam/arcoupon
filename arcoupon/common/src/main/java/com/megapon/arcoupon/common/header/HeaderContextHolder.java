package com.megapon.arcoupon.common.header;

public class HeaderContextHolder {
	private static final ThreadLocal<HeaderContext> userContext = new ThreadLocal<HeaderContext>();

	public static final HeaderContext getContext() {
		HeaderContext context = userContext.get();

		if (context == null) {
			context = createEmptyContext();
			userContext.set(context);

		}
		
		return userContext.get();
	}

	public static final void setContext(HeaderContext context) {
		// Assert.notNull(context, "Only non-null UserContext instances are permitted");
		userContext.set(context);
	}

	public static final HeaderContext createEmptyContext() {
		return new HeaderContext();
	}
}