import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Crypto {

	public static final String INPUT_FILE = "crypto.in";
	public static final String OUTPUT_FILE = "crypto.out";

	// numar de calculatoare si bani disponibili
	int n, B;
	//vector cu obiecte ce contin p[i] si u[i]
	Vector<CryptoObj> array = new Vector<>();

	// aici se efectueaza citirea si scrierea in fisier asemenea laboratorului
	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {  //citire ca in laborator
		try {
			MyScanner sc = new MyScanner(INPUT_FILE);
			n = sc.nextInt();
			B = sc.nextInt();
			for (int i = 0; i < n; i++) {
				array.add(new CryptoObj(sc.nextInt(),sc.nextInt()));
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
		Collections.sort(array);  // sortam dupa puterea initiala a pcului
		long S = 0; // aici o sa contorizam suma finala de la un pas
		// suma este costul total necesar pentru un upgrade
		// tuturor pcurilor pana la un anumit pas
		// suma2 retine fostul S (cat s-a platit pana in pct curent)
		long suma = 0, suma2 = 0;
		int i;

		for (i = 0; i < array.size() - 1; i++) {
			// daca diferenta dintre 2 pcuri este egala, doar le pun in suma
			// altfel, inseamna ca imi propun sa ajung la puterea celui mai mare
			if (array.get(i + 1).x == array.get(i).x) {
				suma += array.get(i).y;
			} else {
				suma += array.get(i).y;
				// aici incerc sa ajung la puterea celui mai mare de la pas curent
				S = suma2 + suma * (array.get(i + 1).x - array.get(i).x);
				// daca suma totala sa ajung la puterea lui array[i+1]
				// pentru toate calc este mai mare decat suma de bani de care
				// dispun, ies din for
				if (S > B) {
					break;
				}
				// altfel actualizez suma2 cu valoarea curenta S
				suma2 = S;
				array.get(i).x = array.get(i + 1).x;
				// daca sunt la ultima iteratie in for si nu am dat break
				// actualizez si suma cu costul upgradeului pt ult pc
				if (i + 1 == array.size() - 1) {
					suma += array.get(i + 1).y;
				}
			}
		}
		S = suma2;
		// daca am iesit din for inseamna ca
		// ori am reusit sa le upgradez pe toate la nivelul ult pc si mai am
		// bani ori nu mai am bani si m-am oprit cand nu puteam ajunge
		// la nivelul urmatorului
		int maxim;
		// daca am ajuns la niv ultimului atunci maxim = 0
		// daca nu, jumatate din diferenta lor
		if (i == array.size() - 1) {
			maxim = 0;
		} else {
			maxim = (array.get(i + 1).x - array.get(i).x) / 2;
		}
		// daca maxim este diferit de 0
		// incerc sa ajung cat mai aproape de sol finala
		if (maxim != 0) {
			while ((suma2 + suma * maxim) > B) {
				maxim = maxim / 2;
			}
		} else {
			maxim = 1;
		}
		// daca maxim iese 1 incerc sa upgradez cate 1 nivel pt toate odata
		// altfel le upgradez pe toate cu maxim o data si upgradez cu cate 1
		// pana la sol finala
		if (maxim == 1) {
			while (S <= B) {
				S += suma;
				array.get(i).x++;
			}
			array.get(i).x--;
		} else {
			S = suma2 + suma * maxim;
			array.get(i).x += maxim;
			while (S <= B) {
				S += suma;
				array.get(i).x++;
			}
			array.get(i).x--;
		}
		return array.get(i).x;
	}
	public static void main(String[] args) {
		new Crypto().solve();
	}
}


