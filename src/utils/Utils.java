package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Utils {

	public static void writeString(String data,String path) throws IOException
	{
		
		File file = new File(path);
		if(!file.getParentFile().exists())file.getParentFile().mkdirs();
		
		
		OutputStream os = new FileOutputStream(path);
		os.write(data.getBytes());
		
		os.close();
		
	}
	
	public static String readString(String path) throws Exception
	{
		File file = new File(path);
		if(!file.exists())return null;
		if(!file.canRead())return null;
		InputStream is = new FileInputStream(path);
		byte[] buff = new byte[is.available()];
		is.read(buff);
		is.close();
		return new String(buff);
	}

	
	public static Object readObject(String path) throws Exception
	{
		InputStream is = new FileInputStream(path);
		
		
		ObjectInputStream objin = new ObjectInputStream(is);
		
		return objin.readObject();
		
		
		
	}
	public static void writeObject(String path,Object obj) throws Exception
	{
		File out= new File(path);
		OutputStream os = new FileOutputStream(out);
		
		ObjectOutputStream objectout = new ObjectOutputStream(os);
		objectout.writeObject(obj);
		objectout.close();
		
	}
}
