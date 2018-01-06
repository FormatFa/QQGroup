package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

	String name;
	public static String line = System.getProperty("line.separator","\n");
	String savePath = "D:/MyData/";
	
	String data = null;
	String split = "format:";
	Map < String , String> maps;
	public Data(String name) {
		super();
		this.name = name;
		
		
		savePath = savePath + name;
		maps = new HashMap<String,String>();
		
		String s = null;
		
		try {
			s=Utils.readString(savePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(s==null)return;
		
		String[] ss = s.split("\n");
		for(String str:ss)
		{
			String[] item = str.split(split);
			if(item.length==2)
			{
				maps.put(item[0], item[1]);
			}
		}
		
	}
	
	public void add(String key,String value)
	{
		if(maps.containsKey(key))maps.remove(key);
		maps.put(key,value);
	}
	
	public String getData(String key)
	{
		return maps.get(key);
		
	}
	public void addCookiesData(List<String> cookies)
	{
		if(cookies==null)return;
		for(String s:cookies)
		{
			String[] coo = s.split(";");
			
			for(String item:coo)
			{
				
				String[] keyandvalue = item.split("=");
				add(keyandvalue[0],keyandvalue[1]);
				
			}
			
		}
		
	}
	public void save()
	{
		
		
		StringBuilder sb = new StringBuilder();
		
		for(String s:maps.keySet())
		{
			
			sb.append(s);
			sb.append(split);
			sb.append(maps.get(s));
			sb.append(line);
			
		}
		
		try {
			Utils.writeString(sb.toString(), savePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
