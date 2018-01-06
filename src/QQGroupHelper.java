import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

import utils.MyDialog;
import utils.Utils;
import FQQSdk.FGroup;
import FQQSdk.FGroup.GroupItem;
import FQQSdk.FGroup.Member;
import FQQSdk.LoginListener;
import FQQSdk.QQSDK;
import QQAnalysis.GroupAnalysis;
import QQAnalysis.GroupAnalysis.MostResult;


public class QQGroupHelper extends JFrame implements LoginListener{

	String line = System.getProperty("line.seperator","\n");
	public QQGroupHelper ()
	{
		
		sdk = new QQSDK();
		sdk.setLoginListener(this);
		
		fgroup = new FGroup(sdk);
		
		initUI();
		initMenu();
		
		loadGroupList();
	}
	String[] selectKey;
	void explortSelect(File path)
	{
		
	 selectKey= new String[]{
			"card", "flag", "g", "join_time",
			"last_speak_time", "nick","qage", "role",
			"tags", "uin"
	};
	Checkbox[] boxs = new Checkbox[selectKey.length];
	JDialog dialog = new JDialog(this);
	
	dialog.setLayout(new GridLayout(3,3));
	dialog.setSize(300,300);
	CheckboxGroup boxgroup = new CheckboxGroup();

	for(int i = 0 ;i< selectKey.length;i+=1)
	{
		boxs[i] = new Checkbox(selectKey[i]);
		boxs[i].setSize(200,50);
		if(selectKey[i].equals("nick") ||selectKey[i].equals("uin"))
			boxs[i].setState(true);
		dialog.add(boxs[i]);
	
	}
	Button ok = new Button("确定");
	ok.setBounds(10,10,20,50);
	ok.setSize(200,20);
	ok.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for(int i = 0;i< boxs.length;i+=1)
			{
				if(!boxs[i].getState())
				{
					selectKey[i]=null;
					try {
						export(path,selectKey);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	});
	ok.setVisible(true);
	
	//dialog.add(boxgroup);
dialog.getContentPane().add(ok);

	dialog.setVisible(true);
	
	}
	void export(File path,String[] key) throws Exception
	{
		String split = "	";
		
		for(GroupItem gr:groups)
		{
			
			StringBuilder sb = new StringBuilder();
			for(Member m:gr.getMembers())
			{
			
				for(String k:key)
				{
					if(k!=null)
					{
						sb.append(m.getByFiledName(k));
						sb.append(split);
					}
				}
				
				sb.append(line);
			}
			File file  = new File(path,gr.getGc()+".txt");
			
			Utils.writeString(sb.toString(),file.getAbsolutePath());
			System.out.println("save to:" + file.getAbsolutePath());
			
			
		}
	
	}
	
	int test =0;
	List<MostResult> mresult ;
	private void initMenu() {
		// TODO Auto-generated method stub
		MenuBar mbar = new MenuBar();
		
		Menu mlogin = new Menu("登录");
		
		MenuItem login = new MenuItem("获取登录二维码");
		login.addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				mresult =null;
		login();
			}
			
		});
		mlogin.add(login);
		
		
		mbar.add(mlogin);
		
		
        Menu mGroup = new Menu("群");
		
		MenuItem refreshMem = new MenuItem("刷新成员列表");
		refreshMem.addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		
				if(grouplist.getSelectedIndex()==-1)
				{
					System.out.println("当前没有选择的群");
					return;
				}
				GroupItem item = groups.get(grouplist.getSelectedIndex());
						e.getSource();
				System.out.println("当前选择的是:" + item.getQn());
				
				try {
				loadMembersList(item);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		mGroup.add(refreshMem);
		mbar.add(mGroup);
		
		Menu mtool = new Menu("工具");
		
		MenuItem recard = new MenuItem("修改群名片");
		recard.addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		
				if(grouplist.getSelectedIndex()==-1)
				{
					System.out.println("当前没有选择的群");
					return;
				}
				
			
				
						GroupItem item = groups.get(grouplist.getSelectedIndex());
						e.getSource();
				System.out.println("当前选择的是:" + item.getQn());
				
				try {
					int i = 0;
					for(Member m:item.getMembers())
					{
						fgroup.setCard(item, m, "ApktoolHelper");
						i+=1;
				
							System.out.println("sleep 10 s");
							Thread.sleep(8000);i=0;
			
					}
					
					System.out.println("出理ok");
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					
				try {
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		MenuItem analysis = new MenuItem("成员分析");
		analysis.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GroupAnalysis analysis = new GroupAnalysis(groups);
				System.out.println("正在处理....");
				try {
					if(mresult==null)
					mresult= analysis .getMost();
					MostResult first = mresult.get(test);
					System.out.println("你的幸运QQ号 is:" + first.getMember().getUin());
					
					System.out.println("他叫is:" + first.getMember().getNick());
					System.out.println("居然和你同在:"+first.getGroups().size()+"个qq群里!");
					for(GroupItem item:first.getGroups())
					{
						
						System.out.println("你们一起在:" + item.getQn());
					}
					test+=1;
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
		});
		mtool.add(analysis);
		
		MenuItem export = new MenuItem("导出列表");
		export.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser filechoose = new JFileChooser();
				FileSystemView fsysview = FileSystemView.getFileSystemView();
				filechoose.setCurrentDirectory(fsysview.getHomeDirectory());
			
				filechoose.setDialogTitle("保存目录");
				filechoose.setApproveButtonText("ok");
				filechoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = filechoose.showOpenDialog(QQGroupHelper.this);
				if(result==filechoose.APPROVE_OPTION)
				{
				try {
					explortSelect(filechoose.getSelectedFile());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				else
				{
					
				}
			}
			
		});
		mtool.add(export);
		mtool.add(recard);
		mbar.add(mtool);
		
		this.setMenuBar(mbar);
		
	}


	JLabel urcode ;
	
	JList grouplist;
	JList memlist;
	
	JLabel name;
	JLabel qq;
	TextArea logs ;
	
	QQSDK sdk;
	
	String mypath = System.getProperty("user.dir","");
	
	
	FGroup fgroup;
	
	List<FGroup.GroupItem> groups; 
	List<Member> nowMembers;
	
	private void initUI() {
		int scrw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int scrh = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation(scrw/2,scrh/2);
		
		this.setSize(600,600);
		
		//坐标的绝对布局，设成null
		this.setLayout(new GridLayout(3,2,0,0));
		
		urcode = new JLabel();
		urcode.setSize(200,200);
	//	urcode.setLocation(0,0);
		urcode.setVisible(true);
		
		
		
		
		grouplist = new JList();
	//	grouplist.setSize(200,400);
	//	grouplist.setLocation(0,200);
		grouplist.setVisible(true);
	
		grouplist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		name = new JLabel();
		name.setSize(400,50);
	//	name.setLocation(200,0);
		name.setText("Name");
		
		qq = new JLabel();
		qq .setSize(400,50);
		qq .setLocation(200,50);
		qq .setText("@@ number");
		
		
		logs = new TextArea();
		logs.setSize(400,200);
		//logs.setLocation(200,100);
		logs.setVisible(true);
		logs.setText("There is log message");
		
		
		memlist = new JList();
	//	memlist.setSize(400,400);
		//memlist.setLocation(200,300);
		memlist.setVisible(true);
		
		this.add(urcode);
		
	
	
		
		this.add(getScroll(grouplist));
		this.add(name);
		this.add(qq);
		this.add(logs);
		this.add(getScroll(memlist));
		
		
		
		grouplist.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
			//	loadMembersList(  groups.get(e.getLastIndex()));
			}
			
		});
		
	}
	
	
	
	
	
	JScrollPane getScroll(JList list)
	{
		JScrollPane gp =new JScrollPane (list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//gp.setBounds(0,200,200,400);
	//	gp.setSize(200,400);
	//	gp.add(list);
		gp.setVisible(true);

		return gp;
	}
	void log(String s)
	{
		logs.setText(   logs.getText() + System.getProperty("line.separator","\n")  + s);
		
	}
	void login()
	{
		logs.setText("");
		
		File urpath = new File(mypath,"urcode.png");
		
		urpath = new File("C:/Users/formatfa/Nox_share/Image/ur.png");
		log("urpath:" + urpath);
		
		
		try {
			sdk.startLink(urpath.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	void loadGroupList()
	{
		
		try {
			groups = fgroup.getGroupList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MyDialog.showDialog(this, "加载群列表失败，请重新登录!", e.toString());
			e.printStackTrace();
			return;
		}
		
		
		grouplist.setModel(new FGroup.GroupModel(groups));
	}

	
	void loadMembersList(GroupItem item)
	{
		
		try {
			nowMembers = item.getMembers();
			
			FGroup.MemberModel m = new FGroup.MemberModel(nowMembers);
			
			memlist.setModel(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
		
	}
	@Override
	public void onLogined(QQSDK sdk, String responedUrls) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onQRLoaded(QQSDK sdk, String path) {
		// TODO Auto-generated method stub
		log("二维码已加载。。显示中..:" + path);
		
		ImageIcon ur =new ImageIcon(path);
		urcode.setIcon(ur);
	}


	@Override
	public void onQRResult(QQSDK sdk, int requestTime, String result) {
		// TODO Auto-generated method stub
		log(result);
		
		if(result.indexOf("登录成功")!=-1)
		{
			sdk.cancel();
			
			try {
				sdk.saveCookies();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] msg = sdk.ptuiCBParse(result);
			
			try {
				sdk.login(msg[2]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			name.setText(msg[5]);
			
			loadGroupList();
		}
		else if(result.indexOf("已失效")!=-1)
		{
			sdk.cancel();
			log("二维码已失效,请重新登录获取");
		}
	}


	@Override
	public void onError(QQSDK sdk, String error) {
		// TODO Auto-generated method stub
		log(error);
	}


	@Override
	public void onStop(QQSDK sdk) {
		// TODO Auto-generated method stub
		log("请求验证已停止");
	}
	
	
}
