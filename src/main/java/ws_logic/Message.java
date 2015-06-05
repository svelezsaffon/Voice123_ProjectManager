package ws_logic;

public class Message {
	
	private RespondMessages status;
	
	private String message;
	
	private Project project;

	public RespondMessages getStatus() {
		return status;
	}

	public void setStatus(RespondMessages status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	

}
