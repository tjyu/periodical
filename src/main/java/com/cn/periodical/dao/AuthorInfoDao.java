/*
 * Powered By code-generator
 * Since 2015 - 2015
 */

package com.cn.periodical.dao;

import java.util.List;

import com.cn.periodical.pojo.AuthorInfo;
import com.cn.periodical.pojo.AuthorInfoQuery;

public interface AuthorInfoDao {
	int countByExample(AuthorInfoQuery example);
	
	int deleteByExample(AuthorInfoQuery example);
	
	int deleteByPrimaryKey(Long id);
	
	int insert(AuthorInfo record);
	
	int insertSelective(AuthorInfo record);
	
	List<AuthorInfo> selectByExample(AuthorInfoQuery example);
	
	List<AuthorInfo> selectPageByExample(AuthorInfoQuery example);
	
	AuthorInfo selectByPrimaryKey(Long id);
	
	int updateByPrimaryKeySelective(AuthorInfo record);
	
	int updateByPrimaryKey(AuthorInfo record);
}
