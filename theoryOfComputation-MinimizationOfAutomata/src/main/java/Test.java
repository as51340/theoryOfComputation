import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Test {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Set<String> s = new TreeSet<String>();
		s.add("s1");
		s.add("s2");
		s.add("s3");
		s.add("s4");
		for(String s1: s) {
			for(String s2: s) {
				if(s1.compareTo(s2) >= 0 ) {
					continue;
				}
				if(s2.equals("s3")) {
					s.remove("s3");
				}
				System.out.print(s1 + " " + s2);
			}
		}
	
	}
}

/*
 * for(String stanje: stanja)   {
			System.out.print(stanje + " ");
		}
		System.out.println();
		for(String simbol: simboli)   {
			System.out.print(simbol + " ");
		}
		System.out.println();
		for(String prihStanje: prihStanja) {
			System.out.print(prihStanje + " ");
		}
		System.out.println();
		System.out.println(pocStanje); 
		*/
		
		
		/*
		for(String key: map.keySet()) {
			for(String simbol: map.get(key).keySet()) {
				System.out.println(key + " " + simbol + " " + map.get(key).get(simbol));
			}
		}*/
