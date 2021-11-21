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
@RequestMapping("/enterprise/{eno}/image")
public class ImageController {

	@PostMapping("")
	public String image() {
		return "POST Image";
	}

	@GetMapping("/{ino}")
	public String getImage(@PathVariable("eno") String eno, @PathVariable("ino") String ino) {
		return "GET Image " + eno + " / " + ino;
	}

	@PutMapping("/{ino}")
	public String putEnterprise(@PathVariable("eno") String eno, @PathVariable("ino") String ino) {
		return "PUT Image " + eno + " / " + eno;
	}

	@DeleteMapping("/{ino}")
	public String deleteEnterprise(@PathVariable("eno") String eno, @PathVariable("ino") String ino) {
		return "DELETE Image " + eno + " / " + ino;
	}

}
