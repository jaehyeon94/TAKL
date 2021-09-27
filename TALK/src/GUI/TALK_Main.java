package GUI;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.ChatRoom.TALK_ChatRoomList;
import GUI.Friend.TALK_FriendList;
import GUI.MyProfile.TALK_MyProfile;
import Listener.MouseEvent;
import Socket.SocketClient;
import datasend.SendUser;

public class TALK_Main extends JFrame implements ActionListener {

	private JPanel card_Panel, menu, body;
	private CardLayout cards = new CardLayout();
	private TALK_MyProfile proFile;
	private TALK_FriendList friendList;
	private TALK_ChatRoomList chatRoom;
	private JButton but_profile, but_friendList, but_chatroom;
	private MouseEvent mouseEvent;
	private ImageIcon[] imageIcons = new ImageIcon[6];
	private String[] imageMenu = { "home1.png","home.png",
			"profile_ico.png","profile_ico1.png",
			"icon_bubble.png","icon_bubble1.png"
			};
	private SocketClient socketClient;
	private int count = 0;
	
	public TALK_Main(SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		
		this.socketClient = socketClient;
		socketClient.setCount(count);
		socketClient.setjFrame(this);
	
		for(int i=0; i < imageIcons.length; i ++) {
			imageIcons[i] = new ImageIcon(getClass().getClassLoader().getResource(imageMenu[i])); 
		}
		mouseEvent = new MouseEvent();
		mouseEvent.mouseDrag(this);


		
		setSize(335, 570);
		getContentPane().setLayout(cards);
		setLayout(new FlowLayout(0, 0, 0));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(700, 300);
		CardLalyOut();
		
		setUndecorated(true);
		
		
		
		
//		

	}

	private void CardLalyOut() {

		menu = new JPanel();
		card_Panel = new JPanel();
		card_Panel.setLayout(cards);
	
		proFile = new TALK_MyProfile(count,socketClient);
		card_Panel.add("ProFile", proFile);
		cards.show(card_Panel, "ProFile");
		menu.setLayout(new FlowLayout(0, 0, 0));
		but_profile = new JButton(imageIcons[1]);
		but_friendList = new JButton(imageIcons[2]);
		but_chatroom = new JButton(imageIcons[5]);
		setButton(but_profile);
		setButton(but_friendList);
		setButton(but_chatroom);
		menu.setPreferredSize(new Dimension(62, 570));
		menu.add(but_profile);
		menu.add(but_friendList);
		menu.add(but_chatroom);
		
		socketClient.setBut_profile(but_profile);
		socketClient.setBut_friendList(but_friendList);
		socketClient.setBut_chatroom(but_chatroom);
		
		add(menu);
		add(card_Panel);

	}

	private void setButton(JButton jbutton) {
		jbutton.setPreferredSize(new Dimension(60, 60));
		jbutton.addActionListener(this);
		jbutton.setContentAreaFilled(false);
		jbutton.setBorderPainted(false);
		jbutton.setFocusPainted(false);
		jbutton.setOpaque(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		socketClient.setCardPanel(card_Panel);
		socketClient.setCards(cards);
		if (ob == but_profile) {
			cards.show(card_Panel, "ProFile");
			but_profile.setIcon(imageIcons[1]);
			but_friendList.setIcon(imageIcons[2]);
			but_chatroom.setIcon(imageIcons[5]);
		} else if (ob == but_friendList) {
			SendUser friendList1 = new SendUser(socketClient.getMyDto().getId());
			friendList1.setComment("friendList");
			try {
				socketClient.friendListWrite(friendList1);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			friendList = new TALK_FriendList(socketClient,cards,card_Panel,count,this);
			card_Panel.add("FriendsList", friendList);
			cards.show(card_Panel, "FriendsList");
			but_profile.setIcon(imageIcons[0]);
			but_friendList.setIcon(imageIcons[3]);
			but_chatroom.setIcon(imageIcons[5]);
		} else if (ob == but_chatroom) {
			SendUser chatRoomList = new SendUser(socketClient.getMyDto().getId());
			chatRoomList.setComment("chatRoomList");						
			try {
				socketClient.chatRoomListWrite(chatRoomList);	
				Thread.sleep(500);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			
			chatRoom = new TALK_ChatRoomList(socketClient,cards,card_Panel,this);
			card_Panel.add("ChatRoom", chatRoom);
			cards.show(card_Panel, "ChatRoom");
			but_profile.setIcon(imageIcons[0]);
			but_friendList.setIcon(imageIcons[2]);
			but_chatroom.setIcon(imageIcons[4]);
			
			
			
		}

	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	

	

}
