
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class A {
	void foo() {
		System.out.println("A");
	}
}

public class StreamPractice extends A{

	void printString(String input) {
		List<String> a = new ArrayList<>(Arrays.asList(input.split(" ")));

		String k = a.stream()
				.sorted((a1, a2) -> {
			return a1.length() - a2.length();
		}).map(String::toLowerCase).collect(Collectors.joining(" "));

		System.out.println(k + ".");
	}

	XYZ a;
	
	public XYZ tester() {
		return a;
	}
	
	public static void main(String args[]) {
	//	new StreamPractice().printString("What ddhye a Day!");
		
		//StreamPractice p = 	new A();
		
	}
}

abstract class XYZ implements Predicate<String>{
	@Override
	public boolean test(String t) {
		// TODO Auto-generated method stub
		return false;
	}
}