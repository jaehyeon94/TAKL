package datasend;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class SendChat implements Serializable {
    private int room_id;
    private String user_id;
    private String story;
    private String picture;
    private ImageIcon icon;
    private String comment;
    
    static final long serialVersionUID = 42L;

    public SendChat(int room_id, String user_id, String story, ImageIcon icon) {
        this.room_id = room_id;
        this.user_id = user_id;
        this.story = story;
        this.icon = icon;
    }

    public int getRoom_id() {
        return room_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStory() {
        return story;
    }

	public String getPicture() {
		return picture;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
    
}