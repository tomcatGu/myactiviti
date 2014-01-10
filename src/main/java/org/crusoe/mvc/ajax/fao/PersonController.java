package org.crusoe.mvc.ajax.fao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.crusoe.dto.RoleDTO;
import org.crusoe.dto.UserDTO;
import org.crusoe.dto.foreignAffairsOffice.PersonDTO;
import org.crusoe.entity.Role;
import org.crusoe.entity.User;
import org.crusoe.entity.foreignAffairsOffice.Person;
import org.crusoe.service.foreignAffairsOffice.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private PersonService personService;

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		PersonDTO personDTO = new PersonDTO();
		personDTO.getDomesticeRelatives().add(new PersonDTO());
		PersonDTO p=new PersonDTO();
		p.setAppellation("father");
		personDTO.getAbroadRelatives().add(p);
		model.addAttribute("person", personDTO);
		
		p=new PersonDTO();
		p.setAppellation("mother");
		personDTO.getAbroadRelatives().add(p);
		model.addAttribute("person", personDTO);
		return "person/createForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(
			@Valid @RequestBody PersonDTO personDTO,
			RedirectAttributes redirectAttributes) {
		Person person = new Person();
		BeanUtils.copyProperties(personDTO, person);
		person.setAbroadRelatives(new ArrayList());
		for(PersonDTO p : personDTO.getAbroadRelatives()){
			Person par=new Person();
			BeanUtils.copyProperties(p, par);
			person.getAbroadRelatives().add(par);
			
		}
		
		
		personService.save(person);

		return Collections.singletonMap("id", person.getId());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody
	Map<String, ? extends Object> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException error) {

		List<ObjectError> fes = error.getBindingResult().getAllErrors();
		Map<String, String> errs = new HashMap<String, String>();
		errs.put("err", "true");
		for (ObjectError fe : fes) {
			// System.out.println("FieldError : " + fe.getField() + " : "
			// + fe.getDefaultMessage());
			errs.put(((FieldError) fe).getField(), fe.getDefaultMessage());
		}
		return errs;

	}
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
