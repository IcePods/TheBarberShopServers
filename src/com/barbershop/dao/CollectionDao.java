package com.barbershop.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
		Session session = this.getSession();
		Query q = session.createQuery("delete from Collections where user=? and shop=? ");
		q.setParameter(0, user);
		q.setParameter(1, shop);
        q.executeUpdate();
		
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
	public List<Collections> findListCollectionsByUser(Users user){
		Session session = this.getSession();
		Query<Collections> q = session.createQuery("from Collections where user=?");
		q.setParameter(0, user);
		List <Collections> list = q.list();
		return list;		
	}
}
