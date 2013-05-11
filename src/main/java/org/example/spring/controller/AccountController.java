package org.example.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.servlet.ModelAndView;

@Controller 
public class AccountController {

	@RequestMapping(value="account.do",method=RequestMethod.GET)
	public ModelAndView account(HttpServletRequest request, HttpServletResponse response){ 
		System.out.println("account.do");		 
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account");		
		return mv;
		 
	}
}
