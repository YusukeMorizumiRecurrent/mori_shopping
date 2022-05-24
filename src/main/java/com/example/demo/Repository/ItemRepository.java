package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Items;

@Repository
public interface ItemRepository extends JpaRepository<Items, Integer> {
	
	List<Items> findBySellerUserCode(Integer usercode);
	List<Items> findByNameContains(@Param("namePrefix") String str);
	List<Items> findByCode(Integer code);
	List<Items> findByCategoryKey(Integer categoryKey);

}
