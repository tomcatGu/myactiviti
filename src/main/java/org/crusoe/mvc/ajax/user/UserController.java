package org.crusoe.mvc.ajax.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.engine.impl.Direction;
import org.crusoe.dto.ResourceDTO;
import org.crusoe.dto.RoleDTO;
import org.crusoe.dto.UserDTO;
import org.crusoe.entity.Resource;

import org.crusoe.entity.Role;

import org.crusoe.entity.User;
import org.crusoe.service.AccountService;
import org.crusoe.service.RoleService;
import org.crusoe.util.JSONUtil;
import org.crusoe.util.persisterce.SearchFilter;
import org.crusoe.util.persisterce.SearchFilter.Operator;
import org.crusoe.web.datatables.DataTableReturnArray;
import org.crusoe.web.datatables.DataTableReturnObject;
import org.crusoe.web.datatables.JSONParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /user/ Create page : GET /user/create Create action : POST
 * /user/create Update page : GET /user/update/{id} Update action : POST
 * /user/update Delete action : DELETE /user/{id} GET action : GET /user/{id}
 * 
 * @author gwx
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	private static final int PAGE_SIZE = 3;
	@Autowired
	protected AccountService accountService;
	@Autowired
	protected RoleService roleService;

	@RequestMapping(value = "index")
	public String list(
	// @RequestParam(value = "pageNumber") List<SearchFilter> filtes,
	// @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
	// @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
	// @RequestParam(value = "sortDirection", defaultValue = "DESC") String
	// sortDirection,
	// @RequestParam(value = "sortBy") String sortBy, Model model,
			ServletRequest request) {
		// PageRequest pageRequest = new PageRequest(pageNumber, pageSize,
		// "DESC".equals(sortDirection.toUpperCase()) ? Sort.Direction.DESC
		// : Sort.Direction.ASC, sortBy);
		// accountService.searchUser(filtes,pageRequest);
		return "user/index";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		UserDTO userDTO = new UserDTO();
		// userDTO.setRoles(new HashSet());
		List<Role> roles = roleService.getAllRoles();
		Iterator iter = roles.iterator();
		while (iter.hasNext()) {
			RoleDTO roleDTO = new RoleDTO();
			BeanUtils.copyProperties((Role) iter.next(), roleDTO);
			userDTO.getRoles().add(roleDTO);

		}

		model.addAttribute("user", userDTO);
		// model.addAttribute("allRoles",))
		model.addAttribute("action", "create");
		return "user/createForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(@Valid @RequestBody UserDTO newUser,
			RedirectAttributes redirectAttributes) {

		User user = new User();
		user.setLoginName(newUser.getLoginName());
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());

		// BeanUtils.copyProperties(newUser, user);
		if (newUser.getRoles() != null) {
			Iterator iter = newUser.getRoles().iterator();
			while (iter.hasNext()) {
				RoleDTO roleDTO = (RoleDTO) iter.next();
				Role role = roleService.load(roleDTO.getId());

				user.getRoles().add(role);

			}

		}

		user.setStatus("enabled");
		try {
			accountService.saveUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return Collections.singletonMap("id", user.getId());
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

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		// model.addAttribute("task", taskService.getTask(id));
		model.addAttribute("action", "update");

		HashSet<RoleDTO> allRoles = new HashSet<RoleDTO>();
		List<Role> roles = roleService.getAllRoles();
		Iterator iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			RoleDTO roleDTO = new RoleDTO();
			BeanUtils.copyProperties(role, roleDTO); // resource.getRoles().add(role);
			allRoles.add(roleDTO); // allRoles.add(role);

		}

		model.addAttribute("allRoles", allRoles);

		User user = accountService.getUser(id);
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		userDTO.setRoles(new ArrayList<RoleDTO>());
		iter = user.getRoles().iterator();
		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			RoleDTO roleDTO = new RoleDTO();
			BeanUtils.copyProperties(role, roleDTO); // resource.getRoles().add(role);

			userDTO.getRoles().add(roleDTO);

		}

		model.addAttribute("user", userDTO);

		return "user/updateForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @RequestBody UserDTO newUser,
			RedirectAttributes redirectAttributes) {
		User user = new User();
		user.setId(newUser.getId());
		user.setLoginName(newUser.getLoginName());
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		user.setPassword(newUser.getPassword());

		// BeanUtils.copyProperties(newUser, user);
		if (newUser.getRoles() != null) {
			Iterator iter = newUser.getRoles().iterator();
			while (iter.hasNext()) {
				RoleDTO roleDTO = (RoleDTO) iter.next();
				Role role = roleService.load(roleDTO.getId());

				user.getRoles().add(role);

			}

		}

		user.setStatus("enabled");
		try {
			accountService.saveUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("message", "更新用户成功");

		return "redirect:/task/";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	Map<String, ? extends Object> delete(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) throws Exception {
		// taskService.deleteTask(id);
		User user = accountService.getUser(id);
		accountService.deleteUser(user);
		// redirectAttributes.addFlashAttribute("message", "删除用户成功");

		Map<String, String> msgs = new HashMap<String, String>();

		msgs.put("msg", "删除用户成功");
		return msgs;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String get(@PathVariable("id") Long id,
			RedirectAttributes redirectAttributes) {
		// taskService.deleteTask(id);
		redirectAttributes.addFlashAttribute("message", "获取用户成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.POST)
	public @ResponseBody
	DataTableReturnObject getUsers(@RequestBody JSONParam[] params) {

		// System.out.println(order);
		Map<String, Object> rets = new ConcurrentHashMap<String, Object>();

		// convertToMap定义于父类，将参数数组中的所有元素加入一个HashMap
		HashMap<String, Object> paramMap = JSONUtil.convertToMap(params);
		int sEcho = Integer.valueOf(paramMap.get("sEcho").toString());
		// String customerName = paramMap.get("customerName");
		int start = Integer.parseInt(paramMap.get("iDisplayStart").toString());
		int length = Integer
				.parseInt(paramMap.get("iDisplayLength").toString());

		// customerService.search返回的第一个元素是满足查询条件的记录总数，后面的是
		// 页面当前页需要显示的记录数据

		String iSortingCols = paramMap.get("iSortingCols").toString();

		int sortingCols = Integer.parseInt(iSortingCols);
		Sort.Order[] orders = new Sort.Order[sortingCols];
		for (int i = 0; i < sortingCols; i++) {
			Object sortCol = paramMap.get("iSortCol_" + String.valueOf(i));
			String sortName = paramMap.get("mDataProp_" + sortCol.toString())
					.toString();
			String sortDirection = paramMap
					.get("sSortDir_" + String.valueOf(i)).toString();
			orders[i] = new Sort.Order(
					"asc".equals(sortDirection) ? Sort.Direction.ASC
							: Sort.Direction.DESC, sortName);

		}

		PageRequest pageRequest = new PageRequest(start / length, length,
				new Sort(orders));
		// new PageRequest(1,15,new Sort() );
		Page<User> users = accountService.searchUser(paramMap, pageRequest);

		// 将查询结果转换为一维数组
		Object[] data = new Object[users.getNumberOfElements()];
		Iterator iter = users.iterator();
		int i = 0;
		while (iter.hasNext()) {
			User user = (User) iter.next();
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			userDTO.setRoles(new ArrayList<RoleDTO>());
			Iterator roleIter = user.getRoles().iterator();
			while (roleIter.hasNext()) {
				RoleDTO roleDTO = new RoleDTO();
				Role role = (Role) roleIter.next();
				BeanUtils.copyProperties(role, roleDTO);
				userDTO.getRoles().add(roleDTO);

			}

			// userDTO.getRoles().clear();
			data[i++] = userDTO;

		}

		return new DataTableReturnObject(users.getTotalElements(),
				users.getTotalElements(), sEcho, data);
	}

	/**
	 * 使用@ModelAttribute, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
	 */
	@ModelAttribute("preloadUser")
	public UserDTO getUser(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			// return taskService.getTask(id);
		}
		return null;
	}
}
