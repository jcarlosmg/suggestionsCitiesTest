package com.cities.services;

import java.util.List;

import com.cities.pojo.Suggestions;

public interface ISuggestionsService {

	public List<Suggestions> search(String p, String latitude, String longitude);
	
}
