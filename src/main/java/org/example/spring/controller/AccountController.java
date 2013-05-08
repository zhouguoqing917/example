package org.example.spring.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.example.spring.service.AccountService;
import org.springframework.web.servlet.ModelAndView;

@Controller 
public class AccountController {

	@Resource
	private AccountService accountService;

	@RequestMapping(value="account.do",method=RequestMethod.GET)
	public ModelAndView account(HttpServletRequest request, HttpServletResponse response) throws Exception { 
		System.out.println("account.do");
		ModelAndView mv = new ModelAndView("account","message","login ok!");	 
		return mv;
		 
	}
}
