package com.tjoeun.springLifeCycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		/*
		// afterPropertiesSet() 메소드는 실행되지만 destroy() 메소드가 실행되지 않는다.
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		System.out.println("--------------------------------------------");
		Person person = ctx.getBean("person", Person.class);
		System.out.println(person);
		
		System.out.println("--------------------------------------------");
		System.out.println(person.getName() + "는 " + person.getAge() + "살 입니다." );
		System.out.println("--------------------------------------------");
		
		Person person2 = ctx.getBean("person2", Person.class);
		System.out.println(person2);
		*/
		
		// DI 컨테이너를 만든다. => 빈 컨테이너 => 필요할 때 bean 설정 정보를 채워서 사용한다.
		// GenericXmlApplicationContext 클래스 생성자로 bean 설정 정보를 넘기지 않고 만든 DI 컨테이너는
		// 빈 컨테이너만 생성되고 bean 설정 정보는 들어있지 않다.
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		
		
		// load() 메소드로 xml 파일에서 정의한 bean 설정 정보를 DI 컨테이너에 넣어준다.
		// load() 메소드는 DI 컨테이너에 bean 설정 정보를 넣어주기만 하기 때문에 아직 bean이 생성되지 않은
		// 상태이다. => 생성자가 실행되지 않은 상태이다.
		ctx.load("classpath:applicationCTX.xml"); // bean 설정 정보를 넣어준다.
		
		// load() 메소드를 사용해서 bean 설정 정보를 DI 컨테이너에 넣어준 경우 bean이 생성되는 시점이 2가지가 있다.
		// 1. refresh() 메소드는 실행되는 순간 DI 컨테이너에 넣어준 bean이 한꺼번에 그냥 생성된다.
		// 2. refresh() 메소드를 실행하지 않으면 getBean() 메소드로 DI 컨테이너에 넣어줘야 bean이 생성된다.
		
		ctx.refresh(); // bean 설정 정보에 따른 bean을 만든다.
		
		
		System.out.println("--------------------------------------------");
		Person person = ctx.getBean("person", Person.class);
		System.out.println(person);
		
		System.out.println(person.getName() + "는 " + person.getAge() + "살 입니다." );
		System.out.println("--------------------------------------------");
		
		Person person2 = ctx.getBean("person2", Person.class);
		System.out.println(person2.getName() + "는 " + person2.getAge() + "살 입니다." );
		System.out.println(person2);
		
		// refresh() 메소드를 실행했더라도 close() 메소드를 실행하지 않으면 destroy() 메소드는 실행되지 않는다.
		// close() 메소드를 실행했더라도 refresh() 메소드를 실행하지 않았다면 destroy() 메소드는 실행되지 않는다.
		ctx.close();
		
	}
}




















