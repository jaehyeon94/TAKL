package GUI.Friend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DTO.FriendDto;
import DTO.MyDto;
import GUI.MyProfile.TALK_Profile;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;
import Style.RoundedButton;
import datasend.SendFriend;
import datasend.SendUser;

public class TALK_FriendProfile extends JFrame {
	private MyDto myDto;
	private FriendDto friendDto;
	private SendUser userDto;
	private JPanel body, info, but, exit, my;
	private JLabel id, status;
	private JButton update, withdraw, one_one, delete, input, exitBut, insert;
	private RoundedButton roundedButton;
	private ButtonStyle buttonStyle;
	private MouseEvent mouseEvent = new MouseEvent();
	boolean ck = true;
	private TALK_Profile profile;
	private SocketClient socketClient;

	public TALK_FriendProfile() {
	}

	public TALK_FriendProfile(FriendDto friendDto, int count, SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		buttonStyle = new ButtonStyle();
		this.friendDto = friendDto;
		this.socketClient = socketClient;
		ck = true;
		mouseEvent.mouseDrag(this);
		setSize(300, 570);
		setLocation(600, 400);
		setResizable(false);
		setUndecorated(true);
		friendProfile(count);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public TALK_FriendProfile(SendUser userDto, SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		buttonStyle = new ButtonStyle();
		this.userDto = userDto;
		this.socketClient = socketClient;
		ck = false;
		mouseEvent.mouseDrag(this);
		setSize(300, 570);
		setLocation(1150, 400);
		setResizable(false);
		setUndecorated(true);
		friendProfile(0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void friendProfile(int count) {
		body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		info = new JPanel(new GridLayout(2, 1, 10, 10));
		but = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

		exitBut = new JButton("x");
		exit.add(exitBut);
		exit.setPreferredSize(new Dimension(273, 100));
		exit.setOpaque(false);
		buttonStyle.setButton(exitBut);
		if (ck) {
			id = new JLabel(friendDto.getFriendId());
			status = new JLabel(friendDto.getStatus());
			roundedButton = new RoundedButton(friendDto.getIcon());
		} else {
			id = new JLabel(userDto.getId());
			status = new JLabel(userDto.getStatus());
			roundedButton = new RoundedButton(userDto.getPicture_Icon());
		}
		
		roundedButton.setPreferredSize(new Dimension(150, 150));
		roundedButton.setBorder(new LineBorder(Color.black, 3, true));
		body.setOpaque(false);
		body.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		body.add(exit);
		body.add(roundedButton);
		body.setPreferredSize(new Dimension(263, 570));
		info.setPreferredSize(new Dimension(200, 100));
		info.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
		id.setHorizontalAlignment(JLabel.CENTER);
		status.setHorizontalAlignment(JLabel.CENTER);
		info.setOpaque(false);
		but.setOpaque(false);
		but.setPreferredSize(new Dimension(250, 180));
		but.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));

		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (profile != null) {
					profile.dispose();
					profile = null;
				}
				dispose();
			}
		});

		roundedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (ck) {
					if (profile == null) {
						profile = new TALK_Profile(friendDto.getIcon(), false, socketClient, roundedButton, count);
					} else {
						profile.dispose();
						profile = new TALK_Profile(friendDto.getIcon(), false, socketClient, roundedButton, count);
					}
					profile.setVisible(true);
				} else {
					if (profile == null) {

						profile = new TALK_Profile(userDto.getPicture_Icon(), false, socketClient, roundedButton, count);
					} else {
						profile.dispose();
						profile = new TALK_Profile(userDto.getPicture_Icon(), false, socketClient, roundedButton, count);
					}
					profile.setVisible(true);
				}
			}
		});

		if (!socketClient.getMyDto().getId().equals(id.getText())) {
//			if (ck) {
//				one_one = new JButton("1:1채팅");
//				buttonStyle.setButton(one_one);
//				one_one.addActionListener(new ActionListener() {
//
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						try {
//							int ckNumber = -1;
//							for (int i = 0; i < socketClient.getChatRoomDtos().size(); i++) {
//								if (socketClient.getChatRoomDtos().get(i).getUserDto().size() == 2) {
//									if (socketClient.getChatRoomDtos().get(i).getUserDto().get(0).getId()
//											.equals(id.getText()) || socketClient.getChatRoomDtos().get(i).getUserDto().get(1).getId()
//											.equals(id.getText())) {
//										ckNumber = i;
//									}
//								}
//							}
//
//							if (ckNumber == -1) {
//									ArrayList<SendUser> sendUserList = new ArrayList<SendUser>();
//									sendUserList.add(new SendUser(id.getText()));
//									sendUserList.add(new SendUser(socketClient.getMyDto().getId()));
//
//									SendChatRoom sendChatRoom = new SendChatRoom("insertChatRoom", sendUserList);
//									socketClient.insertChatRoomWrite(sendChatRoom);
//									SendUser chatRoomList = new SendUser("chatRoomList",
//											socketClient.getMyDto().getId(), "");
//									socketClient.chatRoomListWrite(chatRoomList);
//
//									Thread.sleep(500);
//
//									TALK_ChatRoom chatRoom = new TALK_ChatRoom(socketClient.getChatRoomDtos()
//											.get(socketClient.getChatRoomDtos().size() - 1), socketClient);
//									socketClient.getChatRooms()
//											.put(socketClient.getChatRoomDtos()
//													.get(socketClient.getChatRoomDtos().size() - 1).getRoom_Id(),
//													chatRoom);
//									chatRoom.setVisible(true);
//								
//							} else {
//								Iterator<Entry<Integer, TALK_ChatRoom>> entries = socketClient.getChatRooms().entrySet()
//										.iterator();
//								if (socketClient.getChatRooms().size() != 0) {
//									while (entries.hasNext()) {
//										Entry<Integer, TALK_ChatRoom> entry = entries.next();
//
//										if (entry.getKey() != socketClient.getChatRoomDtos().get(ckNumber)
//												.getRoom_Id()) {
//											TALK_ChatRoom chatRoom = new TALK_ChatRoom(
//													socketClient.getChatRoomDtos().get(ckNumber), socketClient);
//											socketClient.getChatRooms().put(
//													socketClient.getChatRoomDtos().get(ckNumber).getRoom_Id(),
//													chatRoom);
//											chatRoom.setVisible(true);
//										}
//									}
//								} else {
//									TALK_ChatRoom chatRoom = new TALK_ChatRoom(
//											socketClient.getChatRoomDtos().get(ckNumber), socketClient);
//									socketClient.getChatRooms().put(
//											socketClient.getChatRoomDtos().get(ckNumber).getRoom_Id(),
//											chatRoom);
//									chatRoom.setVisible(true);
//								}
//							}
//						} catch (ClassNotFoundException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//					}
//				});
//				but.add(one_one, "South");
//			}
			if (ck) {
				delete = new JButton("친구 삭제");
				buttonStyle.setButton(delete);
				but.add(delete, "South");
				delete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub

						try {
							if(socketClient.getFriendDtos().size() == 1) {
								socketClient.getFriendDtos().remove(0);
							}
							
							
							SendFriend sendFriend = new SendFriend("deleteFriend", socketClient.getMyDto().getId(),
									id.getText());
							socketClient.deleteFriendWrite(sendFriend);
							SendUser sendUser = new SendUser("friendList", socketClient.getMyDto().getId(), "");
							socketClient.friendListWrite(sendUser);
							Thread.sleep(500);
							
						
							socketClient.setFriend(new TALK_FriendList(socketClient, socketClient.getCards(),
									socketClient.getCardPanel(), socketClient.getCount(), socketClient.getjFrame()));
							
							socketClient.getCardPanel().add("FriendsList", socketClient.getFriend());
							socketClient.getCards().show(socketClient.getCardPanel(), "FriendsList");

							socketClient.getBut_profile().setIcon(new ImageIcon(getClass().getClassLoader().getResource("home1.png")));
							socketClient.getBut_friendList().setIcon(new ImageIcon(getClass().getClassLoader().getResource("profile_ico1.png")));
							socketClient.getBut_chatroom().setIcon(new ImageIcon(getClass().getClassLoader().getResource("icon_bubble1.png")));
							
							
							dispose();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
			} else {
				int count1 = 0;
				for (int i = 0; i < socketClient.getFriendDtos().size(); i++) {
					if (id.getText().equals(socketClient.getFriendDtos().get(i).getFriendId())) {
						count1++;
						break;
					}
				}
				if (count1 == 0) {
					insert = new JButton("친구 추가");
					insert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub

							try {
								if (!socketClient.getMyDto().getId().equals(id.getText())) {
									SendFriend sendFriend = new SendFriend("insertFriend",
											socketClient.getMyDto().getId(), id.getText());
									if (socketClient.insertFriendWrite(sendFriend)) {
										SendUser sendUser = new SendUser("friendList", socketClient.getMyDto().getId(),
												"");
										socketClient.friendListWrite(sendUser);

										socketClient.setFriend(new TALK_FriendList(socketClient,
												socketClient.getCards(), socketClient.getCardPanel(),
												socketClient.getCount(), socketClient.getjFrame()));
										// socketClient.getCardPanel().remove(friend);
										socketClient.getCardPanel().add("FriendsList", socketClient.getFriend());
										dispose();
										
										socketClient.getCards().show(socketClient.getCardPanel(), "FriendsList");
										socketClient.getBut_profile().setIcon(new ImageIcon(getClass().getClassLoader().getResource("home1.png")));
										socketClient.getBut_friendList()
												.setIcon(new ImageIcon(getClass().getClassLoader().getResource("profile_ico1.png")));
										socketClient.getBut_chatroom()
												.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icon_bubble1.png")));
									}
								} else {
									System.out.println("본인은 추가할수 없습니다.");
								}
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();

							}

						}
					});
				} else if (count1 == 1) {
					insert = new JButton("친구 삭제");
					insert.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub

							try {
								SendFriend sendFriend = new SendFriend("deleteFriend", socketClient.getMyDto().getId(),
										id.getText());
								socketClient.deleteFriendWrite(sendFriend);
								SendUser sendUser = new SendUser("friendList", socketClient.getMyDto().getId(), "");
								socketClient.friendListWrite(sendUser);
								Thread.sleep(500);
								socketClient.setFriend(new TALK_FriendList(socketClient, socketClient.getCards(),
										socketClient.getCardPanel(), socketClient.getCount(),
										socketClient.getjFrame()));
								socketClient.getCardPanel().add("FriendsList", socketClient.getFriend());

								socketClient.getCards().show(socketClient.getCardPanel(), "FriendsList");
					
								
								socketClient.getBut_profile().setIcon(new ImageIcon(getClass().getClassLoader().getResource("home1.png")));
								socketClient.getBut_friendList().setIcon(new ImageIcon(getClass().getClassLoader().getResource("profile_ico1.png")));
								socketClient.getBut_chatroom().setIcon(new ImageIcon(getClass().getClassLoader().getResource("icon_bubble1.png")));
								dispose();

							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}
					});
				}
				buttonStyle.setButton(insert);
				but.add(insert, "South");
			}

		}

		info.add(id);
		info.add(status);
		body.add(info);
		body.add(but);

		add(body);

	}

	public MyDto getMyDto() {
		return myDto;
	}

	public void setMyDto(MyDto myDto) {
		this.myDto = myDto;
	}

	public FriendDto getFriendDto() {
		return friendDto;
	}

	public void setFriendDto(FriendDto friendDto) {
		this.friendDto = friendDto;
	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("1531451089669.jpg"));
		g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
		// setOpaque(false);

	}
}
