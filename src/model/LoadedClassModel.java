package model;

public class LoadedClassModel {
	private String name, path, wcName;
	private int xSize, ySize;
	private boolean Tor = true;
	public LoadedClassModel(String n, String p, String mcN, int x, int y, boolean isTor) {
		name = n;
		path = p;
		wcName = mcN;
		xSize = x;
		ySize = y;
		Tor = isTor;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getWindowClass() {
		return wcName;
	}
	public int getXsz() {
		return xSize;
	}
	public int getYsz() {
		return ySize;
	}
	public boolean isTor() {
		return Tor;
	}
	public void setTor(boolean th) {
		Tor = th;
	}
	public LoadedClassModel() {
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPath(String pt) {
		path = pt;
	}
	public void setmcName(String mname) {
		wcName = mname;
	}
	public void setBounds(int xsize, int ysize) {
		xSize = xsize;
		ySize = ysize;
	}
	@Override
	public String toString() {
		return name;
	}
}
