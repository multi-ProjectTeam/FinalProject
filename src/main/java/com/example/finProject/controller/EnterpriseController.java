package com.example.finProject.controller;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.finProject.dto.Image;
import com.example.finProject.dto.Menu;
import com.example.finProject.dto.Order;
import com.example.finProject.dto.OrderDetail;
import com.example.finProject.dto.Table;
import com.example.finProject.mapper.EnterpriseMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/enterprises", produces="application/json;charset=utf8")
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
		ent.setPassword(null);
		
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
			
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
			Map<String, Object> nullRemover = gson.fromJson(param, type);
			
			for( Iterator<Map.Entry<String, Object>> it = nullRemover.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String,Object> entry = it.next();
				String key =entry.getKey();
				if(key.equals("eno"))
					continue;
				else if(entry.getValue() == null)
					continue;
				else {
					if (insertJson.get(key).getAsJsonPrimitive().isBoolean())
						insertJson.addProperty(key, json.get(key).getAsBoolean());
					else if (insertJson.get(key).getAsJsonPrimitive().isNumber())
						insertJson.addProperty(key, json.get(key).getAsInt());
					else
						insertJson.addProperty(key, json.get(key).getAsString());
				}
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
	public String getCategories(@PathVariable("eno") int eno) {
		String[] categories = mapper.GETcategories(eno);
		
		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( String category : categories) {
			jsonArr.add(category);
		}
		result.add("categories", jsonArr);
		
		return result.toString();
	}

	// 이미지(image) 레이어
	@GetMapping("/{eno}/images")
	public String getImages(@PathVariable("eno") int eno) {
		Image[] images = mapper.GETimages(eno); 
		
		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( Image element : images) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("images", jsonArr);
		
		return result.toString();
	}

	// 테이블(table) 레이어
	@GetMapping("/{eno}/tables")
	public String getTables(@PathVariable("eno") int eno) {
		Table[] tables = mapper.GETtables(eno);
		
		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( Table element : tables) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("tables", jsonArr);
		
		return result.toString();
	}

	// 메뉴(menu) 레이어
	@GetMapping("/{eno}/menus")
	public String getMenus(@PathVariable("eno") int eno) {
		Menu[] menus = mapper.GETmenus(eno);

		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( Menu element : menus) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("menus", jsonArr);
		
		return result.toString();
	}

	// 주문(order) 레이어
	@GetMapping("/{eno}/orders")
	public String getOrders(@PathVariable("eno") int eno) {
		Order[] orders = mapper.GETorders(eno);
		
		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( Order element : orders) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("orders", jsonArr);
		
		return result.toString();
	}

	// 주문상세(orderdetail) 레이어
	@GetMapping("/{eno}/orderdetails")
	public String getOrederDetails(@PathVariable("eno") int eno) {
		OrderDetail[] od = mapper.GETorderdetails(eno);

		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( OrderDetail element : od) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("orderdetails", jsonArr);
		
		return result.toString();
	}
}
