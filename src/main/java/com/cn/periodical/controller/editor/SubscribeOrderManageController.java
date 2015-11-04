package com.cn.periodical.controller.editor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * 发行编辑-订单管理Controller
 * */
@Controller
public class SubscribeOrderManageController extends EditorController{
	
	private static final Logger logger = LoggerFactory.getLogger(SubscribeOrderManageController.class);
	/**
	 * toSubscribeOrderManagePage
	 * 订单管理
	 */
	@RequestMapping(value="/toSubscribeOrderManagePage",method = RequestMethod.GET)
	public ModelAndView toSubscribeOrderManagePage(@RequestParam("userId") String userId,
			HttpServletRequest request) {
		logger.info("发行编辑-订单管理Page:["+userId+"]");
		ModelAndView mav = new ModelAndView("editor_subscribeOrderManagePage");
		mav.addObject("userId", userId);
		
		
		return mav;
	}
}
