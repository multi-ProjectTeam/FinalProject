package com.example.finProject.controller;

import java.util.Iterator;

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
import com.example.finProject.dto.Order;
import com.example.finProject.dto.OrderDetail;
import com.example.finProject.mapper.OrderMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/enterprise/{eno}/order", produces="application/json;charset=utf8")
public class OrderController {

	@Autowired
	private OrderMapper mapper;

	@PostMapping("")
	public String order(@PathVariable("eno") int eno) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);

		try {

			mapper.POSTorder(eno);

			int ocode = mapper.POSTorderResponse(eno);

			result.addProperty("ocode", ocode);
			result.addProperty("status", true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	@GetMapping("/{ocode}")
	public Order getOrder(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode) {

		Order order = mapper.GETorder(ocode);

		return order;
	}

	@PutMapping("/{ocode}")
	public String putEnterprise(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode,
			@RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();

		try {
			Order order = mapper.GETorder(ocode);
			String tempJson = gson.toJson(order);
			JsonObject insertJson = gson.fromJson(tempJson, JsonObject.class);
			JsonObject json = gson.fromJson(param, JsonObject.class);

			Iterator<String> iterator = json.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (key.equals("ocode"))
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

			mapper.PUTorder(ocode, eno, insertJson.get("payment").getAsString().charAt(0),
					insertJson.get("total").getAsInt());
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	@DeleteMapping("/{ocode}")
	public String deleteEnterprise(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		try {
			mapper.DELETEorder(ocode);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	@PostMapping("/{ocode}/payment")
	public String orderPayment(@PathVariable("ocode") int ocode) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		
		try {
			mapper.payment(ocode);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	// 주문상세(orderdetail) 레이어
	@GetMapping("/{ocode}/orderdetails")
	public String getOrederDetails(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode) {
		OrderDetail[] od = mapper.GETorderdetails(eno, ocode);

		JsonObject result = new JsonObject();
		Gson gson = new Gson();
		JsonArray jsonArr =new JsonArray();
		for( OrderDetail element : od) {
			JsonObject insertJson = gson.fromJson(gson.toJson(element), JsonObject.class);
			jsonArr.add(insertJson);
		}
		
		result.add("tables", jsonArr);
		
		return result.toString();
	}

}
