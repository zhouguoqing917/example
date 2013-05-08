package org.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello.do")
public class Hello {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView hello() {
		System.out.println("hello world");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello world");
		return mv;
	}
}
