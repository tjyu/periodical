/*
 * Powered By code-generator
 * Since 2015 - 2015
 */

package com.cn.periodical.manager;

import java.util.List;
import java.util.Map;

import com.cn.periodical.pojo.ArticleInfo;
import com.cn.periodical.pojo.ArticleInfoPage;
import com.cn.periodical.pojo.ArticleInfoQuery;
import com.cn.periodical.pojo.EditorAreaInfos;

public interface ArticleInfoManager {
	void saveArticleInfo(ArticleInfo articleInfo);

	ArticleInfo findArticleInfoById(Long id);

    public ArticleInfo selectByArticleId(String articleId);
	
	List<ArticleInfo> queryList(ArticleInfoQuery query);
	List<ArticleInfo> queryListZuGao(ArticleInfoQuery query);
	/**
	 * 主编查看系统稿件信息
	 * */
	List<ArticleInfo> querySysArticleInfosList(ArticleInfoQuery query);
	
	void deleteArticleInfoById(Long id);
	
	void deleteArticleInfo(ArticleInfoQuery query);
	
	ArticleInfoPage queryPageList(ArticleInfoQuery query);
	
	List<EditorAreaInfos> queryArticleTotalInfos(Map<String,String> map);
	
	List<ArticleInfo> queryListByUserId(Map reqMap);
	
	ArticleInfo qryInfoByArticleId(Map reqMap);
}
