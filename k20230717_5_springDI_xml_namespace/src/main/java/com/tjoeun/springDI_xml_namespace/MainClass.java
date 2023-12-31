package com.tjoeun.springDI_xml_namespace;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		String configLocation = "classpath:applicationCTX.xml";
		String configLocation2 = "classpath:applicationCTX2.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation, configLocation2);
		
		
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		System.out.println("이름: " + student.getName());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("-----------------------------------------");
		
		Student student2 = ctx.getBean("student", Student.class);
		System.out.println(student2);
		System.out.println("이름: " + student.getName());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("-----------------------------------------");
		
		// student와 student2는 같은 bean을 저장하고 있으므로 비교하면 같다.
		System.out.println(student.equals(student2) ? "같다" : "다르다");
		System.out.println("-----------------------------------------");

		Student student3 = ctx.getBean("student3", Student.class);
		System.out.println(student3);
		System.out.println("이름: " + student.getName());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("-----------------------------------------");
		
		System.out.println(student2.equals(student3) ? "같다" : "다르다");
		System.out.println("-----------------------------------------");
		
		Student student4 = ctx.getBean("student4", Student.class);
		System.out.println(student4);
		System.out.println("이름: " + student.getName());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("-----------------------------------------");
		
		System.out.println(student2.equals(student4) ? "같다" : "다르다");
		System.out.println("-----------------------------------------");
		
		StudentInfo studentInfo = ctx.getBean("studentInfo", StudentInfo.class);
		System.out.println(studentInfo);
		studentInfo.getStudentInfo();
		System.out.println("-----------------------------------------");
		
		Student student5 = ctx.getBean("student5", Student.class);
		System.out.println(student5);
		System.out.println("이름: " + student.getName());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("-----------------------------------------");
		
		Family family = ctx.getBean("family", Family.class);
		System.out.println(family);
		System.out.println("아빠 이름: " + family.getPapaName());
		System.out.println("엄마 이름: " + family.getManiName());
		System.out.println("언니 이름: " + family.getSisterName());
		System.out.println("오빠 이름: " + family.getBrotherName());
		
	}
}




















