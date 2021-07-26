public class CryptoObj implements Comparable<CryptoObj> {
	int x, y;
	public CryptoObj() {
		this(0,0);
	}
	public CryptoObj(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public int compareTo(CryptoObj o) {
		return this.x - o.x;
	}
}
