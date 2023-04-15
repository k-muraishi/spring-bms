package com.muraishi.spring_bms.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.muraishi.spring_bms.entity.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Repository
public class BookDao {
	
	// エンティティマネージャー
	private EntityManager entityManager;
	
	// クエリ生成用インスタンス
	private CriteriaBuilder builder;
	
	// クエリ実行用インスタンス
	private CriteriaQuery<Book> query;
	
	// 検索されるエンティティのルート
	private Root<Book> root;
	
	/*
	 * コンストラクタ(DB接続準備)
	 */
	public BookDao(EntityManager entityManager) {
		// EntityManager取得
		this.entityManager = entityManager;
		
		// クエリ生成用インスタンス
		builder = entityManager.getCriteriaBuilder();
		
		// クエリ実行用インスタンス
		query = builder.createQuery(Book.class);
		
		// 検索されるエンティティのルート
		root = query.from(Book.class);
	}
	
	/*
	 * 書籍情報検索
	 * @param String isbn
	 * @param String title
	 * @param String price
	 * @return ArrayList<Book> book_list
	 */
	public ArrayList<Book> find(String isbn, String title, String price){
		
		// SELECT句設定
		query.select(root);
		
		// where句
		query.where(
			builder.like(root.get("isbn"), "%" + isbn + "%"),
			builder.like(root.get("title"), "%" + title + "%"),
			builder.like(root.get("price"), "%" + price + "%")
		);
		
		// クエリ実行
		return (ArrayList<Book>)entityManager.createQuery(query).getResultList();
	}
}
