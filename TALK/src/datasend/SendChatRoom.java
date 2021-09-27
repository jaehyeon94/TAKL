package datasend;

import java.io.Serializable;
import java.util.ArrayList;


public class SendChatRoom implements Serializable {

	
	private int room_Id;
	private ArrayList<SendUser> userDto;
	private String room_Name;
	private String comment;
	
	static final long serialVersionUID = 42L;
	//private String path;
	
	public int getRoom_Id() {
		return room_Id;
	}
	
	
	public SendChatRoom(String comment, ArrayList<SendUser> userDto) {
		super();
		this.userDto = userDto;
		this.comment = comment;
	}


	public SendChatRoom(int room_Id, String room_Name, ArrayList<SendUser> userDto) {
		super();
		this.room_Id = room_Id;
		this.userDto = userDto;
		this.room_Name = room_Name;
	}
	
	public void setRoom_Id(int room_Id) {
		this.room_Id = room_Id;
	}
	public String getRoom_Name() {
		return room_Name;
	}
	public void setRoom_Name(String room_Name) {
		this.room_Name = room_Name;
	}
	public ArrayList<SendUser> getUserDto() {
		return userDto;
	}
	public void setUserDto(ArrayList<SendUser> userDto) {
		this.userDto = userDto;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
