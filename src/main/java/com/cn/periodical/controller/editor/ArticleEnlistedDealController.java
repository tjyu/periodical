package com.cn.periodical.controller.editor;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.cn.periodical.manager.*;
import com.cn.periodical.pojo.*;
import com.cn.periodical.service.AuthorContributeService;
import com.cn.periodical.utils.PropertiesInitManager;
import com.cn.periodical.utils.UtilLoad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cn.periodical.enums.ArticleStateEnums;
import com.cn.periodical.enums.RoleIdEnums;
import com.cn.periodical.enums.SystemIdEnums;
import com.cn.periodical.request.AritcleWorkFlowReqDto;
import com.cn.periodical.request.ArticleQueryReqDto;
import com.cn.periodical.request.UserQueryReqDto;
import com.cn.periodical.response.ArticleQueryRespDto;
import com.cn.periodical.service.ArticleQueryService;
import com.cn.periodical.service.ArticleWorkFlowService;
import com.cn.periodical.service.EditorArticleDealService;
/**
 * 稿件编辑-稿件处理Controller
 * */
@Controller
public class ArticleEnlistedDealController extends EditorController{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleEnlistedDealController.class);
	
	@Autowired
	EditorArticleDealService articleDealService;/**作废*/
	
	@Autowired
	ArticleQueryService articleQueryService;
	
	@Autowired
	ArticleWorkFlowService articleWorkFlowService;
	
	@Autowired
	ArticleInfoManager articleInfoManager;
	
	@Autowired
	ArticleFlowsManager articleFlowsManager;
	
	@Autowired
	UserInfoManager userInfoManager;
	
	@Autowired 
	UserQueryManager userQueryManager;
	@Autowired 
	ExpertInfoManager expertInfoManager;

    @Autowired
    AuthorContributeService authorContributeService ;

    @Autowired
    PeriodicalChongtouLogManager periodicalChongtouLogManager;
	
	/**
	 * toEnlistedArticlePage
	 * 已登记 End
	 */
	@RequestMapping(value="/toEnlistedArticlePage",method = RequestMethod.GET)
	public ModelAndView toEnlistedArticlePage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("editor_enlistedArticlePage");
		return mav;

	}
	/**
	 * toEnlistedArticlePage
	 * 已登记 End
	 */
	@RequestMapping(value="/toEnlistedArticlePageSet")
    @ResponseBody
    public JSONObject   toEnlistedArticlePageSet(HttpServletRequest request,HttpServletResponse response,
                                                 @ModelAttribute ArticleQueryReqDto query,
                                                 @RequestParam(required = false, value = "page", defaultValue = "1") int page,
                                                 @RequestParam(required = false, value = "rows", defaultValue = "10") int rows) {
		UserInfo userInfo = getUserInfo(request);
		logger.info("已登记Page入参:["+JSON.toJSONString(userInfo)+"]");

        // 返回给页面的一个json
        JSONObject json = new JSONObject();
        query.setEditorState(ArticleStateEnums.ENLISTED_ARTICLE.getCode());
        query.setRoleId(userInfo.getRoleId());
        /**
         * 查询总数
         */
        int count = articleQueryService.queryArticleInfosCount(query);
        json.put("total", count);
        logger.info("+++++++++"+count);

        query.setPageSize((page-1)*rows);//开始
        query.setPageNo(rows);//截止

        logger.info("已登记Page出参 in:["+JSON.toJSONString(query)+"]");
        ZuoZheGaoJianPage zuoZheGaoJianPage =articleQueryService.queryArticleInfos(query,count);
        json.put("rows", zuoZheGaoJianPage.getValues());

        logger.info("已登记Page出参 out:[]");
        return json;

	}

	/**
	 * toEnlistedArticleDetailPage
	 * 已登记-详情页 End
	 */
	@RequestMapping(value="/toEnlistedArticleDetailPage")
	public ModelAndView toEnlistedArticleDetailPage(@RequestParam("artilceId") String artilceId,
			HttpServletRequest request) {
		logger.info("已登记稿件详情页Page入参:artilceId:["+artilceId+"]");
		ModelAndView mav = new ModelAndView("editor_enlistedArticleDetailPage");
		ArticleQueryReqDto reqDto= new ArticleQueryReqDto();
		reqDto.setArticleId(artilceId);
		reqDto.setRoleId(RoleIdEnums.AUTHOR.getCode());/**编辑下载作者的稿件*/
		ArticleQueryRespDto articleQueryRespDto =articleQueryService.queryArticleInfoDetail(reqDto);
		mav.addObject("respDto", articleQueryRespDto);
		logger.info("已登记稿件详情页Page出参:["+JSON.toJSONString(articleQueryRespDto)+"]");
		return mav;
	}
	
	/**
	 * toSubmitPage
	 * 登记
	 * 稿件状态变更 End
	 */
	@RequestMapping(value="/toSubmitPage",method = RequestMethod.GET)
	public ModelAndView toSubmitPage(@RequestParam("articleId") String articleId,
			HttpServletRequest request) {
		logger.info("送审Page入参:artilceId:["+articleId+"]");
		ModelAndView mav = new ModelAndView("editor_artilce_submitedPage");
		ArticleQueryReqDto reqDto= new ArticleQueryReqDto();
		reqDto.setArticleId(articleId);
		reqDto.setRoleId(RoleIdEnums.AUTHOR.getCode());/**编辑下载作者的稿件*/
		ArticleQueryRespDto articleQueryRespDto =articleQueryService.queryArticleInfoDetail(reqDto);
        PeriodicalChongtouLog periodicalChongtouLog = new PeriodicalChongtouLog () ;
        periodicalChongtouLog.setArticleNo(articleId);
        List<PeriodicalChongtouLog> listFanxiu = periodicalChongtouLogManager.selectByCondition(periodicalChongtouLog);
        if(null!=listFanxiu && !listFanxiu.isEmpty()){
            articleQueryRespDto.setArticleId(listFanxiu.get(0).getGroupFlag());
        }else{
            articleQueryRespDto.setArticleId(articleId);
        }
        articleQueryRespDto.setOriArticleId(articleId);
        mav.addObject("respDto", articleQueryRespDto);
		
		/**
		 * 查询专家
		 * */
		ExpertInfoQuery query = new ExpertInfoQuery();
        query.setExtend1(RoleIdEnums.CN_EXPERT.getCode());
		List<ExpertInfo> expertInfos = expertInfoManager.queryList(query);
		
		mav.addObject("expertInfos", expertInfos);
		logger.info("送审Page出参:["+JSON.toJSONString(articleQueryRespDto)+"]");
		return mav;
	}
	
	/**
	 * toSubmitState
	 *  
	 * 稿件状态变更 End
	 */
	@RequestMapping(value="/toSubmitState",method = RequestMethod.POST)
	@ResponseBody
	public Object toSubmitModify(@RequestParam("articleId") String articleId,String eId,
			HttpServletRequest request,@RequestParam(value="files", required=true) MultipartFile[] files) {
		logger.info("修改稿件送审入参:artilceId:["+articleId+"]&expertId:["+eId+"]");

        Map<String , Object> map = new HashMap<String , Object>();

        /**
         * 编辑送审给哪个专家
         * */
		UserQueryReqDto dto = new UserQueryReqDto();
		dto.setRoleId(RoleIdEnums.CN_EXPERT.getCode());
		dto.setRefId(eId.split(",")[0].trim());
		dto.setSystemId(SystemIdEnums.EXPERT_SYS.getCode());
		logger.info("为毛多一个参数!!!!!"+JSON.toJSONString(dto));
		UserQueryReqDto dtoResult = userQueryManager.queryUserRef(dto);
		
		/**
		 * 记录稿件开始处理流水
		 * refId谁把articleId稿件送给dtoResult.getUserId审
		 * */
		AritcleWorkFlowReqDto reqDto = new AritcleWorkFlowReqDto();
		reqDto.setUserId(dtoResult.getUserId());
		reqDto.setArticleId(articleId);
		reqDto.setRoleId(RoleIdEnums.ARTICLE_EDITOR.getCode());
		reqDto.setDealState(ArticleStateEnums.SUBMITED_ARTICLE.getCode());
		reqDto.setToRoleId(RoleIdEnums.CN_EXPERT.getCode());
		reqDto.setDealStartTime(new Date());
		reqDto.setRefId(getUserInfo(request).getUserId());
		articleWorkFlowService.registArticleWorkFlow(reqDto);
		
		/**
		 * 变更稿件状态
		 * */
		ArticleInfoQuery query= new ArticleInfoQuery();
		query.setArticleId(articleId);
		List<ArticleInfo> articleInfos = articleInfoManager.queryList(query);
		ArticleInfo	articleInfo = articleInfos.get(0);
		articleInfo.setId(articleInfo.getId());
		articleInfo.setAuthorState(ArticleStateEnums.SUBMITED_ARTICLE.getCode());
		articleInfo.setEditorState(ArticleStateEnums.SUBMITED_ARTICLE.getCode());
		articleInfo.setExpertState(ArticleStateEnums.SUBMITED_ARTICLE.getCode());
		articleInfoManager.saveArticleInfo(articleInfo);


        //  ==========================

        Map<String, Object> resMap = UtilLoad.fileUpload(files, "editorPath", articleId);
        String filePathRet = (String) resMap.get("filePath");
        if(null!=filePathRet){
            String type = RoleIdEnums.ARTICLE_EDITOR.getCode();
            try {
                authorContributeService.saveAtricalAtt(articleId ,  files[0].getOriginalFilename() ,  filePathRet,type );
            } catch (Exception e) {
            }
        }
        map.put("message" , "success");
		
		logger.info("修改稿件状态出参:[]");
		return map;
	}


}
