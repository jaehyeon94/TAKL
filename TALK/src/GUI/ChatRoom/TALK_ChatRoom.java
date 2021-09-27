package GUI.ChatRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledDocument;

import GUI.Friend.TALK_FriendProfile;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;
import datasend.SendChat;
import datasend.SendChatRoom;

public class TALK_ChatRoom extends JFrame implements MouseListener {
	private JPanel jPanel, haeder, body, exit, chatRoomImagePanel, chatTextPanel, chatInPutTextPanel;
	private JButton exitBut, inputTextBut;
	private JTextArea jTextArea, inputText;
	private ButtonStyle buttonStyle;
	private MouseEvent mouseEvent = new MouseEvent();
	private SendChatRoom chatRoomDto;
	private TALK_FriendProfile friendProfile;
	private TALK_ChatRoom_UserList chatRoom_UserList;
	private SocketClient socketClient;
	private ArrayList<SendChat> chats;
	private JTextPane chatTextPane;
	Container contentPane;
	StyledDocument doc;

	public TALK_ChatRoom(SendChatRoom chatRoomDto, SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		this.chatRoomDto = chatRoomDto;
		this.socketClient = socketClient;
		setSize(350, 600);
		setLocation(800, 400);
		buttonStyle = new ButtonStyle();
		mouseEvent.mouseDrag(this);
		setResizable(false);
		setUndecorated(true);
		chatRoom();
	}

