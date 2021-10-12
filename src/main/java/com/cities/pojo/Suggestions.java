package com.cities.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Suggestions {
	
	@JsonIgnore
	private Long id;
	
	private String name;
	@JsonIgnore
	private String alt_name;
	@JsonIgnore
	private String ascii;
	
	private Double latitud;
	
	private Double longitud;
	@JsonIgnore
	private Long population;
	@JsonIgnore
	private String feat_class;
	@JsonIgnore
	private String feat_code;
	@JsonIgnore
	private String country;
	@JsonIgnore
	private String tz;
	@JsonIgnore
	private Long dem;	
	private Double score;

}
