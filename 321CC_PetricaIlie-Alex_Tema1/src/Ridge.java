import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class Ridge {

	public static final String INPUT_FILE = "ridge.in";
	public static final String OUTPUT_FILE = "ridge.out";

	MyScanner sc;
	int n;
	long [][]dp;
	Vector<RidgeObj> array = new Vector<>();

	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {  //citire ca in laborator
		try {
			sc = new MyScanner(INPUT_FILE);
			n = sc.nextInt();
			dp = new long[n + 1][3];
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
		// adaug in vector prima intrare ( primul varf si costul unei taieri ale acestuia)
		array.add(new RidgeObj(sc.nextInt(), sc.nextInt()));
		// formez matricea folosita pentru dinamica
		// pentru un sir cu un singur varf stiu costurile pentru 0 1 2 taieri
		dp[0][0] = 0;
		dp[0][1] = array.get(0).cost;
		dp[0][2] = array.get(0).cost * 2;
		// parcurg matricea pe linii si la fiecare pas mai citesc in vector un obiect RidgeObj
		// adica mai descopar un varf
		for (int i = 1; i < n; i++) {
			array.add(new RidgeObj(sc.nextInt(), sc.nextInt()));
			// pentru acest varf nou descoperit testez toate cazurile posibile pentru
			// a afla costul minim pentru tot sirul de a obtine varfuri adiacente de lungimi
			// diferite practic testez ce ar fi daca mi-as taia varful de 0, 1 ,2 ori
			// si incerc sa aflu daca ar fi egal cu varful anterior, daca si acesta
			// ar fi taiat de 0 1 sau 2 ori
			// adica iau toate cazurile posibile si aflu costul ce il voi stoca in dp[i][]
			for (int j = 0; j < 3; j++) {
				// valoare de referinta pentru costul minim pentru j taieri asupra varfului i
				long min = Long.MAX_VALUE;
				// testez daca se poate face taierea si pt fiecare caz daca acesta ar fi
				// egal cu varful anterior daca ar fi taiat de 0 1 2 ori
				// practic daca sunt egale in vreunul din cazuri minimul devine minimul
				// dintre celelalte 2 posibilitati ( adica daca de ex varful taiat de j ori
				// ar fi egal cu varful anterior taiat 1 data, aleg minimul dintre
				// daca varful anterior ar fi taiat niciodata sau de 2 ori, pentru a nu obtine
				// adiacenta cu valori egale de varfuri
				if (array.get(i).level - j >= 0) {
					if (array.get(i).level - j == array.get(i - 1).level) {
						min = Math.min(dp[i - 1][1], dp[i - 1][2]);
					}
					if (array.get(i).level - j == array.get(i - 1).level - 1) {
						min = Math.min(dp[i - 1][0], dp[i - 1][2]);
					}
					if (array.get(i).level - j == array.get(i - 1).level - 2) {
						min = Math.min(dp[i - 1][0], dp[i - 1][1]);
					}
				}

				// daca min a iesit diferit de infinit inseamna ca
				// varful i taiat de j ori a ajuns sa fie egal cu taierea varfului anterior in unul
				// din cele trei cazuri posibile, deci min contine
				// costul minim pentru a pastra sirul
				// cu varfuri adiacente de lungimi diferite
				// mai ramane doar de adaugat costul taierii j pentru varful i (acest cost
				// neluat in considerare pana acum

				if (min != Long.MAX_VALUE) {
					dp[i][j] = j * array.get(i).cost + min;
				} else {
					// daca min a iesit cu infinit inseamna  ori ca nu am putut efectua
					// taiere j asupra varfului caz in care dp[i][j] este infinit ori
					// dupa taiere totusi varful este >= 0,
					// nu a fost egal cu nici o taiere a varfului anterior, inseamna ca intre
					// taierea j si varful anterior taiat in toate posibilitatile
					// nu este o egalitate deci diferenta intre varful la pasul i taiat
					// cu j si varful anterior taiat in toate posibilitatile este in modul > 2
					// astfel linia lui dp[i][j] beneficiaza de minimul "global" intre cele 3 poz
					if (array.get(i).level - j >= 0) {
						min = Math.min(dp[i - 1][0], Math.min(dp[i - 1][1], dp[i - 1][2]));
						dp[i][j] = j * array.get(i).cost + min;
					} else {
						dp[i][j] = min;
					}
				}
			}
		}
		// rezultatul este minimul dintre costurile de pe linia n - 1
		return Math.min(dp[n - 1][0], Math.min(dp[n - 1][1], dp[n - 1][2]));
	}
	public static void main(String[] args) {
		new Ridge().solve();
	}
}


