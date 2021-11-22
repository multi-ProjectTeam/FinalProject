package com.example.finProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.finProject.dto.Enterprise;
import com.example.finProject.mapper.BaseMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BaseController {
	
	@Autowired
	private BaseMapper mapper;
	
	// 로그인 컨트롤러
	@PostMapping("/login")
	public String login(@RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			JsonObject json = gson.fromJson(param, JsonObject.class);
			System.out.println(json);
			
			Enterprise ent = mapper.Login(json.get("eno").getAsInt(), json.get("password").getAsString());
			
			if(ent != null)
				result.addProperty("status", true);
		}catch(Exception e) {
			
		}
		
		return result.toString();
	}

	// 모든 업체 정보 가져오기
	@GetMapping("/enterprises")
	public String getEnterprises() {
		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		
		JsonArray jsonArr =new JsonArray();
		Enterprise[] enterprises = mapper.GETenterprises();
		for( Enterprise ent : enterprises) {
			ent.setPassword(null);
			JsonObject insertJson = gson.fromJson(gson.toJson(ent), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("enterprises", jsonArr);
		
		return result.toString();
	}
}
