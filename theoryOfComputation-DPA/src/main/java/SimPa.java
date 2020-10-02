import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class SimPa {
	
	public static void main(String[] args) {
		Map<String, String> prijelazi = new LinkedHashMap<>();
		List<List<String>> ulaz_nizovi = new ArrayList<List<String>>();
		List<String> stanja;
		List<String> ulaz_znakovi;
		List<String> stog_znakovi;
		String pocStanje = null;
		String pocZnakStog = null;
		List<String> prihStanja;
		Scanner sc = null;
		//			sc = new Scanner(new File("C:\\Users\\Korisnik\\Desktop\\SPRUT\\DPA\\tests\\test15\\primjer.in"));
		sc = new Scanner(System.in);
		String[] arr = sc.nextLine().split("\\|");
		for(String s: arr) {
			List<String> inner_niz = new ArrayList<String>();
			inner_niz.addAll(Arrays.asList(s.split(",")));
			ulaz_nizovi.add(inner_niz);
		}
		stanja = new ArrayList<String>(Arrays.asList(sc.nextLine().split(",")));
		ulaz_znakovi = new ArrayList<String>(Arrays.asList(sc.nextLine().split(",")));
		stog_znakovi = new ArrayList<String>(Arrays.asList(sc.nextLine().split(",")));
		prihStanja = new ArrayList<String>(Arrays.asList(sc.nextLine().split(",")));
		pocStanje = sc.nextLine();
		pocZnakStog = sc.nextLine();
		//citaj prijelaze
		String line = null;
		while(sc.hasNextLine() == true && (line = sc.nextLine()).isEmpty() == false) {
			String[] io = line.split("->");
			String[] i = io[0].split(",");
			StringBuilder sb = new StringBuilder();
			for(String input: i) {
				sb.append(input);
			}
			prijelazi.put(sb.toString(), io[1]);
 		}
		Stack<String> stog = new Stack<String>();
		for(List<String> niz: ulaz_nizovi) {
			stog.push(pocZnakStog);
			System.out.println(parse(prijelazi, niz,pocStanje,stog,prihStanja));
			stog.clear();
		}
		sc.close();
	}
	
	private static String parse(Map<String, String> prijelazi, List<String> niz, String pocStanje, Stack<String> stog, List<String> prihStanja) {
		StringBuilder sb = new StringBuilder(pocStanje+"#" +stog.peek() + "|");
		String trenutno_stanje = pocStanje;
		boolean fail = false;
		trenutno_stanje = rijesiEpsilon(prijelazi, stog, trenutno_stanje,sb);
		if(trenutno_stanje == null) {
			sb.append("fail|0");
			return sb.toString();
		}
		String peek = null;
		for(String ulazni_znak: niz) {
			try {
				peek = stog.pop();
			} catch(EmptyStackException ex) {
				sb.append("fail|");
				fail = true;
				break;
			}
			String check = trenutno_stanje.concat(ulazni_znak).concat(peek);
//			System.out.println(check);
			if(prijelazi.containsKey(check) == false) {
				stog.push(peek);
				String novo_stanje = rijesiEpsilon(prijelazi, stog, trenutno_stanje, sb);
				if(novo_stanje == null) {
					sb.append("fail|0");
					return sb.toString();
				}
				if(novo_stanje.equals(trenutno_stanje) && peek.equals(stog.peek())) {
					sb.append("fail|");
					fail = true;
					break;
				} else {
					String check2 = novo_stanje.concat(ulazni_znak).concat(stog.pop());
					if(prijelazi.containsKey(check2) == false) {
						sb.append("fail|");
						fail = true;
						break;
					}
					trenutno_stanje = rijesiMain(prijelazi, stog, novo_stanje, check2, sb);
				}
			} else {
				trenutno_stanje = rijesiMain(prijelazi, stog, trenutno_stanje, check, sb);
			}
		
		}
		if(fail == true) {
			sb.append("0");
		} else if(prihStanja.contains(trenutno_stanje) == true) {
			sb.append("1");
		} else if(prihStanja.contains(trenutno_stanje) == false) {
			trenutno_stanje = rijesiEpsilonModified(prijelazi, stog, trenutno_stanje,sb,prihStanja);
			if(prihStanja.contains(trenutno_stanje) == false) {
				sb.append("0");
			} else {
				sb.append("1");
			}
		}
		
		return sb.toString();
	}
	
	private static String rijesiEpsilon(Map<String,String> prijelazi, Stack<String> stog, String trenutno_stanje, StringBuilder sb) {
		String peek;
		while(true) {
			try {
				peek = stog.pop();
			} catch(EmptyStackException ex) {
				return null;
			}
			
			String check = trenutno_stanje.concat("$").concat(peek);
			if(prijelazi.containsKey(check) == true) {
				trenutno_stanje = rijesiMain(prijelazi, stog, trenutno_stanje, check, sb);
			} else {
				stog.push(peek);
				return trenutno_stanje;
			}
		}
	}
	
	private static String rijesiEpsilonModified(Map<String,String> prijelazi, Stack<String> stog, String trenutno_stanje, StringBuilder sb,List<String> prihStanja) {
		String peek;
		while(true) {
			if(prihStanja.contains(trenutno_stanje) == true) {
				return trenutno_stanje;
			}
			try {
				peek = stog.pop();
			} catch(EmptyStackException ex) {
				return null;
			}
			String check = trenutno_stanje.concat("$").concat(peek);
			if(prijelazi.containsKey(check) == true) {
				trenutno_stanje = rijesiMain(prijelazi, stog, trenutno_stanje, check, sb);
			} else {
				stog.push(peek);
				return trenutno_stanje;
			}
		}
	}
	
	private static String rijesiMain(Map<String, String> prijelazi, Stack<String> stog, String trenutno_stanje, String check, StringBuilder sb) {
		String output = prijelazi.get(check);
		String[] arr = output.split(",");
		String novoStanje = arr[0];
		char[] stog_novo = arr[1].toCharArray();
		Stack<String> tmpStack = new Stack<String>();
		if(stog_novo[0] != '$') {
			for(Character novi_znak_stog:stog_novo) {
				tmpStack.push(novi_znak_stog.toString());
			}
			while(!tmpStack.isEmpty()) {
				stog.push(tmpStack.pop());
			}
		}
		sb.append(novoStanje + "#" + getStog(stog) + "|");
		trenutno_stanje = novoStanje;
		return trenutno_stanje;
	}
	
	private static String getStog(Stack<String> stog) {
		String s = "";
		if(stog.isEmpty() == true) {
			return "$";
		}
		Stack<String> tmp = new Stack<String>();
		tmp.addAll(stog);
		Stack<String> tmp2 = new Stack<String>();
		tmp2.addAll(tmp);
		while(!tmp2.isEmpty()) {
			s = s.concat(tmp2.pop());
		}
		
		return s;
	}
	
	

}
