import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class BigImageTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		File file = new File("C:/Users/formatfa/Documents/formatfa.png");
	
	if(file.exists() == false)
	{
		System.out.println("file not exixts");
	}
		OutputStream os = new FileOutputStream(file,true);
		
		for(int i = 0;i<10;i+=1)
		{
			
			byte[] buff =new byte[1024*1024];
			
			os.write(buff);
		}
os.close();
System.out.println("oki");
	}

}
