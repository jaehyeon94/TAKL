package GUI.ChatRoom;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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

import DTO.MyDto;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;
import datasend.SendChatRoom;
import datasend.SendUser;

public class TALK_ChatRoomList extends JPanel {

	private JPanel body, exit, title, chatRoomAdd, card, chatRoomListPanel;
	private JButton exitBut, chatRoomBut;
	private ButtonStyle buttonStyle;
	private MouseEvent mouseEvent;
	private JLabel titleName;
	private MyDto myDto;
	private ArrayList<SendChatRoom> chatRoomList;
	private SocketClient socketClient;
	private TALK_ChatRoomList chatRoom;
	private TALK_AddChatRoom addChatRoom;
	public TALK_ChatRoomList(SocketClient socketClient, CardLayout cards, JPanel card, JFrame jFrame) {
		// TODO Auto-generated constructor stub
		this.myDto = socketClient.getMyDto();
		this.socketClient = socketClient;
		this.chatRoomList = socketClient.getChatRoomDtos();
		this.card = card;
		this.chatRoom = this;
		// ---------------------------------------------
		
		mouseEvent = new MouseEvent();
		buttonStyle = new ButtonStyle();
		mouseEvent = new MouseEvent();
		buttonStyle = new ButtonStyle();
		chatRoomList(cards,jFrame);
		setPreferredSize(new Dimension(273, 570));
	}

	public void chatRoomList(CardLayout cards, JFrame jFrame) {
		body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		title = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		chatRoomAdd = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
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
		titleName = new JLabel("채팅");
		titleName.setFont(font);
		title.add(titleName);
		title.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 70));
		title.setOpaque(false);
		chatRoomBut = new JButton(new ImageIcon(getClass().getClassLoader().getResource("icon_addchats.png")));
		chatRoomBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(addChatRoom == null) {
				addChatRoom = new TALK_AddChatRoom(socketClient,chatRoom);
				} else {
					addChatRoom.dispose();
					addChatRoom = new TALK_AddChatRoom(socketClient,chatRoom);
				}
			}
		});
		mouseEvent.mouseTransparent(chatRoomBut, "icon_addchats1.png", "icon_addchats.png");
		buttonStyle.setButton(chatRoomBut);
		chatRoomAdd.add(chatRoomBut);
		chatRoomAdd.setOpaque(false);
		chatRoomAdd.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
		chatRoomBut.setPreferredSize(new Dimension(30, 30));
		body.add(title);
		body.add(chatRoomAdd);

		// -----------------------------------------------------------

		
		chatRoomListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		chatRoomListPanel.setPreferredSize(new Dimension(275, 60*chatRoomList.size()));
		
		chatRoomListPanel.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(chatRoomListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(275, 493));
		scrollPane.setMinimumSize(new Dimension(275, 0));
		scrollPane.setOpaque(false);
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		scrollPane.getViewport().setOpaque(false);
		
		
		
		
		
		for(int i = 0; i < chatRoomList.size(); i++) {
			for(int j=0; j < chatRoomList.get(i).getUserDto().size(); j++) {
				if(chatRoomList.get(i).getUserDto().get(j).getId().equals(myDto.getId())) {
					SendUser user = chatRoomList.get(i).getUserDto().get(j);
					chatRoomList.get(i).getUserDto().remove(j);
					chatRoomList.get(i).getUserDto().add(user);	
				}
			}
		}
		
		
		
		
		
		
		
	
		 MouseEvent[] mouseEvent = new MouseEvent[chatRoomList.size()];
		for (int i = 0; i < chatRoomList.size(); i++) {
			JPanel chatRoom = new JPanel(new FlowLayout(FlowLayout.LEFT));
			chatRoom.setAlignmentY(Component.CENTER_ALIGNMENT);
			chatRoom.setPreferredSize(new Dimension(273, 60));
			chatRoom.setOpaque(false);
			if (chatRoomList.get(i).getUserDto().size() == 2) {
				//ImageIcon profileImage = new ImageIcon(chatRoomList.get(i).getUserDto().get(0).getPicture());
				ImageIcon profileImage =chatRoomList.get(i).getUserDto().get(0).getPicture_Icon();
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
				chatRoom.add(chatRoomImage);
			} else {
				JPanel chatRoomImage = new JPanel(new GridLayout(2, 1, 1, 1));
				chatRoomImage.setOpaque(false);
				chatRoomImage.setPreferredSize(new Dimension(50, 50));
				for (int j = 0; j < chatRoomList.get(i).getUserDto().size(); j++) {
					if (j < 4) {
						//ImageIcon profileImage = new ImageIcon(chatRoomList.get(i).getUserDto().get(j).getPicture());
						ImageIcon profileImage = chatRoomList.get(i).getUserDto().get(j).getPicture_Icon();
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
						
						chatRoomImage.add(chatRoomMembersImage);
					} else {
						break;
					}
				}
				chatRoomImage.setBorder(new LineBorder(Color.darkGray, 1, true));
				chatRoom.add(chatRoomImage);
			}
			StringBuilder name = new StringBuilder();
			name.append(chatRoomList.get(i).getUserDto().get(0).getId());
			for(int z = 1; z < chatRoomList.get(i).getUserDto().size()-1; z++) {
				name.append("," + chatRoomList.get(i).getUserDto().get(z).getId());
			}
			
			String member;
			if (name.toString().length() > 7) {
				member = name.toString().substring(0, 7) + "...";
			} else {
				member = name.toString();
			}
			
			JPanel chatRoomInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
			chatRoomInfo.setOpaque(false);
			chatRoomInfo.setPreferredSize(new Dimension(200, 30));
			chatRoomInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			JLabel chatRoomName;
			if(chatRoomList.get(i).getUserDto().size() != 2) {
				chatRoomName = new JLabel(member + " ("+ chatRoomList.get(i).getUserDto().size() +"명)");
			} else {
				chatRoomName = new JLabel(member);
			}
			chatRoomName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
			chatRoomInfo.add(chatRoomName);
			chatRoom.add(chatRoomInfo);
			
			
			chatRoomListPanel.add(chatRoom);
			body.add(scrollPane);
			
			mouseEvent[i] = new MouseEvent();
			mouseEvent[i].mouseChatRoom(i,mouseEvent,chatRoom,chatRoomList.get(i),cards,card,chatRoomList.size(),socketClient);
			
						
		}

		add(body);

	}

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("1531451089669.jpg"));
		g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
		// setOpaque(false);

	}
}
