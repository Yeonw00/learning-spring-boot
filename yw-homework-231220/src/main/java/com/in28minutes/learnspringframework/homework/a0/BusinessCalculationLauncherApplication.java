package com.in28minutes.learnspringframework.homework.a0;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
class ClassA {
	
}

@Component
@Lazy
class ClassB {
	private ClassA classA;
	
	public ClassB(ClassA classA) {
		System.out.println("Some Initialization logic");
		this.classA = classA;
	}
	
	public void doSomething() {
		System.out.println("Do Something");
	}
}

@Configuration
@ComponentScan
public class BusinessCalculationLauncherApplication {
	
	public static void main(String[] args) {
		
		try(var context 
				= new AnnotationConfigApplicationContext
					(BusinessCalculationLauncherApplication.class)) {
			
//			Arrays.stream(context.getBeanDefinitionNames())
//				.forEach(System.out::println);
			
//			System.out.println(
//					"find Max : " + context.getBean(BusinessCalculationService.class).findMax());
			
//			System.out.println(
//					"find Min : " + context.getBean(BusinessCalculationService.class).findMin());
			
			System.out.println("Initialization of context is completed");
			
			context.getBean(ClassB.class).doSomething();
		}
	}
}
