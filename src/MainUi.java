import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import utils.Data;
import utils.HttpUtils;
import utils.HttpUtils.HttpResult;
import utils.MyDialog;


public class MainUi extends JFrame {

	
	final int ID_DOWNLOAD = 1;
	
	String urpath = "D:/urCode.png";
	JButton downloadURcode;
	
	Data data;
	boolean isLogined = false;
	public String qrsig=null;
	public MainUi()
	{
		data = new Data("cookies.txt");
		initView();
		
		
	}

	
	CookieManager manager;
	private void initView() {
		// TODO Auto-generated method stub
		
		
		manager = new CookieManager();
		CookieHandler.setDefault(manager);
		
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		
		
		
		downloadURcode = new JButton();
		downloadURcode.setText("down ur code");
		downloadURcode.setPreferredSize(new Dimension(200, 40));
		downloadURcode.addActionListener(new Listener(ID_DOWNLOAD));
		
		
		this.add(downloadURcode);
	
		requestCookies();
		
	}
	HttpResult result;
	
	private void requestCookies() {
		
		String url = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=715030901&daid=73&pt_no_auth=1&s_url=http%3A%2F%2Fqun.qq.com%2F";

			 try {
				result = HttpUtils.down(url);
			} catch (Exception e) {
	
				e.printStackTrace();
			}
					
			
			
			
		
		
	}
	
	class Listener implements ActionListener
	{

		int id;
		public Listener(int id) {
			super();
			this.id = id;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
				
			switch(id)
			{
			case ID_DOWNLOAD:
				
				try {
					
		
			
			HttpResult result = HttpUtils.downFile(QQValue.urcode, urpath,null);
			
			
			
			
			
			
			
			for(URI u:manager.getCookieStore().getURIs())
	    	{
	    		
	  
	      
			List<HttpCookie> cookies = manager.getCookieStore().get(u);
			
			cookies.forEach(cook->
					{
				
						if("qrsig".equals(cook.getName()))
								{
							qrsig = cook.getValue();
								}
					}
					
					);
	    	}
			
		
		
			
			
			
			
			
			showURCode(urpath);
			
			
			
			
			
			
		
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
				MyDialog.showDialog(MainUi.this, "title test", "download ok");
				
				new WaitScanner(MainUi.this);
				break;
			
			
			}
			
		}
		
		
		
	}
	
	
	
	void showURCode(String path)
	{
		
		
		JDialog dialog = new JDialog(this);
		dialog.setSize(500,500);
		JLabel label = new JLabel();
		ImageIcon img = new ImageIcon(path);
		label.setIcon(img);
		dialog.add(label);
		
		dialog.setVisible(true);
		
		
	}
}
