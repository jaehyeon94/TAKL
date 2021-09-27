package datasend;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class SendUser implements Serializable {
    private final String id;
    private String password;
    private String comment;
    private String status;
    private String picture;
    private ImageIcon picture_Icon;
    
    static final long serialVersionUID = 42L;

    public SendUser() {
		this.id = null;
    }
    public SendUser(String id) {
        this.id = id;
    }

    public SendUser(String id, String password) {
        this.id = id;
        this.password = password;
    }
    
    public SendUser(String comment, String id, String picture) {
  		super();
  		this.comment = comment;
  		this.id = id;
  		this.picture = picture;
  	}
    public SendUser(String id, String status, String picture,ImageIcon icon) {
		super();
		this.id = id;
		this.status = status;
		this.picture = picture;
		this.picture_Icon = icon;
	}


	public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
	public ImageIcon getPicture_Icon() {
		return picture_Icon;
	}

	public void setPicture_Icon(ImageIcon picture_Icon) {
		this.picture_Icon = picture_Icon;
	}

	@Override
    public String toString() {
        return "User{" +
                "name='" + id + '\'' +
                '}';
    }

}