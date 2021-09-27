package GUI.MyProfile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import DTO.MyDto;
import DTO.UserDto;
import GUI.TALK_Title;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.ButtonStyle;
import Style.HintTextField;
import datasend.SendUser;

public class TALK_UpdateMyProfile extends JFrame {

	private JPanel updateUser, userID, update;
	private JLabel  userId,myId, userStatus;
	private JButton exitBut, UpdateUserBut;
	private SocketClient socketClient;
	private MyDto myDto;
	private MouseEvent mouseEvent = new MouseEvent();
	
	public TALK_UpdateMyProfile(SocketClient socketClient, JLabel status) {
		// TODO Auto-generated constructor stub
		
		setTitle("프로필 수정");
		setSize(300, 400);
		setLocation(800, 400);
		this.socketClient = socketClient;
		this.myDto = socketClient.getMyDto();
		mouseEvent.mouseDrag(this);
		updateUser(status);
		setVisible(true);
		
	}
	
	
	public void updateUser(JLabel status) {
		
		setUndecorated(true);
		TALK_Title title = new TALK_Title(this,"프로필 수정");

		userID = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
		userID.setPreferredSize(new Dimension(300, 30));
		userId = new JLabel("ID");
		userID.setOpaque(false);
		myId = new JLabel(myDto.getId());
		userId.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		myId.setFont(new Font("궁서 맑음", Font.ITALIC, 15));
		userId.setPreferredSize(new Dimension(100, 30));
		userId.setFont(new Font("궁서 맑음", Font.ITALIC, 15));
		userId.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		userID.add(userId);
		userID.add(myId);
		
		updateUser = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		updateUser.setPreferredSize(new Dimension(300, 30));
		updateUser.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		userStatus = new JLabel("상태 메세지");
		userStatus.setFont(new Font("궁서 맑음", Font.ITALIC, 15));
		userStatus.setPreferredSize(new Dimension(100, 30));
		
		HintTextField hintTextField = new HintTextField(socketClient.getMyDto().getStatus());
		hintTextField.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
		hintTextField.setFont(new Font("궁서 맑음", Font.ITALIC, 15));
		hintTextField.setPreferredSize(new Dimension(180, 30));
		hintTextField.setBackground(Color.white);
		updateUser.setBackground(Color.white);	
		updateUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		updateUser.add(userStatus);
		updateUser.add(hintTextField);
		title.getBody().add(userID);
		title.getBody().add(updateUser);
		
		update = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		update.setPreferredSize(new Dimension(300, 250));
		update.setBorder(BorderFactory.createEmptyBorder(190, 0, 0, 0));
		update.setBackground(Color.white);
		UpdateUserBut = new JButton("프로필 수정");
		UpdateUserBut.setPreferredSize(new Dimension(100, 50));
		update.add(UpdateUserBut);
		
		UpdateUserBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			
				try {
					SendUser user = new SendUser(socketClient.getMyDto().getId());
					user.setStatus(hintTextField.getText());
					user.setComment("statusUpdate");
					socketClient.ObjectUpdate(user);
					socketClient.getMyDto().setStatus(hintTextField.getText());
					status.setText(hintTextField.getText());
					dispose();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		});
		title.getBody().add(update);
		
		
		add(title.getBody());
		
	}
	
	
}
