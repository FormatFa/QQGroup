import java.awt.TextArea;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.MyDialog;
import utils.Utils;
import FQQSdk.FGroup;
import FQQSdk.LoginListener;
import FQQSdk.QQSDK;


public class sdktest extends JFrame{
	
	
	QQSDK sdk;
	
	TextArea message;
	public sdktest ()
	{
		showOption();
		init();
	}

	
	
	void setMessage(String str)
	{
		message.setText(message.getText()+ System.getProperty("line.separator","\n")+ str);
		
	}
	private void init() {
		// TODO Auto-generated method stub
		
		
		
		message = new TextArea();
		message.setSize(300,300);

		this.add(message);
		
		
		
		sdk = new QQSDK();
		sdk.setLoginListener(new LoginListener(){

			@Override
			public void onLogined(QQSDK sdk, String responedUrls) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onQRLoaded(QQSDK sdk, String path) {
				// TODO Auto-generated method stub
				setMessage("load urcode ok");
				
				showURCode(path);
			}

			@Override
			public void onQRResult(QQSDK sdk, int requestTime, String result) {
			
				setMessage("try:" + requestTime + " result:"+ result  );
				
				if(result.indexOf("登录成功") != -1)
				{
					
					System.out.println("Login sucessful!");
					sdk.printCookies();
					sdk.cancel();
					showOption();
				}
			}

			@Override
			public void onError(QQSDK sdk, String error) {
				// TOsetDO Auto-generated method stub
				setMessage("has error:"+ error);
			}

			@Override
			public void onStop(QQSDK sdk) {
				// TODO Auto-generated method stub
				setMessage("request has stop");
			}
			
		});
		
		try {
			sdk.startLink("D:/test.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public void showOption()
	{
		String[] ops = {
			"群列表","哈哈"	
		};
		JDialog dialog = new JDialog (this);
		
		dialog.setSize(300,200);
		JList list = new JList(ops);
		list.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int i = list.getSelectedIndex();
				if(i == 0)
				{
					FGroup group = new FGroup(sdk);
					try {
//						String grouplist = group.getGroupList();
//						
//						Utils.writeString(grouplist,"D:/friends.txt");
//						System.out.println(grouplist);
//						MyDialog.showDialog(sdktest.this, "list", grouplist);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		dialog.add(list);
		
		
		dialog.setVisible(true);
		
		
	}
	void showURCode(String path)
	{
		
		
		JDialog dialog = new JDialog(this);
		dialog.setTitle("scan ur code");
		dialog.setSize(500,500);
		JLabel label = new JLabel();
		ImageIcon img = new ImageIcon(path);

		label.setIcon(img);
		dialog.add(label);
		
		dialog.setVisible(true);
		
		
	}
}
