
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class SimEnka {

	public static void main(String[] args) throws IOException {
		List<String> stanja = new ArrayList<String>(); 
		List<String> abeceda = new ArrayList<String>();
		List<String> prihStanja = new ArrayList<String>();
		String pocStanje;
		BufferedReader sc;
		//System.setIn(new FileInputStream("C:\\Users\\Korisnik\\Desktop\\SPRUT\\lab1\\test26\\test.a"));
		sc = new BufferedReader(new InputStreamReader(System.in));
		List<String> ulaz = new ArrayList<String>(Arrays.asList(sc.readLine().split("\\|"))); //parse bez zareza
		List<String>[] ulazniNiz = new ArrayList[ulaz.size()]; //ulazni znakovi
		int z = 0;
		for (String s: ulaz) {
			String[] arr = s.split(",");
			ulazniNiz[z] = new ArrayList<String>(Arrays.asList(arr));
			z++;
		}
		stanja =  Arrays.asList(sc.readLine().split(","));
		abeceda = Arrays.asList(sc.readLine().split(","));
		prihStanja = Arrays.asList(sc.readLine().split(","));
		pocStanje = sc.readLine();	
		Map<String, Map<String, List<String>>> mapa = new LinkedHashMap<String, Map<String,List<String>>>();
		String line;
		boolean flag = true;
		/*tu je provjerit kraj, stavljan mapu u mapu i listu unutar,
		trebalo bi bit sve okej s redoslijedon jer koristin linkedHashMap a addAll stavlja odiza*/
		while((line = sc.readLine()) != null) {
			String[] myArr = line.split("->");
			String[] izlazStanja = myArr[1].split(",");
			List<String> izlazStanjaList = new ArrayList<String>();
			izlazStanjaList.addAll(Arrays.asList(izlazStanja));
			String[] stanjeZnak = myArr[0].split(",");
			if(stanjeZnak[1].equals("$") && izlazStanjaList.contains(stanjeZnak[0])) {
				izlazStanjaList.remove(stanjeZnak[0]);
			}
			if(mapa.containsKey(stanjeZnak[0]) == false) {
				Map<String, List<String>> innerMap = new LinkedHashMap<String,List<String>>();
				List<String> list = new ArrayList<String>();
				list.addAll(izlazStanjaList);
				innerMap.put(stanjeZnak[1], list);
				mapa.put(stanjeZnak[0], innerMap);
			}
			else {
				List<String> list = new ArrayList<String>(izlazStanjaList);
				mapa.get(stanjeZnak[0]).put(stanjeZnak[1], list);
			}		
		}
		
		/*for(String key: mapa.keySet()) {
			System.out.printf(key + " ");
			for(String znak: mapa.get(key).keySet()) {
				System.out.printf(znak + " ");
				for(String prijelaz: mapa.get(key).get(znak)) {
					System.out.printf(prijelaz + " ");
				}
			}
			System.out.println();
		}*/
		//sb da ne
		String dolar = "$";
		for(int k = 0; k < ulaz.size(); k++) {
			Set<String> cont = new TreeSet<String>();
			cont.add(pocStanje);
			Stack<String> stog = new Stack<String>();
			stog.push(pocStanje);
			Map<String, Integer> used = new HashMap<String, Integer>();
			while(stog.isEmpty() == false) {
				String myStanje = stog.pop();
				if(used.get(myStanje) == null && mapa.get(myStanje) != null && mapa.get(myStanje).get(dolar) != null) {
					cont.addAll(mapa.get(myStanje).get(dolar));
					stog.addAll(mapa.get(myStanje).get(dolar));
					used.put(myStanje, 0);
				}
			}
			cont.remove("#");
			print(cont);
			System.out.printf("|");
			int cnt = 0;
			for(String ulazniZnak: ulazniNiz[k]) {
				
				Set<String> temp = new TreeSet<String>();
				for(String elem: cont) {
					if(mapa.get(elem) != null) {
						if(mapa.get(elem).get(ulazniZnak) != null)
							temp.addAll(mapa.get(elem).get(ulazniZnak));
					}
				}
				cont.clear();
				cont.addAll(temp);
				//System.out.println(temp);
				Stack<String> st = new Stack<String>();
				st.addAll(temp);
				Map<String, Character> us = new HashMap<String, Character>(); 
				//System.out.println(st);
				while(st.isEmpty() == false) {
					//System.out.println("petlja");
					String e = st.pop();
					//System.out.println(e);
					if(mapa.get(e) != null && mapa.get(e).get(dolar) != null && us.get(e) == null) {
						cont.addAll(mapa.get(e).get(dolar));
						st.addAll(mapa.get(e).get(dolar));
						us.put(e,'a');
					}

				}
				cont.remove("#");
				print(cont);
				cnt++;
				if(cnt != ulazniNiz[k].size())
					System.out.printf("|");
			}
			System.out.printf("\n");
		}
		sc.close();
	}
	
	public static void print(Set<String> mySet) {
		int y = 0;
		if(mySet.isEmpty())
			System.out.printf("#");
		for(String s: mySet) {
			if(y == mySet.size() -1) {
				System.out.printf(s);
			}
			else {
				System.out.printf(s +",");
			}
			y++;
		}
	}
	
	
	
	
	
	
	
	

}
