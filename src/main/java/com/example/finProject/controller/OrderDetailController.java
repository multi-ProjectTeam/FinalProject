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
@RequestMapping("/enterprise/{eno}/order/{ocode}/orderdetail")
public class OrderDetailController {

	@PostMapping("")
	public String orderDetail() {
		return "POST OrderDetail";
	}

	@GetMapping("/{odcode}")
	public String getOrderDetail(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode,
			@PathVariable("odcode") String odcode) {
		
		return "GET OrderDetail " + eno + " / " + ocode + " / " + odcode;
	}

	@PutMapping("/{odcode}")
	public String putOrderDetail(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode,
			@PathVariable("odcode") String odcode) {
		
		return "PUT OrderDetail " + eno + " / " + eno + " / " + odcode;
	}

	@DeleteMapping("/{odcode}")
	public String deleteOrderDetail(@PathVariable("eno") String eno, @PathVariable("ocode") String ocode,
			@PathVariable("odcode") String odcode) {
		return "DELETE OrderDetail " + eno + " / " + ocode + " / " + odcode;
	}

}
