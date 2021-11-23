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

import com.example.finProject.dto.Table;
import com.example.finProject.mapper.TableMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value="/enterprise/{eno}/table", produces="application/json;charset=utf8")
public class TableController {
	
	@Autowired
	private TableMapper mapper;

	@PostMapping("")
	public String table(@PathVariable("eno") int eno, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			JsonObject json = gson.fromJson(param, JsonObject.class);
			System.out.println(json);
			
			mapper.POSTtable(eno, json.get("seat_num").getAsInt(), json.get("window_seat").getAsString().charAt(0));
			
			int tno = mapper.POSTtableResponse(eno);
			
			result.addProperty("tno", tno);
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@GetMapping("/{tno}")
	public Table getTable(@PathVariable("eno") int eno, @PathVariable("tno") int tno) {
		
		Table table = mapper.GETtable(tno);
		
		return table;
	}

	@PutMapping("/{tno}")
	public String putEnterprise(@PathVariable("eno") int eno, @PathVariable("tno") int tno, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			Table table = mapper.GETtable(tno);
			String tempJson = gson.toJson(table);
			JsonObject insertJson = gson.fromJson(tempJson, JsonObject.class);
			JsonObject json = gson.fromJson(param, JsonObject.class);
			
			Type type = new TypeToken<Map<String, Object>>() {}.getType();
			Map<String, Object> nullRemover = gson.fromJson(param, type);
			
			for( Iterator<Map.Entry<String, Object>> it = nullRemover.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String,Object> entry = it.next();
				String key =entry.getKey();
				if(key.equals("tno"))
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
//				if (key.equals("tno"))
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
			
			mapper.PUTtable(tno, eno, insertJson.get("seat_num").getAsInt(), insertJson.get("window_seat").getAsString().charAt(0), 
					insertJson.get("like").getAsInt(), insertJson.get("state").getAsString().charAt(0));
			
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@DeleteMapping("/{tno}")
	public String deleteEnterprise(@PathVariable("eno") int eno, @PathVariable("tno") int tno) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		try {
			mapper.DELETEtable(tno);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

}
