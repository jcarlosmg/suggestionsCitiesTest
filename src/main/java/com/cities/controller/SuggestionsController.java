package com.cities.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cities.pojo.Suggestions;
import com.cities.services.ISuggestionsService;

@RestController
@RequestMapping("/")
public class SuggestionsController {
	
	@Autowired
	private ISuggestionsService suggestionsService;
	
	@GetMapping(value = "suggestions")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam(value = "p") String p, 
									@RequestParam(value = "latitude") String latitude, 
    							    @RequestParam(value = "longitude") String longitude){
		List<Suggestions> resulList = suggestionsService.search(p, latitude, longitude);
		Map suggestionsMap = new HashMap<>();
		suggestionsMap.put("Suggestions", resulList);
		return new ResponseEntity(suggestionsMap, HttpStatus.OK);
	}
}
