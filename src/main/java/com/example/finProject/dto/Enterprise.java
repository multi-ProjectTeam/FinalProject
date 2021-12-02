package com.example.finProject.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enterprise {

	private int eno;
	private String password;
	private String ename;
	private String postcode;
	private String road_address;
	private String jibun_address;
	private String detail_address;
	private String phone;
	private String email;
	private char confirm;
	private String introduction;
	private int open1;
	private int close1;
	private int open2;
	private int close2;
	private char valid;
	private String eimage;
	private String ecategory;
	private Timestamp modification_time;
	
	public Enterprise() {}
}
