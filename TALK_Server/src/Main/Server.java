package Main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Dao.ChatRoomDao;
import Dao.FriendDao;
import Dao.UserDao;
import Dto.ChatRoom;
import Dto.User;
import datasend.SendChat;
import datasend.SendChatRoom;
import datasend.SendFriend;
import datasend.SendImage;
import datasend.SendUser;

public class Server {

	private static final int SERVER_PORT = 5050;

	private ObjectOutputStream outToClient = null;
	private ObjectInputStream inFromClient = null;
	private ServerSocket server;
	private Map<String, ObjectOutputStream> nameToSocketList;
	private Map<String, Socket> nameToSocketList1;

	private final static int BUFFER_SIZE = 1024;
	private ArrayList<SendUser> chatRoomMemberList;
	private ArrayList<SendChatRoom> ChatRoom = new ArrayList<SendChatRoom>();
	private UserDao userDao = new UserDao();;
	private FriendDao friendDao = new FriendDao();
	private ChatRoomDao chatRoomDao = new ChatRoomDao();
	private ObjectOutputStream objectOutputStream;

	private Object object;

	@SuppressWarnings("unused")
	public Server() {

		try {
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_PORT);
			ExecutorService receiver = Executors.newCachedThreadPool();
			this.server = new ServerSocket();
			this.nameToSocketList1 = new HashMap<>();
			this.nameToSocketList = new HashMap<>();
			this.server.bind(inetSocketAddress);

			while (true) {

				Socket client = this.server.accept();
				System.out.println("Client connected IP address = " + client.getRemoteSocketAddress().toString());
				boolean checkClientClosed = client.isClosed();

				if (!checkClientClosed) {
					receiver.execute(() -> {
						String nickname = null;
						try (OutputStream send = client.getOutputStream();
								InputStream recv = client.getInputStream();) {
							ObjectOutputStream outToClient = new ObjectOutputStream(client.getOutputStream());
							ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());

							while ((object = inFromClient.readObject()) != null) {
								if (object instanceof String) {
								} else if (object instanceof SendUser) {
									SendUser member = (SendUser) object;
									if (member.getComment().equals("login")) {
										nickname = member.getId();
										this.nameToSocketList.put(member.getId(), outToClient);
										this.nameToSocketList1.put(member.getId(), client);
										User userck = userDao.userloginCk(member.getId(), member.getPassword());

										if (userck != null) {
											SendUser user = new SendUser(userck.getId(), userck.getStatusMessage(),
													userck.getPicture());
											user.setPicture_Icon(new ImageIcon(userck.getPicture()));
											user.setComment("login");
											outToClient.writeObject(user);
											outToClient.flush();
										} else {
											SendUser user = new SendUser();
											user.setComment("login");
											outToClient.writeObject(user);
											outToClient.flush();
										}

									} else if (member.getComment().equals("insertUser")) {
										User userck = userDao.userName(member.getId());
										if (userck == null) {
											SendUser sendUser = new SendUser("ok");
											sendUser.setComment("insertUser");
											outToClient.writeObject(sendUser);
											userDao.userInsert(member.getId(), member.getPassword(),
													"images/profile0.png");

										} else {
											SendUser sendUser = new SendUser("No");
											sendUser.setComment("insertUser");
											outToClient.writeObject(sendUser);
										}
										outToClient.flush();
									}

									else if (member.getComment().equals("deleteUser")) {
										userDao.userDelete(member.getId());
									} else if (member.getComment().equals("friendList")) {

										ArrayList<SendFriend> friends = new ArrayList<>();
										ArrayList<User> list = new ArrayList<>();

										list = friendDao.friendList(member.getId());

										for (int i = 0; i < list.size(); i++) {
											friends.add(new SendFriend(member.getId(), list.get(i).getId(),
													list.get(i).getStatusMessage(), list.get(i).getPicture(),
													new ImageIcon(list.get(i).getPicture())));

										}
										outToClient.writeObject(friends);
										outToClient.flush();
									} else if (member.getComment().equals("chatRoomList")) {
										ArrayList<SendChatRoom> chatRooms = new ArrayList<SendChatRoom>();
										ArrayList<ChatRoom> chatRoomlist = new ArrayList<ChatRoom>();
										ArrayList<Integer> numbers = new ArrayList<Integer>();
										numbers = chatRoomDao.getMyRoomNo(member.getId());
										for (int i = 0; i < numbers.size(); i++) {
											chatRoomlist.add(chatRoomDao.getMyRoom(numbers.get(i)));
										}

										for (int i = 0; i < chatRoomlist.size(); i++) {
											ArrayList<SendUser> senduser = new ArrayList<SendUser>();
											for (int j = 0; j < chatRoomlist.get(i).getMembers().size(); j++) {
												senduser.add(new SendUser(
														chatRoomlist.get(i).getMembers().get(j).getId(),
														chatRoomlist.get(i).getMembers().get(j).getStatusMessage(),
														chatRoomlist.get(i).getMembers().get(j).getPicture(),
														new ImageIcon(
																chatRoomlist.get(i).getMembers().get(j).getPicture())));
											}
											chatRooms.add(new SendChatRoom(chatRoomlist.get(i).getRoom_id(),
													chatRoomlist.get(i).getRoom_name(), senduser));
										}
										outToClient.writeObject(chatRooms);
										outToClient.flush();

									} else if (member.getComment().equals("statusUpdate")) {
										userDao.userStatusUpdate(member.getId(), member.getStatus());
									}

								} else if (object instanceof SendFriend) {
									SendFriend friend = (SendFriend) object;
									if (friend.getComment().equals("insertFriend")) {
										User userID = userDao.userName(friend.getFriendID());
										if (userID != null) {
											if (friendDao.FriendCk(friend.getId(), friend.getFriendID()) != null) {
												outToClient.writeObject("friendExist");
											} else {
												friendDao.FriendInsert(friend.getId(), friend.getFriendID());
												outToClient.writeObject("friendNotNone");
											}
											outToClient.flush();
										} else {
											outToClient.writeObject("NOID");
											outToClient.flush();
										}
									} else if (friend.getComment().equals("deleteFriend")) {
										friendDao.FriendDelete(friend.getId(), friend.getFriendID());
									}
								} else if (object instanceof SendChatRoom) {

									SendChatRoom sendChatRoom = (SendChatRoom) object;
									if (sendChatRoom.getComment().equals("insertChatRoom")) {
										StringBuffer name = new StringBuffer();
										name.append(sendChatRoom.getUserDto().get(0).getId());
										for (int i = 1; i < sendChatRoom.getUserDto().size() - 1; i++) {
											name.append("," + sendChatRoom.getUserDto().get(i).getId());
										}
										String member;
										if (name.toString().length() > 7) {
											member = name.toString().substring(0, 7) + "...";
										} else {
											member = name.toString();
										}
										int LAST_INSERT_ID = chatRoomDao.chatRoomInsert(member);
										for (int i = 0; i < sendChatRoom.getUserDto().size(); i++) {
											chatRoomDao.chatRoomMemberInsert(LAST_INSERT_ID,
													sendChatRoom.getUserDto().get(i).getId());
										}
									}
								} else if (object instanceof SendChat) {
									SendChat sendChat = (SendChat) object;
							
										int room_id = sendChat.getRoom_id();
										ArrayList<User> members = chatRoomDao.getMyRoom(room_id).getMembers();
										Iterator<Entry<String, ObjectOutputStream>> it = nameToSocketList.entrySet()
												.iterator();
										while (it.hasNext()) {
											Entry<String, ObjectOutputStream> entry = it.next();
											// if(entry.getValue() != null) {
											entry.getValue().writeObject(sendChat);
											entry.getValue().flush();
											// }
										}
										File file = null;
										FileWriter fw = null;
										BufferedWriter bw = null;
										try {
											String roomPath = "room/" + room_id + ".txt";
											file = new File(roomPath);
											fw = new FileWriter(file, true);
											bw = new BufferedWriter(fw);

											bw.write("<%=" + sendChat.getUser_id() + "%> : ");
											bw.write("<%=" + sendChat.getStory() + "%>");
											bw.newLine();
											bw.flush();
											fw.close();
											bw.close();
										} catch (Exception e) {
											// TODO: handle exception
										} finally {
											try {
												fw.close();
											} catch (IOException e) {
											}
											try {
												bw.close();
											} catch (IOException e) {
											}
										}
									

								} else if (object instanceof SendImage) {
									SendImage sendImage = (SendImage) object;
									BufferedImage bi = ImageIO.read(sendImage.getFile());

									int imageCount = userDao.userImage();
									if (sendImage.getExtensions().equals("jpg")
											|| sendImage.getExtensions().equals("JPG")) {
										String name = "images/TAKL" + imageCount++ + ".jpg";
										File imageJpg = new File(name);
										ImageIO.write(bi, "jpg", imageJpg);
										userDao.userPicUpdate(sendImage.getId(), name);
										ImageIcon icon = new ImageIcon(name);
										outToClient.writeObject(icon);
										outToClient.flush();

									} else if (sendImage.getExtensions().equals("png")
											|| sendImage.getExtensions().equals("PNG")) {
										String name = "images/TAKL" + imageCount++ + ".gif";
										File imageGif = new File("images/TAKL" + imageCount++ + ".gif");
										ImageIO.write(bi, "gif", imageGif);
										userDao.userPicUpdate(sendImage.getId(), name);
										ImageIcon icon = new ImageIcon(name);
										outToClient.writeObject(icon);
										outToClient.flush();
									}
									userDao.imageCountUpdate(imageCount);
								} else if (object instanceof Integer) {
									int room_id = (int) object;
									try {
										String roomPath = "room/" + room_id + ".txt";
										File file = new File(roomPath);
										FileReader fileReader = new FileReader(file);
										BufferedReader bufferedReader = new BufferedReader(fileReader);
										String line = "";
										ArrayList<SendChat> chattingLoad = new ArrayList<>();
										while ((line = bufferedReader.readLine()) != null) {
											Pattern pattern = Pattern.compile("<%=(.*?)%>");
											Matcher matcher = pattern.matcher(line);
											ArrayList<String> arrayList = new ArrayList<>();
											while (matcher.find()) { // 일치하는 게 있다면
												arrayList.add(matcher.group(1));
												if (matcher.group(1) == null)
													break;
											}
											
											String PICTURE = userDao.selectImage(arrayList.get(0));
											SendChat sendChat = new SendChat(room_id, arrayList.get(0), arrayList.get(1), new ImageIcon(PICTURE));
											chattingLoad.add(sendChat);
											
											
										}
										
										bufferedReader.close();
										outToClient.writeObject(chattingLoad);
										outToClient.flush();

									} catch (Exception e) {
										// TODO: handle exception
									}

								}

							}
						} catch (Exception e) {
							e.printStackTrace();
							nameToSocketList.remove(nickname);
						} finally {
							try {
								if (inFromClient != null) {
									inFromClient.close();
								}
							} catch (IOException ioe) {
							}
							try {
								if (outToClient != null) {
									outToClient.close();
								}
							} catch (IOException ioe) {
							}
						}
					});
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (!server.isClosed()) {
					server.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
	}

}