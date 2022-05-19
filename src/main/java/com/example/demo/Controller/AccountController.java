package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Users;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.UsersRepository;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UsersRepository usersRepository;

	@RequestMapping("/")
	public String login() {
		session.invalidate();
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
		}
		Users users = new Users(name,address,tel,email,pass);
		usersRepository.saveAndFlush(users);

		mv.addObject("users", usersRepository.findAll());
			
		mv.setViewName("login");
		return mv;
	}

	@RequestMapping("/logout")
	public String logout() {

		return login();
	}

	public boolean isNull(String text) {
		return (text == null || text.length() == 0);
	}

}
