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

@Controller
public class ItemController {

	@Autowired
	HttpSession session;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	CategoryRepository categoryRepository;


	@RequestMapping(value = "/showItem")
	public ModelAndView showItem(ModelAndView mv) {

		List<Items> itemList = itemRepository.findAll();
		mv.addObject("categories",categoryRepository.findAll());
		mv.addObject("items", itemList);
		mv.setViewName("showItem");

		return mv;
	}
	/**
	 * 検索機能
	 * @param code
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/search",method=RequestMethod.POST)
	public ModelAndView searchItem(
			@RequestParam("search") String search,
			ModelAndView mv) {

		List<Items> itemList = itemRepository.findByNameContains(search);
		mv.addObject("items", itemList);
		mv.setViewName("showItem");
		
		return mv;
	}
	
	@RequestMapping(value="/showItem/{code}")
	public ModelAndView showItemByCode(
			@PathVariable(name = "code") Integer code,
			ModelAndView mv) {
		
		List<Items> itemList = itemRepository.findByCategoryKey(code);

		
		mv.addObject("categories",categoryRepository.findAll());
		mv.addObject("items", itemList);
		mv.setViewName("showItem");
		return mv;
	}
	

	@RequestMapping("/itemDetail/{code}")
	public ModelAndView itemDetail(@PathVariable("code") Integer code, ModelAndView mv) {

		Items itemList = itemRepository.findById(code).get();
		mv.addObject("items", itemList);
		mv.setViewName("itemDetail");

		return mv;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.GET)
	public ModelAndView addItem(ModelAndView mv) {
		
		return mv;
	}

	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(@RequestParam(name = "code", defaultValue = "") String code,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryKey", defaultValue = "1") Integer categoryKey,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "stock", defaultValue = "") Integer stock,
			@RequestParam(name = "picture", defaultValue = "") String picture,
			@RequestParam(name = "delivaryDays", defaultValue = "") Integer delivaryDays,ModelAndView mv) {

		if (isNull(name) || isNull(categoryKey) || isNull(price) || isNull(stock) || isNull(picture)
				|| isNull(delivaryDays)) {
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("addItem");
			
			
			return mv;
			
		}
		Users user = (Users)session.getAttribute("userInfo");
		
		Items items = new Items(name, price, picture, stock, categoryKey, delivaryDays, user.getCode());
		itemRepository.saveAndFlush(items);

		mv.addObject("items", itemRepository.findBySellerUserCode(user.getCode()));
		mv.setViewName("addItemResult");

		return mv;
	}

	/**
	 * 商品登録処理
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "/addItem/result", method = RequestMethod.POST)
	public ModelAndView addItemResult(ModelAndView mv) {

		// 詳細情報の登録: orderDetailへの登録
		// 商品の登録
		Items items = (Items) session.getAttribute("itemInfo");
		itemRepository.saveAndFlush(items);
		
		session.setAttribute("itemInfo", items);

		mv.setViewName("addItemResult");
		return mv;
	}

	@RequestMapping(value = "/addItemResult/{code}/edit")
	public ModelAndView addUser(@PathVariable("code") int code, ModelAndView mv) {

		Items items = itemRepository.findById(code).get();

		mv.addObject("item", items);
		mv.setViewName("editItem");
		return mv;
	}

	/**
	 * 更新処理
	 * 
	 * @param code
	 * @param mv
	 * @return
	 */

	@RequestMapping(value = "/addItemResult/{code}", method = RequestMethod.POST)
	public ModelAndView editItem(@PathVariable(name = "code") Integer code,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categoryKey", defaultValue = "1") Integer categoryKey,
			@RequestParam(name = "price", defaultValue = "") Integer price,
			@RequestParam(name = "stock", defaultValue = "") Integer stock,
			@RequestParam(name = "picture", defaultValue = "") String picture,
			@RequestParam(name = "delivaryDays", defaultValue = "") Integer delivaryDays,ModelAndView mv) {
		Items items = itemRepository.findById(code).get();

		if (isNull(name) || isNull(price) || isNull(stock)|| isNull(categoryKey)|| isNull(delivaryDays)) {

			mv.addObject("item", items);
			mv.addObject("message", "未入力の項目があります");
			mv.setViewName("editItem");
		} else {

			items.setName(name);
			items.setCategoryKey(categoryKey);
			items.setPrice(price);
			items.setStock(stock);
			items.setPicture(picture);
			items.setDelivaryDays(delivaryDays);
			
			itemRepository.saveAndFlush(items);
			
			Users user = (Users) session.getAttribute("userInfo");

//			mv.addObject("items", itemRepository.findById(code).get());
			mv.addObject("items", itemRepository.findBySellerUserCode(user.getCode()));
			mv.setViewName("addItemResult");
		}
		return mv;

	}

	/**
	 * データの削除処理
	 * 
	 * @param code
	 * @param mv 
	 * @return
	 */
	@RequestMapping(value = "/addItemResult/{code}/delete", method = RequestMethod.POST)
	public ModelAndView deleteItem(@PathVariable("code") int code, ModelAndView mv) {

		itemRepository.deleteById(code);
		itemRepository.flush();
		
		Users user = (Users) session.getAttribute("userInfo");
		
		mv.addObject("items", itemRepository.findBySellerUserCode(user.getCode()));
//		mv.addObject("items", itemRepository.findAll());
		mv.setViewName("addItemResult");

		return mv;

	}
	
	@RequestMapping("/addItemResult")
	public ModelAndView jumpItemResult(ModelAndView mv) {

		Users user = (Users)session.getAttribute("userInfo");

		mv.addObject("items", itemRepository.findBySellerUserCode(user.getCode()));
		mv.setViewName("addItemResult");
		return mv;
	}

	/**
	 * 未入力判定メソッド
	 * 
	 * @param text
	 * @return
	 */
	public boolean isNull(String text) {
		return (text == null || text.length() == 0);
	}

	public boolean isNull(Integer num) {
		return (num == null || num <= 0);
	}

}
