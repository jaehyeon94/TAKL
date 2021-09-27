package GUI.Login;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Socket.SocketClient;
import datasend.SendUser;

public class signUp extends JFrame {

	private JPanel contentPane;
	private JTextField idText;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private String id, passwd, passwdCheck;
	private SocketClient socketClient;
	
	public signUp(SocketClient socketClient) {
		this.socketClient = socketClient;
		socketClient.setSignUp(this);
		setBounds(100, 100, 350, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel loginPnl = new JPanel();
		loginPnl.setBackground(Color.WHITE);
		loginPnl.setBounds(0, 0, 334, 129);
		contentPane.add(loginPnl);
		loginPnl.setLayout(null);
		
		JLabel signupLabel = new JLabel("회원가입");
		signupLabel.setFont(new Font("바탕", Font.BOLD, 40));
		signupLabel.setHorizontalAlignment(SwingConstants.CENTER);
		signupLabel.setForeground(Color.BLACK);
		signupLabel.setBounds(12, 10, 310, 109);
		loginPnl.add(signupLabel);
		
		JButton signupBtn = new JButton("회원가입");
		signupBtn.setFont(new Font("바탕", Font.PLAIN, 15));
		signupBtn.setBounds(95, 455, 149, 46);
		contentPane.add(signupBtn);
		
		
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setFont(new Font("굴림", Font.BOLD, 15));
		idLabel.setBounds(12, 177, 90, 24);
		contentPane.add(idLabel);
		
		JLabel passwdLabel = new JLabel("비밀번호");
		passwdLabel.setFont(new Font("굴림", Font.BOLD, 15));
		passwdLabel.setBounds(12, 244, 90, 24);
		contentPane.add(passwdLabel);
		
		JLabel passwdLabel2 = new JLabel("비밀번호확인");
		passwdLabel2.setFont(new Font("굴림", Font.BOLD, 15));
		passwdLabel2.setBounds(12, 309, 90, 24);
		contentPane.add(passwdLabel2);
		
		JLabel idCheackLabel = new JLabel("");
		idCheackLabel.setForeground(Color.RED);
		idCheackLabel.setBounds(42, 211, 202, 15);
		contentPane.add(idCheackLabel);
		
		JLabel passwdCheackLabel = new JLabel("");
		passwdCheackLabel.setForeground(Color.RED);
		passwdCheackLabel.setBounds(42, 278, 202, 15);
		contentPane.add(passwdCheackLabel);
		
		idText = new JTextField();
		idText.setBounds(113, 178, 141, 24);
		contentPane.add(idText);
		idText.setColumns(10);
		
		passwordField1 = new JPasswordField();
		passwordField1.setBounds(113, 245, 141, 24);
		contentPane.add(passwordField1);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(114, 303, 141, 24);
		contentPane.add(passwordField2);
		
		JButton idCheckBtn = new JButton("확인");
		idCheckBtn.setFont(new Font("바탕", Font.PLAIN, 12));
		idCheckBtn.setBounds(261, 178, 61, 23);
		contentPane.add(idCheckBtn);
		
		JButton passwdCheckBtn = new JButton("확인");
		passwdCheckBtn.setFont(new Font("바탕", Font.PLAIN, 12));
		passwdCheckBtn.setBounds(261, 303, 61, 23);
		contentPane.add(passwdCheckBtn);
		
		signupBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(idText.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
				}else if(getPasswd().isEmpty()) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
				}else if(getPasswdCheck().isEmpty()) {
					JOptionPane.showMessageDialog(null, "비밀번호 확인을 입력하세요.");
				}else if(passwdCheckBtn.isEnabled()) {
					JOptionPane.showMessageDialog(null, "비밀번호 확인을 해주세요.");
				}
				else {
					SendUser sendUser = new SendUser(idText.getText(), passwordField1.getText());
					sendUser.setComment("insertUser");
				
					try { 
						boolean ck = socketClient.insertUserWrite(sendUser);
						if(ck) {
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "아이디가 존재합니다.");
						}
						
 					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				}
			}
		});
		
		
		passwdCheckBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getPasswd().equals(getPasswdCheck())) {
					passwdCheckBtn.setEnabled(false);
					passwdCheackLabel.setForeground(Color.GREEN);
					passwdCheackLabel.setText("비밀번호가 일치합니다.");
					passwordField1.setEditable(false);
					passwordField2.setEditable(false);
				}
				else {
					passwdCheackLabel.setForeground(Color.RED);
					passwdCheackLabel.setText("비밀번호가 다릅니다");
				}
				
			}
		});
		
	}

	public String getId() {
		return id;
	}

	
	public String getPasswd() {
		passwd = "";
		char[] pwdChar = passwordField1.getPassword();
		for(char cha : pwdChar) {
			Character.toString(cha);
			passwd += (passwd.equals("")) ? "" + cha + "" : "" + cha + "";
		}		
		return passwd;
	}

	public String getPasswdCheck() {
		passwdCheck = "";
		char[] pwdCheckChar = passwordField2.getPassword();
		for (char chaCheck : pwdCheckChar) {
			Character.toString(chaCheck);
			passwdCheck += (passwdCheck.equals("")) ? "" + chaCheck + "" : "" + chaCheck + "";
		}
		return passwdCheck;
	}
}
