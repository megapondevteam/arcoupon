package com.megapon.arcoupon.common.domain;

import com.megapon.arcoupon.common.domain.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Tbl_mem_info extends BaseObject {
	@ApiModelProperty(notes = "회원코드", required = true)
	private String mem_cd;

	@ApiModelProperty(notes = "회원아이디", required = false)
	private String mem_id;	
	
	@ApiModelProperty(notes = "비밀번호", required = false)
	private String mem_pwd;
		
	@ApiModelProperty(notes = "회원명", required = false)
	private String mem_name;
	
	@ApiModelProperty(notes = "이메일", required = false)
	private String mem_email;
	
	//@ApiModelProperty(notes = "코인주소비밀번호", required = false)
	private String coin_pwd;
	
	@ApiModelProperty(notes = "잠김여부", required = false)
	private String is_lock;
	
	@ApiModelProperty(notes = "사용여부", required = false)
	private String is_valid;
	
	@ApiModelProperty(notes = "등록일", required = false)
	private String regdate;
	
	@ApiModelProperty(notes = "수정일", required = false)
	private String uptdate;
}
