package GUI.Friend;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import DTO.FriendDto;
import DTO.MyDto;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;

public class TALK_FriendList extends JPanel {
	private JPanel body, exit, title, friendAdd, card, friendListPanel;
	private JButton exitBut, friendAddBut;
	private ButtonStyle buttonStyle;
	private MouseEvent mouseEvent;
	private JLabel titleName;
	private MyDto myDto;
	private ArrayList<FriendDto> friendList;
	private SocketClient socketClient;
	public TALK_FriendProfile friendProfile;
	private TALK_FriendList friend;
	private TALK_AddFriend addFriend;
	
	public TALK_FriendList(SocketClient socketClient, CardLayout cards,JPanel card,int count, JFrame jFrame) {
		// TODO Auto-generated constructor stub
		this.socketClient = socketClient;
		this.myDto = socketClient.getMyDto();
		this.friendList = socketClient.getFriendDtos(); 
		this.card = card;
		this.friend = this;
		mouseEvent = new MouseEvent();
		buttonStyle = new ButtonStyle();
		frienslist(cards,count,jFrame);
		setPreferredSize(new Dimension(273, 570));
		socketClient.setFriend(this);
		
	}

	public void frienslist(CardLayout cards, int count , JFrame jFrame) {
		body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		title = new JPanel((new FlowLayout(FlowLayout.LEFT, 0, 0)));
		friendAdd = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
		Font font = new Font("맑은 고딕", Font.BOLD, 19);
		exitBut = new JButton("x");
		buttonStyle.setButton(exitBut);
		exit.add(exitBut);
		exit.setPreferredSize(new Dimension(273, 40));
		exit.setOpaque(false);
		body.setOpaque(false);
		body.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		body.add(exit);
		title.setPreferredSize(new Dimension(150, 30));

		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		body.setPreferredSize(new Dimension(273, 570));
		titleName = new JLabel("친구");
		titleName.setFont(font);
		title.add(titleName);
		title.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		title.setOpaque(false);
		friendAddBut = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icon_addfriend.png")));
		friendAddBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(addFriend == null) {
				addFriend = new TALK_AddFriend(socketClient,friend);
				} else {
					addFriend.dispose();
					addFriend = new TALK_AddFriend(socketClient,friend);
				}
				addFriend.setVisible(true);
				
			}
		});
		mouseEvent.mouseTransparent(friendAddBut, "icon_addfriend1.png", "icon_addfriend.png");
		buttonStyle.setButton(friendAddBut);
		friendAdd.add(friendAddBut);
		friendAdd.setOpaque(false);
		friendAdd.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		friendAddBut.setPreferredSize(new Dimension(30, 30));
		body.add(title);
		body.add(friendAdd);

		friendListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		friendListPanel.setPreferredSize(new Dimension(275, 60*friendList.size()));
		friendListPanel.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(friendListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(275, 497));
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		scrollPane.getViewport().setOpaque(false);
		
		
		// --------------------------------------------------------
		
		MouseEvent[] mouseEvent = new MouseEvent[friendList.size()];
		for(int i = 0; i < friendList.size(); i++) {
		
		JPanel friendMember = new JPanel(new FlowLayout(FlowLayout.LEFT));
		friendMember.setPreferredSize(new Dimension(273, 60));
		friendMember.setOpaque(false);
		ImageIcon profileImage = friendList.get(i).getIcon();
		//ImageIcon profileImage = new ImageIcon(friendList.get(i).getFriendId());
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
		JPanel friendInfo = new JPanel(new GridLayout(2,1,0,0));
		friendInfo.setOpaque(false);
		friendInfo.setPreferredSize(new Dimension(200, 50));
		friendInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		JLabel friendId = new JLabel(friendList.get(i).getFriendId());
		JLabel friendStatus = new JLabel(friendList.get(i).getStatus());
		//setPreferredSize(new Dimension(30, 30));
		friendMember.add(friendImage);
		friendInfo.add(friendId);
		friendInfo.add(friendStatus);
		friendMember.add(friendInfo);
		friendListPanel.add(friendMember);
		body.add(scrollPane);
		
		mouseEvent[i] = new MouseEvent(friendProfile);
		mouseEvent[i].mouseFriendlist(i,mouseEvent,friendMember,friendList.get(i),cards,card,friendList.size(),count,socketClient);
		
		}
		
		
		// -----------------------------------------------------------------------
		
	
		add(body);

	}

	public JPanel getCard() {
		return card;
	}

	public void setCard(JPanel card) {
		this.card = card;
	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("1531451089669.jpg"));
		g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
		// setOpaque(false);

	}
}
