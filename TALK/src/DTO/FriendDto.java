package DTO;

import javax.swing.ImageIcon;

public class FriendDto extends UserDto {


	private String friendId;
	
	public FriendDto(String myId, String friendId) {
		// TODO Auto-generated constructor stub
		super(myId);
		this.friendId = friendId;
	}
	
	public FriendDto(String id, String friendId, String status, String picture, ImageIcon icon) {
		super(id, status, picture,icon);
		this.friendId = friendId;
		// TODO Auto-generated constructor stub
	}

	
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	
	
	
	
	
}
