package com.megapon.arcoupon.common;

public enum CommonCode {
	SUCC_COMPLETE_PROCESS("0000", "처리가 완료되었습니다."),
    FAIL_SYSTEM_ERROR_AND_PROCESS("9000", "시스템 오류가 발생하였습니다. 처리가 실패되었습니다."),
    FAIL_DATABASE_ERROR_AND_PROCESS("9001", "DB 처리중 오류가 발생하였습니다. 처리가 실패되었습니다."),
    FAIL_EXIST_SOME_CANCEL_PROCESS("9002", "취소처리중에 일부 처리가 실패한 건이 존재합니다.");
    
	private String code;
	private String message;

	CommonCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return this.code;
	}
}
