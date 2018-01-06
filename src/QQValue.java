
public class QQValue {

	

	
	// from https://bbs.125.la/thread-13983831-1-1.html
	
	
	public static String getptqrtoken(String qrsig)
	{
		int e = 0;
		for (int i = 0, n = qrsig.length(); n > i; ++i)
		e += (e << 5) + qrsig.charAt(i);
	return String.valueOf(2147483647 & e);

	}
	public static String qrsig ="qrsig";
	public static String urcode="https://ssl.ptlogin2.qq.com/ptqrshow?appid=715030901";
}
