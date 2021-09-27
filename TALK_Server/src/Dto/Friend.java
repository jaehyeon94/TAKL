package Dto;

public class Friend {
	String myUserId;
	String otherUserId;
	
	

	public Friend(String myUserId, String otherUserId) {
		super();
		this.myUserId = myUserId;
		this.otherUserId = otherUserId;
	}

	public String getMyUserId() {
		return myUserId;
	}
	
	public void setMyUserId(String myUserId) {
		this.myUserId = myUserId;
	}
	
	public String getOtherUserId() {
		return otherUserId;
	}
	
	public void setOtherUserId(String otherUserId) {
		this.otherUserId = otherUserId;
	}
	
	@Override
	public String toString() {
		return "Friend [myUserId=" + myUserId + ", otherUserId=" + otherUserId + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((myUserId == null) ? 0 : myUserId.hashCode());
		result = prime * result + ((otherUserId == null) ? 0 : otherUserId.hashCode());
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
		Friend other = (Friend) obj;
		if (myUserId == null) {
			if (other.myUserId != null)
				return false;
		} else if (!myUserId.equals(other.myUserId))
			return false;
		if (otherUserId == null) {
			if (other.otherUserId != null)
				return false;
		} else if (!otherUserId.equals(other.otherUserId))
			return false;
		return true;
	}
	
	
}