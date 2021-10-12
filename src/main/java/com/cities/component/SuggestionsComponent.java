package com.cities.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Component;

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
}
