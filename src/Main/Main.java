package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Loaders.WindowLoader;
import interfaces.PostLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.LoadedClassModel;


public class Main extends Application {
	private static Stage stage;
	private BorderPane rootLayout;
	private static Scene launcher;
	private static Node left;
	private Runnable loadrun = ()->{Platform.runLater(()->{
		ProgressIndicator pi = new ProgressIndicator(-1);
		pi.setMaxHeight(50);
		pi.setMaxWidth(50);
		left = rootLayout.getLeft();
		rootLayout.setCenter(pi);
		rootLayout.setLeft(null);
	});
	};
	private PostLoader show = (wd)->{Platform.runLater(()->{
			try {
				stage.setScene(wd.getScene());
			} catch (IOException e) {
				e.printStackTrace();
			}
	});};
	@Override
	public void start(Stage primaryStage) throws IOException {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../view/Launcher.fxml"));
		rootLayout = loader.load();
		launcher = new Scene(rootLayout);
		stage.setScene(launcher);
		ListView<LoadedClassModel> st = (ListView<LoadedClassModel>) rootLayout.getLeft();
		Collection col = initLoadedClasses();
		System.out.print(col.size());
		st.getItems().addAll(col);
		WindowLoader ml = new WindowLoader(loadrun, show);
		st.getSelectionModel().selectedItemProperty().addListener((observable,
				oldValue, newValue)->{
			LoadedClassModel th = observable.getValue();
			ml.load(th);
		});
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);	
	}
	public static void reload() {
		((BorderPane)launcher.getRoot()).setLeft(left);
		stage.setScene(launcher);
		((BorderPane)launcher.getRoot()).setCenter(null);
		System.gc();
	}
	private LinkedList<LoadedClassModel> initLoadedClasses() throws IOException {
		LinkedList<LoadedClassModel> ret = new LinkedList<LoadedClassModel>();
		InputStream is = new FileInputStream("./config");
		Scanner r = new Scanner(is);
		LoadedClassModel cur = new LoadedClassModel();
		Pattern pt = Pattern.compile("^(Name|Path|Bounds|WindowClass|IsTor)\\s*=\\s*");
		Pattern pt2 = Pattern.compile("^\\[Module\\]$");
		while(r.hasNext()) {
			String line = r.nextLine();
			Matcher mt = pt.matcher(line);
			Matcher mt2 = pt2.matcher(line);
			if(mt.find()) {
				String arg = line.substring(mt.end());
				switch (mt.group(1).toString()) {
				case "Name":
					cur.setName(arg);
					break;
				case "IsTor":
					Scanner br = new Scanner(arg);
					cur.setTor(br.nextBoolean());
					break;
				case "Path":
					cur.setPath(arg);
					break;
				case "Bounds":
					Scanner ar = new Scanner(arg);
					int i1 = ar.nextInt();
					int i2 = ar.nextInt();
					System.out.println(i1 + " " + i2);
					cur.setBounds(i1, i2);
					break;
				case "WindowClass":
					cur.setmcName(arg);
				default:
					break;
				}
			}else if (mt2.find()){
				cur = new LoadedClassModel();
				ret.add(cur);
			}
		}
		is.close();
		return ret;
	}
}
