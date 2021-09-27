package Listener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import DTO.FriendDto;

public class KeyEvent {
	private static final char INITIAL_SOUND_BEGIN_UNICODE = 12593; // 초성 유니코드 시작 값
	private static final char INITIAL_SOUND_LAST_UNICODE = 12622; // 초성 유니코드 마지막 값
	private static final char HANGUL_BEGIN_UNICODE = 44032; // 한글 유니코드 시작 값
	private static final char HANGUL_LAST_UNICODE = 55203; // 한글 유니코드 마지막 값
	private static final char NUMBER_BEGIN_UNICODE = 48; // 숫자 유니코드 시작 값
	private static final char NUMBER_LAST_UNICODE = 57; // 숫자 유니코드 마지막 값
	private static final char ENGLISH_ROWER_BEGIN_UNICODE = 65; // 영문(소문자) 유니코드 시작 값
	private static final char ENGLISH_ROWER_LAST_UNICODE = 90; // 영문(소문자) 유니코드 마지막 값
	private static final char ENGLISH_UPPER_BEGIN_UNICODE = 97; // 영문(대문자) 유니코드 시작 값
	private static final char ENGLISH_UPPER_LAST_UNICODE = 122; // 영문(대문자) 유니코드 마지막 값
	private static final char HANGUL_BASE_UNIT = 588; // 자음 마다 가지는 글자수 // 초성
	private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ',
			'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

	public static ArrayList<FriendDto> friendDtos;
	ArrayList<FriendDto> friendDto;
	JRadioButton[] jRadioButton;
	boolean ck = true;

	public KeyEvent(ArrayList<FriendDto> friendDtos) {
		// TODO Auto-generated constructor stub
		KeyEvent.friendDtos = friendDtos;
	}

	private static boolean isInitialSound(char c) {
		for (int i = 0; i < INITIAL_SOUND.length; i++) {
			if (INITIAL_SOUND[i] == c) {
				return true;
			}
		}
		return false;
	}

	private static char getInitialSound(char c) {
		int hanBegin = (c - HANGUL_BEGIN_UNICODE);
		int index = hanBegin / HANGUL_BASE_UNIT;
		return INITIAL_SOUND[index];
	}

	private static boolean isHangul(char c) {
		return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE;
	}

	private static boolean checkUnicode(char c) {
		if (((c >= NUMBER_BEGIN_UNICODE && c <= NUMBER_LAST_UNICODE)
				|| (c >= ENGLISH_UPPER_BEGIN_UNICODE && c <= ENGLISH_UPPER_LAST_UNICODE)
				|| (c >= ENGLISH_ROWER_BEGIN_UNICODE && c <= ENGLISH_ROWER_LAST_UNICODE)
				|| (c >= HANGUL_BEGIN_UNICODE && c <= HANGUL_LAST_UNICODE)
				|| (c >= INITIAL_SOUND_BEGIN_UNICODE && c <= INITIAL_SOUND_LAST_UNICODE))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean matchString(String keyword, String value) {
		int t = 0;
		int seof = value.length() - keyword.length();
		int slen = keyword.length();
		if (seof < 0 || slen == 0) {
			return false;
		}

		for (int i = 0; i <= seof; i++) {
			t = 0;
			while (t < slen) {
				if (!checkUnicode(keyword.charAt(t))) {
					return false;
				}

				if (isInitialSound(keyword.charAt(t)) && isHangul(value.charAt(i + t))) {

					if (getInitialSound(value.charAt(i + t)) == keyword.charAt(t)) {
						t++;
					} else {
						break;
					}
				} else {

					if (value.charAt(i + t) == keyword.charAt(t)) {
						t++;
					} else {
						break;
					}
				}
			}

			if (t == slen) {
				return true;
			}
		}
		return false;
	}

	public void searchItem(String keyword) {
		friendDto = new ArrayList<FriendDto>();

		for (int i = 0; i < friendDtos.size(); i++) {

			if (matchString(keyword, KeyEvent.friendDtos.get(i).getFriendId())) {
				friendDto.add(friendDtos.get(i));
			}
		}

	}

	public void SearchKey(JTextField jTextField, JPanel jPanel, JButton jButton) {

		jTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				// TODO Auto-generated method stub

				ck = false;
				if (jTextField.getText().equals("")) {
					friendDto = KeyEvent.friendDtos;
				} else {
					searchItem(jTextField.getText());
					for (int i = 0; i < friendDto.size(); i++) {
						
					}
				}
				jPanel.removeAll();

				jRadioButton = new JRadioButton[friendDto.size()];
				MouseEvent[] mouseEvent = new MouseEvent[friendDto.size()];
				for (int i = 0; i < friendDto.size(); i++) {
					JPanel friendMember = new JPanel(new FlowLayout(FlowLayout.LEFT));
					friendMember.setPreferredSize(new Dimension(295, 60));
					friendMember.setOpaque(false);
					ImageIcon profileImage = friendDto.get(i).getIcon();
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
					friendMember.add(friendImage);
					JPanel friendInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
					friendInfo.setOpaque(false);
					friendInfo.setPreferredSize(new Dimension(230, 50));
					JLabel friendId = new JLabel(friendDto.get(i).getFriendId());
					friendId.setPreferredSize(new Dimension(175, 50));
					friendInfo.add(friendId);

					jRadioButton[i] = new JRadioButton();
					jRadioButton[i].setSelected(false);
					jRadioButton[i].setOpaque(false);
					jRadioButton[i].setSize(100, 100);
					jRadioButton[i].setPreferredSize(new Dimension(20, 50));
					friendInfo.add(jRadioButton[i]);
					friendMember.add(friendInfo);
					jPanel.add(friendMember);
					mouseEvent[i] = new MouseEvent();
					jRadioButton[i] = mouseEvent[i].mouseFriendlist(i, mouseEvent, friendMember, friendDto.size(),
							jRadioButton[i], jButton);
				}
				jPanel.validate();
				jPanel.repaint();
			}

			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				// TODO Auto-generated method stub
				jPanel.setBackground(Color.white);
			}
		});

	}

	public JRadioButton[] getjRadioButton() {
		return jRadioButton;
	}

	public void setjRadioButton(JRadioButton[] jRadioButton) {
		this.jRadioButton = jRadioButton;
	}

	public boolean isCk() {
		return ck;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public ArrayList<FriendDto> getFriendDto() {
		return friendDto;
	}

	public void setFriendDto(ArrayList<FriendDto> friendDto) {
		this.friendDto = friendDto;
	}

}
