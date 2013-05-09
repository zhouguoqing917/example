package org.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Hello {
	
	@RequestMapping(value="/hello.do",method = RequestMethod.GET)
	public ModelAndView hello() {
		System.out.println("hello.do");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("hello");
		System.out.println(mv.getView().PATH_VARIABLES);
		return mv;
	}

	@ResponseBody
    @RequestMapping("/")
    public String index() {
		System.out.println("index");
        return "This is a rest demo";
    }

}
