package com.in28minutes.learnspringaop.aopexample.business;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.in28minutes.learnspringaop.aopexample.anotations.TrackTime;
import com.in28minutes.learnspringaop.aopexample.data.DataService1;

@Service
public class BusinessService1 {
	private DataService1 dataService1;
	
	public BusinessService1(DataService1 dataService1) {
		super();
		this.dataService1 = dataService1;
	}

	@TrackTime
	public int calculateMax() {
		int[] data = dataService1.retrieveData();
		
//		throw new RuntimeException("Something went wrong!");
		return Arrays.stream(data).max().orElse(0);
	}
}
