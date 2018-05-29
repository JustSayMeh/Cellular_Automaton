package application;

import Automatons.CellularAutomaton;

public class SimpleCellularAutomaton extends CellularAutomaton{
	private int nrule;
	private int curRSize = 1;
	private boolean changed = false;
	public SimpleCellularAutomaton(int sz, int r) {
		this(sz, sz, r);
	}
	public SimpleCellularAutomaton(int csz, int rsz, int r) {
		super(csz, rsz);
		nrule = r;
	}
	@Override
	public void tick() {
		for (int i = 1; i < rSize; i++) {
			for (int j = 0; j < cSize; j++){
				int center = boxes[i - 1][j];
				int right = boxes[i - 1][(j + 1) % cSize];
				int left = boxes[i - 1][(j == 0)? cSize - 1: j - 1];
				cboxes[i][j] = rule(left, center, right);
			}
			int[] cp = boxes[i];
			boxes[i] = cboxes[i];
			cboxes[i] = cp;
		}
		changed = false;
	}
	public void setRule(int f) {
		nrule = f;
		changed = true;
	}
	private int rule(int l, int c, int r) {
		int pw = l * (1 << 2) + c * (1 << 1) + r;
		return (nrule & (1 << pw)) >> pw;
	}
	@Override
	public int chClass(int i, int j, int klass) {
		if (i > 0)
			return boxes[i][j];
		boxes[i][j] = klass;
		changed = true;
		return klass;
	}
	@Override
	public int getState(int i, int j) {
		return boxes[i][j];
	}
	@Override
	public boolean isChanged() {
		return changed;
	}

}
