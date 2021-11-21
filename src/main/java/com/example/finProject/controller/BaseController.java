package com.example.finProject.controller;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finProject.dto.Enterprise;
import com.example.finProject.mapper.BaseMapper;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BaseController {
	
	@Autowired
	private BaseMapper mapper;
	
	// 로그인 컨트롤러
	@PostMapping("/login")
	public String login() {
		return "login";
	}

	// 모든 업체 정보 가져오기
	@GetMapping("/enterprises")
	public Enterprise[] getEnterprises() {
		Enterprise[] enterprises = mapper.GETenterprises();
		return enterprises;
	}
}
