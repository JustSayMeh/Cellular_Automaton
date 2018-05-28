package Automatons;

import java.util.TreeMap;

public abstract class TDCellularAutomaton extends CellularAutomaton{
		protected TreeMap<Integer, Integer> kmap = new TreeMap<Integer, Integer>();
		protected boolean Tor, changed = false;
		public TDCellularAutomaton(int csz, int rsz, boolean tr) {
			super(csz, rsz);
			Tor = tr;
		}
		public TDCellularAutomaton(int csz, boolean tr) {
			this(csz, csz, tr);
		}
		public TDCellularAutomaton(int csz) {
			this(csz, csz, true);
		}
		@Override
		public int chClass(int i, int j, int klass) {
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
