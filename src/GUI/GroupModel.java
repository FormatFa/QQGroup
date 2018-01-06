package GUI;

import java.util.List;

import javax.swing.AbstractListModel;

import FQQSdk.FGroup.GroupItem;

/**
 * @author formatfa
 *ListµÄÄ£ÐÍ
 */
public class GroupModel extends AbstractListModel
{
	
	List<GroupItem> list;

	public GroupModel(List<GroupItem> list) {
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
		return list.get(index).getQn();
	}
	
}