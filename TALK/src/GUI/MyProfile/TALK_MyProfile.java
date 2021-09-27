package GUI.MyProfile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DTO.MyDto;
import GUI.TALK_Main;
import Socket.SocketClient;
import Style.ButtonStyle;
import Style.RoundedButton;
import datasend.SendUser;

public class TALK_MyProfile extends JPanel {

	private MyDto myDto;
	private JPanel body, info, but, exit, my;
	private JLabel id, status;
	private JButton update, withdraw, exitBut;
	private RoundedButton roundedButton;
	private ButtonStyle buttonStyle;
	private TALK_Profile profile;
	public boolean ck;
	private SocketClient socketClient;
	int imageCount;

	public TALK_MyProfile(int count, SocketClient socketClient) {
		// TODO Auto-generated constructor stub
		ck = true;
		my = this;
		socketClient.setMyProfile(this);
		buttonStyle = new ButtonStyle();
		this.myDto = socketClient.getMyDto();
		this.socketClient = socketClient;
		imageCount = 0;
		myProfile(count);
	}

	public void myProfile(int count) {
		body = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		info = new JPanel(new GridLayout(2, 1, 10, 10));
		but = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

		exitBut = new JButton("x");
		exit.add(exitBut);
		exit.setPreferredSize(new Dimension(273, 100));
		exit.setOpaque(false);
		buttonStyle.setButton(exitBut);
		id = new JLabel("11");
		status = new JLabel("11");
		//JOptionPane.showMessageDialog(null,myDto.getPicture() , "경고창", JOptionPane.WARNING_MESSAGE);
		roundedButton = new RoundedButton(myDto.getIcon());
		id.setText(myDto.getId());
		status = new JLabel(myDto.getStatus());

		
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
		but.setPreferredSize(new Dimension(200, 180));
		but.setBorder(BorderFactory.createEmptyBorder(140, 0, 0, 0));

		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				System.exit(0);
			}
		});

		roundedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(profile == null)  {
				profile = new TALK_Profile(myDto.getIcon(),true,socketClient,roundedButton,imageCount++);
		
				} else {
					profile.dispose();
					profile = new TALK_Profile(myDto.getIcon(),true,socketClient,roundedButton,imageCount++);
	
				}
				myDto = profile.getMyDto();
				profile.setVisible(true);
				profile.setVisible(true);

			}
		});

		update = new JButton("수정");
		buttonStyle.setButton(update);
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				TALK_UpdateMyProfile takl_UpdateUser = new TALK_UpdateMyProfile(socketClient,status);
				
			}
		});
		withdraw = new JButton("탈퇴");
		buttonStyle.setButton(withdraw);
		withdraw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				SendUser sendUser = new SendUser(socketClient.getMyDto().getId());
				sendUser.setComment("deleteUser");
				
				TALK_Main main = new TALK_Main(socketClient);
				main.setVisible(true);
				socketClient.getjFrame().dispose();
						
				try {
					socketClient.deleteUserWrite(sendUser);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		but.add(update, "South");
		but.add(withdraw, "South");

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

	public void paintComponent(Graphics g) {
		Dimension d = getSize();
		URL imageURL = getClass().getClassLoader().getResource("1531451089669.jpg");
		ImageIcon background = new ImageIcon(imageURL);
		g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);
		// setOpaque(false);

	}

	public RoundedButton getRoundedButton() {
		return roundedButton;
	}

	public void setRoundedButton(RoundedButton roundedButton) {
		this.roundedButton = roundedButton;
	}
	

}
