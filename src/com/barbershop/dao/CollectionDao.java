package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.barbershop.bean.Collections;
import com.barbershop.bean.Shop;
import com.barbershop.bean.Users;

@Repository
public class CollectionDao {
	@Autowired
	private  SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 添加收藏
	 * @param user
	 * @param shop
	 * @return
	 */
	public Collections saveCollection(Users user,Shop shop) {
		
		Session session = this.getSession();
		Collections collections = new Collections();
		collections.setShop(shop);
		collections.setUser(user);
		session.save(collections);	
		return collections;		
	}
	/**
	 * 删除收藏
	 * @param id
	 */
	public void deleteCollection(Users user,Shop shop) {
		this.getSession().createQuery("delete from Collections where user=? and shop=? ")
			.setParameter(0, user)
			.setParameter(1, shop)
			.executeUpdate();
	}
	/**
	 * 判断该店铺是否被该用户搜藏
	 * @param user
	 * @param shop
	 * @return
	 */
	public Collections CheckIsCollected(Users user,Shop shop) {
		Collections collections = (Collections) this.getSession()
				.createQuery("from Collections where user = ? and shop = ?")
				.setParameter(0, user)
				.setParameter(1, shop)
				.uniqueResult();
		return collections;
		
	}
	/**
	 * 通过用户查找用户收藏的店铺
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Collections> findListCollectionsByUser(Users user){
		return this.getSession().createQuery("from Collections where user=?")
				.setParameter(0, user)
				.list();	
	}
}
