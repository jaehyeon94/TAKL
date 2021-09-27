package Socket;

import java.awt.CardLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import DTO.FriendDto;
import DTO.MyDto;
import DTO.UserDto;
import GUI.ChatRoom.TALK_CharRoom_Manager;
import GUI.ChatRoom.TALK_ChatRoom;
import GUI.Friend.TALK_AddFriend;
import GUI.Friend.TALK_FriendList;
import GUI.Login.login;
import GUI.Login.signUp;
import GUI.MyProfile.TALK_MyProfile;
import datasend.SendChat;
import datasend.SendChatRoom;
import datasend.SendFriend;
import datasend.SendImage;
import datasend.SendUser;

public class SocketClient {
	private final static int BUFFER_SIZE = 1024;
	private static final int SERVER_PORT = 5050;
	private ObjectOutputStream outTOServer;
	private ObjectInputStream inFromServer;
	private boolean insertChatRoomCk = false;
	private boolean insertFriendCk = false;
	private boolean loginCk = false;
	private boolean insertUser = false;
	private boolean friendlistCk = false;
	private Socket client = null;
	private TALK_ChatRoom chatRoom;
	private TALK_AddFriend addFriend;
	private int count;
	private CardLayout cards;
	private JFrame jFrame;
	private JPanel CardPanel;
	private JButton but_profile, but_friendList, but_chatroom;
	private TALK_FriendList friend;
	public Thread chatting;
	private SendUser sendUser;
	private SendFriend sendFriend;
	private SendChat sendChat;
	private SendChatRoom sendChatRoom;
	public ImageIcon iconz;
	private boolean ck;
	private signUp signUp;
	private MyDto myDto;
	public ArrayList<SendFriend> friendlist;
	public ArrayList<FriendDto> friendDtos;
	public ArrayList<SendChatRoom> chatRoomlist;
	private ArrayList<SendChat> chats;
	public ArrayList<UserDto> userdto;
	private login login;
	private HashMap<Integer, TALK_ChatRoom> chatRooms;
	public Object object;
	int number;
	public TALK_CharRoom_Manager charRoom;
	public TALK_MyProfile myProfile;
	private Thread thread;
	public SocketClient() throws IOException {
		// TODO Auto-generated constructor stub

		client = new Socket();
		InetAddress ip = InetAddress.getLocalHost();
		String hostAddress = ip.getHostAddress();
		chats = new ArrayList<>();
		chatRooms = new HashMap<Integer, TALK_ChatRoom>();
		InetSocketAddress ipep = new InetSocketAddress("115.138.112.190", SERVER_PORT);
		client.connect(ipep);
		doProcess();

	}

