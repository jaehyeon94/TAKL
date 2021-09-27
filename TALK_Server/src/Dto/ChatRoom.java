package Dto;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private int room_id;
    private String room_name;
    private ArrayList<User> members;
    private String path;

    public ChatRoom(int room_id, String room_name, ArrayList<User> members, String path) {
        super();
        this.room_id = room_id;
        this.room_name = room_name;
        this.members = members;
        this.path = path;
    }
    
   
    public ChatRoom(int room_id) {
		super();
		this.room_id = room_id;
	}

	public ChatRoom(int room_id, String room_name) {
        super();
        this.room_id = room_id;
        this.room_name = room_name;
    }

    public ChatRoom(ArrayList<User> members, String path) {
        super();
        this.members = members;
        this.path = path;
    }

    public ChatRoom(String path, int room_id) {
        super();
        this.path = path;
        this.room_id = room_id;
    }
    public ChatRoom() {}
 
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

  
    public ArrayList<User> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<User> members) {
		this.members = members;
	}

	public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

     

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + room_id;
        result = prime * result + ((room_name == null) ? 0 : room_name.hashCode());
        result = prime * result + ((members == null) ? 0 : members.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ChatRoom other = (ChatRoom) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (room_id != other.room_id)
            return false;
        if (room_name == null) {
            if (other.room_name != null)
                return false;
        } else if (!room_name.equals(other.room_name))
            return false;
        if (members == null) {
            if (other.members != null)
                return false;
        } else if (!members.equals(other.members))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ChatRoom [room_id=" + room_id + ", room_name=" + room_name + ", user_id=" + members + ", path=" + path
                + "]";
    }


}
