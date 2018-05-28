package Window;

import java.io.IOException;

import Automatons.CellularAutomaton;
import Main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Render;

public class Window {
	private float scale;
	protected ColorPicker cp;
	private BorderPane Root;
	protected Render rnd;
	private Scene scene;
	protected CellularAutomaton gm;
	private MenuBar mb;
	private Menu edit, file;
	public Window() throws IOException {
		rnd = new Render();
		initRoot();
	}
	public final Scene getScene() throws IOException {
		return scene;
	}
	protected void initRoot() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/Root.fxml"));
		Root = loader.load();
		mb = (MenuBar) Root.getTop();
		file = mb.getMenus().get(0);
		edit = mb.getMenus().get(1);
		edit.getItems().get(0).setAccelerator(KeyCombination.keyCombination("="));
		edit.getItems().get(1).setAccelerator(KeyCombination.keyCombination("-"));
		file.getItems().get(1).setAccelerator(KeyCombination.keyCombination("Alt+F4"));
		file.getItems().get(1).setOnAction((e)->{
			((Stage)(scene.getWindow())).close();
		});
		file.getItems().get(0).setOnAction((e)->{
			Main.reload();
		});
		scene = new Scene(Root);
	}
	protected final void setCenter(Node th) {
		ScrollPane sp = (ScrollPane) Root.getCenter();
		sp.setContent(th);
		sp.setPrefViewportHeight(100);
		sp.setPrefViewportWidth(100);
		scale = 1;
		edit.getItems().get(0).setOnAction((e)->{
				scale = 1;
				th.setScaleX(scale);
				th.setScaleY(scale);
			});
		edit.getItems().get(1).setOnAction((e)->{
			if (scale > 0.3)
				scale -= 0.1;
			th.setScaleX(scale);
			th.setScaleY(scale);
		});
	}
	protected final void setRight(Node th) {
		Root.setRight(th);
	}
	public static Window initialize() throws IOException {
		return new Window();
	}
}
