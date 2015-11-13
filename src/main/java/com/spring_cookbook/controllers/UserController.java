package com.spring_cookbook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring_cookbook.dao.UserDAO;
import com.spring_cookbook.domain.Users;
import java.util.List;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping("user_list")
	public void userList() {
		System.out.println("UserController.userList()");
                Users usuario1 =  new Users();
                usuario1.setFirstName("Rudy Sorto");
                usuario1.setAge(27);
		userDAO.add(usuario1);
	}
        
        @RequestMapping("buscarPorId")
	public void buscarPorId() {
		Users user = userDAO.findById(11L);
		System.out.println(user.getFirstName());
		System.out.println(user.getAge());
	}
        
        
        @RequestMapping("buscarTodos")
	public void buscarTodos() {
		List<Users> userList = userDAO.findAll();
		for (Users user : userList) {
			System.out.println(user.getFirstName());
			System.out.println(user.getAge());			
		}
	}
        
        @RequestMapping("buscarTodosDepen")
	public void buscarTodosDepen() {
		List<Users> userList = userDAO.findAllDepen();
		for (Users user : userList) {
			System.out.println(user.getFirstName());
			System.out.println(user.getAge());
                       
		}
	}

}