	private void chatRoom() {
	
		jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jPanel.setPreferredSize(new Dimension(350, 600));
		Font font = new Font("궁서 맑음", Font.ITALIC, 20);
		haeder = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		haeder.setPreferredSize(new Dimension(350, 70));
		exit = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		haeder.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		haeder.setBackground(Color.LIGHT_GRAY);
		exit.setPreferredSize(new Dimension(30, 70));
		exit.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		exitBut = new JButton("x");
		buttonStyle.setButton(exitBut);
		exitBut.setBorderPainted(false);
		exitBut.setContentAreaFilled(false);
		exitBut.setFocusPainted(false);
		exitBut.setFont(font);

		exit.setOpaque(false);
		exit.add(exitBut);
		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				socketClient.getChatRooms().remove(chatRoomDto.getRoom_Id());
				setVisible(false);
				// dispose();

			}
		});

		chatRoomImagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		chatRoomImagePanel.setPreferredSize(new Dimension(315, 70));
		chatRoomImagePanel.setOpaque(false);
		if (chatRoomDto.getUserDto().size() == 2) {
			ImageIcon profileImage = chatRoomDto.getUserDto().get(0).getPicture_Icon();
			JLabel chatRoomImage = new JLabel() {
				public void paintComponent(Graphics g) {
					BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
					g.drawImage(profileImage.getImage(), 0, 0, 49, 49, null);
					ImageIcon newIco = new ImageIcon(bi);
					setIcon(newIco);
					setOpaque(false);
					super.paintComponent(g);
				};
			};
			chatRoomImage.setBorder(new LineBorder(Color.darkGray, 1, true));
			chatRoomImage.setPreferredSize(new Dimension(50, 50));
			chatRoomImagePanel.add(chatRoomImage);
			chatRoomImage.setName("one");
			chatRoomImage.addMouseListener(this);

		} else {
			JPanel chatRoomImage = new JPanel(new GridLayout(2, 1, 1, 1));
			chatRoomImage.setOpaque(false);
			chatRoomImage.setPreferredSize(new Dimension(50, 50));
			for (int j = 0; j < chatRoomDto.getUserDto().size(); j++) {
				if (j < 4) {
					ImageIcon profileImage = chatRoomDto.getUserDto().get(j).getPicture_Icon();
					JLabel chatRoomMembersImage = new JLabel() {
						public void paint(Graphics g) {
							BufferedImage bi = new BufferedImage(23, 23, BufferedImage.TYPE_INT_ARGB);
							g.drawImage(profileImage.getImage(), 0, 0, 23, 23, null);
							ImageIcon newIco = new ImageIcon(bi);
							setIcon(newIco);
							setOpaque(false);
							super.paintComponent(g);
						};

					};
					chatRoomMembersImage.setName("two");
					chatRoomMembersImage.addMouseListener(this);
					chatRoomImage.add(chatRoomMembersImage);
				} else {
					break;
				}
			}
			chatRoomImage.setBorder(new LineBorder(Color.darkGray, 1, true));
			chatRoomImagePanel.add(chatRoomImage);

		}

		StringBuilder name = new StringBuilder();
		name.append(chatRoomDto.getUserDto().get(0).getId());
		for (int z = 1; z < chatRoomDto.getUserDto().size() - 1; z++) {
			name.append("," + chatRoomDto.getUserDto().get(z).getId());
		}
		JPanel chatRoomInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		chatRoomInfo.setOpaque(false);
		chatRoomInfo.setPreferredSize(new Dimension(220, 40));
		chatRoomInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JLabel chatRoomName;
		String member = "";
		if (chatRoomDto.getUserDto().size() != 2) {
			if (name.toString().length() > 16) {
				member = name.toString().substring(0, 16) + "...";
			} else {
				member = name.toString();
			}
			chatRoomName = new JLabel(member + " (" + chatRoomDto.getUserDto().size() + "명)");

		} else {
			chatRoomName = new JLabel(name.toString());
		}
		chatRoomInfo.add(chatRoomName);
		chatRoomImagePanel.add(chatRoomInfo);
		haeder.add(chatRoomImagePanel);
		haeder.add(exit);
		body = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		body.setPreferredSize(new Dimension(350, 540));

		chatTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)) {
			public void paintComponent(Graphics g) {
				Dimension d = getSize();
				
				ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("1531451089669.jpg"));
				g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
				// setOpaque(false);
			}

		};
		chatTextPanel.setOpaque(false);
		chatTextPanel.setPreferredSize(new Dimension(350, 430));
		chatTextPane = new JTextPane();
		JScrollPane scrollPane = new JScrollPane(chatTextPane);
		scrollPane = new JScrollPane(chatTextPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(350, 430));
		scrollPane.setMinimumSize(new Dimension(50, 50));
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);

		chatTextPane.setEditable(false);
		chatTextPane.setPreferredSize(new Dimension(350, 430));
		chatTextPane.setOpaque(false);
		chatTextPane.setCaretPosition(chatTextPane.getDocument().getLength());
		body.add(chatTextPanel);

		chatTextPanel.add(scrollPane);

		chatInPutTextPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		chatInPutTextPanel.setPreferredSize(new Dimension(360, 100));
		chatInPutTextPanel.setBackground(Color.white);
		inputText = new JTextArea();
		inputText.setPreferredSize(new Dimension(250, 70));
		inputText.setLineWrap(true);

		inputTextBut = new JButton("전송");
		inputTextBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// myChatting(chatTextPane,socketClient.getMyDto().getId(),inputText.getText());

				if (!inputText.getText().equals("") && inputText.getText() != null) {
					SendChat sendChat = new SendChat(chatRoomDto.getRoom_Id(), socketClient.getMyDto().getId(),
							inputText.getText(), socketClient.getMyDto().getIcon());
					socketClient.sendChatting(sendChat);
					inputText.setText("");

					
				} else if (inputText.getText().equals("")) {

				}

			}
		});

		inputText.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					if (e.isControlDown()) {
						inputText.append(System.lineSeparator());
						
					} else {
						if (!inputText.getText().equals("") && inputText.getText() != null) {

							SendChat sendChat = new SendChat(chatRoomDto.getRoom_Id(), socketClient.getMyDto().getId(),
									inputText.getText(), socketClient.getMyDto().getIcon());

							socketClient.sendChatting(sendChat);

							inputText.setText(null);

						} else if (inputText.getText().equals("")) {

						}
						e.consume();
					}
				}
			}
		});

		inputTextBut.setPreferredSize(new Dimension(70, 50));
		inputTextBut.setFont(new Font(null, ABORT, 10));
		chatInPutTextPanel.add(inputText);
		chatInPutTextPanel.add(inputTextBut);

		body.add(chatInPutTextPanel);
		jPanel.add(haeder);
		jPanel.add(body);
		add(jPanel);

