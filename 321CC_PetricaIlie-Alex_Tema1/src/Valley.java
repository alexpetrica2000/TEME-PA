import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;

public class Valley {

	public static final String INPUT_FILE = "valley.in";
	public static final String OUTPUT_FILE = "valley.out";

	// numar de calculatoare si bani disponibili
	int n, min, poz, i;
	//vector cu obiecte ce contin p[i] si u[i]
	Vector<Integer> array = new Vector<>();
	long cost = 0;
	// aici se efectueaza citirea si scrierea in fisier asemenea laboratorului
	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {  //citire ca in laborator
		try {
			MyScanner sc = new MyScanner(INPUT_FILE);
			n = sc.nextInt();
			min = Integer.MAX_VALUE;
			poz = 0;
			// determinul minimul dintre varfuri
			// deoarece de aici pleaca cea mai optima solutie
			// il iau pe primul dar daca minimul exista de mai multe ori
			// este irelevant, oricum intre ele va trebui sa se ajunga tot
			// la valoarea minimului
			for (i = 0; i < n; i++) {
				array.add(sc.nextInt());
				if (min > array.get(i)) {
					min = array.get(i);
					poz = i;
				}
			}
			// daca minimul este pe pozitia 0 trebuie sa iau punctul de reper
			// pentru vale pozitia imediat urmatoare (deoarece sufixul si prefixul
			// au minim 2 elemente
			if (poz == 0) {
				poz = 1;
				cost = array.get(1) - array.get(0);
			}
			// daca minimul este pe pozitia n - 1 trebuie sa iau punctul
			// de reper pentru vale pozitia imediat anterioara din acelasi motiv
			// intre sufix si prefix
			if (poz == n - 1) {
				poz = n - 2;
				cost = array.get(n - 2) - array.get(n - 1);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(long result) { // scriere ca in laborator
		try {
			PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
			pw.printf("%d\n", result);
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private long getResult() { // functia ce rezolva  taskul
		// imi propun sa fac siruri descrescatoare
		// atat intre 0 si poz - 1 cat si intre n - 1 si poz + 1
		// astfel se formeaza o vale cu punctul dintre sufix si prefix pe pozitia poz

		// formez sirul descrescator intre 0 si poz - 1
		// daca este nevoie sa sap un varf deoarece este
		// mai mare decat varful anterior deci strica sirul desc, il modific
		// si rectific costul (daca este mai mare decat cel anterior el
		// trebuie sa ajunga la nivelul acestuia pentru a se pastra valea
		// si costul minim )
		for (i = 0; i < poz - 1; i++) {
			if (array.get(i + 1) > array.get(i)) {
				cost += array.get(i + 1) - array.get(i);
				array.set(i + 1, array.get(i));
			}
		}
		// acelasi lucru doar ca intre pozitiile n - 1 si poz
		for (i = n - 1; i > poz + 1; i--) {
			if (array.get(i - 1) > array.get(i)) {
				cost += array.get(i - 1) - array.get(i);
				array.set(i - 1, array.get(i));
			}
		}
		return cost;
	}
	public static void main(String[] args) {
		new Valley().solve();
	}
}

