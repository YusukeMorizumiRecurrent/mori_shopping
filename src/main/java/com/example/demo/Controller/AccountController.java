package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Items;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	ItemRepository itemRepository;

	@RequestMapping("/")
	public String login() {
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getlogin() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam(name = "name") String name, @RequestParam(name = "pass") String pass,
			ModelAndView mv) {

		// 未入力チェック
		if (isNull(name) || isNull(pass)) {
			// 未入力の場合はログイン画面に戻る
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("login");
		} else {
			Users users = usersRepository.findByNameAndPass(name, pass);

			if (users == null) {
				mv.addObject("message", "名前またはパスワードが違います");
				mv.setViewName("login");
			} else {

				session.setAttribute("userInfo", users);
				session.setAttribute("categories", categoryRepository.findAll());

				List<Items> itemList = itemRepository.findAll();
				mv.addObject("items", itemList);
				mv.addObject("categories", categoryRepository.findAll());

				mv.addObject("users", users);
				mv.setViewName("showItem");
			}
		}
		return mv;
	}

	@RequestMapping(value = "/newAccount", method = RequestMethod.GET)
	public String getNewAccount() {
		return "newAccount";
	}

	@RequestMapping(value = "/newAccount", method = RequestMethod.POST)
	public ModelAndView newAccount(@RequestParam("name") String name, @RequestParam("address") String address,
			@RequestParam("tel") String tel, @RequestParam("email") String email, @RequestParam("pass") String pass,
			ModelAndView mv) {

		// 未入力チェック
		if (isNull(name) || isNull(address) || isNull(tel) || isNull(email) || isNull(pass)) {
			// 未入力の場合はログイン画面に戻る
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("newAccount");
		} else {
			// 同一の名前を持つデータがある場合、登録しない

			// ユーザー情報からデータ取得
			List<Users> userlist = usersRepository.findAllByName(name);
			// 取得できた場合登録しない
			if (userlist.size() > 0) {
				mv.addObject("message", "すでに登録してあります");
				mv.setViewName("newAccount");
			}
			// 大丈夫な場合は登録する
			else {
				Users users = new Users(name, address, tel, email, pass);
				usersRepository.saveAndFlush(users);
				mv.addObject("users", usersRepository.findAll());
				mv.setViewName("login");
			}

		}

		return mv;
	}

	/**
	 * ユーザ情報の確認
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userInfo")
	public ModelAndView showUser(ModelAndView mv) {

		mv.addObject("users", usersRepository.findAll());
		mv.setViewName("userInfo");
		return mv;
	}

	@RequestMapping(value = "/userInfo/{code}/edit")
	public ModelAndView editUser(@PathVariable("code") int code, ModelAndView mv) {

		Users users = usersRepository.findById(code).get();

		mv.addObject("users", users);
		mv.setViewName("editUser");
		return mv;
	}

	// http://localhost:8080/userInfo/1
	@RequestMapping(value = "/editUser/{code}", method = RequestMethod.POST)
	public ModelAndView editUser(@PathVariable(name = "code") Integer code,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "pass", defaultValue = "") String pass, ModelAndView mv) {

		if (isNull(name) || isNull(address) || isNull(tel) || isNull(email) || isNull(pass)) {

			Users users = usersRepository.findById(code).get();

			mv.addObject("users", users);
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("editUser");
		} else {
			// 同一の名前を持つデータがある場合、登録しない

			// ユーザー情報からデータ取得
			List<Users> userlist = usersRepository.findAllByNameAndCodeNot(name, code);
			// 取得できた場合登録しない
			if (userlist.size() > 0) {
				Users users = usersRepository.findById(code).get();

				mv.addObject("users", users);
				mv.addObject("message", "すでに登録してあります");
				mv.setViewName("editUser");
			}
			// 大丈夫な場合は登録する
			else {

				Users user = usersRepository.findById(code).get();

				user.setName(name);
				user.setAddress(address);
				user.setTel(tel);
				user.setEmail(email);
				user.setPass(pass);

				usersRepository.saveAndFlush(user);
				session.setAttribute("userInfo", user);

				mv.addObject("users", usersRepository.findAll());
				mv.setViewName("userInfo");
			}
		}
		return mv;

	}

	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return login();
	}

	public boolean isNull(String text) {
		return (text == null || text.length() == 0);
	}

	public boolean isNull(Integer num) {
		return (num == null || num <= 0);
	}

}
