package com.tjoeun.springDI_java_in_xml;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//	java 파일에서 xml 파일의 bean 설정 정보를 읽어오려면 @ImportResource 어노테이션으로 읽어들일 xml 파일을
//	java 파일에 포함시키면 된다.

@ImportResource("classpath:applicationCTX.xml")

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


















