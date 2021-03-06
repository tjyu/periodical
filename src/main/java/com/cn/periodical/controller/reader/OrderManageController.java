package com.cn.periodical.controller.reader;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.cn.periodical.enums.ArticalCodeEnums;
import com.cn.periodical.manager.*;
import com.cn.periodical.pojo.*;
import com.cn.periodical.service.SongKanInfoService;
import com.cn.periodical.service.Zeng1KanInfoService;
import com.cn.periodical.service.Zeng4KanInfoService;
import com.cn.periodical.utils.WriteExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cn.periodical.enums.OrderStatusEnums;
import com.cn.periodical.service.OrderInfoService;
import com.cn.periodical.utils.GenerateOrderNo;

/**
 * 读者工作区-期刊征订-订单管理Controller
 * */
@Controller
public class OrderManageController extends ReaderController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderManageController.class);

    @Autowired
    OrderInfoService orderInfoService ;
    
    @Autowired
    PeriodicalInfoManager periodicalInfoManager;
    
    @Autowired
    OrderInfoManager orderInfoManager;
    
    @Autowired
    PayeeInfoManager payeeInfoManager;
    
    @Autowired
    AddressInfoManager addressInfoManager;
    @Autowired
    BizOrderManager bizOrderManager;
    
    @Autowired
    UserInfoManager userInfoManager;
    @Autowired
    PeriodicalDistributManager periodicalDistributManager;
    @Autowired
    SongKanInfoService songKanInfoService;
    @Autowired
    Zeng4KanInfoService zeng4KanInfoService;
    @Autowired
    Zeng1KanInfoService zeng1KanInfoService;
    @Autowired
    ArticalCodeManager articalCodeManager;
    @Autowired
    ArticalCodeManager  aticalCodeManager;
	/**
	 * toOrderManagePage
	 * 订单管理页面
	 */
	@RequestMapping(value="/toOrderManagePage")
	public ModelAndView toOrderManagePage(HttpServletRequest request,BizOrder bizOrder) {
		ModelAndView mav = new ModelAndView("reader_orderManagePage");
		return mav;
	}
	/**
	 * toOrderManagePage
	 * 订单管理页面
	 */
	@RequestMapping(value="/toOrderManagePageSet")
    @ResponseBody
    public JSONObject toOrderManagePageSet(HttpServletRequest request,BizOrderQuery query
                                            ,@RequestParam(required = false, value = "page", defaultValue = "1") int page,
                                             @RequestParam(required = false, value = "rows", defaultValue = "10") int rows) {

        JSONObject json = new JSONObject();
        int count = orderInfoManager.queryOrderListQueryCount(query);
        json.put("total", count);

        query.setPageSize((page-1)*rows);//开始
        query.setPageNo(rows);//截止

        BizOrderPage bizAdPage = orderInfoManager.queryOrderListQuery(query,count);
        json.put("rows", bizAdPage.getValues());

        logger.info("广告管理首页Page out:[]");
        return json;
	}

	
	/**
	 * toCreatOrderPage
	 * 新建订单页面
	 */
	@RequestMapping(value="/toCreatOrderPage")
	public ModelAndView toCreatOrderPage(HttpServletRequest request,String periodicalId,String payeeId) {
		UserInfo userInfo = getUserInfo(request);
		logger.info("新建读者订单Page:["+userInfo.getUserId()+"]&["+periodicalId+"]");
		ModelAndView mav = new ModelAndView("reader_creatOrderPage");
		PeriodicalInfoQuery periodicalInfoQuery = new PeriodicalInfoQuery();
		periodicalInfoQuery.setPeriodicalId(periodicalId);
		List<PeriodicalInfo> periodicalInfos = periodicalInfoManager.queryList(periodicalInfoQuery);
		PeriodicalInfo periodicalInfo = periodicalInfos.get(0);
		PayeeInfoQuery payeeInfoQuery = new PayeeInfoQuery();
		payeeInfoQuery.setPayeeId(payeeId);
//		payeeInfoQuery.setType("000");
		List<PayeeInfo> payeeInfos = payeeInfoManager.queryList(payeeInfoQuery);
		PayeeInfo payeeInfo = payeeInfos.get(0);
		mav.addObject("periodicalInfo", periodicalInfo);
		mav.addObject("payeeInfo", payeeInfo);
		return mav;
	}
	
	/**
	 * toCreatOrder
	 * 订阅
	 */
	@RequestMapping(value="/toCreatOrder",method ={ RequestMethod.POST ,RequestMethod.GET})
	public ModelAndView toCreatOrder(HttpServletRequest request,String periodicalId,String singlPrice
            ,String periodicalYear) {
		UserInfo userInfo = getUserInfo(request);
		logger.info("新建读者订单Page:["+userInfo.getUserId()+"]&["+periodicalId+"]");
		ModelAndView mav = new ModelAndView("redirect:../reader/toOrderManagePage");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setUserId(userInfo.getUserId());

        String orderNo = aticalCodeManager.getCode(ArticalCodeEnums.ORDER_CONDE.getCode(),ArticalCodeEnums.ORDER_CONDE.getName());

		orderInfo.setOrderNo(orderNo);
		orderInfo.setPeriodicalYear(periodicalYear);
		orderInfo.setSubscribeNums(0);
		orderInfo.setOrderStatus(OrderStatusEnums.NONE.getCode());/**订单状态:N未付款订单*/
		orderInfo.setPeriodicalId(periodicalId);
		orderInfo.setAmount(0l);
		orderInfo.setCreateTime(new Date());
		orderInfoManager.saveOrderInfo(orderInfo);
		return mav;
	}

    /**
     * toCreatOrder
     * 送刊保存
     */
    @RequestMapping(value="/toCreatOrderSongKan",method ={ RequestMethod.POST ,RequestMethod.GET})
    public  @ResponseBody Map<String,Object> toCreatOrderSongKan(HttpServletRequest request,
                                                                 String periodicalId,String periodicalIssueNo,String qishu,
                                                                 String array , String year,String orderNo) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("保存送刊Page:["+userInfo.getUserId()+"]&["+periodicalId+"]");
        Map<String,Object> map_ret = new HashMap<String,Object>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        JSONArray arr = (JSONArray) JSONArray.parse(array);
        int zengSongNums = 0 ;
        for(int i=0;i<arr.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();
            String articleId = arr.getJSONObject(i).getString("articleId");
            int nums = Integer.parseInt(arr.getJSONObject(i).getString("nums"));
            zengSongNums = zengSongNums+  nums ;
            map.put("articleId",articleId);
            map.put("nums",nums);
            list.add(map);
        }
        SongKanInfo songKanInfo =new SongKanInfo();
        songKanInfo.setPeriodicalId(periodicalId);
        songKanInfo.setPeriodicalIssueNo(periodicalIssueNo);
        songKanInfo.setZengSonNums(zengSongNums);
        songKanInfo.setCycleNums(Integer.parseInt(qishu));
        songKanInfo.setYear(year);
        if(null==orderNo || "".equals(orderNo)){
            orderNo = articalCodeManager.getCode(ArticalCodeEnums.SONGKAN_CONDE.getCode(),ArticalCodeEnums.SONGKAN_CONDE.getName()) ;   // 订单号生成
        }
        songKanInfo.setOrderNo(orderNo);

        int r = songKanInfoService.insert(songKanInfo,list);
        if(1==r){
            map_ret.put("message","保存成功");
        } else{
            map_ret.put("message","保存失败");
        }
        return map_ret;
    }
    /**
     * toSubscribePostPage 邮寄管理
     */
    @RequestMapping(value = "/toSongKanYouJiPage", method = RequestMethod.GET)
    public ModelAndView toSubscribePostPage(HttpServletRequest request) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("发行编辑-送刊邮寄管理Page:[" + userInfo.getUserId() + "]");
        ModelAndView mav = new ModelAndView("editor_songKanYouJiPage");
        /**
         * 订单信息
         */
        SongKanInfo songKanInfo = new SongKanInfo();
        List<SongKanInfo> list = songKanInfoService.selectByCondition(songKanInfo);
        mav.addObject("list", list);
        return mav;
    }
    /**
     * toSubscribePostPage 邮寄管理
     */
    @RequestMapping(value = "/toZeng4KanYouJiPage", method = RequestMethod.GET)
    public ModelAndView toZeng4KanYouJiPage(HttpServletRequest request) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("发行编辑-赠刊邮寄管理Page:[" + userInfo.getUserId() + "]");
        ModelAndView mav = new ModelAndView("editor_zeng4KanYouJiPage");
        /**
         * 订单信息
         */
        Zeng4KanInfo songKanInfo = new Zeng4KanInfo();
        List<Zeng4KanInfo> list = zeng4KanInfoService.selectByCondition(songKanInfo);
        mav.addObject("list", list);
        return mav;
    }
    /**
     * toSubscribePostPage 邮寄管理
     */
    @RequestMapping(value = "/toZeng1KanYouJiPage", method = RequestMethod.GET)
    public ModelAndView toZeng1KanYouJiPage(HttpServletRequest request) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("发行编辑-增刊邮寄管理Page:[" + userInfo.getUserId() + "]");
        ModelAndView mav = new ModelAndView("editor_zeng1KanYouJiPage");
        /**
         * 订单信息
         */
        Zeng1KanInfo songKanInfo = new Zeng1KanInfo();
        List<Zeng1KanInfo> list = zeng1KanInfoService.selectByCondition(songKanInfo);
        mav.addObject("list", list);
        return mav;
    }
    /**
     * toReaderAddressInfoDetails 读者订阅地址及信息明细
     */
    @RequestMapping(value = "/toSongKanYouJiDetailsPage")
    public ModelAndView toSongKanYouJiDetailsPage(HttpServletRequest request, String orderNo, String periodicalId,
                                                   String periodicalIssueNo) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("送刊-查看读者订阅地址信息Page:[" + userInfo.getUserId() + "]orderNo:[" + orderNo + "]" + "periodicalId:["
                + periodicalId + "]" + "periodicalIssueNo:[" + periodicalIssueNo + "]");
        ModelAndView mav = new ModelAndView("editor_songKanDetailPage");

        BizDistribut distribut = new BizDistribut();
        distribut.setOrderNo(orderNo);
        distribut.setpId(periodicalId);
        distribut.setpIsNo(periodicalIssueNo);
        List<SongKanDetail> list = songKanInfoService.selectByOrderNo(orderNo);
        mav.addObject("list", list);
        mav.addObject("distribut", distribut);

        return mav;
    }
    /**
     * toReaderAddressInfoDetails 读者订阅地址及信息明细
     */
    @RequestMapping(value = "/toZeng4KanYouJiDetailsPage")
    public ModelAndView toZeng4KanYouJiDetailsPage(HttpServletRequest request, String orderNo, String periodicalId,
                                                  String periodicalIssueNo,String cycleNums) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("赠刊-查看读者订阅地址信息Page:[" + userInfo.getUserId() + "]orderNo:[" + orderNo + "]" + "periodicalId:["
                + periodicalId + "]" + "periodicalIssueNo:[" + periodicalIssueNo + "]");
        ModelAndView mav = new ModelAndView("editor_zeng4KanDetailPage");

        BizDistribut distribut = new BizDistribut();
        distribut.setOrderNo(orderNo);
        distribut.setpId(periodicalId);
        distribut.setpIsNo(periodicalIssueNo);
        distribut.setAmount(cycleNums);// 期数
        List<Zeng4KanDetail> list = zeng4KanInfoService.selectByOrderNo(orderNo);
        mav.addObject("list", list);
        mav.addObject("distribut", distribut);

        return mav;
    }
    /**
     * toReaderAddressInfoDetails 读者订阅地址及信息明细
     */
    @RequestMapping(value = "/toZeng1KanYouJiDetailsPage")
    public ModelAndView toZeng1KanYouJiDetailsPage(HttpServletRequest request, String orderNo, String periodicalId,
                                                  String periodicalIssueNo,String cycleNums) {
        UserInfo userInfo = getUserInfo(request);
        logger.info("赠刊-查看读者订阅地址信息Page:[" + userInfo.getUserId() + "]orderNo:[" + orderNo + "]" + "periodicalId:["
                + periodicalId + "]" + "periodicalIssueNo:[" + periodicalIssueNo + "]");
        ModelAndView mav = new ModelAndView("editor_zeng1KanDetailPage");

        BizDistribut distribut = new BizDistribut();
        distribut.setOrderNo(orderNo);
        distribut.setpId(periodicalId);
        distribut.setpIsNo(periodicalIssueNo);
        distribut.setAmount(cycleNums);// 期数
        List<Zeng1KanDetail> list = zeng1KanInfoService.selectByOrderNo(orderNo);
        mav.addObject("list", list);
        mav.addObject("distribut", distribut);

        return mav;
    }
	/**
	 * 去订单编辑页
	 * 只能修改订阅数等信息
	 * */
	@RequestMapping(value="/toEditorOrderPage",method ={ RequestMethod.POST ,RequestMethod.GET})
	public ModelAndView toEditorOrderPage(String userId ,String orderId) {
		logger.info("读者修改订单Page:["+userId+"]&["+orderId+"]");
		ModelAndView mav = new ModelAndView("reader_orderEditorManagePage");
		OrderInfoQuery orderInfoQuery =  new OrderInfoQuery();
		orderInfoQuery.setUserId(userId);
		orderInfoQuery.setOrderNo(orderId);
		List<OrderInfo> orderInfos = orderInfoManager.queryList(orderInfoQuery);
		
		mav.addObject("userId", userId);
		mav.addObject("orderInfo", orderInfos.get(0));
		return mav;
	}
	
	/**
	 * 修改订单
	 * */
	@RequestMapping(value="/toEditorOrder",method ={ RequestMethod.POST ,RequestMethod.GET})
	public ModelAndView toEditorOrder(String userId ,@ModelAttribute OrderInfo orderInfo) {
		logger.info("读者修改订单Page:["+userId+"]&["+JSON.toJSONString(orderInfo)+"]");
		ModelAndView mav = new ModelAndView("redirect:../reader/toOrderManagePage");
		
		orderInfoManager.saveOrderInfo(orderInfo);
		
		mav.addObject("userId", userId);
		return mav;
	}


    @RequestMapping(value="/readEditAddress",method = RequestMethod.POST)
    public ModelAndView readEditAddress(@RequestParam(value="files", required=true) MultipartFile[] files,HttpServletRequest request) {
        ModelAndView mav = null;
        try{
            orderInfoService.insertAddressInfo(files,"er","qwe");
            return mav;
        }catch(Exception e){
            logger.info("投稿功能异常!!!!!!",e);
            mav=new ModelAndView("error");
            return mav;
        }

    }
    /**
     * 查看订单审核意见
     * */
    @RequestMapping(value="/queryOpinion",produces = "application/text; charset=utf-8")
    @ResponseBody
    public String queryOpinion(String orderNo,HttpServletRequest request) {
    	/**
    	 * 根据orderNo查询order_info表
    	 * 表中extend_1字段为订单审核意见
    	 * */
    	OrderInfoQuery orderInfoQuery =  new OrderInfoQuery();
		orderInfoQuery.setUserId(getUserInfo(request).getUserId());
		orderInfoQuery.setOrderNo(orderNo);
		List<OrderInfo> orderInfos = orderInfoManager.queryList(orderInfoQuery);
		OrderInfo orderInfo = orderInfos.get(0);
        return orderInfo.getExtend1();
    }
    
	/**
	 * 跳转到期刊分配页面
	 */
	@RequestMapping(value="/toDistributionPage")
	public ModelAndView toDistributionPage(HttpServletRequest request,String orderNo ) {
		logger.info("读者编辑订单-订单号:["+orderNo+"]");
		UserInfo userInfo = getUserInfo(request);
		ModelAndView mav = new ModelAndView("reader_orderDistribution");
		
		
		
		
		OrderInfoQuery orderQuery=new OrderInfoQuery();
		orderQuery.setUserId(userInfo.getUserId());
		orderQuery.setOrderNo(orderNo);
		List<OrderInfo> orderInfos = orderInfoManager.queryList(orderQuery);
		OrderInfo orderInfo = orderInfos.get(0);
		mav.addObject("orderInfo", orderInfo);
		BizOrder bizOrder = new BizOrder();
		bizOrder.setOrderNo(orderNo);
		List<BizOrder> list = bizOrderManager.queryDistributeOrderInfos(bizOrder);
		mav.addObject("list", list);
		return mav;
	}
	
	
	/**
	 * 期刊分配
	 */
	@RequestMapping(value="/toDistribution")
	public ModelAndView toDistribution(HttpServletRequest request,String array,String periodicalId,String orderNo) {
		UserInfo userInfo = getUserInfo(request);
		logger.info("期刊分配入参:["+array+"]");
		logger.info("读者编辑订单-订单号:["+orderNo+"]&periodicalId["+periodicalId+"]");
		JSONArray str = (JSONArray) JSONArray.parse(array);
		for (int i=0;i<str.size();i++){
			PeriodicalDistribut p = new PeriodicalDistribut();
			p.setPeriodicalId(periodicalId);
			p.setRefId(orderNo);
			
			logger.info("++++++++++++++++++++++++++");
			logger.info((str.getJSONObject(i).getString("pId")=="")+"");
			logger.info((str.getJSONObject(i).getString("pId")==null)+"");
			logger.info("++++++++++++++++++++++++++");
			if(!"".equals(str.getJSONObject(i).getString("pId"))){
				p.setId(Long.valueOf(str.getJSONObject(i).getString("pId")));/**需要把pd的id传进来,进行修改,否则就会是添加*/
			}
			p.setAddressId(str.getJSONObject(i).getString("aId"));
			
			if(!"".equals(str.getJSONObject(i).getString("nums"))){
				p.setDistributeNums(Integer.valueOf(str.getJSONObject(i).getString("nums")));
			}else{
				p.setDistributeNums(0);
			}
			periodicalDistributManager.savePeriodicalDistribut(p);
		}
		ModelAndView mav = new ModelAndView("redirect:../reader/toOrderManagePage");
		return mav;
	}


    /**
     *
     * 进入增刊信息页面
     */
    @RequestMapping(value="/inToZeng1KanPageOne")
    public ModelAndView inToZeng1KanPageOne(HttpServletRequest request) {
        logger.info("进入增刊信息页面");
        ModelAndView mav = new ModelAndView("inToZeng1KanPageOne");
        return mav;
    }
    /**
     *
     * 保存增刊信息
     */
    @RequestMapping(value="/saveZeng1KanPageOne")
    public ModelAndView saveZeng1KanPageOne(HttpServletRequest request,Zeng1KanInfo zeng1KanInfo) {
        logger.info("增刊保存");
        ModelAndView mav = new ModelAndView("redirect:../reader/toZeng1KanYouJiPage");
        String orderNo = articalCodeManager.getCode(ArticalCodeEnums.ZENG1KAN_CONDE.getCode()
                ,ArticalCodeEnums.ZENG1KAN_CONDE.getName()) ;
        zeng1KanInfo.setOrderNo(orderNo);
        zeng1KanInfo.setPeriodicalId(orderNo);
        zeng1KanInfo.setCycleNums(1);
        zeng1KanInfo.setZengSonNums(0);
        zeng1KanInfoService.insert(zeng1KanInfo);
        return mav;
    }
    /**
     *
     * 进入增刊信息页面
     */
    @RequestMapping(value="/inToZeng4KanPageOne")
    public ModelAndView inToZeng4KanPageOne(HttpServletRequest request) {
        logger.info("进入增刊信息页面");
        ModelAndView mav = new ModelAndView("inToZeng4KanPageOne");
        return mav;
    }
    /**
     *
     * 保存增刊信息
     */
    @RequestMapping(value="/saveZeng4KanPageOne")
    public ModelAndView saveZeng4KanPageOne(HttpServletRequest request,Zeng4KanInfo zeng4KanInfo) {
        logger.info("增刊保存");
        ModelAndView mav = new ModelAndView("redirect:../reader/toZeng4KanYouJiPage");
        String orderNo = articalCodeManager.getCode(ArticalCodeEnums.ZENGKAN_CONDE.getCode()
                ,ArticalCodeEnums.ZENGKAN_CONDE.getName()) ;
        zeng4KanInfo.setOrderNo(orderNo);
        zeng4KanInfo.setPeriodicalId(orderNo);
        zeng4KanInfo.setPeriodicalIssueNo(orderNo);
        zeng4KanInfo.setZengSonNums(0);
        zeng4KanInfoService.insert(zeng4KanInfo);
        return mav;
    }

}
