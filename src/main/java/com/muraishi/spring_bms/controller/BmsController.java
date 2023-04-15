package com.muraishi.spring_bms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.muraishi.spring_bms.dao.BookDao;
import com.muraishi.spring_bms.entity.Book;
import com.muraishi.spring_bms.repository.BookRepository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class BmsController {

	// Repositoryインターフェースを自動インスタンス化
	@Autowired
	private BookRepository bookinfo;
	
	// EntityManager自動インスタンス化
	@PersistenceContext
	private EntityManager entityManager;
	
	// DAO自動インスタンス化
	@Autowired
	private BookDao bookDao;
	
	@PostConstruct
	public void init() {
		bookDao = new BookDao(entityManager);
	}
	
	/*
	 * 「/menu」にアクセスがあった場合
	 */
	@RequestMapping("/menu")
	public ModelAndView menu(ModelAndView mav) {
		
		//画面に出力するView指定
		mav.setViewName("menu");
		
		//ModelとView情報を返す
		return mav;
	}
	
	/*
	 * 「/list」にアクセスがあった場合
	 */
	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		
		//bokinfテーブルから全件取得
		Iterable<Book> book_list = bookinfo.findAll();
		
		//Viewに渡す変数をModelに格納
		mav.addObject("book_list",book_list);
		
		//画面に出力するView指定
		mav.setViewName("list");
		
		//ModelとView情報を返す
		return mav;
	}
	
	/*
	 * 「/insert」へアクセスがあった場合
	 */
	@RequestMapping("/insert")
	public ModelAndView insert(@ModelAttribute Book book, ModelAndView mav) {
		
		// Viewに渡す変数をModelに格納
		mav.addObject("book", book);
		
		// 画面に出力するViewを指定
		mav.setViewName("insert");
		
		// ModelとView情報を渡す
		return mav;
	}
	
	/*
	 * 「/insert」へのPOST送信された場合
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	// POSTデータをBookインスタンスとして受け取る
	public ModelAndView insertPost(@ModelAttribute @Validated Book book, BindingResult result, ModelAndView mav) {
		
		if (result.hasErrors()) {
			// エラーメッセージ
			mav.addObject("message", "There is an error in the input content");
			
			// 画面に出力するviewを指定
			mav.setViewName("insert");
			
			// Modelとview情報を返す
			return mav;
		}
		
		//入力さえたデータをDBに保存
		bookinfo.saveAndFlush(book);
		
		// リダイレクト先を指定
		mav = new ModelAndView("redirect:/list");
		
		// ModelとView情報を渡す
		return mav;
	}
	
	/*
	 * 「/search」へのアクセスがあった場合
	 */
	@RequestMapping("/search")
	public ModelAndView search(HttpServletRequest request, ModelAndView mav) {
		
		// bookinfoテーブルから検索
		Iterable<Book> book_list = bookDao.find(
			request.getParameter("isbn"),
			request.getParameter("title"),
			request.getParameter("price")
		);
		
		// Viewに渡す変数をModelに格納
		mav.addObject("book_list", book_list);
		
		// 画面に出力するviewを指定
		mav.setViewName("list"); 
		
		// ModelとView情報を返す
		return mav;
	}
	
}
