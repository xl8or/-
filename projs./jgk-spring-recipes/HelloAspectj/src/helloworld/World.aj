package helloworld;

public aspect World {
	pointcut thereMethod() : execution(* Hello.there(..));
	pointcut summerTime() : execution(* Hello.summer(Integer));
	pointcut greeting() : execution(* Hello.sayHello(..));
	after() returning() : greeting() || thereMethod() || summerTime() { 
        System.out.println(" World!"); 
    } 
	before()  : summerTime() { 
        System.out.println(" SUMM IT UP!"); 
    } 

}
