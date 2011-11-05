package helloworld;

public class Hello {
	
	public void there() {
		System.out.println("GRAVY");
	}

	public void summer(Integer age) {
		System.out.println("Your age is "+age);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		sayHello();
		new Hello().summer(34);
		new Hello().there();
	}
	public static void sayHello() {
	     System.out.print("Hello");
	}
}
