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
@RequestMapping("/enterprise/{eno}/table")
public class TableController {

	@PostMapping("")
	public String table() {
		return "POST Table";
	}

	@GetMapping("/{tno}")
	public String getTable(@PathVariable("eno") String eno, @PathVariable("tno") String tno) {
		return "GET Table " + eno + " / " + tno;
	}

	@PutMapping("/{tno}")
	public String putEnterprise(@PathVariable("eno") String eno, @PathVariable("tno") String tno) {
		return "PUT Table " + eno + " / " + eno;
	}

	@DeleteMapping("/{tno}")
	public String deleteEnterprise(@PathVariable("eno") String eno, @PathVariable("tno") String tno) {
		return "DELETE Table " + eno + " / " + tno;
	}

}
