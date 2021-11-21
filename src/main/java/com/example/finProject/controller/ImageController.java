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
import com.example.finProject.dto.Image;
import com.example.finProject.mapper.ImageMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enterprise/{eno}/image")
public class ImageController {
	
	@Autowired
	private ImageMapper mapper;

	@PostMapping("")
	public String image(@PathVariable("eno") int eno, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			JsonObject json = gson.fromJson(param, JsonObject.class);
			
			mapper.POSTimage(eno, json.get("path").getAsString());
			
			int ino = mapper.POSTimageResponse(eno);
			
			result.addProperty("ino", ino);
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@GetMapping("/{ino}")
	public Image getImage(@PathVariable("eno") int eno, @PathVariable("ino") int ino) {
		
		Image img = mapper.GETimage(ino);
		
		return img;
	}

	@PutMapping("/{ino}")
	public String putEnterprise(@PathVariable("eno") int eno, @PathVariable("ino") int ino, @RequestBody String param) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		Gson gson = new Gson();
		
		try {
			Image img = mapper.GETimage(ino);
			String tempJson = gson.toJson(img);
			JsonObject insertJson = gson.fromJson(tempJson, JsonObject.class);
			JsonObject json = gson.fromJson(param, JsonObject.class);
			
			Iterator<String> iterator = json.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				if (key.equals("ino"))
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
			
			mapper.PUTimage(ino, eno, insertJson.get("path").getAsString());
			result.addProperty("status", true);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}

	@DeleteMapping("/{ino}")
	public String deleteEnterprise(@PathVariable("eno") int eno, @PathVariable("ino") int ino) {
		JsonObject result = new JsonObject();
		result.addProperty("status", false);
		try {
			mapper.DELETEimage(ino);
			result.addProperty("status", true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

}
