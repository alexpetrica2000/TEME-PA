import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Stocks {

	public static final String INPUT_FILE = "stocks.in";
	public static final String OUTPUT_FILE = "stocks.out";

	// nr de burse, bugetul si pierderile maxime de care dispunem
	int nr_stocks, budget, maxloss;
	MyScanner sc;
	// echivalentul lui dp din laborator
	int [][][] stocks;

	// aici se efectueaza citirea si scrierea in fisier asemenea laboratorului
	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {  //citire ca in laborator
		try {
			// initializez numarul de stocuri bugetul si maxloss
			// + prima linie din dp, le pun pe toate 0
			sc = new MyScanner(INPUT_FILE);
			nr_stocks = sc.nextInt();
			budget = sc.nextInt();
			maxloss = sc.nextInt();
			stocks = new int [nr_stocks + 1][budget + 1][maxloss + 1];
			for (int j = 0; j <= budget; j++) {
				for (int k = 0; k <= maxloss; k++) {
					stocks[0][j][k] = 0;
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void writeOutput(int result) { // scriere ca in laborator
		try {
			PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
			pw.printf("%d\n", result);
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private int getResult() { // functia ce rezolva  taskul

		// asemenea problemei rucsacului imi pun problema: care este profitul maxim
		// pe care il pot obtine din primele i obiecte de buget j si maxloss k
		for (int i = 1; i <= nr_stocks; i++) {
			// valorile nu le mai tin intr-un vector, le folosesc direct din citire
			int currentValue = sc.nextInt();
			int minValue = sc.nextInt();
			int maxValue = sc.nextInt();
			// completez vectorul tridimensional
			for (int j = 0; j <= budget; j++) {
				for (int k = 0; k <= maxloss; k++) {
					// initial pun profitul obtinut din primele i - 1 obiecte ( ca la ruscac )
					// caz in care nu folosesc bursa, o copiez pe cea veche
					stocks[i][j][k] = stocks[i - 1][j][k];
					// profitul este calculat evident ca valoarea maxima posibila - cea curenta
					// iar lossul valoarea curenta - cea pe care o poate avea
					// in cel mai rau caz posibil
					int profit = maxValue - currentValue;
					int loss = currentValue - minValue;
					// testez daca pot adauga bursa la profitul meu maxim
					// adica daca se incadreaza in bugetul de care dispun si pierderile maxime
					// pe care mi le asum
					// si este si mai mare decat profitul adus de cele i - 1 burse de pana acum
					if (j - currentValue >= 0 && k - loss >= 0) {
						int sol_aux = stocks[i - 1][j - currentValue][k - loss] + profit;
						stocks[i][j][k] = Math.max(stocks[i][j][k], sol_aux);
					}
				}
			}
		}
		return stocks[nr_stocks][budget][maxloss];
	}
	public static void main(String []args) {
		new Stocks().solve();
	}
}

