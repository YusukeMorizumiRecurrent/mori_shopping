package com.example.demo.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Ordered;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderedRepository;

@Controller
public class OrderedController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OrderedRepository orderedRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@RequestMapping("/order")
	public ModelAndView getCart(ModelAndView mv) {
		
		Cart cart = getCart();
		
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());

		mv.setViewName("confirmCart");
	return mv;
	}
	
	@RequestMapping(value = "/order/doOrder", method=RequestMethod.POST)
	public ModelAndView doOrder(ModelAndView mv) {
		
		Cart cart= getCart();
		
		Users user = (Users)session.getAttribute("userInfo");

		//オーダー情報の登録：オーダーへの登録
		Ordered order = new Ordered(null,user.getCode(), new Date(), cart.getTotal(),0);
		int order_code = orderedRepository.saveAndFlush(order).getCode();
		//オーダー詳細情報の登録: orderDetailへの登録
		//カートのアイテム一覧を登録
		for(Items item : cart.getItems().values()) {
			OrderDetail orderdetail = new OrderDetail(order_code, item.getCode(), 0);
			orderDetailRepository.saveAndFlush(orderdetail);
		}
		mv.setViewName("ordered");
		
		return mv;
	}
	
	

	public boolean isNull(String text) {
		// text == null ||text.length()==0 => 0 true
		return (text == null || text.length() == 0);
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



