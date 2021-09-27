package GUI.MyProfile;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;

import DTO.MyDto;
import Listener.MouseEvent;
import Socket.SocketClient;
import Style.RoundedButton;
import datasend.SendImage;
import datasend.SendUser;

public class TALK_Profile extends JFrame {

	private JPanel photo, image, exit;
	private JButton exitBut;
	private JButton imageBut;
	private MouseEvent mouseEvent = new MouseEvent();
	private MyDto myDto;
	int imageCount;
	private SocketClient socketClient;
	String image1;

	public TALK_Profile(ImageIcon imageIcon, boolean ck, SocketClient socketClient, RoundedButton roundedButton,
			int count) {
		// TODO Auto-generated constructor stub
		setTitle("Photo");
		setSize(300, 300);
		setLocation(800, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		imageCount = count;
		this.myDto = socketClient.getMyDto();
		this.socketClient = socketClient;
		mouseEvent.mouseDrag(this);
		Font font = new Font(null, Font.ITALIC, 25);
		ImageIcon icon = imageIcon;
		photo = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(icon.getImage(), 0, 0, 300, 300, null);
				setOpaque(false);
				// super.paintComponent(g);
			}
		};
		photo.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		exit = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		exitBut = new JButton("x");
		exitBut.setBorderPainted(false);
		exitBut.setContentAreaFilled(false);
		exitBut.setFocusPainted(false);
		exitBut.setFont(font);
		exit.setOpaque(false);
		exit.add(exitBut);
		if (ck) {
			image = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
			
			imageBut = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img_sendcards_addphoto.png")));
			imageBut.setOpaque(false);
			imageBut.setContentAreaFilled(false);
			imageBut.setBorderPainted(false);
			imageBut.setFocusPainted(false);
			mouseEvent.mouseTransparent(imageBut, "img_sendcards_addphoto1.png",
					"img_sendcards_addphoto.png");
			imageBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					JFileChooser jFileChooser = new JFileChooser();
					FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "png");
					jFileChooser.setFileFilter(filter);
					jFileChooser.setMultiSelectionEnabled(false);
					int ret = jFileChooser.showOpenDialog(null);
					if (ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
						return;
					}
					String filePath = jFileChooser.getSelectedFile().getPath();

					try {

						File file = new File(filePath);
						String extensions = FilenameUtils.getExtension(filePath);
						String name = FilenameUtils.getName(filePath);

						SendImage sendImage = new SendImage(myDto.getId(),name,file,extensions);

						socketClient.updateimage(sendImage);

					
						
						SendUser chatRoomList = new SendUser(socketClient.getMyDto().getId());
						chatRoomList.setComment("chatRoomList");						
						socketClient.chatRoomListWrite(chatRoomList);	
						dispose();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});
			image.add(imageBut);
			image.setPreferredSize(new Dimension(330, 280));
			// image.setBackground(new Color(0,0,0,0));
			image.setOpaque(false);
			image.setBorder(BorderFactory.createEmptyBorder(220, 0, 0, 0));
			photo.add(exit);
			photo.add(image);

		} else {
			photo.add(exit);
		}
		add(photo);
		setUndecorated(true);
		exitBut.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

	}

	public MyDto getMyDto() {
		return myDto;
	}

	public void setMyDto(MyDto myDto) {
		this.myDto = myDto;
	}

}
