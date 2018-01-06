package QQAnalysis;

import java.util.ArrayList;
import java.util.List;

import FQQSdk.FGroup;
import FQQSdk.FGroup.GroupItem;
import FQQSdk.FGroup.Member;

public class GroupAnalysis {
	
	List<FGroup.GroupItem> groups;

	List<Member> allMembers;
	
	public GroupAnalysis(List<GroupItem> groups) {
		super();
		this.groups = groups;
		
		
		allMembers = new ArrayList<Member>();
		for(GroupItem group: groups)
		{
			
			try {
				for(Member mb:group.getMembers())
				{
					if(!isContain(mb,allMembers))
					{
						allMembers.add(mb);
					}
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	public class MostResult
	{
		public int getSize()
		{
			return groups.size();
		}
		List<GroupItem> groups;
		public List<GroupItem> getGroups() {
			return groups;
		}
		public void setGroups(List<GroupItem> groups) {
			this.groups = groups;
		}
		public Member getMember() {
			return member;
		}
		public void setMember(Member member) {
			this.member = member;
		}
		Member member;
		
	}
	
	//是否包含某个人，根据号区别
	
	boolean isContain(Member aim,GroupItem groups) throws Exception
	{
		
		for(Member m:groups.getMembers())
		{
			
			if(aim.getUin().equals(m.getUin()))
			{
				return true;
			}
			
			
			
			
			
		}
		return false;
	}
	
	boolean isContain(Member m,List<Member> mems)
	{
		for(Member item:mems)
		{
			
			if(item.getUin().equals(m.getUin()))
				return true;
		}
		
		return false;
	}
	//在tony一个群
	public List<MostResult> getMost() throws Exception
	{
		
		List<MostResult> result= new ArrayList<MostResult>();
		
		for(Member m:allMembers)
		{
			
			
			MostResult item= new MostResult();
			
			item.setMember(m);
			
			List<GroupItem> myGroup = new ArrayList<GroupItem>();
			for(GroupItem group:groups)
			{
				
				if(isContain(m,group))myGroup.add(group);
					
		
			}
			
			item.setGroups(myGroup);
			result.add(item);
			
			
		}
		
		sortResult(result);
		return result;
		
	}

	
	
	
	
	void sortResult(List<MostResult> result)
	{
		
		
		
		for(int i = 0;i< result.size();i+=1)
		{
			
			
			int max = i;
			
			for(int j = i+1;j< result.size();j+=1)
			{
				if( result.get(j).getSize() > result.get(max).getSize())
				{
					
					max = j;
				}
				
				
			}
			
			if(max != i)
			{
				
				//switch
				MostResult temp = result.get(i);
				
				result.set(i,result.get(max));
				result.set(max,temp);
				
			}
			
			
			
		}
	
		
		
		
	}
	
	
}
