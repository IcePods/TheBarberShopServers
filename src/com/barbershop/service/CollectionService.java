package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Collections;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;
import com.barbershop.dao.CollectionDao;

@Service
@Transactional
public class CollectionService {
	@Autowired
	private CollectionDao CDao;
	
	public Collections saveCollection(Users user,Shop shop) {
		return CDao.saveCollection(user, shop);		
	}
	
	public void deleteCollection(Users user,Shop shop) {
		CDao.deleteCollection(user,shop);
	}
	public Collections CheckIsCollected(Users user,Shop shop) {
		return CDao.CheckIsCollected(user, shop);
	}
	public List<Collections> findListCollectionsByUser(Users user){
		return CDao.findListCollectionsByUser(user);
	}
}
