package com.barbershop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbershop.bean.Dynamic;
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
}