//		for(int i =0; i < socketClient.getChats().size(); i++) {
//			if(socketClient.getChats().get(i).getRoom_id() == chatRoomDto.getRoom_Id()) {
//				if(socketClient.getChats().get(i).getUser_id().equals(socketClient.getMyDto().getId())) {
//					myChatting(socketClient.getChats().get(i).getUser_id(),socketClient.getChats().get(i).getStory());
//				} else {
//					otherChatting(socketClient.getChats().get(i).getUser_id(),socketClient.getChats().get(i).getStory(),socketClient.getChats().get(i).getIcon());
//				}
//			}
//		}
	}
	
	public void myChatting(String name, String s) {
		JPanel chat = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		chat.setOpaque(false);
		JLabel userId = new JLabel(name);
		userId.setPreferredSize(new Dimension(350, 30));
		userId.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		userId.setHorizontalAlignment(JLabel.RIGHT);
		JPanel aa = new JPanel(new BorderLayout());
		StringBuffer text = new StringBuffer();

		int count = 1;
		while (true) {
			if ((count * 25) < s.length()) {
				count++;
			} else {
				break;
			}

		}
		

		for (int i = 1; i <= count; i++) {
			if (count == i) {
				text.append(s.substring((i - 1) * 25, s.length()));
			} else {
				text.append(s.substring((i - 1) * 25, i * 25) + "\n");
			}
		}		
		JTextArea chatting = new JTextArea(text.toString());
		//chatting.setOpaque(false);
		chatting.setEditable(false); 
		chatting.setWrapStyleWord(true); 
		
		 
		JScrollPane sp = new JScrollPane(chatting);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.getViewport().setOpaque(false);
		sp.setOpaque(false);
		aa.setOpaque(false);
		chat.setPreferredSize(new Dimension(350, (count * 20) + 25));
	
		aa.add(sp, BorderLayout.CENTER );
		chat.add(userId);
		chat.add(aa);

		// chatTextPane.insertComponent(chat);
		chatTextPane.insertComponent(chat);
		setEndline();
	}

	private void setEndline() {
		// TODO Auto-generated method stub
		chatTextPane.selectAll();
		chatTextPane.setSelectionStart(chatTextPane.getSelectionEnd());
	}

	public void otherChatting(String name, String s, ImageIcon image) {

		JPanel chat = new JPanel();
		chat.setLayout(null);
		chat.setOpaque(false);
		ImageIcon profileImage = image;
		JLabel userImage = new JLabel() {
			public void paintComponent(Graphics g) {
				BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
				g.drawImage(profileImage.getImage(), 0, 0, 49, 49, null);
				ImageIcon newIco = new ImageIcon(bi);
				setIcon(newIco);
				setOpaque(false);
				super.paintComponent(g);
			};
		};
		userImage.setBounds(0, 5, 50, 50);
		chat.add(userImage);

		JLabel userId = new JLabel(name);
		userId.setBounds(55, 0, 57, 25);

		JPanel aa = new JPanel(new FlowLayout(FlowLayout.LEFT));

		StringBuffer text1 = new StringBuffer();

		int count = 1;
		while (true) {
			if ((count * 22) < s.length()) {
				count++;
			} else {
				break;
			}

		}
		

		for (int i = 1; i <= count; i++) {
			if (count == i) {
				text1.append(s.substring((i - 1) * 22, s.length()));
			} else {
				text1.append(s.substring((i - 1) * 22, i * 22) + "\n");
			}
		}		

		JTextArea jTextArea = new JTextArea(text1.toString());
//		jTextArea.setOpaque(false); 
		jTextArea.setWrapStyleWord(true);
		JScrollPane sp = new JScrollPane(jTextArea);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.getViewport().setOpaque(false);
		sp.setOpaque(false);
		aa.setOpaque(false);	
		aa.setPreferredSize(new Dimension(300, count * 22));
		aa.setBounds(55, 18, 300, count * 22);
		aa.setOpaque(false);
		chat.setPreferredSize(new Dimension(350, (count * 20) + 25));
		
		aa.add(sp);
		chat.add(userId);
		chat.add(aa);

		chatTextPane.insertComponent(chat);
		setEndline();
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jLabel = (JLabel) e.getSource();

		if (jLabel.getName().equals("one")) {
			if (friendProfile == null) {
				friendProfile = new TALK_FriendProfile(chatRoomDto.getUserDto().get(0), socketClient);
			} else {
				friendProfile.dispose();
				friendProfile = new TALK_FriendProfile(chatRoomDto.getUserDto().get(0), socketClient);
			}
			friendProfile.setVisible(true);
			chatRoom_UserList = null;
		} else if (jLabel.getName().equals("two")) {
			if (chatRoom_UserList == null) {
				chatRoom_UserList = new TALK_ChatRoom_UserList(chatRoomDto, socketClient);
			} else {
				chatRoom_UserList.dispose();
				chatRoom_UserList = new TALK_ChatRoom_UserList(chatRoomDto, socketClient);
			}
			chatRoom_UserList.setVisible(true);
			friendProfile = null;
		}
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public SendChatRoom getChatRoomDto() {
		return chatRoomDto;
	}

	public void setChatRoomDto(SendChatRoom chatRoomDto) {
		this.chatRoomDto = chatRoomDto;
	}

}
