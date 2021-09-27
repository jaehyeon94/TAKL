package DTO;

import javax.swing.ImageIcon;

public class UserDto {

	private String id;
	private String status;
	private String picture;
	private ImageIcon icon;
	
	public UserDto(String id) {
		super();
		this.id = id;
	}
	
	public UserDto(String id, String status, String picture, ImageIcon icon) {
		super();
		this.id = id;
		this.status = status;
		this.picture = picture;
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	
	
	
}
