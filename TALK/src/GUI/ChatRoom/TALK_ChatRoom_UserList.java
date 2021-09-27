package GUI.ChatRoom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;
import datasend.SendChatRoom;

public class TALK_ChatRoom_UserList extends JFrame {
	private JPanel jPanel, haeder, body, exit, chatRoomImagePanel, chatRoomMemberName, dividerLine, memberListPanel;
	private JButton exitBut;
	private JLabel membersName, totalMember;
	private ButtonStyle buttonStyle;
	private MouseEvent mouseEvent = new MouseEvent();
	private SendChatRoom chatRoomDto;
	SocketClient socketClient;
	public TALK_ChatRoom_UserList(SendChatRoom chatRoomDto ,SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		this.chatRoomDto = chatRoomDto;
		this.socketClient = socketClient;
		mouseEvent.mouseDrag(this);
		buttonStyle = new ButtonStyle();
		setTitle("대화방 멤버 리스트");
		setSize(300,570);
		setLocation(400, 400);
		setResizable(false);
		setUndecorated(true);
		userList();
	}
	
	private void userList() {
		jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jPanel.setPreferredSize(new Dimension(300, 570));
		jPanel.setOpaque(false);
		haeder = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		haeder.setPreferredSize(new Dimension(300, 200));
		haeder.setBackground(Color.white);
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		haeder.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		exit.setPreferredSize(new Dimension(300, 30));
		exit.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		exitBut = new JButton("x");
		buttonStyle.setButton(exitBut);
		exitBut.setBorderPainted(false);
		exitBut.setContentAreaFilled(false);
		exitBut.setFocusPainted(false);
		exitBut.setFont(new Font("궁서 맑음", Font.ITALIC, 20));
		
		exit.setOpaque(false);
		exit.add(exitBut);
		haeder.add(exit);
		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				//friendProfile.setVisible(false);
				
			}
		});

		
		chatRoomImagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		chatRoomImagePanel.setPreferredSize(new Dimension(300, 110));
		chatRoomImagePanel.setBackground(Color.white);
		
		JPanel chatRoomImage = new JPanel(new GridLayout(2, 1, 1, 1));
		chatRoomImage.setOpaque(false);
		chatRoomImage.setPreferredSize(new Dimension(100, 100));
		for (int j = 0; j < chatRoomDto.getUserDto().size(); j++) {
			if (j < 4) {
				ImageIcon profileImage = chatRoomDto.getUserDto().get(j).getPicture_Icon();
				JLabel chatRoomMembersImage = new JLabel() {
					public void paint(Graphics g) {
						BufferedImage bi = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
						g.drawImage(profileImage.getImage(), 0, 0, 50, 50, null);
						ImageIcon newIco = new ImageIcon(bi);
						setIcon(newIco);
						setOpaque(false);
						super.paintComponent(g);
					};

				};
				chatRoomMembersImage.setName("two");
				chatRoomImage.add(chatRoomMembersImage);
			} else {
				break;
			}
		}

		chatRoomImage.setBorder(new LineBorder(Color.darkGray, 1, true));
		chatRoomImagePanel.add(chatRoomImage);

		haeder.add(chatRoomImagePanel);
		
		chatRoomMemberName = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		chatRoomMemberName.setPreferredSize(new Dimension(300,60));
		chatRoomMemberName.setOpaque(false);
		StringBuilder memberName = new StringBuilder();
		
		memberName.append(chatRoomDto.getUserDto().get(1).getId());
		for(int i = 2; i < chatRoomDto.getUserDto().size(); i++) {
			memberName.append("," + chatRoomDto.getUserDto().get(i).getId());			
		}
		membersName = new JLabel(memberName.toString());
		membersName.setHorizontalAlignment(JLabel.CENTER);
		membersName.setPreferredSize(new Dimension(300,30));
		chatRoomMemberName.add(membersName);
		totalMember = new JLabel(chatRoomDto.getUserDto().size() +"명");
		totalMember.setHorizontalAlignment(JLabel.CENTER);
		totalMember.setPreferredSize(new Dimension(300,30));
		chatRoomMemberName.add(totalMember);
		haeder.add(chatRoomMemberName);
		dividerLine = new JPanel();
		dividerLine.setPreferredSize(new Dimension(300,10));
		
		jPanel.add(haeder);
		jPanel.add(dividerLine);
		
		body = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		body.setPreferredSize(new Dimension(300,370));
		body.setOpaque(false);

		
		
		
		memberListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		memberListPanel.setPreferredSize(new Dimension(300, 60*chatRoomDto.getUserDto().size()));
		memberListPanel.setBackground(Color.white);
		JScrollPane scrollPane = new JScrollPane(memberListPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(300, 500));
		scrollPane.setBorder(new MatteBorder(0, 0, 0, 0, Color.GRAY));
		//scrollPane.setBackground(Color.white);
		//scrollPane.getViewport().setOpaque(false);
		
		
		// --------------------------------------------------------

		MouseEvent[] mouseEvent = new MouseEvent[chatRoomDto.getUserDto().size()];
		for(int i = 0; i < chatRoomDto.getUserDto().size(); i++) {
		JPanel friendMember = new JPanel(new FlowLayout(FlowLayout.LEFT));
		friendMember.setPreferredSize(new Dimension(300, 60));
		friendMember.setOpaque(false);
		ImageIcon profileImage =chatRoomDto.getUserDto().get(i).getPicture_Icon();
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
		friendImage.setOpaque(false);
		friendImage.setBorder(new LineBorder(Color.darkGray, 1, true));
		friendImage.setPreferredSize(new Dimension(50, 50));
		JPanel friendInfo = new JPanel(new GridLayout(2,1,0,0));
		friendInfo.setOpaque(false);
		friendInfo.setPreferredSize(new Dimension(200, 50));
		friendInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		JLabel friendId = new JLabel(chatRoomDto.getUserDto().get(i).getId());
		JLabel friendStatus = new JLabel(chatRoomDto.getUserDto().get(i).getStatus());
		//setPreferredSize(new Dimension(30, 30));
		friendMember.add(friendImage);
		friendInfo.add(friendId);
		friendInfo.add(friendStatus);
		friendMember.add(friendInfo);
		memberListPanel.add(friendMember);
		body.add(scrollPane);
		mouseEvent[i] = new MouseEvent();
		mouseEvent[i].mouseMemberlist(i,mouseEvent,friendMember,chatRoomDto.getUserDto().get(i),chatRoomDto.getUserDto().size(),socketClient);
	
		
		
		}
		
		
		
		jPanel.add(body);
		
		
		
		add(jPanel);

	}
	
	

}
