package com.megapon.arcoupon.common.header;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class HeaderContextInterceptor implements ClientHttpRequestInterceptor {
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders headers = request.getHeaders();
		
		if(HeaderContextHolder.getContext().getAuthToken() != null) headers.add(HeaderContextConst.AUTH_TOKEN, HeaderContextHolder.getContext().getAuthToken());
		if(HeaderContextHolder.getContext().getTransId() != null) headers.add(HeaderContextConst.TRANS_ID, HeaderContextHolder.getContext().getTransId());
		
		log.debug("request uri : " + request.getURI());
		ClientHttpResponse response = execution.execute(request, body);
		log.debug("response status : " + response.getStatusCode());
		
		return response;
	}
}