package GUI.Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import GUI.TALK_Main;
import Socket.SocketClient;
import datasend.SendUser;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField idTextField;
	private JPasswordField passwordField;
	private String id, passwd;
	private SocketClient socketClient;
//	public void paintComponent(Graphics g) {
//		Dimension d = getSize();
//		ImageIcon image = new ImageIcon(".\\img\\Login.png");
//		g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
//		
//	}	

	public login(SocketClient socketClient) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.socketClient = socketClient;
		setBounds(100, 100, 350, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		socketClient.setLogin(this);
		
		JPanel loginPnl = new JPanel();
		loginPnl.setBackground(Color.WHITE);
		loginPnl.setBounds(0, 0, 334, 129);
		contentPane.add(loginPnl);
		loginPnl.setLayout(null);

		//ImageIcon image = new ImageIcon("images/Login.png");
		URL imageURL = getClass().getClassLoader().getResource("Login.png");
		ImageIcon image = new ImageIcon(imageURL);
		JLabel loginLabel = new JLabel() {
			public void paintComponent(Graphics g) {
				g.drawImage(image.getImage(), 0, 0, 300, 100, null);
				setOpaque(false);
				// super.paintComponent(g);
			}
		};

		loginLabel.setIcon(image);
		loginLabel.setFont(new Font("바탕", Font.BOLD, 40));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setForeground(Color.BLACK);
		loginLabel.setBounds(12, 10, 310, 109);
		loginPnl.add(loginLabel);

		JLabel idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		idLabel.setForeground(Color.BLACK);
		idLabel.setFont(new Font("바탕", Font.BOLD, 25));
		idLabel.setBounds(12, 179, 123, 30);
		contentPane.add(idLabel);

		JLabel passwdLabel = new JLabel("Passwd");
		passwdLabel.setHorizontalAlignment(SwingConstants.LEFT);
		passwdLabel.setForeground(Color.BLACK);
		passwdLabel.setFont(new Font("바탕", Font.BOLD, 25));
		passwdLabel.setBounds(12, 235, 123, 30);
		contentPane.add(passwdLabel);

		JPanel panel = new JPanel();
		panel.setBounds(18, 424, 304, 46);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JButton loginBtn = new JButton("로그인");
		loginBtn.setFont(new Font("바탕", Font.BOLD, 15));

		panel.add(loginBtn);

		JButton signupBtn = new JButton("회원가입");
		signupBtn.setFont(new Font("바탕", Font.BOLD, 15));
		panel.add(signupBtn);

//		
//		DocumentListener listener = new DocumentListener() {
//			private void txtSetEnable() {
//				if (idTextField.getText().isEmpty() || getPwd().isEmpty()) {
//					loginBtn.setEnabled(false);					
//				} else {
//					loginBtn.setEnabled(true);					
//				}
//			}
//
//			@Override
//			public void insertUpdate(DocumentEvent e) {
//				
//				
//			}
//
//			@Override
//			public void removeUpdate(DocumentEvent e) {
//				 txtSetEnable();
//				
//			}
//
//			@Override
//			public void changedUpdate(DocumentEvent e) {
//				 txtSetEnable();
//			}
//		};

		idTextField = new JTextField();

//		idTextField.getDocument().addDocumentListener(listener);
		idTextField.setFont(new Font("굴림", Font.PLAIN, 20));
		idTextField.setBounds(147, 179, 175, 30);
		contentPane.add(idTextField);
		idTextField.setColumns(10);

		passwordField = new JPasswordField();
//		passwordField.getDocument().addDocumentListener(listener);
		passwordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();
			}
		});
		passwordField.setFont(new Font("굴림", Font.PLAIN, 20));
		passwordField.setBounds(147, 235, 175, 30);
		contentPane.add(passwordField);

		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (idTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
				} else if (getPwd().isEmpty()) {
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
				}
			}
		});

		loginBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SendUser sendUser = new SendUser(idTextField.getText(), passwordField.getText());
				sendUser.setComment("login");
				try {
					boolean ck = socketClient.loginWrite(sendUser);
					
					if (ck) {						
						SendUser friendList = new SendUser(socketClient.getMyDto().getId());
						friendList.setComment("friendList");
						try {
							socketClient.friendListWrite(friendList);
							
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						
//						SendUser chatRoomList = new SendUser(socketClient.getMyDto().getId());
//						chatRoomList.setComment("chatRoomList");						
//						try {
//							socketClient.chatRoomListWrite(chatRoomList);							
//						} catch (ClassNotFoundException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
						
						TALK_Main talk_Main = new TALK_Main(socketClient);
						talk_Main.setVisible(true);
						
						dispose();
						
						
					} else {
						JOptionPane.showMessageDialog(null, "아이디와 비밀번호가 틀렸습니다.", "경고창", JOptionPane.WARNING_MESSAGE);					
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
			}
		});

		signupBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					signUp frame = new signUp(socketClient);
					frame.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});

	}

	public String getPwd() {
		passwd = "";
		char[] pChar = passwordField.getPassword();
		for (char cha : pChar) {
			Character.toString(cha);
			passwd += (passwd.equals("")) ? "" + cha + "" : +cha + "";
		}
		return passwd;
	}

	public String getId() {
		return id;
	}

}
