package FQQSdk;

public interface LoginListener {


	public void onLogined(QQSDK sdk,String responedUrls);
	
	public void onQRLoaded(QQSDK sdk,String path);
	public void onQRResult(QQSDK sdk,int requestTime,String result);
	
	public void onError(QQSDK sdk,String error);
	public void onStop(QQSDK sdk);
}
