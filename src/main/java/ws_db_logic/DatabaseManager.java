package ws_db_logic;

import ws_logic.Message;
import ws_logic.Project;
import ws_logic.RespondMessages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class DatabaseManager {
	
	private static DatabaseManager instance=null;
	
	private static Map<String, Project> projects;
	
	
	
	private Connection connection = null;
	
	private boolean created;
	
	private DatabaseManager(){
		
		this.created= false;
		try {
            
			 String myDriver = "org.gjt.mm.mysql.Driver";
		     String myUrl = "jdbc:mysql://localhost/test";
		     
		     Class.forName(myDriver);
		      
		     connection = DriverManager.getConnection(myUrl, "root", "");
            
		     

            System.out.println("Connected");
            
            this.created=true;
            
        } catch (SQLException | ClassNotFoundException e) {
        	
            System.out.println("Connection Failed!");
            
            projects=new HashMap<String, Project>();
        }
		
		
	}
	
	
	public ResultSet excecuteQuery(String query) throws SQLException {

        Statement statement = this.connection.createStatement();

        ResultSet result = statement.executeQuery(query);
        
        return result;
        
    }
	
	
	public static DatabaseManager getInstance(){
		if(instance==null){
			instance=new DatabaseManager();
		}
		
		return instance;
	}
	
	
	public Message createProject(String name,String priority){
		Message ret=new Message();
		
		if(this.created){

			String query = " INSERT INTO project (name,priority) values (?, ?)";
			PreparedStatement ps;
			
			try {
				ps = connection.prepareStatement(query);
			
				ps.setString(1, name);
				ps.setInt(2, Integer.parseInt(priority));
				
				boolean cre=ps.execute();
				
				if(cre){
					
					ret.setMessage("Project created");			
				
					ret.setStatus(RespondMessages.Created);
				
					ret.setProject(new Project(name, Integer.parseInt(priority)));
				}
				ps.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ret.setMessage("Internal error");
				ret.setStatus(RespondMessages.Internal_Server_Error);
				e.printStackTrace();
			}
			
		}else{
			
			ret.setMessage("Internal error, DB connection Failed, using map");
			ret.setStatus(RespondMessages.Internal_Server_Error);
			Project neu=new Project(name, Integer.parseInt(priority));
			ret.setProject(neu);
			projects.put(name, neu);
			
		}
		
		
		return ret;	
	}
	
	
	public Message readProject(String Name){
		
		Message ret=new Message();
		
		if(this.created){
		
		String sql = "SELECT * FROM project WHERE name = ?";
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(sql);
		
			ps.setString(1, Name);
		
			ResultSet tables = ps.executeQuery();
			
			
			while(tables.next()){  
				
				ret.setMessage("Project found");
				
				ret.setStatus(RespondMessages.OK);
				
				ret.setProject(new Project(tables.getString("name"),tables.getInt("priority")));                           
            }
			
			
			
			ps.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ret.setMessage("Internal error");
			ret.setStatus(RespondMessages.Internal_Server_Error);
			e.printStackTrace();
		}
		
		}else{
			ret.setMessage("Internal error,DB connection failed, using map");
			ret.setStatus(RespondMessages.Internal_Server_Error);
			
			ret.setProject(this.projects.get(Name));
						
		}
		return ret;		
	}
	
	
	public Message updateProject(String name,int priority){
		Message ret=new Message();
		
		if(this.created){
		
		String sql = "UPDATE project SET name = ?,priority=?";
		PreparedStatement ps;
		
		try {
			ps = connection.prepareStatement(sql);
		
			ps.setString(1, name);
			
			ps.setInt(2, priority);
		
			ps.execute();
			
			ret.setMessage("Project Updated");			
			
			ret.setStatus(RespondMessages.updated);
			
			ret.setProject(new Project(name, priority));
			
			ps.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ret.setMessage("Internal error");
			ret.setStatus(RespondMessages.Internal_Server_Error);
			e.printStackTrace();
		}
		
		}else{
			
			ret.setMessage("Internal error,DB connection failed, using map");
			
			ret.setStatus(RespondMessages.Internal_Server_Error);
			
			Project neu=new Project(name, priority);
			
			ret.setProject(neu);
			
			projects.put(name, neu);
		}
		
		return ret;		
	}
	
	public Message deleteProject(String name){
		
		Message ret= new Message();
		
		if(this.created){
			
			String sql = "DELETE FROM project WHERE name = ?";
			PreparedStatement ps;
			
			try {
				ps = connection.prepareStatement(sql);
			
				ps.setString(1, name);
				
				ps.execute();
				
				ret.setMessage("Project Updated");			
				
				ret.setStatus(RespondMessages.updated);
								
				ps.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ret.setMessage("Internal error");
				ret.setStatus(RespondMessages.Internal_Server_Error);
				e.printStackTrace();
			}
			
			
		}else{
			
			ret.setMessage("Internal error,DB connection failed, using map");
			
			ret.setStatus(RespondMessages.Internal_Server_Error);
			
			projects.remove(name);			
		}
		
		return ret;		
	}
		
	
	
	
	

}
