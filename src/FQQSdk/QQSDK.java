package FQQSdk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import utils.HttpUtils;
import utils.HttpUtils.HttpResult;
import utils.Utils;

public class QQSDK {

	CookieManager cookiemng;

	LoginListener loginListener;

	String CSRFToken;

	public LoginListener getLoginListener() {
		return loginListener;
	}

	public void setLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

	int period = 3000;
	boolean isCancel = false;

	String result = null;

	
	
	
	List<Friends> qqFriends;
	public List<Friends> loadFriends() throws Exception
	{
	
		qqFriends = new ArrayList<Friends>();

		HashMap postData = new HashMap();
		postData.put("bkn", this.getCSRFToken(this.getCookie("skey")));
		String result =(String) HttpUtils.downloadString("http://qun.qq.com/cgi-bin/qun_mgr/get_friend_list", postData).getResult();
		
		return null;
	}
	public void printCookies() {
		for (URI u : cookiemng.getCookieStore().getURIs()) {

			List<HttpCookie> cookies = cookiemng.getCookieStore().get(u);

			cookies.forEach(cook -> {

				System.out.println(cook.getName() + "  " + cook.getValue());
			}

			);
		}
	}

	public String getCookie(String name) {

		for (URI u : cookiemng.getCookieStore().getURIs()) {

			List<HttpCookie> cookies = cookiemng.getCookieStore().get(u);

			cookies.forEach(cook -> {

				if (name.equals(cook.getName())) {
					result = cook.getValue();
				}
			}

			);
		}
		return result;
	}

	public static String getptqrtoken(String qrsig) {
		int e = 0;
		for (int i = 0, n = qrsig.length(); n > i; ++i)
			e += (e << 5) + qrsig.charAt(i);
		return String.valueOf(2147483647 & e);

	}

	public String[] ptuiCBParse(String ptui)
	{
		String[] result = new String[6];
		
		if(ptui==null)return result;
		if(ptui.startsWith("ptuiCB")==false)return result;
		
		if(ptui.length()< 8)return result;
		ptui = ptui.substring(7,ptui.length()-2);
		
		
		
		result = ptui.split(",");
		
		
		for(int i = 0;i< result.length;i+=1)
		{
			result[i] = result[i].replace("'","");
		}
			return result;
		
	}
	// 获取好友列表那些要post一个bkn参数
	String getCSRFToken(String skey) {

		if(skey == null)return null;
		int r = 5381;
		for (int n = 0, o = skey.length(); o > n; ++n)
			r += (r << 5) + skey.charAt(n);
		return this.CSRFToken = String.valueOf(2147483647 & r);

	}

	public static String qrsig = "qrsig";
	public static String urcode = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=715030901";

	public QQSDK() {
		super();

		cookiemng = new CookieManager();
		cookiemng.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

		CookieHandler.setDefault(cookiemng);
		
		try {
			readCookies();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}

	public void cancel() {
		isCancel = true;
	}

	int requestTime = 0;

	
	public void login(String url) throws Exception
	{
		String result =(String) HttpUtils.downloadString(url,null).getResult();
		System.out.println("返回的登录成功的url访问结果:" + result);
		
	}
	/**
	 * @param urpath 保存2维码的目录
	 * @throws Exception
	 */
	public void startLink(String urpath) throws Exception {

		isCancel = false;
		String url = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=715030901&daid=73&pt_no_auth=1&s_url=http%3A%2F%2Fqun.qq.com%2F";

		try {
			HttpResult result = HttpUtils.down(url);
		} catch (Exception e) {

			e.printStackTrace();

			if (loginListener != null)
				loginListener.onError(this, e.toString());
			return;
		}

		HttpResult result = HttpUtils.downFile(urcode, urpath, null);
		if (loginListener != null)
			loginListener.onQRLoaded(this, urpath);

		String qrsign = getCookie(qrsig);

		Timer tim = new Timer();
		requestTime = 0;
		tim.schedule(new TimerTask() {

			@Override
			public void run() {

				// 扫描结果
				/*
				 * ptuiCB('67','0','','0','二维码认证中。(231758162)', '')
				 * ptuiCB('67','0','','0','二维码认证中。(3707783276)', '')
				 * ptuiCB('0','0',
				 * 'http://ptlogin2.qun.qq.com/check_sig?pttype=1&uin=1758759399&service=ptqrlogin&nodirect=0&ptsigx=b1545813dcd3109c25a9f04ac83e013653b72f68b01a7d02c489a92c7d72c108362a0ea649481b2c76c8e5ddf3193f996dfa5f513cef3485a378dfe9ea28cc01&s_url=http%3A%2F%2Fqun.qq.com%2F&f_url=&ptlang=2052&ptredirect=101&aid=715030901&daid=73&j_later=0&low_login_hour=0&regmaster=0&pt_login_type=3&pt_aid=0&pt_aaid=16&pt_light=0&pt_3rd_aid=0','1','登录成功！',
				 * '格式化法')
				 */

				// 判断是否扫了二维码的网址，其中的qrsig 为下载 2维码图片时
				// 返回的cookies，ptqrtoken为根据qrsig计算的
				String url = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=http%3A%2F%2Fqun.qq.com%2F&ptqrtoken="
						+

						getptqrtoken(qrsign)
						+ "&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1514632963316&js_ver=10233&js_type=1&login_sig="
						+ qrsign
						+ "&pt_uistyle=40&aid=715030901&daid=73&has_onekey=1&";

				try {
					String result = (String) HttpUtils
							.downloadString(url, null).getResult();
					;

					System.out.println("Response Result:" + result);

					if (loginListener != null)
						loginListener.onQRResult(QQSDK.this, requestTime,
								result);

				} catch (Exception e) {

					e.printStackTrace();

					if (loginListener != null)
						loginListener.onError(QQSDK.this, e.toString());
					return;
				}
				requestTime += 1;

				if (isCancel)
					this.cancel();
			}

		}, 0, period);

	}
	
	String mypath = System.getProperty("user.dir","");
	File savePath =new File(mypath,"localcookies");
	public void saveCookies() throws Exception
	{
		
		if(savePath.exists()==false)
		{
			if(savePath.mkdirs()==false)System.out.println("create cookies dir fail:" + savePath.getAbsolutePath());
		}
		
		int i = 0;
		for(URI u:cookiemng.getCookieStore().getURIs())
		{
			
			File out= new File(savePath, i+ ".uri1");
			Utils.writeObject(out.getAbsolutePath(),u);
			
			out = new File(savePath, i+ ".cookie1");
			Utils.writeObject(out.getAbsolutePath(),cookiemng.get(u,null));
			i+=1;
			
			
		}
		
		
		
		
	}

	
	public void readCookies() throws Exception{
		
		for(int i = 0 ; ;i+=1)
		{
			
			File in= new File(savePath, i+ ".uri1");
	
			File coo = new File(savePath, i+".cookie1");
			if(in.exists() == false)break;
			if(coo.exists() == false)break;
			
			URI u =(URI) Utils.readObject(in.getAbsolutePath());
			
			Map<String,List<String>> data = (Map<String,List<String>>) Utils.readObject(coo.getAbsolutePath()) ;
			
			
			cookiemng.put(u, data);
			
		}
	}
}
