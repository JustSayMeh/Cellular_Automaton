package application;
import Automatons.TDCellularAutomaton;
public class GameOfLife extends TDCellularAutomaton{
	public GameOfLife(int csz, int rsz, boolean tr) {
		super(csz, rsz, tr);
		Tor = tr;
	}
	public GameOfLife(int csz, boolean tr) {
		this(csz, csz, tr);
	}
	public GameOfLife(int csz) {
		this(csz, csz, true);
	}
	
	@Override
	public void tick() {
		changed = false;
		for (int i = 0; i < rSize; i++) {
			for (int j = 0; j < cSize; j++){
				int count = 0;
				int ui = (i == 0)? rSize - 1: i - 1;
				int di = (i + 1 == rSize)? 0: i + 1;
				int lj = (j == 0)? cSize - 1: j - 1;
				int rj = (j + 1 == cSize)? 0: j + 1;
				if ((i - 1 >= 0 && j - 1 >= 0) || Tor)
					count += boxes[ui][lj];
				if (i - 1 >= 0 || Tor)
					count += boxes[ui][j];
				if ((i - 1 >= 0 && j + 1 <  boxes[i].length) || Tor)
					count += boxes[ui][rj];
				if (j + 1 <  boxes[i].length || Tor)
					count += boxes[i][rj];
				if (j - 1 >= 0 || Tor)
					count += boxes[i][lj];
				if ((i + 1 < boxes.length && j - 1 >= 0) || Tor)
					count += boxes[di][lj];
				if (i + 1 < boxes.length || Tor)
					count += boxes[di][j];
				if ((i + 1 < boxes.length && j + 1 < boxes[i].length) || Tor)
					count += boxes[di][rj];
				
				if (count < 2 || count > 3) 
					cboxes[i][j] = 0;
				else if (count == 2)
					cboxes[i][j] = boxes[i][j];
				else
					cboxes[i][j] = 1;
				
				if (cboxes[i][j] != boxes[i][j])
					changed = true;
			}
		}
		int[][] cp = boxes;
		boxes = cboxes;
		cboxes = cp;
	};
}
