package GUI.ChatRoom;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import DTO.FriendDto;
import Socket.SocketClient;
import datasend.SendChat;
import datasend.SendChatRoom;
import datasend.SendFriend;

public class TALK_CharRoom_Manager implements Runnable {

	ArrayList<TALK_ChatRoom> chatRooms;
	SocketClient socketClient;
	public Object object;
	Socket socket;
	private int count = 0;
	private volatile boolean done = false;
	private volatile boolean ck = false;
	ImageIcon icon;
	ObjectInputStream inFromServer;
	ObjectOutputStream outTOServer;

	public TALK_CharRoom_Manager(SocketClient socketClient, Socket socket, ObjectInputStream objectInputStream) {

		this.inFromServer = objectInputStream;
		this.socketClient = socketClient;
		this.socket = socket;
		this.outTOServer = socketClient.getOutTOServer();
	}

	private CompletionHandler<ImageIcon, Void> callback = new CompletionHandler<ImageIcon, Void>() {

		@Override
		public void completed(ImageIcon result, Void attachment) {

			socketClient.getMyProfile().getRoundedButton().setButton(result);
			socketClient.getMyDto().setIcon(result);

		}

		@Override
		public void failed(Throwable exc, Void attachment) {
			System.out.println("failed() 실행 : " + exc.toString());
		}
	};

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			Object object;
			while ((object = inFromServer.readObject()) != null) {
				if (object instanceof SendChat) {
					SendChat sendChat = (SendChat) object;
					Iterator<Entry<Integer, TALK_ChatRoom>> entries = socketClient.getChatRooms().entrySet().iterator();
					while (entries.hasNext()) {
						Entry<Integer, TALK_ChatRoom> entry = entries.next();
						if (entry.getKey() == sendChat.getRoom_id()) {
							if (sendChat.getUser_id().equals(socketClient.getMyDto().getId())) {
								entry.getValue().myChatting(sendChat.getUser_id(), sendChat.getStory());
							} else {
								entry.getValue().otherChatting(sendChat.getUser_id(), sendChat.getStory(),
										sendChat.getIcon());
							}
						}
					}

				} else if (object instanceof ArrayList<?>) {

					if (socketClient.friendDtos == null) {					
						socketClient.friendDtos = new ArrayList<>();
					}
					if (socketClient.userdto == null) {
						socketClient.chatRoomlist = new ArrayList<>();
					}
					if (((ArrayList<?>) object).size() != 0 && ((ArrayList<?>) object).get(0) instanceof SendFriend) {
						socketClient.friendlist = (ArrayList<SendFriend>) object;
						socketClient.friendDtos = new ArrayList<FriendDto>();
						for (int i = 0; i < socketClient.friendlist.size(); i++) {
							socketClient.friendDtos.add(new FriendDto(socketClient.friendlist.get(i).getId(),
									socketClient.friendlist.get(i).getFriendID(),
									socketClient.friendlist.get(i).getStatus(),
									socketClient.friendlist.get(i).getPicture(),
									socketClient.friendlist.get(i).getPicture_Icon()));
						}
						Collections.sort(socketClient.friendDtos, new Comparator<FriendDto>() {

							public int compare(FriendDto o1, FriendDto o2) {
								// TODO Auto-generated method stub
								return o1.getFriendId().compareTo(o2.getFriendId());
							}
						});

					} else if (((ArrayList<?>) object).size() != 0
							&& ((ArrayList<?>) object).get(0) instanceof SendChatRoom) {
						socketClient.chatRoomlist = (ArrayList<SendChatRoom>) object;

					} else if (((ArrayList<?>) object).size() != 0
							&& ((ArrayList<?>) object).get(0) instanceof SendChat) {
						socketClient.setChats((ArrayList<SendChat>) object);

						Iterator<Entry<Integer, TALK_ChatRoom>> entries = socketClient.getChatRooms().entrySet()
								.iterator();
						while (entries.hasNext()) {
							Entry<Integer, TALK_ChatRoom> entry = entries.next();
							for (int i = 0; i < socketClient.getChats().size(); i++) {
								if (entry.getKey() == socketClient.getChats().get(i).getRoom_id()) {
									if (socketClient.getChats().get(i).getUser_id()
											.equals(socketClient.getMyDto().getId())) {
										entry.getValue().myChatting(socketClient.getChats().get(i).getUser_id(),
												socketClient.getChats().get(i).getStory());
									} else {
										entry.getValue().otherChatting(socketClient.getChats().get(i).getUser_id(),
												socketClient.getChats().get(i).getStory(),
												socketClient.getChats().get(i).getIcon());
									}
								}
							}
						}

					}

				} else if (object instanceof String) {
					String ckID = (String) object;
					System.out.println(ckID);
					if (ckID.equals("friendExist")) {
						System.out.println("이미 친구상태입니다.");
						ck = false;
					} else if (ckID.equals("friendNotNone")) {
						ck = true;
					} else if (ckID.equals("NOID")) {
						System.out.println("아이디가 존재하지 않습니다.");
						ck = false;
					}
				} else if (object instanceof ImageIcon) {
					icon = (ImageIcon) object;
					callback.completed(icon, null);
				}
				// executorService.shutdown();
			}
		} catch (Exception ioe) {
			System.out.println("연결이 끊겼습니다.");
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();

		} finally {
			// try {if(inFromServer != null) {inFromServer.close();}}catch (IOException
			// ioe){}
			// try {if(outTOServer != null) {outTOServer.close();}}catch (IOException ioe){}
			// try {if(!socket.isClosed()) {socket.close();}} catch (IOException ioe){}
		}

	}

	public void not() {
		synchronized (this) {
			this.notifyAll();
		}
	}

};
