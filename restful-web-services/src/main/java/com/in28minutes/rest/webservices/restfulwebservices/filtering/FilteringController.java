package com.in28minutes.rest.webservices.restfulwebservices.filtering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	private List<String> lists;
	private MappingJacksonValue  mappingJacksonValue;
	
	@GetMapping("/filtering")
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		lists = new ArrayList<>();
		lists.add("field1");
		lists.add("field3");
		
		mappingJacksonValue = setFilter(lists);
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
				new SomeBean("value4", "value5", "value6"));
		
		lists = new ArrayList<>();
		lists.add("field2");
		lists.add("field3");
		
		mappingJacksonValue = setFilter(lists);
		
	return mappingJacksonValue;
	}
	
	public MappingJacksonValue setFilter(List<String> fields) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(fields);
		FilterProvider filters = null;
		for(String field: fields) {
			SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field);
			filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		}
		mappingJacksonValue.setFilters(filters);
		
	return mappingJacksonValue;
	}
}
