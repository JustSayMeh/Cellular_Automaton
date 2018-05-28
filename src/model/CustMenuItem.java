package model;

import javafx.scene.control.MenuItem;

public class CustMenuItem extends MenuItem{
	int curNum;
	public CustMenuItem(int i) {
		super("Rule: " + i);
		curNum = i;
	}
	public int getNumber() {
		return curNum;
	}
}
