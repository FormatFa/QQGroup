package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author formatfa
 *  Http工具类，下载字符，图片那些
 */
public class HttpUtils {
	
	
	/**
	 * @author formatfa
	 * Http返回结果，headerFiels为返回的头，object 自行转换为相应的类型
	 */
	public static class HttpResult
	{
		public HttpResult(Object result, Map<String, List<String>> headerFields) {
			super();
			this.result = result;
			this.headerFields = headerFields;
		}
		Object result;
		
		public Object getResult() {
			return result;
		}

		public void setResult(Object result) {
			this.result = result;
		}

		public Map<String, List<String>> getHeaderFields() {
			return headerFields;
		}

		public void setHeaderFields(Map<String, List<String>> headerFields) {
			this.headerFields = headerFields;
		}
		Map<String, List<String>> headerFields;
		
		
	}
	
	
	/**
	 * @param url 下载的路径
	 * @param path 保存的目录
	 * @param maps post参数键
	 * @return 下载结果
	 * @throws Exception
	 */
	public static HttpResult downFile(String url,String path,HashMap<String,String> maps) throws Exception {
		HttpResult re = down(url,maps);
		InputStream is =(InputStream) re.getResult();
		
		OutputStream os = new FileOutputStream (path);
		copyStream(is,os);
		
		return re;
	}
	
	
	
	
	public static HttpResult downloadString(String url,HashMap<String,String > cookies) throws Exception
	{
	return downloadString(url,cookies,"UTF-8");
	}
	
	/**
	 * @param url 网址
	 * @param post post键值对
	 * @param charset 下载返回的编码
	 * @return httpresult string
	 * @throws Exception
	 */
	public static HttpResult downloadString(String url,HashMap<String,String > post,String charset) throws Exception
	{
		
		StringBuilder result = new StringBuilder();
		
		HttpResult re = down(url,post);
		
		InputStream is =(InputStream) re.getResult();
		
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(is,charset));
				
		String line = null;
				
				while(  (line = buffReader.readLine())!=null )
				{
					result.append(line)
;				}
		return new HttpResult(result.toString(), re.getHeaderFields());
		
		
	}
	
	/**
	 * @param url
	 * @return inputstream
	 * @throws Exception
	 */
	public static HttpResult down(String url) throws Exception
	{
		return down(url,null);
	}
	
	public static HttpResult down(String url,HashMap<String,String> cook) throws Exception
	{
		
		URL u = new  URL(url);
		
		HttpURLConnection connection = (HttpURLConnection)u.openConnection();
		
		
		if(cook!=null)
		{
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			
		}
		
        Map<String, List<String>> requestheader = connection.getRequestProperties();
		

		connection.connect();
		
		
		
		
		
		
		if(cook!=null)
		{
			DataOutputStream os = new DataOutputStream(connection.getOutputStream());
			StringBuilder sb = new StringBuilder();
			for(String str:cook.keySet())
				
			{
				String s = str+"="+cook.get(str);
				sb.append(s);
				
				sb.append('&');
			}
			
			String po = sb.toString();
			
			if(po.endsWith("&"))
				po = po.substring(0,po.length()-1);
	
			os.write(po.getBytes());
			os.flush();
			os.close();
		}
		
		


		Map<String, List<String>> header = connection.getHeaderFields();

	 return new HttpResult(connection.getInputStream(),header);
	}
	
	
	
	
	
	public static void copyStream (InputStream is,OutputStream os) throws IOException
	{
	byte[] buff = new byte[1024*4];
	int readcout;
	
	while (   (readcout=is.read(buff))!=-1   )
	{
		os.write(buff,0,readcout);
	}
	
	os.close();
	
	
		
	}

}
