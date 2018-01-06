import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import utils.HttpUtils;


public class WaitScanner extends JDialog{

	
	//�ȴ��û�ɨ��2ά��
	MainUi main;

	JButton stop;
	JLabel message;
	
	int requestTime;
	
	
	public WaitScanner(MainUi main) {
		super(main);
		this.main = main;
		
		
		requestTime = 0;
		initView();
	}

	
	/**
	 * 
	 */
	private void initView() {

		stop = new JButton();
		stop.setText("stop");
		this.add(stop);
		
		
		message = new JLabel();
		this.add(message);
		
		
		this.setSize(300,300);
		this.setVisible(true);
		
		startWait();
		
	}

	private void startWait() {
	
		Timer tim = new Timer();
		
		tim.schedule(new TimerTask(){

			@Override
			public void run() {
	
				
				
				//�ж��Ƿ�ɨ�˶�ά�����ַ�����е�qrsig Ϊ���� 2ά��ͼƬʱ ���ص�cookies��ptqrtokenΪ����qrsig�����
				String url = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=http%3A%2F%2Fqun.qq.com%2F&ptqrtoken="+
				
						
						QQValue.getptqrtoken(main.qrsig)+"&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1514632963316&js_ver=10233&js_type=1&login_sig="
						+
						main.qrsig+
						"&pt_uistyle=40&aid=715030901&daid=73&has_onekey=1&";
				
				System.out.println("get login sign value is:" + main.qrsig);
				
		
				message.setText("Time:"+ String.valueOf(requestTime)+" " + url);
				
				try {
			String result = (String)HttpUtils.downloadString(url,null).getResult();;
		
			
			//ɨ����
			/*ptuiCB('67','0','','0','��ά����֤�С�(231758162)', '')
			 *ptuiCB('67','0','','0','��ά����֤�С�(3707783276)', '')
			 * ptuiCB('0','0','http://ptlogin2.qun.qq.com/check_sig?pttype=1&uin=1758759399&service=ptqrlogin&nodirect=0&ptsigx=b1545813dcd3109c25a9f04ac83e013653b72f68b01a7d02c489a92c7d72c108362a0ea649481b2c76c8e5ddf3193f996dfa5f513cef3485a378dfe9ea28cc01&s_url=http%3A%2F%2Fqun.qq.com%2F&f_url=&ptlang=2052&ptredirect=101&aid=715030901&daid=73&j_later=0&low_login_hour=0&regmaster=0&pt_login_type=3&pt_aid=0&pt_aaid=16&pt_light=0&pt_3rd_aid=0','1','��¼�ɹ���', '��ʽ����')
			 */
			 
			
			
			System.out.println("Response Result:" + result);
			
			message.setText("Time:"+ String.valueOf(requestTime)+result);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				requestTime+=1;
				
				
			}
			
		}, 0,3000);
	}
	
	
	
	
	
	
}
