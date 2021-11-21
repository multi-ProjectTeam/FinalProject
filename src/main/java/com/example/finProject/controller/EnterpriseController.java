package com.example.finProject.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finProject.dto.Enterprise;
import com.example.finProject.mapper.EnterpriseMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseMapper mapper;

	// 업체 추가하기
	@PostMapping("")
	public String enterprise(@RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();

		try {
			JsonObject json = gson.fromJson(param, JsonObject.class);
			System.out.println(json);

			String PASSWORD = json.get("password").getAsString();
			String ENAME = json.get("ename").getAsString();
			String POSTCODE = json.get("postcode").getAsString();
			String ROAD_ADDRESS = json.get("road_address").getAsString();
			String JIBUN_ADDRESS = json.get("jibun_address").getAsString();
			String DETAIL_ADDRESS = json.get("detail_address").getAsString();
			String PHONE = json.get("phone").getAsString();
			String EMAIL = json.get("email").getAsString();
			String INTRODUCTION = json.get("introduction").getAsString();
			int OPEN1 = json.get("open1").getAsInt();
			int CLOSE1 = json.get("close1").getAsInt();
			int OPEN2 = json.get("open2").getAsInt();
			int CLOSE2 = json.get("close2").getAsInt();
			String EIMAGE = json.get("eimage").getAsString();
			String ECATEGORY = json.get("ecategory").getAsString();

			mapper.POSTenterprise(PASSWORD, ENAME, POSTCODE, ROAD_ADDRESS, JIBUN_ADDRESS, DETAIL_ADDRESS, PHONE, EMAIL,
					INTRODUCTION, OPEN1, CLOSE1, OPEN2, CLOSE2, EIMAGE, ECATEGORY);

			int eno = mapper.POSTenterpriseResponse(PASSWORD, ENAME);

			result.addProperty("eno", eno);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	@GetMapping("/{eno}")
	public Enterprise getEnterprise(@PathVariable("eno") int eno) {

		Enterprise ent = mapper.GETenterprise(eno);

		return ent;
	}

	@PutMapping("/{eno}")
	public String putEnterprise(@PathVariable("eno") int eno, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();

		try {

			Enterprise ent = mapper.GETenterprise(eno);
			String tempJson = gson.toJson(ent);

			JsonObject insertJson = gson.fromJson(tempJson, JsonObject.class);

			JsonObject json = gson.fromJson(param, JsonObject.class);
//		System.out.println(json.get("postcode").getAsString());

			Iterator<String> iterator = json.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (key.equals("eno"))
					continue;
				// 넘겨 받은 Json element의 타입에 따라서 설정.
				// boolean의 경우 boolean으로, number의 경우 int로, 나머지는 string으로 받는다.
				if (json.get(key).getAsJsonPrimitive().isBoolean())
					insertJson.addProperty(key, json.get(key).getAsBoolean());
				else if (json.get(key).getAsJsonPrimitive().isNumber())
					insertJson.addProperty(key, json.get(key).getAsInt());
				else
					insertJson.addProperty(key, json.get(key).getAsString());
			}

			mapper.PUTenterprise(insertJson.get("eno").getAsInt(), insertJson.get("password").getAsString(),
					insertJson.get("ename").getAsString(), insertJson.get("postcode").getAsString(),
					insertJson.get("road_address").getAsString(), insertJson.get("jibun_address").getAsString(),
					insertJson.get("detail_address").getAsString(), insertJson.get("phone").getAsString(),
					insertJson.get("email").getAsString(), insertJson.get("confirm").getAsString(),
					insertJson.get("introduction").getAsString(), insertJson.get("open1").getAsInt(),
					insertJson.get("close1").getAsInt(), insertJson.get("open2").getAsInt(),
					insertJson.get("close2").getAsInt(), insertJson.get("valid").getAsString(),
					insertJson.get("eimage").getAsString(), insertJson.get("ecategory").getAsString());

			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	@DeleteMapping("/{eno}")
	public String deleteEnterprise(@PathVariable("eno") int eno) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		try {
			mapper.DELETEenterprise(eno);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	// 카테고리(category) 레이어
	@GetMapping("/{eno}/categories")
	public String getCategories(@PathVariable("eno") String eno) {
		return "Get Categories " + eno;
	}

	
	
	// 이미지(image) 레이어
	@GetMapping("/{eno}/images")
	public String getImages(@PathVariable("eno") String eno) {
		return "Get ImageS " + eno;
	}

	// 테이블(table) 레이어
	@GetMapping("/{eno}/tables")
	public String getTables(@PathVariable("eno") String eno) {
		return "Get Tables " + eno;
	}

	// 메뉴(menu) 레이어
	@GetMapping("/{eno}/menus")
	public String getMenus(@PathVariable("eno") String eno) {
		return "Get Menus " + eno;
	}

	// 주문(order) 레이어
	@GetMapping("/{eno}/orders")
	public String getOrders(@PathVariable("eno") String eno) {
		return "Get Orders " + eno;
	}

	// 테이블(table) 레이어
	@GetMapping("/{eno}/orderdetails")
	public String getOrederDetails(@PathVariable("eno") String eno) {
		return "Get OrederDetails " + eno;
	}
}