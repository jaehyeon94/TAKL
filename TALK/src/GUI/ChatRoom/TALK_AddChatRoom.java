package GUI.ChatRoom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import DTO.FriendDto;
import GUI.TALK_Title;
import Listener.KeyEvent;
import Listener.MouseEvent;
import Socket.SocketClient;
import datasend.SendChatRoom;
import datasend.SendUser;

public class TALK_AddChatRoom extends JFrame {
	private JPanel addChatRoom_friend, friendListPanel, totalFriendPanel;
	private JButton addfriendBut;
	private JLabel totalFriend;
	private JTextField friend_name_select;
	private MouseEvent mouseEvent = new MouseEvent();
	private ArrayList<FriendDto> friendList;
	private JRadioButton[] jRadioButton;
	private ArrayList<SendUser> sendUserList;
	private ArrayList<String> addMember;
	private KeyEvent KeyEvent;
	private SocketClient socketClient;
	private JPanel chatRoom;
	boolean ck = true;
	int i = 0;

	public TALK_AddChatRoom(SocketClient socketClient, JPanel chatRoom) {
		// TODO Auto-generated constructor stub
		this.socketClient = socketClient;
		this.chatRoom = chatRoom;
		setSize(300, 500);
		setLocation(800, 400);
		friendList = socketClient.getFriendDtos();

		addMember = new ArrayList<String>();
		jRadioButton = new JRadioButton[friendList.size()];
		KeyEvent = new KeyEvent(friendList);
		mouseEvent.mouseDrag(this);
		setResizable(false);
		setUndecorated(true);
		addChatRoom();
		setVisible(true);
	}

