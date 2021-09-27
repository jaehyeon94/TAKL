package datasend;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class SendFriend implements Serializable {

    private final String id;
	private String friendID;
	private String comment;
	private String status;
	private String picture;
	private ImageIcon picture_Icon;
	
	static final long serialVersionUID = 42L;

	
	 public SendFriend(String id) {
	        this.id = id;
	    }
	 
	 public SendFriend(String comment, String id, String friendID) {
			super();
			this.id = id;
			this.friendID = friendID;
			this.comment = comment;
		}
	 
	public SendFriend(String id, String friendID) {
		super();
		this.id = id;
		this.friendID = friendID;
	}

	public SendFriend(String id, String friendID, String status, String picture, ImageIcon icon) {
		super();
		this.id = id;
		this.friendID = friendID;
		this.status = status;
		this.picture = picture;
		this.picture_Icon = icon;
	}

	



	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getFriendID() {
		return friendID;
	}

	public void setFriendID(String friendID) {
		this.friendID = friendID;
	}

	public String getId() {
		return id;
	}

	public ImageIcon getPicture_Icon() {
		return picture_Icon;
	}

	public void setPicture_Icon(ImageIcon picture_Icon) {
		this.picture_Icon = picture_Icon;
	}
}
