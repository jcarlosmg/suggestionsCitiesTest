package com.cities.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.cities.pojo.Suggestions;

@Component
public class SuggestionsComponent {

	
	public ArrayList<String[]> tsvr(File test2) {
	    ArrayList<String[]> Data = new ArrayList<>(); 
	    try (BufferedReader TSVReader = new BufferedReader(new FileReader(test2))) {
	        String line = null;
	        while ((line = TSVReader.readLine()) != null) {
	            String[] lineItems = line.split("\t"); 
	            Data.add(lineItems); 
	        }
	    } catch (Exception e) {
	        System.out.println("Something went wrong");
	    }
	    
	    Data.forEach(array -> System.out.println(Arrays.toString(array)));
	    return Data;
	}
	
	public List<Suggestions> readerJson(String param1, String param2, String param3) {
		 
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
