package datasend;

import java.io.File;
import java.io.Serializable;

public class SendImage implements Serializable {
	private String id;
	private String imageName;
	private File file;
	private String extensions;
	static final long serialVersionUID = 42L;

	public SendImage(String id, String imageName, File file, String extensions) {
		super();
		this.id = id;
		this.imageName = imageName;
		this.file = file;
		this.extensions = extensions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getExtensions() {
		return extensions;
	}

	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}

}

