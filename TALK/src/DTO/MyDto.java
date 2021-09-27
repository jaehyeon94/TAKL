package DTO;

import javax.swing.ImageIcon;

public class MyDto extends UserDto{

	private String password;
	
	
	public MyDto(String id,String password) {
			super(id);
			this.password = password;
		// TODO Auto-generated constructor stub
	}
	
	public MyDto(String id,String status, String picture, ImageIcon icon) {
		super(id, status, picture,icon);
		// TODO Auto-generated constructor stub
	}
	
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

	
}
