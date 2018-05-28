package application;

import Automatons.TDCellularAutomaton;

public class WireWorldAutomaton extends TDCellularAutomaton{

	public WireWorldAutomaton(int csz, int rsz, boolean tr) {
		super(csz, rsz, tr);
		Tor = tr;
	}

	@Override
	public void tick() {
		changed = false;
		for (int i = 0; i < rSize; i++) {
			for (int j = 0; j < cSize; j++){
				int status = boxes[i][j];
				if (status == 0)
					continue;
				if (status == 3)
				{
					int count = 0;
					cboxes[i][j] = boxes[i][j];
					int ui = (i == 0)? rSize - 1: i - 1;
					int di = (i + 1 == rSize)? 0: i + 1;
					int lj = (j == 0)? cSize - 1: j - 1;
					int rj = (j + 1 == cSize)? 0: j + 1;
					if (((i - 1 >= 0 && j - 1 >= 0) || Tor) && boxes[ui][lj] == 1)
						count += 1;
					if ((i - 1 >= 0 || Tor) && boxes[ui][j] == 1)
						count += 1;
					if (((i - 1 >= 0 && j + 1 <  boxes[i].length) || Tor)  && boxes[ui][rj] == 1)
						count += 1;
					if ((j + 1 <  boxes[i].length || Tor) && boxes[i][rj] == 1)
						count += 1;
					if ((j - 1 >= 0 || Tor) && boxes[i][lj] == 1)
						count += 1;
					if (((i + 1 < boxes.length && j - 1 >= 0) || Tor) && boxes[di][lj] == 1)
						count += 1;
					if ((i + 1 < boxes.length || Tor)&& boxes[di][j] == 1)
						count += 1;
					if (((i + 1 < boxes.length && j + 1 < boxes[i].length) || Tor)  && boxes[di][rj] == 1)
						count += 1;
					if (count >= 1 && count < 3)
						cboxes[i][j] = 1;
				}else
					cboxes[i][j] = boxes[i][j] + 1;
				if (cboxes[i][j] != boxes[i][j])
					changed = true;
			}
		}
		int[][] cp = boxes;
		boxes = cboxes;
		cboxes = cp;
	}

}