	private void addChatRoom() {
		TALK_Title title = new TALK_Title(this, "대화 상대 선택");
		addChatRoom_friend = new JPanel(new FlowLayout(FlowLayout.CENTER));
		addChatRoom_friend.setPreferredSize(new Dimension(300, 30));
		friend_name_select = new JTextField(25);
		addChatRoom_friend.setOpaque(false);
		addChatRoom_friend.add(friend_name_select);
		title.getBody().add(addChatRoom_friend);
		totalFriendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		totalFriendPanel.setPreferredSize(new Dimension(280, 25));
		totalFriendPanel.setBackground(Color.white);
		totalFriend = new JLabel("친구 " + friendList.size() + "명");
		totalFriend.setPreferredSize(new Dimension(280, 25));
		totalFriendPanel.add(totalFriend);
		title.getBody().add(totalFriendPanel);

		friendListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		friendListPanel.setPreferredSize(new Dimension(295, 60 * friendList.size()));
		friendListPanel.setBackground(Color.white);
		JButton jButton = new JButton("확인");
		jButton.setPreferredSize(new Dimension(70, 50));
		jButton.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(friendListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(295, 315));
		scrollPane.setBackground(Color.white);
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));

		// -------------------------------------------------------

		MouseEvent[] mouseEvent = new MouseEvent[friendList.size()];
		for (i = 0; i < friendList.size(); i++) {
			JPanel friendMember = new JPanel(new FlowLayout(FlowLayout.LEFT));
			friendMember.setPreferredSize(new Dimension(295, 60));
			friendMember.setOpaque(false);
			ImageIcon profileImage = friendList.get(i).getIcon();
			JLabel friendImage = new JLabel() {
				public void paintComponent(Graphics g) {
					BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
					g.drawImage(profileImage.getImage(), 0, 0, 49, 49, null);
					ImageIcon newIco = new ImageIcon(bi);
					setIcon(newIco);
					setOpaque(false);
					super.paintComponent(g);
				};
			};
			friendImage.setBorder(new LineBorder(Color.darkGray, 1, true));
			friendImage.setPreferredSize(new Dimension(50, 50));
			friendMember.add(friendImage);
			JPanel friendInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			friendInfo.setOpaque(false);
			friendInfo.setPreferredSize(new Dimension(230, 50));
			JLabel friendId = new JLabel(friendList.get(i).getFriendId());
			friendId.setPreferredSize(new Dimension(175, 50));
			friendInfo.add(friendId);

			jRadioButton[i] = new JRadioButton();
			jRadioButton[i].setOpaque(false);
			jRadioButton[i].setSize(100, 100);
			mouseEvent[i] = new MouseEvent();

			jRadioButton[i].setPreferredSize(new Dimension(20, 50));
			friendInfo.add(jRadioButton[i]);
			friendMember.add(friendInfo);

			friendListPanel.add(friendMember);
			mouseEvent[i] = new MouseEvent();

			jRadioButton[i] = mouseEvent[i].mouseFriendlist(i, mouseEvent, friendMember, friendList.size(),
					jRadioButton[i], jButton);

		}
		// --------------------------------------------------------

		KeyEvent.SearchKey(friend_name_select, friendListPanel, jButton);
		friendListPanel.setBackground(Color.white);
		JPanel but = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		but.setPreferredSize(new Dimension(300, 60));
		but.setOpaque(false);
		but.setBorder(new MatteBorder(1, 0, 0, 0, Color.GRAY));
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (KeyEvent.isCk()) {
					addMember = new ArrayList<>();
					for (int i = 0; i < jRadioButton.length; i++) {
						if (jRadioButton[i].isSelected()) {
							addMember.add(friendList.get(i).getFriendId());
						}
					}
				} else {
					addMember = new ArrayList<>();
					for (int i = 0; i < KeyEvent.getjRadioButton().length; i++) {
						if (KeyEvent.getjRadioButton()[i].isSelected()) {
							addMember.add(KeyEvent.getFriendDto().get(i).getFriendId());
						}

					}
				}
				if (KeyEvent.isCk()) {
					try {
						sendUserList = new ArrayList<SendUser>();
						for (int i = 0; i < addMember.size(); i++) {
							sendUserList.add(new SendUser(addMember.get(i)));
						}
						sendUserList.add(new SendUser(socketClient.getMyDto().getId()));

						int ckNumber = -1;

						if (sendUserList.size() == 2) {
							for (int i = 0; i < socketClient.getChatRoomDtos().size(); i++) {
								if (socketClient.getChatRoomDtos().get(i).getUserDto().size() == 2) {
									if (socketClient.getChatRoomDtos().get(i).getUserDto().get(0).getId()
											.equals(sendUserList.get(0).getId())) {
										ckNumber = i;
										System.out.println(ckNumber);
									}
								}
							}
						}
						if (ckNumber != -1) {
							TALK_ChatRoom chatRoom = new TALK_ChatRoom(socketClient.getChatRoomDtos().get(ckNumber),
									socketClient);
							chatRoom.setVisible(true);
						} else {

							SendChatRoom sendChatRoom = new SendChatRoom("insertChatRoom", sendUserList);
							socketClient.insertChatRoomWrite(sendChatRoom);

							SendUser chatRoomList = new SendUser("chatRoomList", socketClient.getMyDto().getId(), "");
							socketClient.chatRoomListWrite(chatRoomList);
							Thread.sleep(500);
							chatRoom = new TALK_ChatRoomList(socketClient, socketClient.getCards(),
									socketClient.getCardPanel(), socketClient.getjFrame());
							socketClient.getCardPanel().add("ChatRoom", chatRoom);
							socketClient.getCards().show(socketClient.getCardPanel(), "ChatRoom");
							socketClient.getCardPanel().invalidate();
							socketClient.getCardPanel().repaint();

							TALK_ChatRoom chatRoom = new TALK_ChatRoom(
									socketClient.getChatRoomDtos().get(socketClient.getChatRoomDtos().size() - 1),
									socketClient);
							socketClient.getChatRooms().put(socketClient.getChatRoomDtos()
									.get(socketClient.getChatRoomDtos().size() - 1).getRoom_Id(), chatRoom);

							chatRoom.setVisible(true);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else {

					try {
						sendUserList = new ArrayList<SendUser>();
						for (int i = 0; i < addMember.size(); i++) {
							sendUserList.add(new SendUser(addMember.get(i)));
						}
						sendUserList.add(new SendUser(socketClient.getMyDto().getId()));

						int ckNumber = -1;

						if (sendUserList.size() == 2) {
							for (int i = 0; i < socketClient.getChatRoomDtos().size(); i++) {
								if (socketClient.getChatRoomDtos().get(i).getUserDto().size() == 2) {
									if (socketClient.getChatRoomDtos().get(i).getUserDto().get(0).getId()
											.equals(sendUserList.get(0).getId())) {
										ckNumber = i;
										System.out.println(ckNumber);
									}
								}
							}
						}
						if (ckNumber != -1) {
							TALK_ChatRoom chatRoom = new TALK_ChatRoom(socketClient.getChatRoomDtos().get(ckNumber),
									socketClient);
							chatRoom.setVisible(true);
						} else {

							SendChatRoom sendChatRoom = new SendChatRoom("insertChatRoom", sendUserList);
							socketClient.insertChatRoomWrite(sendChatRoom);

							SendUser chatRoomList = new SendUser("chatRoomList", socketClient.getMyDto().getId(), "");
							socketClient.chatRoomListWrite(chatRoomList);
							Thread.sleep(500);
							chatRoom = new TALK_ChatRoomList(socketClient, socketClient.getCards(),
									socketClient.getCardPanel(), socketClient.getjFrame());
							socketClient.getCardPanel().add("ChatRoom", chatRoom);
							socketClient.getCards().show(socketClient.getCardPanel(), "ChatRoom");
							socketClient.getCardPanel().invalidate();
							socketClient.getCardPanel().repaint();

							TALK_ChatRoom chatRoom = new TALK_ChatRoom(
									socketClient.getChatRoomDtos().get(socketClient.getChatRoomDtos().size() - 1),
									socketClient);
							socketClient.getChatRooms().put(socketClient.getChatRoomDtos()
									.get(socketClient.getChatRoomDtos().size() - 1).getRoom_Id(), chatRoom);

							chatRoom.setVisible(true);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					
					
					
				}
				dispose();
			}
		});

		for (int i = 0; i < jRadioButton.length; i++) {
			if (!jRadioButton[i].isSelected()) {
				jButton.setEnabled(false);
			}
		}

		JButton cancel = new JButton("취소");
		cancel.setPreferredSize(new Dimension(70, 50));

		but.add(jButton);
		but.add(cancel);
		title.getBody().add(scrollPane);
		// title.getBody().add(friendListPanel);
		title.getBody().add(but);
		add(title.getBody());
	}
}
