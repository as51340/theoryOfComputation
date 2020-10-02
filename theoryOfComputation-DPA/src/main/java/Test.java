import java.util.Stack;

public class Test {
	
	public static void main(String[] args) {
		Stack<String> s = new Stack<>();
		s.add("1");
		promijeni(s);
		System.out.println(s.size());
	}
	
	public static void promijeni(Stack<String> s) {
		s.add("2");
	}
	
	
}
