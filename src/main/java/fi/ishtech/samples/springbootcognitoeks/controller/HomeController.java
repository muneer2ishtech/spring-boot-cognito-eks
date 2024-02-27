package fi.ishtech.samples.springbootcognitoeks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@RestController
@Slf4j
public class HomeController {

	@GetMapping("/")
	public String index() {
		log.trace("In web root");
		return "Welcome to Spring-Boot Cognito EKS";
	}

	@GetMapping("/hello")
	public String hello() {
		log.trace("In hello");
		return "Hello";
	}

}
