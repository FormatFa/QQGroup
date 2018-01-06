import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import utils.Utils;


public class jsontest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String data = Utils.readString("D:/friends.txt");
			
			JSONObject object  = JSONObject.fromObject(data);
			
			
		
			JSONArray mems = object.getJSONArray("mems");
			String count = object.getString("count");
			System.out.println("friends size:" + mems.size());
			for(int i = 0;i< mems.size();i+=1)
			{
				System.out.println(mems.get(i));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}


