package com.tjoeun.springDI_xml_in_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration 
public class ApplicationConfig {

	@Bean
	public Student student2() {
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("찡이 보살피기");
		hobbies.add("밥 먹기");
		hobbies.add("스프링 공부");
		
		Student student = new Student("김유진", 26, hobbies);
		student.setHeight(163);
		student.setWeight(50);
		
		return student; 
	}
	
}


















