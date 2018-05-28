package interfaces;

import Automatons.CellularAutomaton;
import javafx.event.Event;

public interface Clicker {
	public int await(int i, int j, CellularAutomaton at, Event ev);
}
