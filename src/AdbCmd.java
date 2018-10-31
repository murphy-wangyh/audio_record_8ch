
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.print.attribute.standard.Finishings;

public class AdbCmd {
	
	static private int TIMEOUT_MAX = 300000;//5分钟
	public void executeCMDconsole(String cmd) {
	        System.out.println("在cmd里面输入"+cmd);
	        Process p;
	        try {
	            p = Runtime.getRuntime().exec(cmd);            
	            System.out.println(":::::::::::::::::::执行命令::::::::::::::::::::::>>>>>>");
	            //p.waitFor();
	            BufferedReader bReader=new BufferedReader(new InputStreamReader(p.getInputStream(),"gbk"));
	            String line=null;
	            while((line=bReader.readLine())!=null)
	                System.out.println(line);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	    }
	
	public Thread executeCMDconsoleTimeout(String cmd, int timeout) {
		System.out.println("执行adb命令"+cmd);
		Runnable myRunnable = new Runnable() {
			public void run() {
				Process process = null;
				try {
					process = Runtime.getRuntime().exec(cmd);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//OutputStream outputStream = process.getOutputStream();
				System.out.println("执行命令时间:"+timeout);
				try {
					try {
						if (timeout > 0 ) {
							if (timeout > TIMEOUT_MAX) {
								Thread.sleep(TIMEOUT_MAX);
							}else {
								Thread.sleep(timeout);
							}
						} else {
							process.waitFor();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//outputStream.write("exit\n".getBytes());
					//outputStream.flush();	
				}finally {
					process.destroy();
				}
			}
		};
		Thread myThread = new Thread(myRunnable);
		myThread.start();
		return myThread;	
	}
	    	    
	public String executeCMDfile(String[] cmmands, String logToFile, String dirTodoCMD ) throws IOException {
	        //此方法为輸出日志到指定文件夹！！！！！！！！！！！！
	        //此方法跑成功！！！
	        //如果 String cmmand 那麼  String cmmand = "adb logcat -v time > d:/adb.log";
	        //String[] cmmands 所以   String commands[] = { "adb", "logcat","-v","time"};
	        //String logToFile  將日誌保存到logToFile
	        //String dirTodoCMD 在dirTodoCMD執行cmd命令
	        //由于將日志輸出到文件裡面了，就不能再将日誌輸出到console了
	        
	        try {
	            ProcessBuilder builder = new ProcessBuilder(cmmands);
	            if (dirTodoCMD != null)
	                builder.directory(new File(dirTodoCMD));
	            builder.redirectErrorStream(true);
	            builder.redirectOutput(new File(logToFile));
	            Process process = builder.start();
	            process.waitFor();
	            // 得到命令执行后的结果
	            InputStream is = process.getInputStream();
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "gbk"));
	            String line = null;
	            StringBuffer sbBuffer = new StringBuffer();
	            while ((line = buffer.readLine()) != null) {
	                sbBuffer.append(line);
	            }
	            
	            is.close();
	            return sbBuffer.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	/*
	    public static void main(String[] args) throws IOException {
	        //String cmd="D:/Android/android-sdk-windows/platform-tools/adb logcat -v time";
	        //String cmd2="adb devices";
	        //String cmd3="adb logcat -v time";
	        //String cmd4="adb logcat -v time > d:/adb.log";
	        cmdadb adbc = new cmdadb();
	        adbc.executeCMDconsole("adb logcat -v time");
	        String commands[] = { "adb", "logcat","-v","time"};        
	        adbc.executeCMDfile(commands, "D:/adb.logs", "C:/Users/wb-cjz286752");
	        //System.out.println(result);由于將日志輸出到文件裡面了，就不能再将日志輸出到console了
	    }
	    */


}
