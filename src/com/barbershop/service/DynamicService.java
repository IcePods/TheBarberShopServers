package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Dynamic;
import com.barbershop.bean.Users;
import com.barbershop.dao.DynamicDao;

@Service
@Transactional
public class DynamicService {
	@Autowired
	private DynamicDao DDao;
	
	
	public List<Dynamic> showAllDynamic (){
		return DDao.showAllDynamic();
		
	}
	public Dynamic SaveDynamic(Dynamic dynamic) {
		return DDao.SaveDynamic(dynamic);
		
	}
	
	/**
	 * 通过用户获取用户全部动态
	 * @param user
	 * @return
	 */
	public List<Dynamic> getDynamicByUser(Users user){
		return DDao.getDynamicByUser(user);
	}
}
