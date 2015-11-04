/*
 * Powered By code-generator
 * Since 2015 - 2015
 */

package com.cn.periodical.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.periodical.dao.ArticleInfoDao;
import com.cn.periodical.pojo.ArticleInfo;
import com.cn.periodical.pojo.ArticleInfoQuery;
import com.cn.periodical.dao.BaseDao;

@Repository("articleInfoDao")
public class ArticleInfoDaoImpl extends BaseDao implements ArticleInfoDao {
	
	
	public int countByExample(ArticleInfoQuery example) {
		return super.getSqlSession().selectOne("ArticleInfoMapper.countByExample", example);
	}
	
	
	public int deleteByExample(ArticleInfoQuery example) {
		return super.getSqlSession().delete("ArticleInfoMapper.deleteByExample", example);
	}
	
	
	public int deleteByPrimaryKey(Long id) {
		return super.getSqlSession().delete("ArticleInfoMapper.deleteByPrimaryKey", id);
	}
	
	
	public int insert(ArticleInfo record) {
		return super.getSqlSession().insert("ArticleInfoMapper.insert", record);
	}
	
	
	public int insertSelective(ArticleInfo record) {
		return super.getSqlSession().insert("ArticleInfoMapper.insertSelective", record);
	}
	
	
	public List<ArticleInfo> selectByExample(ArticleInfoQuery example) {
		return super.getSqlSession().selectList("ArticleInfoMapper.selectByExample", example);
	}
	
	
	public List<ArticleInfo> selectPageByExample(ArticleInfoQuery example) {
		return super.getSqlSession().selectList("ArticleInfoMapper.selectPageByExample", example);
	}
	
	
	public ArticleInfo selectByPrimaryKey(Long id) {
		return super.getSqlSession().selectOne("ArticleInfoMapper.selectByPrimaryKey", id);
	}
	
	
	public int updateByPrimaryKeySelective(ArticleInfo record) {
		return super.getSqlSession().update("ArticleInfoMapper.updateByPrimaryKeySelective", record);
	}
	
	
	public int updateByPrimaryKey(ArticleInfo record) {
		return super.getSqlSession().update("ArticleInfoMapper.updateByPrimaryKey", record);
	}
	
}