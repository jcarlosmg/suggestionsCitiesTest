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
import org.springframework.stereotype.Service;

import com.cities.pojo.Suggestions;

@Service
public class ISuggestionsServiceImpl implements ISuggestionsService{
	
	@Override
	public List<Suggestions> search(String param, String latitude, String longitude) {

		List<Suggestions> listResult = new ArrayList<>();
		try {
//			File file = ResourceUtils.getFile("classpath:templates/cities-canada-usa.json"); //tsv
			listResult = readerJson(param, latitude, longitude);
			
			listResult.removeIf(city -> city.getName()!=null && !city.getName().contains(param));
			
//			if(latitude != null && !latitude.trim().isEmpty()) { System.out.println("Something 1");
//				listResult.removeIf(city -> city.getLatitud()!=null && !city.getLatitud().toString().contains(latitude));
//			}
//			if(longitude != null  && !longitude.trim().isEmpty()) { System.out.println("Something 2");
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
	
	private List<Suggestions> readerJson(String param1, String param2, String param3) {
				 
		List<Suggestions> suggestionsList = new ArrayList<>();
		try {
			JSONParser jsonParser = new JSONParser();
			
			JSONArray array = (JSONArray) jsonParser.parse(new FileReader("src/main/resources/templates/cities-canada-usa.json"));
			
			for (Object o : array){
			    JSONObject city = (JSONObject) o;
			    Suggestions suggestions = new Suggestions();
			    Long id = (Long) city.get("id");
			    suggestions.setId(id);

			    Double latitud = (Double) city.get("lat");
			    suggestions.setLatitud(latitud);
			    
			    Double longitud = (Double) city.get("long");
			    suggestions.setLongitud(longitud);
			    
			    String country = (String) city.get("country");
			    suggestions.setCountry(country);
			    
			    String tz = (String) city.get("tz");
			    suggestions.setTz(tz);
			    
			    String name = (String) city.get("name");
			    
			    
			    
			    if (name!=null) {
			    	
			    	double score1 = metricaFiltro(name,param1);
			    	double score2 = metricaFiltro(latitud.toString(),param2);
				    double score3 = metricaFiltro(longitud.toString(),param3);
				    
				    Double scoreFinal =(double) (score1 + score2 + score3) / 3;
				    suggestions.setScore(Math.floor(scoreFinal * 10) / 10);
//				    suggestions.setScore(scoreFinal);
				    suggestions.setName(name + ", " + country + ", " + tz); // se concatena contry y tz para formato final
			    	suggestionsList.add(suggestions);
			    }
			  }
		   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return suggestionsList;	
		
	}	
	
	private double metricaFiltro(String predeterminada, String entrada) {
		double score = 0.0F;
	    String aux="";
	    int length1 = predeterminada!=null? predeterminada.length() : 0;
	    int length2 = entrada!=null? entrada.length() : 0;
	    	    
	    int puntuacion = 0;
        for(int i=0;i<length1;i++){
           if(i<length2) {
	           if(predeterminada.charAt(i) == entrada.charAt(i)){
	        	   puntuacion++;
	               aux+=predeterminada.charAt(i);
	           }
           }
        	   
        }
        score =(double) aux.length()/length1;
        System.out.println(predeterminada + " - " + entrada + " : score " + score);

        return score;

	}

}
