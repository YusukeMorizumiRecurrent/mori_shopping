package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
	
	@RequestMapping(value = "/showItem", method=RequestMethod.GET)
	public String getItem() {
		return "showItem";
	}
	
	@RequestMapping(value = "/showItem", method=RequestMethod.POST)
	public ModelAndView showItem(ModelAndView mv) {
		
		
		return mv;
	}
	
	

}
