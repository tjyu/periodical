/*
 * Powered By code-generator
 * Since 2015 - 2015
 */

package com.cn.periodical.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.periodical.dao.ArticleFlowsDao;
import com.cn.periodical.pojo.ArticleFlows;
import com.cn.periodical.pojo.ArticleFlowsQuery;
import com.cn.periodical.pojo.Opinion;
import com.cn.periodical.dao.BaseDao;

@Repository("articleFlowsDao")
public class ArticleFlowsDaoImpl extends BaseDao implements ArticleFlowsDao {
	
	
	public int countByExample(ArticleFlowsQuery example) {
		return super.getSqlSession().selectOne("ArticleFlowsMapper.countByExample", example);
	}
	
	
	public int deleteByExample(ArticleFlowsQuery example) {
		return super.getSqlSession().delete("ArticleFlowsMapper.deleteByExample", example);
	}
	
	
	public int deleteByPrimaryKey(Long id) {
		return super.getSqlSession().delete("ArticleFlowsMapper.deleteByPrimaryKey", id);
	}
	
	
	public int insert(ArticleFlows record) {
		return super.getSqlSession().insert("ArticleFlowsMapper.insert", record);
	}
	
	
	public int insertSelective(ArticleFlows record) {
		return super.getSqlSession().insert("ArticleFlowsMapper.insertSelective", record);
	}
	
	
	public List<ArticleFlows> selectByExample(ArticleFlowsQuery example) {
		return super.getSqlSession().selectList("ArticleFlowsMapper.selectByExample", example);
	}
	
	
	public List<ArticleFlows> selectPageByExample(ArticleFlowsQuery example) {
		return super.getSqlSession().selectList("ArticleFlowsMapper.selectPageByExample", example);
	}
	
	
	public ArticleFlows selectByPrimaryKey(Long id) {
		return super.getSqlSession().selectOne("ArticleFlowsMapper.selectByPrimaryKey", id);
	}
	
	
	public int updateByPrimaryKeySelective(ArticleFlows record) {
		return super.getSqlSession().update("ArticleFlowsMapper.updateByPrimaryKeySelective", record);
	}
	
	
	public int updateByPrimaryKey(ArticleFlows record) {
		return super.getSqlSession().update("ArticleFlowsMapper.updateByPrimaryKey", record);
	}


	public long selectMaxId(String articleId) {
		// TODO Auto-generated method stub
		
		Object id = super.getSqlSession().selectOne("ArticleFlowsMapper.selectMaxId",articleId);
		
		return (Long) (id==null?1L:id);
	}


	public ArticleFlows queryFlowsDetail(ArticleFlowsQuery query) {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectOne("ArticleFlowsMapper.queryFlowsDetail",query);
	}


	public int updateFlows(ArticleFlows record) {
		// TODO Auto-generated method stub
		return super.getSqlSession().update("ArticleFlowsMapper.updateFlows",record);
	}


	public int updateExpertFlows(ArticleFlows record) {
		// TODO Auto-generated method stub
		return super.getSqlSession().update("ArticleFlowsMapper.updateExpertFlows",record);
	}


	public Opinion queryOpinion(Opinion opinion) {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectOne("ArticleFlowsMapper.selectOpinion", opinion);
	}
	public Opinion queryOpinion1(String articleId) {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectOne("ArticleFlowsMapper.selectOpinion1", articleId);
	}
	public Opinion queryOpinion2(String articleId) {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectOne("ArticleFlowsMapper.selectOpinion2", articleId);
	}

}
