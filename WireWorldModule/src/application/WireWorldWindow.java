package application;

import java.io.IOException;

import Window.Window;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.CellularField;

public class WireWorldWindow extends Window{
	private int status = 3;
	private final String RunStr = "Run", StopStr = "Stop"; 
	public WireWorldWindow(int csz, int rsz, boolean tr) throws IOException {
		super();
		gm = new WireWorldAutomaton(csz, rsz, tr);
		CellularField gfield = new CellularField(gm, new Color[]{
				Color.rgb(0, 0, 0, 1), 
				Color.rgb(255, 0, 0, 1),
				Color.rgb(0, 0, 255, 1),
				Color.rgb(230, 230, 0, 1),
				}
		);
		gfield.setPaneStyle("-fx-border-width: 0.4; -fx-border-color: #D3D3D3; -fx-padding: 4 4 4 4;");
		initControlPane(gfield);
		setCenter(gfield.getField());
		rnd.start();
	}
	private void initControlPane(CellularField gf) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(WireWorldWindow.class.getResource("WWCP.fxml"));
		Pane pn = loader.load();
		setRight(pn);
		Button run = (Button) pn.getChildren().get(1);
		Button step = (Button) pn.getChildren().get(2);
		RadioButton cl1 = (RadioButton) pn.getChildren().get(3);
		RadioButton cl2 = (RadioButton) pn.getChildren().get(4);
		RadioButton cl3 = (RadioButton) pn.getChildren().get(5);
		ToggleGroup tg = new ToggleGroup();
		cl1.setToggleGroup(tg);
		cl1.setSelected(true);
		cl2.setToggleGroup(tg);
		cl3.setToggleGroup(tg);
		tg.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)->{
				if (newValue.equals(cl1)) {
					status = 3;
					System.out.print("cl1");
				}else if (newValue.equals(cl2)) {
					status = 1;
					System.out.print("cl2");
				}else if (newValue.equals(cl3)) {
					status = 2;
					System.out.print("cl3");
				}
		});
		gf.setOnMouseClick((i, j, gm, ev)->{
			int old = gm.getState(i, j);
			if (old == 0 && status < 3)
				return old;
			if (old < 3 && status < 3)
				return gm.chClass(i, j, 3);
			if (old == status)
				return gm.chClass(i, j, 0);
			return gm.chClass(i, j, status);
		});
		
		run.setOnMouseClicked((event)->{
			switch (run.getText()) {
			case StopStr:
				run.setText(RunStr);
				rnd.delMain(gf);
				break;
			case RunStr:
				run.setText(StopStr);
				rnd.addMain(gf);
				break;
			default:
				break;
			}
			
		});
		step.setOnAction((event)->{
			rnd.addOnce(gf);
		});
		gf.setOnChangeStopListener(()->{
			run.setText(RunStr);
			rnd.delMain(gf);
		});
		
	}
	
	public static Window initialize(int i, int j, boolean tr) throws IOException {
		return new WireWorldWindow(i, j, tr);
	}
}
