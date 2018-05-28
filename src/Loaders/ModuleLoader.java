package Loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ModuleLoader extends ClassLoader{
	private String pathToSource;
	public ModuleLoader(String path, ClassLoader pl) {
		super(pl);
		pathToSource = path;
	}
	@Override
	public Class<?> findClass(String name){
		byte[] bt = null;
		try {
			bt = fetchClass(pathToSource + "bin/" + name.replace('.', '/') + ".class");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defineClass(name, bt, 0, bt.length);
	}
	
	private byte[] fetchClass(String path) throws IOException {
		File fl = new File(path);
		long len = fl.length();
		byte[] ms = new byte[(int) len];
		InputStream is = new FileInputStream(fl);
		int offset = 0;
		int readC = 0;
		while(offset < ms.length && (readC = is.read(ms, offset, ms.length)) >= 0) {
			offset += readC;
		}
		return ms;
	}
	@Override
	protected URL findResource(String name) {
		File f = loadFile(name);
		try {
			return f.toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private File loadFile(String name) {
		System.out.println(name);
		return new File(pathToSource + "view/" + name);
	}
}
