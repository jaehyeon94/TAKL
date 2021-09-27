package Listener;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import DTO.FriendDto;
import GUI.ChatRoom.TALK_ChatRoom;
import GUI.Friend.TALK_FriendProfile;
import Socket.SocketClient;
import datasend.SendChatRoom;
import datasend.SendUser;

public class MouseEvent {

	int xDrag, yDrag;
	boolean ck = true;
	boolean ck1 = true;
	JPanel jPanel;
	JRadioButton jRadioButton;
	TALK_FriendProfile friendProfile;

	public MouseEvent(TALK_FriendProfile friendProfile) {
		// TODO Auto-generated constructor stub
		this.friendProfile = friendProfile;
	}

	public MouseEvent() {
		// TODO Auto-generated constructor stub

	}

	public void mouseDrag(JFrame jFrame) {

		jFrame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				jFrame.setLocation((jFrame.getLocation().x + e.getX() - xDrag),
						(jFrame.getLocation().y + e.getY() - yDrag));

			}
		});

		jFrame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				xDrag = e.getX();
				yDrag = e.getY();
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void mouseTransparent(JButton jButton, String imageClick, String imageNot) {

		jButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				URL imageURL = getClass().getClassLoader().getResource(imageNot);
				jButton.setIcon(new ImageIcon(imageURL));
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				URL imageURL = getClass().getClassLoader().getResource(imageClick);
				jButton.setIcon(new ImageIcon(imageURL));
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

	}

	public void mouseMemberlist(int i, MouseEvent[] mouseEvents, JPanel jPanel, SendUser userDto, int size,
			SocketClient socketClient) {
		this.jPanel = jPanel;

		jPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck) {
					for (int j = 0; j < size; j++) {
						if (i == j) {
							mouseEvents[j].ck1 = false;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(true);
							mouseEvents[j].jPanel.setBackground(new Color(204, 204, 255));
						} else {
							mouseEvents[j].ck1 = true;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(false);
							mouseEvents[j].jPanel.setBackground(null);
						}
					}
				}
				if (e.getClickCount() == 2) {

					if (friendProfile == null) {
						for (int j = 0; j < size; j++) {
							mouseEvents[j].friendProfile = new TALK_FriendProfile(userDto, socketClient);
							if (i == j) {
								friendProfile.setVisible(true);
							}
						}
					} else {
						for (int j = 0; j < size; j++) {
							mouseEvents[j].friendProfile.dispose();
						}
						friendProfile = new TALK_FriendProfile(userDto, socketClient);
						friendProfile.setVisible(true);
					}

				}

			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck1) {
					jPanel.setOpaque(false);
					jPanel.setBackground(Color.white);
				}
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if (ck1) {
					jPanel.setOpaque(true);
					jPanel.setBackground(new Color(204, 204, 255));

				}
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void mouseFriendlist(int i, MouseEvent[] mouseEvents, JPanel jPanel, FriendDto friendDto, CardLayout cards,
			JPanel card, int size, int count, SocketClient socketClient) {
		this.jPanel = jPanel;

		jPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck) {
					for (int j = 0; j < size; j++) {
						if (i == j) {
							mouseEvents[j].ck1 = false;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(true);
							mouseEvents[j].jPanel.setBackground(new Color(204, 204, 255));
						} else {
							mouseEvents[j].ck1 = true;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(false);
							mouseEvents[j].jPanel.setBackground(null);
						}
					}
				}
				if (e.getClickCount() == 2) {
					if (friendProfile == null) {
						for (int j = 0; j < size; j++) {
							mouseEvents[j].friendProfile = new TALK_FriendProfile(friendDto, count, socketClient);
							if (i == j) {
								friendProfile.setVisible(true);
							}
						}
					} else {
						for (int j = 0; j < size; j++) {
							mouseEvents[j].friendProfile.dispose();
						}
						// friendProfile.dispose();
						friendProfile = new TALK_FriendProfile(friendDto, count, socketClient);
						friendProfile.setVisible(true);
					}
					jPanel.setBackground(null);
				}

			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck1) {
					jPanel.setOpaque(false);
					jPanel.setBackground(null);
				}
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if (ck1) {
					jPanel.setOpaque(true);
					jPanel.setBackground(new Color(204, 204, 255));

				}
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void mouseChatRoom(int i, MouseEvent[] mouseEvents, JPanel jPanel, SendChatRoom chatRoomDto, CardLayout cards,
			JPanel card, int size, SocketClient socketClient) {
		this.jPanel = jPanel;

		jPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck) {
					for (int j = 0; j < size; j++) {
						if (i == j) {
							mouseEvents[j].ck1 = false;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(true);
							mouseEvents[j].jPanel.setBackground(new Color(204, 204, 255));
						} else {
							mouseEvents[j].ck1 = true;
							mouseEvents[j].ck = true;
							mouseEvents[j].jPanel.setOpaque(false);
							mouseEvents[j].jPanel.setBackground(null);
						}
					}
				}
				if (e.getClickCount() == 2) {
					Iterator<Entry<Integer, TALK_ChatRoom>> entries = socketClient.getChatRooms().entrySet().iterator();
					if (socketClient.getChatRooms().size() != 0) {
						while (entries.hasNext()) {
							Entry<Integer, TALK_ChatRoom> entry = entries.next();
							if (entry.getKey() != chatRoomDto.getRoom_Id()) {
								TALK_ChatRoom chatRoom = new TALK_ChatRoom(chatRoomDto, socketClient);
								socketClient.getChatRooms().put(chatRoomDto.getRoom_Id(), chatRoom);
								socketClient.roomChatting(chatRoomDto.getRoom_Id());
								chatRoom.setVisible(true);
							}
						}
					} else {
						TALK_ChatRoom chatRoom = new TALK_ChatRoom(chatRoomDto, socketClient);
						socketClient.getChatRooms().put(chatRoomDto.getRoom_Id(), chatRoom);
						socketClient.roomChatting(chatRoomDto.getRoom_Id());
						
						chatRoom.setVisible(true);
					}

					jPanel.setBackground(null);
					jPanel.setOpaque(false);
				}

			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck1) {
					jPanel.setOpaque(false);
					jPanel.setBackground(null);
				}
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if (ck1) {
					jPanel.setOpaque(true);
					jPanel.setBackground(new Color(204, 204, 255));

				}
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	public JRadioButton mouseFriendlist(int i, MouseEvent[] mouseEvents, JPanel jPanel, int size,
			JRadioButton jRadioButton, JButton jButton) {
		this.jPanel = jPanel;
		this.jRadioButton = jRadioButton;
		jRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (jRadioButton.isSelected()) {
					jRadioButton.setSelected(true);
				} else {
					jRadioButton.setSelected(false);
				}
			}
		});

		jRadioButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				int count = 0;
				jRadioButton.setSelected(false);
				for (int j = 0; j < size; j++) {
					if (i == j) {
						if (mouseEvents[j].ck1) {
							mouseEvents[j].ck1 = false;
							mouseEvents[j].jPanel.setOpaque(true);
							mouseEvents[j].jPanel.setBackground(new Color(204, 204, 255));
							jButton.setEnabled(true);

						} else {
							mouseEvents[j].ck1 = true;
							mouseEvents[j].jPanel.setOpaque(false);
							mouseEvents[j].jPanel.setBackground(null);
							jRadioButton.setSelected(true);
						}
					}
					if (!mouseEvents[j].jRadioButton.isSelected()) {
						count++;
					}
					if (count == size) {
						jButton.setEnabled(false);
					}
				}

			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		jPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				int count = 0;
				for (int j = 0; j < size; j++) {
					if (i == j) {
						if (mouseEvents[j].ck1) {
							mouseEvents[j].ck1 = false;
							mouseEvents[j].jPanel.setOpaque(true);
							mouseEvents[j].jPanel.setBackground(new Color(204, 204, 255));
							jRadioButton.setSelected(true);
							jButton.setEnabled(true);

						} else {
							mouseEvents[j].ck1 = true;
							mouseEvents[j].jPanel.setOpaque(false);
							mouseEvents[j].jPanel.setBackground(null);
							jRadioButton.setSelected(false);
						}
					}
					if (!mouseEvents[j].jRadioButton.isSelected()) {
						count++;
					}
					if (count == size) {
						jButton.setEnabled(false);
					}
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

				if (ck1) {

					jPanel.setOpaque(false);
					jPanel.setBackground(null);

				}
			}

			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				if (ck1) {

					jPanel.setOpaque(true);
					jPanel.setBackground(new Color(204, 204, 255));

				}
			}

			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		return jRadioButton;
	}

}
