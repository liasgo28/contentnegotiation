package br.com.diego.contentnegotiation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.diego.contentnegotiation.repository.PersonRepository;

@Controller
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;

	@RequestMapping( method=RequestMethod.GET)
	public ModelAndView getAllPersons(){		
		ModelAndView modelAndView = new ModelAndView("persons/index");
		modelAndView.addObject("persons", personRepository.getAllPersons());
		return modelAndView;		
		
		
		
	}
}
