import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.RequestingUserName;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableStringConverter;

public class MainTest extends Frame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final private String WAIT_ADB_DEVICE = "adb wait-for-device";
	static final private String ADB_ROOT = "adb root";
	static final private String ADB_REMOUNT = "adb remount";
	static final private String RENAME_APK = 
			"adb shell mv /system/priv-app/vera/vera.apk  /system/priv-app/vera/vera.zip";
	static final private String ADB_REBOOT = "adb reboot";
	static final private String ADB_PUSH_FILE = "adb push play.wav /sdcard/";
	static final private String ADB_SET_VOLUME = "adb shell busybox i2cset -f -y 2 0x2a 0x07 0xbb";
	static final private String ADB_RM_OLD_FILES = "adb shell rm /sdcard/8channel.wav";
	static final private String ADB_PLAY_WAV = "adb shell tinyplay /sdcard/play.wav -d 4";
	static final private String ADB_RECORD = "adb shell tinycap /sdcard/8channel.wav -D 0 -d 3 -c 8 -r 48000 -b 16";
	static final private String ADB_PULL_FILES = "adb pull /sdcard/8channel.wav";
	static final private String RESTORE_APK = 
			"adb shell mv /system/priv-app/vera/vera.zip  /system/priv-app/vera/vera.apk";

	private static int timeout_t = 60000;//ms
	private static String pa_vol = "0xbb";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new JFrame("TEST");
		frame.setSize(400, 400);
		frame.setLocation(200, 200);
		frame.setLayout(new FlowLayout());
		JButton button_1 = new JButton("ֹͣ��������");
		JButton button_2 = new JButton("�Բ���¼");
		JButton button_3 = new JButton("�ָ����Է���");
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ֹͣ��������ť����");
				JOptionPane.showMessageDialog(frame, "ֹͣ���Է����ϵͳ������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
				Runnable myRunnable = new Runnable() {
					public void run(){
						System.out.println("Thread 1!");
						System.out.println(WAIT_ADB_DEVICE);
						AdbCmd adbcmd = new AdbCmd();
						adbcmd.executeCMDconsole(WAIT_ADB_DEVICE);
						System.out.println(WAIT_ADB_DEVICE);
						adbcmd.executeCMDconsole(ADB_ROOT);
						System.out.println(ADB_REMOUNT);
						adbcmd.executeCMDconsole(ADB_REMOUNT);
						System.out.println(RENAME_APK);
						adbcmd.executeCMDconsole(RENAME_APK);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(ADB_REBOOT);
						adbcmd.executeCMDconsole(ADB_REBOOT);
						JOptionPane.showMessageDialog(frame, "ϵͳ������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				};
				Thread thread_1 = new Thread(myRunnable);
				thread_1.start();
			}
		});
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("�Բ���¼���Ͱ���");
				Runnable myRunnable = new Runnable() {
					public void run(){
						System.out.println("Thread 2!");
						System.out.println(WAIT_ADB_DEVICE);
						AdbCmd adbcmd = new AdbCmd();
						adbcmd.executeCMDconsole(WAIT_ADB_DEVICE);
						System.out.println(WAIT_ADB_DEVICE);
						adbcmd.executeCMDconsole(ADB_ROOT);
						System.out.println(ADB_REMOUNT);
						adbcmd.executeCMDconsole(ADB_REMOUNT);
						System.out.println(WAIT_ADB_DEVICE);
						adbcmd.executeCMDconsole(WAIT_ADB_DEVICE);
						System.out.println(ADB_RM_OLD_FILES);
						adbcmd.executeCMDconsole(ADB_RM_OLD_FILES);
						System.out.println(ADB_PUSH_FILE);
						adbcmd.executeCMDconsole(ADB_PUSH_FILE);
						if (pa_vol.equals("0xbb")) {
							System.out.println(ADB_SET_VOLUME);
							adbcmd.executeCMDconsole(ADB_SET_VOLUME);
						} else {
							String cmd = String.format("adb shell busybox i2cset -f -y 2 0x2a 0x07 "+ pa_vol);
							System.out.println(cmd);
							adbcmd.executeCMDconsole(cmd);
						}
						
						System.out.println(ADB_PLAY_WAV);
						Thread playthread = adbcmd.executeCMDconsoleTimeout(ADB_PLAY_WAV, 0);
					
						System.out.println(ADB_RECORD);
						Thread recordthread = adbcmd.executeCMDconsoleTimeout(ADB_RECORD, timeout_t);
				
						if (playthread != null) {
							try {
								playthread.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						if (recordthread != null) {
							try {
								recordthread.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						System.out.println(ADB_PULL_FILES);
						adbcmd.executeCMDconsole(ADB_PULL_FILES);
						JOptionPane.showMessageDialog(frame, "adb pull¼���ļ�...����", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				};
				Thread thread_2 = new Thread(myRunnable);
				thread_2.start();

			}
		});
		button_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Runnable myRunnable = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						AdbCmd adbcmd = new AdbCmd();
						System.out.println(RESTORE_APK);
						adbcmd.executeCMDconsole(RESTORE_APK);
						System.out.println(ADB_REBOOT);
						adbcmd.executeCMDconsole(ADB_REBOOT);
						JOptionPane.showMessageDialog(frame, "�ɹ�...��ȷ������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);
					}
				};
				Thread myThread = new Thread(myRunnable);
				myThread.start();
			}
		});
		frame.setLayout(new FlowLayout());
		frame.add(button_1);
		frame.add(button_2);
		frame.add(button_3);
		JTextField textField =new JTextField();
		textField.setText("Ĭ��60���޸����봿���֣���Ҫ���κε�λ");
		textField.setSize(40,40);
		Button button_4 = new Button("����¼��ʱ��(��)");
		button_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String record_time = textField.getText();
				System.out.println("¼��ʱ��"+record_time);
				timeout_t = Integer.parseInt(record_time) * 1000;
			}
		});
		frame.add(textField);
		frame.add(button_4);
		
		JTextField textField_1 =new JTextField();
		textField_1.setText("Ĭ��ֵ0xbb���޸�����0xXX������0xff");
		textField_1.setSize(40,40);
		Button button_5 = new Button("��������(0x00-0xff)");
		button_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				pa_vol = textField_1.getText();
				System.out.println("¼������"+pa_vol);
			}
		});
		frame.add(textField_1);
		frame.add(button_5);
		
		JTextArea textArea = new JTextArea();
		textArea.setText("¼������ǰ��\"ֹͣ��������\"��ť,ϵͳ��������Է������\"�Բ���¼\"����¼��.���Խ������԰�\"�ָ����Է���\"��ť");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		frame.add(textArea);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return;
	}	
}
