package com.cn.periodical.controller.editor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cn.periodical.pojo.BizEditor;
import com.cn.periodical.pojo.ExpertInfo;
import com.cn.periodical.pojo.UserInfo;

/**
 * 主编-专家管理
 * */
@Controller
public class ExpertManagerController extends EditorController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpertManagerController.class);
	public ExpertManagerController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * toExpertManagerPage
	 * 专家人员管理
	 */
	@RequestMapping(value="/toExpertManagerPage",method = RequestMethod.GET)
	public ModelAndView toExpertManagerPage(HttpServletRequest request,@ModelAttribute BizEditor be) {
		UserInfo userInfo = getUserInfo(request);
		logger.info("专家人员管理首页Page in:[]");
		ModelAndView mav = new ModelAndView("editor_expertManagerPage");
		/**
		 * 专家管理首页查询
		 * 查询条件:
		 * expertName=专家姓名;expertLogonName=账号名;expertType=专家类型
		 * 
		 * 查询一个list列表,页面展示用
		 * */
		
		
		
		logger.info("专家人员管理首页Page out:[]");
		return mav;
	}
	
	@RequestMapping(value = "/toExpertInfoAdd", method = RequestMethod.GET)
	public ModelAndView toExpertInfoAdd() {
		ModelAndView mav = new ModelAndView("editor_expertInfoDetailPage");

		return mav;
	}
	
	/**
	 * 保存编辑信息
	 */
	@RequestMapping(value = "/toSaveExpertInfo", method = { RequestMethod.POST })
	public ModelAndView toSaveExpertInfo(@ModelAttribute ExpertInfo expertInfo,@RequestParam("roleId") String roleId) {
		ModelAndView mav = new ModelAndView("redirect:../editor/toExpertManagerPage");
		logger.info("新增专家信息 in:["+JSON.toJSONString(expertInfo)+"]");
		try {
			/**
			 * 1,email做用户名,默认密码111111.向userInfo中添加记录
			 * ref_id=expert_info中的expert_id
			 * */
			
			
			/**
			 * 2,保存expertInfo到Expert_info表
			 * */
			
			
		} catch (Exception e) {
			//记录错误日志
			logger.error("新增专家信息异常!", e);
			return new ModelAndView("error");
		}
		return mav;
	}
}