package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	HttpSession session;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@RequestMapping("/cart/add/{code}")
	public ModelAndView addCart(@PathVariable("code") int code,
			@RequestParam(name = "stock", defaultValue="1") Integer stock,
			ModelAndView mv) {

		// Cartの情報を取得
		Cart cart = getCart();
		// 商品コードをもとにアイテム情報を取得
		Items item = itemRepository.getById(code);
		// カート情報にアイテム情報を追加
		cart.addCart(item, stock);

		// ページに表示したい情報を設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
//		mv.addObject("categories", categoryRepository.findAll());

		// カートの中身を表示する
		mv.setViewName("cart");
		return mv;
	}

	@RequestMapping("/cart/delete/{code}")
	public ModelAndView deleteCart(@PathVariable("code") int code,

			ModelAndView mv) {

		// カート情報を取得
		Cart cart = getCart();
		// カートの中からcodeが一致するアイテムを消去
		cart.deleteCart(code);
		// ページ表示に必要なデータを設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
//		mv.addObject("categories", categoryRepository.findAll());
		// ページ遷移
		mv.setViewName("cart");

		return mv;
	}

	public Cart getCart() {

		// セッションのカート情報を取得
		Cart cart = (Cart) session.getAttribute("cart");

		// カート情報がない場合、カート情報の初期処理
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		return cart;
	}

}
