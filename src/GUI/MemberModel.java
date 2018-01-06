package GUI;

import java.util.List;

import javax.swing.AbstractListModel;

import FQQSdk.FGroup.Member;


//�б�ģ��,����б��
public  class MemberModel extends AbstractListModel
{
	
	List<Member> list;

	public MemberModel(List<Member> list) {
		super();
		this.list = list;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return  list.get(index).getNick();
	}
	
}
