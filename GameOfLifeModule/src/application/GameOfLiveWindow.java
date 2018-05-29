package application;
import java.io.IOException;

import Window.Window;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.CellularField;

public class GameOfLiveWindow extends Window{
	private final String RunStr = "Run", StopStr = "Stop"; 
	public GameOfLiveWindow(int i, int j, boolean tr) throws IOException {
		super();
		gm = new GameOfLife(i, j, tr);
		CellularField gfield = new CellularField(gm, new Color[]{
				Color.rgb(0,0,0,0), 
				Color.rgb(0,0,214,1)}
		);
		gfield.setPaneStyle("-fx-border-width: 0.4; -fx-border-color: #D3D3D3; -fx-padding: 4 4 4 4;");
		initControlPane(gfield);
		setCenter(gfield.getField());
		rnd.start();
	}
	private void initControlPane(CellularField gf) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GameOfLiveWindow.class.getResource("Pane.fxml"));
		Pane btn = loader.load();
		setRight(btn);
		Button run = (Button) btn.getChildren().get(2);
		Button step = (Button) btn.getChildren().get(3);
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
		cp = (ColorPicker) btn.getChildren().get(0);
		cp.setOnAction((event)->{
			gf.setCurrentColor(cp.getValue());
		});
	}
	public static Window initialize(int i, int j, boolean tr) throws IOException {
		return new GameOfLiveWindow(i, j, tr);
	}
}
