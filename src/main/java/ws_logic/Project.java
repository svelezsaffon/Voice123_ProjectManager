package ws_logic;

public class Project implements IProject {

	private String name;
	
	private int priority;
	
	public Project(){
		
	}
	
	public Project(String name, int priority) {
		super();
		this.name = name;
		this.priority = priority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}
