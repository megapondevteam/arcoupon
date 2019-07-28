package com.megapon.arcoupon.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResultCode extends BaseObject {
	@ApiModelProperty(notes = "결과코드", required = true)
	private String return_cd;
	
	@ApiModelProperty(notes = "결과메시지", required = true)
	private String return_msg;

	//@ApiModelProperty(notes = "결과본문", required = false)
	//private Object response = new Object();
}