	public void doProcess() {
		try {
			outTOServer = new ObjectOutputStream(client.getOutputStream());
			inFromServer = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void notProcess() {
		outTOServer = null;
	}

	public void messageWrite(String message) {
		try {
			outTOServer.writeObject(message);
			outTOServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ObjectUpdate(SendUser sendUesr) throws ClassNotFoundException {

		try {
			if (sendUesr.getComment().equals("statusUpdate")) {

				outTOServer.writeObject(sendUesr);
				outTOServer.flush();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean loginWrite(SendUser sendUesr) throws ClassNotFoundException {

		try {
			if (sendUesr.getComment().equals("login")) {

				outTOServer.writeObject(sendUesr);
				outTOServer.flush();										
				
				Object object = inFromServer.readObject();
				
				if (object instanceof SendUser) {
					SendUser member = (SendUser) object;
					if (member.getComment().equals("login")) {										
						if (member.getId() == null) {	
							return false;
						} else {	
								myDto = new MyDto(member.getId(), member.getStatus(), member.getPicture(),
									member.getPicture_Icon());
							charRoom = new TALK_CharRoom_Manager(this, client, inFromServer);
							thread = new Thread(charRoom);
							thread.start();
							
							return true;
						}
					}
				}
			}
		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertUserWrite(SendUser senduser) throws ClassNotFoundException {

		try {

			if (senduser.getComment().equals("insertUser")) {
				outTOServer.writeObject(senduser);
				outTOServer.flush();
				object = inFromServer.readObject();					
				if (object instanceof SendUser) {
					SendUser member = (SendUser) object;
					if (member.getComment().equals("insertUser")) {
						System.out.println(member.getId());
						if (member.getId().equals("ok")) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void deleteUserWrite(SendUser senduser) throws ClassNotFoundException {

		try {
			if (senduser.getComment().equals("deleteUser")) {

				outTOServer.writeObject(senduser);
				outTOServer.flush();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void friendListWrite(SendUser sendUesr) throws ClassNotFoundException {

		try {
			if (sendUesr.getComment().equals("friendList")) {
				outTOServer.writeObject(sendUesr);
				outTOServer.flush();
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean insertFriendWrite(SendFriend sendFriend) throws ClassNotFoundException {

		try {
			if (sendFriend.getComment().equals("insertFriend")) {
				outTOServer.writeObject(sendFriend);
				outTOServer.flush();
				return ck;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void deleteFriendWrite(SendFriend sendFriend) throws ClassNotFoundException {

		try {
			if (sendFriend.getComment().equals("deleteFriend")) {
				outTOServer.writeObject(sendFriend);
				outTOServer.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateimage(SendImage sendImage) throws ClassNotFoundException {

		try {
			outTOServer.writeObject(sendImage);
			outTOServer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public synchronized void chatRoomListWrite(SendUser sendUesr) throws ClassNotFoundException {

		try {
			if (sendUesr.getComment().equals("chatRoomList")) {

				outTOServer.writeObject(sendUesr);
				outTOServer.flush();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void roomChatting(int room_id) {
		try {
			System.out.println(room_id );
			outTOServer.writeObject(room_id);
			outTOServer.flush();
		
			
		
			if(object instanceof Integer) {
				System.out.println(object);
			
			}
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public synchronized void sendChatting(SendChat sendChat) {
		try {
			outTOServer.writeObject(sendChat);
			outTOServer.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void insertChatRoomWrite(SendChatRoom sendChatRoom) throws ClassNotFoundException {

		try {
			if (sendChatRoom.getComment().equals("insertChatRoom")) {
				outTOServer.writeObject(sendChatRoom);
				outTOServer.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MyDto getMyDto() {
		return myDto;
	}

	public void setMyDto(MyDto myDto) {
		this.myDto = myDto;
	}

	public ArrayList<FriendDto> getFriendDtos() {
		return friendDtos;
	}

	public void setFriendDtos(ArrayList<FriendDto> friendDtos) {
		this.friendDtos = friendDtos;
	}

	public ArrayList<SendChatRoom> getChatRoomDtos() {
		return chatRoomlist;
	}

	public void setChatRoomDtos(ArrayList<SendChatRoom> chatRoomDtos) {
		this.chatRoomlist = chatRoomDtos;
	}

	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	public JPanel getCardPanel() {
		return CardPanel;
	}

	public void setCardPanel(JPanel cardPanel) {
		CardPanel = cardPanel;
	}

	public CardLayout getCards() {
		return cards;
	}

	public void setCards(CardLayout cards) {
		this.cards = cards;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public TALK_FriendList getFriend() {
		return friend;
	}

	public void setFriend(TALK_FriendList friend) {
		this.friend = friend;
	}

	public JButton getBut_profile() {
		return but_profile;
	}

	public void setBut_profile(JButton but_profile) {
		this.but_profile = but_profile;
	}

	public JButton getBut_friendList() {
		return but_friendList;
	}

	public void setBut_friendList(JButton but_friendList) {
		this.but_friendList = but_friendList;
	}

	public JButton getBut_chatroom() {
		return but_chatroom;
	}

	public void setBut_chatroom(JButton but_chatroom) {
		this.but_chatroom = but_chatroom;
	}

	public ObjectOutputStream getOutTOServer() {
		return outTOServer;
	}

	public void setOutTOServer(ObjectOutputStream outTOServer) {
		this.outTOServer = outTOServer;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public TALK_ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(TALK_ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public HashMap<Integer, TALK_ChatRoom> getChatRooms() {
		return chatRooms;
	}

	public void setChatRooms(HashMap<Integer, TALK_ChatRoom> chatRooms) {
		this.chatRooms = chatRooms;
	}

	public SendUser getSendUser() {
		return sendUser;
	}

	public void setSendUser(SendUser sendUser) {
		this.sendUser = sendUser;
	}

	public SendFriend getSendFriend() {
		return sendFriend;
	}

	public void setSendFriend(SendFriend sendFriend) {
		this.sendFriend = sendFriend;
	}

	public SendChat getSendChat() {
		return sendChat;
	}

	public void setSendChat(SendChat sendChat) {
		this.sendChat = sendChat;
	}

	public SendChatRoom getSendChatRoom() {
		return sendChatRoom;
	}

	public void setSendChatRoom(SendChatRoom sendChatRoom) {
		this.sendChatRoom = sendChatRoom;
	}

	public boolean isCk() {
		return ck;
	}

	public void setCk(boolean ck) {
		this.ck = ck;
	}

	public ArrayList<SendFriend> getFriendlist() {
		return friendlist;
	}

	public void setFriendlist(ArrayList<SendFriend> friendlist) {
		this.friendlist = friendlist;
	}

	public TALK_MyProfile getMyProfile() {
		return myProfile;
	}

	public void setMyProfile(TALK_MyProfile myProfile) {
		this.myProfile = myProfile;
	}

	public boolean isLoginCk() {
		return loginCk;
	}

	public void setLoginCk(boolean loginCk) {
		this.loginCk = loginCk;
	}

	public login getLogin() {
		return login;
	}

	public void setLogin(login login) {
		this.login = login;
	}

	public boolean isInsertChatRoomCk() {
		return insertChatRoomCk;
	}

	public void setInsertChatRoomCk(boolean insertChatRoomCk) {
		this.insertChatRoomCk = insertChatRoomCk;
	}

	public boolean isInsertFriendCk() {
		return insertFriendCk;
	}

	public void setInsertFriendCk(boolean insertFriendCk) {
		this.insertFriendCk = insertFriendCk;
	}

	public TALK_AddFriend getAddFriend() {
		return addFriend;
	}

	public void setAddFriend(TALK_AddFriend addFriend) {
		this.addFriend = addFriend;
	}

	public boolean isFriendlistCk() {
		return friendlistCk;
	}

	public void setFriendlistCk(boolean friendlistCk) {
		this.friendlistCk = friendlistCk;
	}

	public boolean isInsertUser() {
		return insertUser;
	}

	public void setInsertUser(boolean insertUser) {
		this.insertUser = insertUser;
	}

	public signUp getSignUp() {
		return signUp;
	}

	public void setSignUp(signUp signUp) {
		this.signUp = signUp;
	}

	public TALK_CharRoom_Manager getCharRoom() {
		return charRoom;
	}

	public void setCharRoom(TALK_CharRoom_Manager charRoom) {
		this.charRoom = charRoom;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public ArrayList<SendChat> getChats() {
		return chats;
	}

	public void setChats(ArrayList<SendChat> chats) {
		this.chats = chats;
	}

	
	
}
