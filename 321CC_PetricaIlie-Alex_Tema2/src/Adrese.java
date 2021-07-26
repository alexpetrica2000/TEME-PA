import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

public class Adrese {

	public static final String INPUT_FILE = "adrese.in";
	public static final String OUTPUT_FILE = "adrese.out";

	int total_mails = 1;
	int k = 0;
	int N;
	public static final int NMAX = (int)1e7 + 5;

	// Hashmap de mailuri in care unui mail i se atribuie un index
	// graf de indexi
	Map<String, Integer> mails = new HashMap<>();
	ArrayList<Integer> []adj = new ArrayList[NMAX];

	// Lista de nume, lista de lista de indexi ca int, lista de stringuri
	// unde se vor stoca toate stringurile cerintei
	ArrayList<String> names = new ArrayList<>();
	List<ArrayList<Integer>> indexes = new ArrayList<>();
	Vector<String> strings = new Vector<>();

	public void solve() throws FileNotFoundException {
		readInput();
		getResult();
	}

	private void readInput() {  //citire ca in laborator
		try {
			MyScanner sc = new MyScanner(INPUT_FILE);
			N = sc.nextInt();
			strings.add("");

			// practic in cadrul citirii in names voi adauga
			// toate numele, in map corelez unui string un index
			// in aux voi tine lista curenta de indexi ce va fi adaugata
			// ulterior in indexes.
			// la finalul citirii voi pune names in string
			// deci pana la total mails am doar mailuri apoi nume
			// ideea corelarii este ca names[i] are mailurile cu index din indexes[i]
			// hashmapul ma ajuta ca pentru un mail deja existent sa nu mai creez alt index
			for (int i = 0; i < N; i++) {
				ArrayList<Integer> aux = new ArrayList<>();
				String name = sc.next();
				names.add(name);
				int nrmails = sc.nextInt();

				for (int j = 0; j < nrmails; j++) {
					String newmail = sc.next();
					mails.putIfAbsent(newmail, total_mails);
					aux.add(mails.get(newmail));
					strings.add(newmail);
					total_mails++;
				}
				indexes.add(aux);
			}
			strings.addAll(names);
			// graful meu are total_mails (mailurile) + names(numele) noduri
			for (int i = 1; i <= total_mails + names.size(); i++) {
				adj[i] = new ArrayList<>();
			}
			// construiesc graful neorientat pe baza corelarii intre names si indexes
			// construiesc nod intre fiecare nume si mailurile pe care acesta le are
			for (String name : names) {
				for (Integer val : indexes.get(k)) {
					adj[total_mails + k].add(val);
					adj[val].add(total_mails + k);
				}
				k ++;
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void getResult() throws FileNotFoundException {

		// in result retin rezultatul
		// stack si visited folosite pentru dfs pentru a afla componentele conexe
		List<List<String>> result = new ArrayList<>();
		boolean []visited = new boolean[total_mails + k + 1];
		Stack<Integer> stack = new Stack<>();
		PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));

		// pornesc dfs practic doar din nodurile nume, nu are sens sa incep din mailuri
		// pentru fiecare aflu componenta lor conexa, si daca in timp ce parcurg nodurile
		// gasesc un nod cu index >= total_mail inseamna ca e nume, compar cu numele actual pentru
		// a afla minimul lexicografic, daca este < total_mail este mail, il adaug la lista curenta
		// de rezultate ce va fi adaugata in rezultatul global (lista de liste)
		for (int i = total_mails; i < total_mails + k; i ++) {
			int s = i;

			if (visited[s]) {
				continue;
			}
			List<String> list = new ArrayList<>();
			String name = strings.get(i);

			stack.push(s);

			while (!stack.empty()) {
				s = stack.pop();
				if (!visited[s]) {
					if (s >= total_mails && name.compareTo(strings.get((s))) > 0) {
						name = strings.get(s);
					}

					if (s < total_mails) {
						list.add(strings.get(s));
					}
					visited[s] = true;
				}
				Iterator<Integer> itr = adj[s].iterator();

				while (itr.hasNext()) {
					int v = itr.next();
					if (!visited[v]) {
						stack.push(v);
					}
				}
			}
			// fac sortarea mailurilor si adaug in head numele
			// am aflat corect o componenta conexa, o adaugam la rezultat
			Collections.sort(list);
			list.add(0, name);
			result.add(list);
		}
		// vom sorta si lista rezultat deoarece avem conditia de a le sorta
		// dupa numarul de mailuri ale unui persoane (crescator) si dupa nume
		// in caz in care au acelasi numar de mailuri (minim lexicografic)
		Collections.sort(result, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> o1, List<String> o2) {
				if (o1.size() == o2.size()) {
					return o1.get(0).compareTo(o2.get(0));
				}
				return o1.size() - o2.size();
			}
		});
		pw.printf("%d\n", result.size());
		for (int i = 0; i < result.size(); i++) {
			pw.printf("%s %d\n", result.get(i).get(0), (result.get(i).size() - 1));
			for (int j = 1; j < result.get(i).size(); j++) {
				pw.printf("%s\n", result.get(i).get(j));
			}
		}
		pw.close();
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Adrese().solve();
	}
}


