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

import com.example.finProject.dto.OrderDetail;
import com.example.finProject.mapper.OderDetailMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/enterprise/{eno}/order/{ocode}/orderdetail", produces="application/json;charset=utf8")
public class OrderDetailController {
	
	@Autowired
	private OderDetailMapper mapper;

	@PostMapping("")
	public String orderDetail(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			JsonObject json = gson.fromJson(param, JsonObject.class);
			System.out.println(json);
			
			mapper.POSTorderdetail(json.get("mcode").getAsInt(), eno, ocode, json.get("count").getAsInt());
			
			int odcode = mapper.POSTorderdetailResponse(eno);
			
			result.addProperty("odcode", odcode);
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@GetMapping("/{odcode}")
	public OrderDetail getOrderDetail(@PathVariable("odcode") int odcode) {
	
		OrderDetail od = mapper.GETorderdetail(odcode);
		
		return od;
	}

	@PutMapping("/{odcode}")
	public String putOrderDetail(@PathVariable("eno") int eno, @PathVariable("ocode") int ocode, @PathVariable("odcode") int odcode, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			OrderDetail od = mapper.GETorderdetail(odcode);
			String tempJson = gson.toJson(od);
			JsonObject insertJson = gson.fromJson(tempJson, JsonObject.class);
			JsonObject json = gson.fromJson(param, JsonObject.class);
			
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
			Map<String, Object> nullRemover = gson.fromJson(param, type);
			
			for( Iterator<Map.Entry<String, Object>> it = nullRemover.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String,Object> entry = it.next();
				String key =entry.getKey();
				if(key.equals("odcode"))
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
//			Iterator<String> iterator = json.keySet().iterator();
//			while (iterator.hasNext()) {
//				String key = iterator.next();
//				if (key.equals("odcode"))
//					continue;
//				// 넘겨 받은 Json element의 타입에 따라서 설정.
//				// boolean의 경우 boolean으로, number의 경우 int로, 나머지는 string으로 받는다.
//				if (json.get(key).getAsJsonPrimitive().isBoolean())
//					insertJson.addProperty(key, json.get(key).getAsBoolean());
//				else if (json.get(key).getAsJsonPrimitive().isNumber())
//					insertJson.addProperty(key, json.get(key).getAsInt());
//				else
//					insertJson.addProperty(key, json.get(key).getAsString());
//			}
			mapper.PUTorderdetail(odcode, insertJson.get("mcode").getAsInt(), eno, ocode, insertJson.get("count").getAsInt());
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@DeleteMapping("/{odcode}")
	public String deleteOrderDetail(@PathVariable("odcode")int odcode) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		try {
			mapper.DELETEorderdetail(odcode);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

}
