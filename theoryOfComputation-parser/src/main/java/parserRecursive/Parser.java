import java.io.IOException;
import java.util.Scanner;

public class Parser {
	
	static int index = 0;

	public static void main(String[] args) throws IOException {
		//BufferedReader sc =  Files.newBufferedReader(Paths.get("src/test/resources/test20/test.in"));
		Scanner sc = new Scanner(System.in);
		String input = sc.next();
		if(index >= input.length()) {
			System.out.println("\nNE"); //possibly error
			sc.close();
			return;
		}
		if(S(input) == false) {
			sc.close();
			return;
		}
		if(index >= input.length()) {
			System.out.println("\nDA");
		} else {
			System.out.println("\nNE");
		}
		sc.close();
	}
	
	private static boolean C(String input) {
		System.out.print('C');
		if(A(input) == false) {
			return false;
		}
		if(A(input) == false) {
			return false;
		}
		return true;
	}
	
	private static boolean S(String input) {
		System.out.print('S');
		char tmp = input.charAt(index++);
		if(tmp != 'a' && tmp != 'b') {
			System.out.println("\nNE");
			return false;
		}
		if(tmp == 'a') {
			if(A(input) == false) {
				return false;
			}
			if(B(input) == false) {
				return false;
			}
		} else if(tmp == 'b' ) {
			if(B(input) == false) {
				return false;
			}
			if(A(input) == false) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean B(String input) {
		System.out.print('B');
		if(index == input.length()) { //nothing to read 
			return true;
		} else if(index > input.length()) {
			return false;
		}
		char tmp1 = input.charAt(index++);
		if(tmp1 != 'c') {
			index--;
			return true;
		}
		/*if(index >= input.length()) { vjv netreba
			System.out.println("\nNE");
			return false;
		} */
		char tmp2 = input.charAt(index++);
		if(tmp2 != 'c') {
			index--;
			return true;
		}
		if(S(input) == false) {
			return false;
		}
		if(index >= input.length()) {
			System.out.println("\nNE");
			return false;
		}
		tmp1 = input.charAt(index++);
		if(index >= input.length()) {
			System.out.println("\nNE");
			return false;
		}
		tmp2 = input.charAt(index++);
		if(tmp1 != 'b' || tmp2 !='c') {
			System.out.println("\nNE");
			return false;
		}
		return true;
	}
	
	private static boolean A(String input) {
		System.out.print("A");
		if(index >= input.length()) {
			System.out.println("\nNE");
			return false;
		}
		char tmp = input.charAt(index++);
		if(tmp != 'b' && tmp != 'a') { //if char is not neither b or a
			System.out.println("\nNE");
			return false;
		}
		if(tmp == 'b') { // if is b then C must be called
			return C(input);
		} 
		return true; //here is a and everything should be okay
	}

}
