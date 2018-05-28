package Automatons;

public abstract class CellularAutomaton {
	protected int[][] boxes, cboxes;
	public final int cSize, rSize;
	public CellularAutomaton(int csz, int rsz) {
		boxes = new int[rsz][csz];
		cboxes = new int[rsz][csz];
		cSize = csz;
		rSize = rsz;
	}
	public abstract void tick();
	public abstract int chClass(int i, int j, int klass);
	public abstract int getState(int i, int j);
	public abstract boolean isChanged();
	public int getFieldClass(int i, int j) {
		return boxes[i][j];
	}
}
