package org.nawa.data;

public class ResponseCode {
	public static final int SUCCESS = 200;
	
	public static final int BAD_REQUEST = 400; //클라이언트에서 url 잘못 전달
	public static final int UNAUTHORIZED = 401; //내가 플젝 권한이 없음
	public static final int NO_DATA = 402; //데이터가 없음
	public static final int PRECONDITION_FAILED= 412; //파라미터 잘못 넘겨줌
	
	public static final int NOT_IMPLEMENTED = 501; //
	
	public static final int UNKNOWN_ERROR = 600; //나머지 에러
	public static final int DUPLICATED = 601; //중복된 것
	public static final int ALREADY_APPLIED = 602; //이미 있는 것들
} //class