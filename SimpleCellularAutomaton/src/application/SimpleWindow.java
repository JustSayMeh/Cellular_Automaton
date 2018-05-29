package application;

import java.io.IOException;
import java.util.LinkedList;

import Window.Window;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.CellularField;
import model.CustMenuItem;

public class SimpleWindow extends Window{
	SimpleCellularAutomaton gm;
	public SimpleWindow(int csz, int rsz) throws IOException {
		gm = new SimpleCellularAutomaton(csz, rsz, 1);
		CellularField gfield = new CellularField(gm, new Color[]{Color.rgb(0,0,0,0), Color.rgb(0,0,214,1)});
		gfield.setPaneStyle("-fx-border-width: 0.4; -fx-border-color: #D3D3D3; -fx-padding: 4 4 4 4;");
		initControlPane(gfield);
		setCenter(gfield.getField());
		rnd.start();
	}
	private void initControlPane(CellularField gf) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SimpleWindow.class.getResource("SimplePane.fxml"));
		Pane btn = loader.load();
		setRight(btn);
		SplitMenuButton smb = (SplitMenuButton) btn.getChildren().get(0);
		smb.setOnMouseClicked((e)->{
			rnd.addOnce(gf);
		});
		smb.setText("Rule: 1");
		LinkedList<MenuItem> list = new LinkedList<MenuItem>();
		for (int i = 0; i < 255; i++) {
			CustMenuItem th = new CustMenuItem((i + 1));
			th.setOnAction(e->{
				gm.setRule(th.getNumber());
				smb.setText(th.getText());
			});
			list.add(th);
		}	
		smb.getItems().addAll(list);
		cp = (ColorPicker) btn.getChildren().get(1);
		cp.setOnAction((event)->{
			gf.setCurrentColor(cp.getValue());
		});
	}
	
	public static Window initialize(int i, int j, boolean tr) throws IOException {
		return new SimpleWindow(i, j);
	}
}
