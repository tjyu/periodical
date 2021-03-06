/*
 * Powered By code-generator
 * Since 2015 - 2015
 */

package com.cn.periodical.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cn.periodical.dao.ArticleFlowsDao;
import com.cn.periodical.manager.ArticleFlowsManager;
import com.cn.periodical.pojo.ArticleFlows;
import com.cn.periodical.pojo.ArticleFlowsPage;
import com.cn.periodical.pojo.ArticleFlowsQuery;
import com.cn.periodical.pojo.Opinion;
import com.cn.periodical.utils.Pagenation;

@Component("articleFlowsManager")
public class ArticleFlowsManagerImpl implements ArticleFlowsManager {
	
	@Autowired
	@Qualifier("articleFlowsDao")
	private ArticleFlowsDao articleFlowsDao;

    public void saveArticleFlowsNew(ArticleFlows articleFlows) {
        articleFlowsDao.insertSelective(articleFlows);
    }

	public void saveArticleFlows(ArticleFlows articleFlows) {
		if (articleFlows.getId() == null) {
			articleFlowsDao.insertSelective(articleFlows);
		} else {
			articleFlowsDao.updateByPrimaryKeySelective(articleFlows);
		}
	}
	
	
	public ArticleFlows findArticleFlowsById(Long id) {
		return articleFlowsDao.selectByPrimaryKey(id);
	}
	
	
	public List<ArticleFlows> queryList(ArticleFlowsQuery query) {
		return articleFlowsDao.selectByExample(query);
	}
	
	
	public void deleteArticleFlowsById(Long id) {
		articleFlowsDao.deleteByPrimaryKey(id);
	}
	
	
	public void deleteArticleFlows(ArticleFlowsQuery query) {
		articleFlowsDao.deleteByExample(query);
	}
	
	
	public ArticleFlowsPage queryPageList(ArticleFlowsQuery query) {
		ArticleFlowsPage articleFlowsPage = new ArticleFlowsPage();
		Integer itemCount = articleFlowsDao.countByExample(query);
		query.setItemCount(itemCount);
		
		if (itemCount == 0) {
			articleFlowsPage.setValues(null);
		} else {
			articleFlowsPage.setValues(articleFlowsDao.selectPageByExample(query));
		}
		
		articleFlowsPage.setPagenation(new Pagenation(query.getPageNo(),query.getPageSize(),query.getItemCount()));
		return articleFlowsPage;
	}


	public long selectMaxId(String articleId) {
		// TODO Auto-generated method stub
		return articleFlowsDao.selectMaxId(articleId);
	}


	public ArticleFlows queryFlowsDetail(ArticleFlowsQuery query) {
		// TODO Auto-generated method stub
		return articleFlowsDao.queryFlowsDetail(query);
	}


	public int updateFlows(ArticleFlows record) {
		// TODO Auto-generated method stub
		return articleFlowsDao.updateFlows(record);
	}
	public int updateExpertFlows(ArticleFlows record) {
		// TODO Auto-generated method stub
		return articleFlowsDao.updateExpertFlows(record);
	}


	public Opinion queryOpinion(Opinion opinion) {
		// TODO Auto-generated method stub
		return articleFlowsDao.queryOpinion(opinion);
	}

    /**
     * 稿件看专家的返修
     * @param articleId
     * @return
     */
	public Opinion queryOpinion1(String articleId) {
		// TODO Auto-generated method stub
		return articleFlowsDao.queryOpinion1(articleId);
	}
    /**
     * 稿件看专家的退稿
     * @param articleId
     * @return
     */
	public Opinion queryOpinion2(String articleId) {
		// TODO Auto-generated method stub
		return articleFlowsDao.queryOpinion2(articleId);
	}
}

