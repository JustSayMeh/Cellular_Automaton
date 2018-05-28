package model;

import java.io.IOException;
import java.util.ArrayList;

import Automatons.CellularAutomaton;
import Main.Main;
import interfaces.Clicker;
import interfaces.Renderer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CellularField implements Renderer{
	private CellularAutomaton gm;
	private Color[] cColor;
	private ArrayList<Pane> array;
	private GridPane gp;
	private Runnable observer = ()->{};
	private Clicker innerHandler = (ri, ci, at, ev)->{
		int ks = at.getFieldClass(ri, ci) ^ 1;
		return gm.chClass(ri, ci, ks);
	};
	protected EventHandler me = (arg0)->{
		Pane rg = (Pane)arg0.getTarget();
		GridPane par = (GridPane)rg.getParent();
		Integer ci = par.getColumnIndex(rg);
		Integer ri = par.getRowIndex(rg);
		int ks = innerHandler.await(ri, ci, gm, arg0);
		Color vc = new Color(cColor[ks].getRed(), cColor[ks].getGreen(), cColor[ks].getBlue(), cColor[ks].getOpacity());
        rg.setBackground(new Background(new BackgroundFill(vc, CornerRadii.EMPTY, Insets.EMPTY)));
	}; 
	private final Runnable reDrawer = () ->{
		Color  vc;
		for (int i = 0; i < gm.rSize; i++) {
			for (int j = 0; j < gm.cSize; j++){
				Pane th = array.get(i * gm.cSize + j);
				int ks = gm.getFieldClass(i, j);
				vc = new Color(cColor[ks].getRed(), cColor[ks].getGreen(), cColor[ks].getBlue(), cColor[ks].getOpacity());
				th.setBackground(new Background(new BackgroundFill(vc, CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
	};
	public final void render() {
		if(!gm.isChanged())
		{
			Platform.runLater(observer);
			return;
		}
		gm.tick();
		Platform.runLater(reDrawer);
	}
	public CellularField(CellularAutomaton tgm, Color[] cl) throws IOException {
		gm = tgm;
		cColor = cl;
		array = new ArrayList<Pane>(gm.cSize * gm.rSize);
		initField();
	}
	public void setPaneStyle(String str) {
		for (Node f : gp.getChildren()) {
			f.setStyle(str);
		}
	}
	private void initField() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/first.fxml"));
		gp = (GridPane) loader.load();
		Platform.runLater(()->{
			
		});
		for (int i = 0; i < gm.rSize; i++) {
			for (int j = 0; j < gm.cSize; j++){
				Pane th = new Pane();
				th.setCursor(Cursor.CROSSHAIR);
				th.setPrefWidth(10.0);
				th.setPrefHeight(10.0);
				th.setMaxSize(10, 10);
                th.setBackground(new Background(new BackgroundFill(cColor[0], CornerRadii.EMPTY, Insets.EMPTY)));
				gp.add(th, j, i);
				array.add(i * gm.cSize + j, th);
			}
		}
		gp.autosize();
		gp.setOnMousePressed(me);
	}
	public GridPane getField() {
		return gp;
	}
	public void setCurrentColor(Color th) {
		cColor[1] = th;
	}
	public void setOnMouseClick(Clicker r) {
		innerHandler = r;
	}
	public void setOnChangeStopListener(Runnable r) {
		observer = r;
	}
}
