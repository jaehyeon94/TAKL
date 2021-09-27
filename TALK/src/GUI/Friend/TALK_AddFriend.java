package GUI.Friend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import GUI.TALK_Title;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.HintTextField;
import datasend.SendFriend;
import datasend.SendUser;

public class TALK_AddFriend extends JFrame {

	private JPanel addfriendId, addfriend;
	private JButton addfriendBut;
	private MouseEvent mouseEvent = new MouseEvent();
	private SocketClient socketClient;
	private JPanel friend;

	public TALK_AddFriend(SocketClient socketClient, JPanel jPanel) {
		// TODO Auto-generated constructor stub
		this.socketClient = socketClient;
		this.friend = jPanel;
		socketClient.setAddFriend(this);
		setTitle("친구 추가");
		setSize(300, 400);
		setLocation(800, 400);
		mouseEvent.mouseDrag(this);
		setResizable(false);
		setUndecorated(true);
		addFreind();
		setVisible(true);
	}

	public void addFreind() {
		TALK_Title title = new TALK_Title(this, "친구추가");
		addfriendId = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		HintTextField hintTextField = new HintTextField("ID로 추가");
		hintTextField.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
		hintTextField.setPreferredSize(new Dimension(260, 30));
		hintTextField.setBackground(Color.white);

		addfriendId.setPreferredSize(new Dimension(300, 70));
		addfriendId.setBackground(Color.white);
		addfriendId.setBorder(BorderFactory.createEmptyBorder(30, 20, 0, 0));
		addfriendId.add(hintTextField);
		title.getBody().add(addfriendId);
		// title.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

		addfriend = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		addfriend.setPreferredSize(new Dimension(300, 250));
		addfriend.setBorder(BorderFactory.createEmptyBorder(190, 0, 0, 0));
		addfriend.setBackground(Color.white);
		addfriendBut = new JButton("친구 추가");
		addfriendBut.setPreferredSize(new Dimension(100, 50));

		addfriendBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					if (!socketClient.getMyDto().getId().equals(hintTextField.getText())) {
						SendFriend sendFriend = new SendFriend("insertFriend", socketClient.getMyDto().getId(),
								hintTextField.getText());
						socketClient.insertFriendWrite(sendFriend);
			
						SendUser sendUser = new SendUser("friendList", socketClient.getMyDto().getId(), "");

						socketClient.friendListWrite(sendUser);
						Thread.sleep(500);
						TALK_FriendList friend = new TALK_FriendList(socketClient, socketClient.getCards(),
								socketClient.getCardPanel(), socketClient.getCount(), socketClient.getjFrame());

						socketClient.getCardPanel().add("FriendsList", friend);
						socketClient.getCards().show(socketClient.getCardPanel(), "FriendsList");

						socketClient.getBut_profile().setIcon(new ImageIcon(getClass().getClassLoader().getResource("home1.png")));
						socketClient.getBut_friendList().setIcon(new ImageIcon(getClass().getClassLoader().getResource("profile_ico1.png")));
						socketClient.getBut_chatroom().setIcon(new ImageIcon(getClass().getClassLoader().getResource("icon_bubble1.png")));
						dispose();
					} else {
						System.out.println("본인은 추가할수 없습니다.");
					}

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		addfriend.add(addfriendBut);
		title.getBody().add(addfriend);
		add(title.getBody());

	}

}
