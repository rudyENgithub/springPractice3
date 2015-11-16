package com.spring_cookbook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring_cookbook.dao.UserDAO;
import com.spring_cookbook.domain.Users;
import com.spring_cookbook.domain.UsersJdbc;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping("user_list")
	public void userList() {
		System.out.println("UserController.userList()");
                UsersJdbc usuario1 =  new UsersJdbc();
                usuario1.setFirstName("Rudy Sorto");
                usuario1.setAge(27);
		userDAO.add(usuario1);
	}
        
        @RequestMapping("buscarPorId")
	public void buscarPorId() {
		UsersJdbc user = userDAO.findById(11L);
		System.out.println(user.getFirstName());
		System.out.println(user.getAge());
	}
        
        
        @RequestMapping("buscarTodos")
	public void buscarTodos() {
		List<UsersJdbc> userList = userDAO.findAll();
		for (UsersJdbc user : userList) {
			System.out.println(user.getFirstName());
			System.out.println(user.getAge());			
		}
	}
        
        @RequestMapping("buscarTodosDepen")
	public void buscarTodosDepen() {
		List<UsersJdbc> userList = userDAO.findAllDepen();
		for (UsersJdbc user : userList) {
			System.out.println(user.getFirstName());
			System.out.println(user.getAge());
                       
		}
	}
        
        @RequestMapping("updateUser")
	public void updateUser() {
		
                UsersJdbc usuario1 =  new UsersJdbc();
                usuario1.setFirstName("Rudyto Sorto Ayala");
                usuario1.setAge(28);
                 usuario1.setId(11L);
		userDAO.update(usuario1);
	}
        
         @RequestMapping("deleteUser")
	public void deleteUser() {
		
                UsersJdbc usuario1 =  new UsersJdbc();
                 usuario1.setId(18L);
		userDAO.delete(usuario1);
	}
         
         
         /*HIBERNATE*/
         @RequestMapping("user_add_hb")
	@ResponseBody
	public String user_add_hb() {
		Users user = new Users();
		user.setFirstName("Merlin");
		user.setAge(372);
		
		userDAO.addHb(user);
		
		return "User was successfully added";
	}
         /*HIBERNATE*/
         
         @RequestMapping("/user_list5")
	public void userList5() {
	}

	@RequestMapping("addUser5")
	public String addUser5() {
		return "addUser";
	}

	@RequestMapping(value="addUser5", method=RequestMethod.POST)
	public String addUserSubmit(HttpServletRequest request) {
		String firstName = request.getParameter("firstName");
		System.out.println(firstName);
		return "redirect:/user_list";
	}
         
        

}
