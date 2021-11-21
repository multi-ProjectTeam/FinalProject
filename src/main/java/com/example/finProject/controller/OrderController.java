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
@RequestMapping("/enterprise/{eno}/order")
public class OrderController {

	@PostMapping("")
	public String order() {
		return "POST Order";
	}

	@GetMapping("/{ocode}")
	public String getOrder(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode) {
		return "GET Order " + eno + " / " + ocode;
	}

	@PutMapping("/{ocode}")
	public String putEnterprise(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode) {
		return "PUT Order " + eno + " / " + eno;
	}

	@DeleteMapping("/{ocode}")
	public String deleteEnterprise(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode) {
		return "DELETE Order " + eno + " / " + ocode;
	}

	@PostMapping("/{ocode}/payment")
	public String orderPayment(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode) {
		return "POST OrderPayment " + eno + " / " + ocode;
	}

}
