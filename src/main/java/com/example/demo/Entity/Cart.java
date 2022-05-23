package com.example.demo.Entity;


import java.util.HashMap;
import java.util.Map;

public class Cart {
	private Map<Integer, Items> items = new HashMap<>();

	private int total;

	public Map<Integer, Items> getItems() {
		return items;
	}

	public int getTotal() {
		return total;
	}

	public Cart() {

	}

	/**
	 * アイテム情報をカートに追加
	 * 
	 * @param item
	 * @param quantity
	 */
	public void addCart(Items item, int stock) {
		Items existedItem = items.get(item.getCode());

		// アイテムが存在しない場合は追加
		if (existedItem == null) {
			// 数量を設定
			item.setStock(stock);
			items.put(item.getCode(), item);
			// アイテムが存在する場合は追加しないけど、数量は追加
		} else {
			existedItem.setStock(existedItem.getStock() + stock);
		}
		recalcTotal();
	}

	/**
	 * カートの処理
	 */
	public void deleteCart(int itemCode) {
		// item.codeを使用してカート配列から消去
		items.remove(itemCode);
		recalcTotal();
	}

	/*
	 * カートの中身の総金額を算出する
	 */
	public void recalcTotal() {
		total = 0;
		for (Items item : items.values()) {
			total += item.getPrice() * item.getStock();
		}
	}

}

