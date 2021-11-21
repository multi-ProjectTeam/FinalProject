package com.example.finProject.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/enterprise/{eno}/menu")
public class MenuController {

	@PostMapping("")
	public String menu() {
		return "POST Menu";
	}

	@GetMapping("/{mcode}")
	public String getMenu(@PathVariable("eno") String eno, @PathVariable("mcode") String mcode) {
		return "GET Menu " + eno + " / " + mcode;
	}

	@PutMapping("/{mcode}")
	public String putEnterprise(@PathVariable("eno") String eno, @PathVariable("mcode") String mcode) {
		return "PUT Menu " + eno + " / " + eno;
	}

	@DeleteMapping("/{mcode}")
	public String deleteEnterprise(@PathVariable("eno") String eno, @PathVariable("mcode") String mcode) {
		return "DELETE Menu " + eno + " / " + mcode;
	}

}
