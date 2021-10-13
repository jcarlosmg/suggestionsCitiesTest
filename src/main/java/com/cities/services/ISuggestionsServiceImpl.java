package com.cities.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cities.component.SuggestionsComponent;
import com.cities.pojo.Suggestions;

@Service
public class ISuggestionsServiceImpl implements ISuggestionsService{
	
	@Autowired
	private SuggestionsComponent suggestionsComponent;
	
	@Override
	public List<Suggestions> search(String param, String latitude, String longitude) {

		List<Suggestions> listResult = new ArrayList<>();
		try {
//			File file = ResourceUtils.getFile("classpath:templates/cities-canada-usa.json"); //tsv
			listResult =  suggestionsComponent.readerJson(param, latitude, longitude);
			listResult.removeIf(city -> city.getName()!=null && !city.getName().contains(param));
			
//			if(latitude != null && !latitude.trim().isEmpty()) { System.out.println(" 1");
//				listResult.removeIf(city -> city.getLatitud()!=null && !city.getLatitud().toString().contains(latitude));
//			}
//			if(longitude != null  && !longitude.trim().isEmpty()) { System.out.println(" 2");
//				listResult.removeIf(city -> city.getLongitud()!=null && !city.getLongitud().toString().contains(longitude));
//			}
			
			//listResult.stream().sorted(Comparator.comparingDouble(Suggestions::getScore)).collect(Collectors.toList());
			Collections.sort(listResult,new Comparator<Suggestions>() {
			    @Override
			    public int compare(Suggestions a, Suggestions b) {
			        return b.getScore().compareTo(a.getScore());
			    }
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listResult;
	}
	
}
