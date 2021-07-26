import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Lego {

	public static final String INPUT_FILE = "lego.in";
	public static final String OUTPUT_FILE = "lego.out";

	int N, K, T;
	String sol = "";
	String prevsol = "";
	int len = 0;
	static int[][] dp;


	public void solve() throws FileNotFoundException {
		readInput();
		getResult();
	}

	private void readInput() {
		try {
			MyScanner sc = new MyScanner(INPUT_FILE);
			// citim datele de intrare
			N = sc.nextInt();
			K = sc.nextInt();
			T = sc.nextInt();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void getResult() throws FileNotFoundException {
		// vectorii de solutie si domeniu din bkt + initializare
		ArrayList<Integer> solution = new ArrayList<>();
		ArrayList<Integer> domain = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			solution.add(0);
		}
		for (int i = 0; i < N; i++) {
			domain.add(i + 1);
		}
		// efectuam backtrackingul ca in lab 5
		// la combinari, doar ca luam doar
		// combinarile care incep cu 1
		back(0, K, domain, solution);
		sol = sol.substring(1);

		PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
		pw.printf("%d\n", len);
		pw.printf("%s\n", sol);
		pw.close();

	}

	void printSolution(ArrayList<Integer> solution, ArrayList<Integer> domain, int stop) {
		// in solsol avem solutia de la acest pas
		// in prevsol o salvam ca string (solutie partiala, se va copia
		// in stringul sol daca este globala)
		int []solsol = new int [K];
		prevsol = "";

		for (int i = 0; i < stop; i++) {
			prevsol = prevsol + " " + domain.get(solution.get(i));
			solsol[i] = domain.get(solution.get(i));
		}
		findmax(solsol);
	}
	public void findmax(int []solsol) {
		// dp este declarat global, deci pentru fiecare solutie posibila
		// vom calcula numarul minim de monede pentru a acoperi
		// toate sumele posibile formate din acele numere
		// in lungime vom stoca lungimea maxima (cate numere consecutive am obtinut folosind
		// maxim T piese)
		// pentru aceasta solutie si va fi comparata cu len(lungimea globala a problemei)
		int i;
		int lungime = 0;
		int dim = solsol.length;
		getMinCoins(solsol, dim + 1, T * solsol[dim - 1] + 1);

		// ultimul numar posibil din sirul consecutiv poate fi T * ultima cifra din solutie
		// iteram si daca la un pas numarul de monede cu care se obtine acea suma
		// este mai mare decat T aflam daca este un sir consecutiv de lungime maxima
		// sol este practic sirul solutie global ca string, prevsol este sirul solutie
		// la pasul curent
		for (i = 1; i <= T * solsol[dim - 1]; i++) {
			if (dp[dim][i] > T) {
				if (lungime > len) {
					len = lungime;
					sol = prevsol;
				}
				lungime = 0;
				continue;
			}
			lungime++;
		}
		// facem si ultima verificare in cazul in care pentru ultima suma posibila
		// am obtinut un numar de monede <= T
		if (lungime > len) {
			len = lungime;
			sol = prevsol;
		}
	}
	void back(int step, int stop, ArrayList<Integer> domain, ArrayList<Integer> solution) {

		if (step == stop) {
			printSolution(solution, domain, stop);
			return;
		}
		int i = step > 0 ? solution.get(step - 1) + 1 : 0;

		for (; i < domain.size(); i++) {
			solution.set(step,i);
			// efectuam doar primul pas in bkt, pentru a avea doar
			// combinarile ce incep cu 1
			if (solution.get(0) != 0) {
				return;
			}
			back(step + 1, stop, domain, solution);
		}
	}

	// NotAgain (Coin Change problem) din laborator, extinsa
	// practic calculeaza numarul minim de monede din primele i monede si suma j
	public static void getMinCoins(int []coin, int rowlength, int collength) {
		int row = rowlength;
		int col = collength;
		dp = new int[row][col];

		// initializare matrice dp
		// din 0 monede nu poti obtine nici o suma, deci numar infinit de monede de 0
		// pentru a obtine suma 0 din primele i monede ai nevoie de 0 monede
		for (int i = 1; i < col; i++) {
			dp[0][i] = Integer.MAX_VALUE - 1;
		}
		for (int i = 0; i < row; i++) {
			dp[i][0] = 0;
		}

		// completare matrice dp
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				// verificam daca se poate folosi moneda descoperita
				// la pasul i (coin este 0 indexed deci i - 1) pentru a obtine suma j
				if (j - coin[i - 1] < 0) {
					dp[i][j] = dp[i - 1][j];
				} else {
					dp[i][j] = Math.min(dp[i - 1][j], 1 + dp[i][j - coin[i - 1]]);
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Lego().solve();
	}
}


