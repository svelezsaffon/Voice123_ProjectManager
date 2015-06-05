package ws_controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ws_db_logic.DatabaseManager;
import ws_logic.Message;


@RestController
public class ProjectController {

	private DatabaseManager manager;
	
	@RequestMapping("/project/create")
	public Message  create(@RequestParam(value="name", defaultValue="New_Project") String name,@RequestParam(value="priority", defaultValue="0") String priority){
		
		manager=DatabaseManager.getInstance();
		
		return manager.createProject(name, priority);
	}
	
	
	
	@RequestMapping("/project/read")
	public Message read(@RequestParam(value="name") String name){
		
		manager=DatabaseManager.getInstance();
		
		return manager.readProject(name);		
		
	}

	@RequestMapping("/project/update")
	public Message update(@RequestParam(value="name") String name,@RequestParam(value="priority", defaultValue="0") String priority){
		
		manager=DatabaseManager.getInstance();
		
		return manager.updateProject(name,Integer.parseInt(priority));		
		
	}
	
	@RequestMapping("/project/delete")
	public Message delete(@RequestParam(value="name") String name){
        
		manager=DatabaseManager.getInstance();
		
		return manager.deleteProject(name);		
		
	}	
	
	
}
