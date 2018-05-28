package Loaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Main.Main;
import Window.Window;
import interfaces.PostLoader;
import model.LoadedClassModel;

public class WindowLoader{
	private PostLoader run;
	private Runnable pre;
	public WindowLoader(Runnable r, PostLoader rn) {
		run = rn;
		pre = r;
	}
	public void load(LoadedClassModel th) {
		new Thread(){
			@Override
			public void run() {
				pre.run();
				Window wd = getWindow(th.getPath(), 
						th.getWindowClass(), 
						th.getXsz(), 
						th.getYsz(), 
						th.isTor());
				run.postLoad(wd);
			}
		}.start();
	}
	
	private Window getWindow(String str, String str2, int i, int j, boolean tr) {
		ModuleLoader wl = new ModuleLoader(str, 
				Main.class.getClassLoader());
		Method m;
		try {
			m = wl.loadClass(str2).getMethod("initialize", int.class, int.class, boolean.class);
			return (Window)m.invoke(null, i, j, tr);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}
