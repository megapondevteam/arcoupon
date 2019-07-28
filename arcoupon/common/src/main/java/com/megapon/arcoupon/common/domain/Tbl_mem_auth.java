package com.megapon.arcoupon.common.domain;

import com.megapon.arcoupon.common.domain.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Tbl_mem_auth extends BaseObject {
	@ApiModelProperty(notes = "해쉬코드", required = true)
	private String hash_cd;
	
	@ApiModelProperty(notes = "회원코드", required = false)
	private String mem_cd;
	
	@ApiModelProperty(notes = "인증타입", required = false)
	private String auth_type;
	
	@ApiModelProperty(notes = "인증코드", required = false)
	private String auth_cd;
	
	@ApiModelProperty(notes = "이메일", required = false)
	private String mem_email;	
	
	@ApiModelProperty(notes = "인증여부", required = false)
	private String is_auth;
	
	@ApiModelProperty(notes = "등록일", required = false)
	private String regdate;
	
	@ApiModelProperty(notes = "수정일", required = false)
	private String uptdate;
}
