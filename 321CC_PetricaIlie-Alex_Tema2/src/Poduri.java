import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Poduri {

	public static final String INPUT_FILE = "poduri.in";
	public static final String OUTPUT_FILE = "poduri.out";

	int N,M, x, y;
	char [][] matrix;
	boolean [][]visited;

	public void solve() {
		readInput();
		writeOutput(getResult());
	}

	private void readInput() {
		try {
			MyScanner sc = new MyScanner(INPUT_FILE);
			// citire date intrare si coordonatele de plecare
			N = sc.nextInt();
			M = sc.nextInt();
			x = sc.nextInt();
			y = sc.nextInt();
			// matricea de caractere data ca input
			// vector vizitati folosit in bfs
			matrix = new char[N + 1][M + 1];
			visited = new boolean[N + 1][M + 1];
			for (int i = 1; i <= N; i++) {
				String s = sc.next();
				for (int j = 1; j <= M; j++) {
					matrix[i][j] = s.charAt(j - 1);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeOutput(int result) {
		try {
			PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
			pw.printf("%d\n", result);
			pw.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private int getResult() {

		// incepem bfs cu obiecte poduri obj
		// acestea retin coordonatele si nivelul lor fata de start
		// nodul sursa are level 0
		Queue<PoduriObj> queue = new LinkedList<>();
		queue.add(new PoduriObj(x, y,0));
		visited[x][y] = true;

		while (!queue.isEmpty()) {
			// scot din coada si verific indicele corespunzator in matricea
			// de caractere pentru a vedea ce mutari posibile am si
			// daca pot iesi din grid sau nu
			PoduriObj aux = queue.remove();

			// cazul 'D' ma pot misca in toate directiile ( fara diagonala )
			// verific daca pot iesi din grid si adaug in coada
			// poduri obj daca in respectivele noi coordonate nu am '.' si
			// nu au fost deja vizitate; level este levelul nodului scos curent din coada + 1.
			if (matrix[aux.x][aux.y] == 'D') {
				if (aux.x + 1 > N) {
					return aux.level + 1;
				}
				if (aux.x - 1 < 1) {
					return aux.level + 1;
				}
				if (aux.y + 1 > M) {
					return aux.level + 1;
				}
				if (aux.y - 1 < 1) {
					return aux.level + 1;
				}
				if (matrix[aux.x + 1][aux.y] != '.' && !visited[aux.x + 1][aux.y]) {
					visited[aux.x + 1][aux.y] = true;
					queue.add(new PoduriObj(aux.x + 1, aux.y, aux.level + 1));
				}
				if (matrix[aux.x - 1][aux.y] != '.' && !visited[aux.x - 1][aux.y]) {
					visited[aux.x - 1][aux.y] = true;
					queue.add(new PoduriObj(aux.x - 1, aux.y, aux.level + 1));
				}
				if (matrix[aux.x][aux.y + 1] != '.' && !visited[aux.x][aux.y + 1]) {
					visited[aux.x][aux.y + 1] = true;
					queue.add(new PoduriObj(aux.x, aux.y + 1, aux.level + 1));
				}
				if (matrix[aux.x][aux.y - 1] != '.' && !visited[aux.x][aux.y - 1]) {
					visited[aux.x][aux.y - 1] = true;
					queue.add(new PoduriObj(aux.x, aux.y - 1, aux.level + 1));
				}
			}
			// identic caz 'D' doar ca pe 2 directii
			if (matrix[aux.x][aux.y] == 'V') {
				if (aux.x + 1 > N) {
					return aux.level + 1;
				}
				if (aux.x - 1 < 1) {
					return aux.level + 1;
				}
				if (matrix[aux.x + 1][aux.y] != '.' && !visited[aux.x + 1][aux.y]) {
					visited[aux.x + 1][aux.y] = true;
					queue.add(new PoduriObj(aux.x + 1, aux.y, aux.level + 1));
				}
				if (matrix[aux.x - 1][aux.y] != '.' && !visited[aux.x - 1][aux.y]) {
					visited[aux.x - 1][aux.y] = true;
					queue.add(new PoduriObj(aux.x - 1, aux.y, aux.level + 1));
				}
			}
			// identic caz ' D' doar ca pe 2 directii
			if (matrix[aux.x][aux.y] == 'O') {
				if (aux.y + 1 > M) {
					return aux.level + 1;
				}
				if (aux.y - 1 < 1) {
					return aux.level + 1;
				}
				if (matrix[aux.x][aux.y + 1] != '.' && !visited[aux.x][aux.y + 1]) {
					visited[aux.x][aux.y + 1] = true;
					queue.add(new PoduriObj(aux.x, aux.y + 1, aux.level + 1));
				}
				if (matrix[aux.x][aux.y - 1] != '.' && !visited[aux.x][aux.y - 1]) {
					visited[aux.x][aux.y - 1] = true;
					queue.add(new PoduriObj(aux.x, aux.y - 1, aux.level + 1));
				}
			}
		}
		// daca s-a terminat bfsul si nu am dat return inseamna ca nu se poate
		// iesi din grid, intorc -1
		return -1;
	}
	public static void main(String[] args) {
		new Poduri().solve();
	}
}


