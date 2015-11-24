package com.cn.periodical.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.periodical.dao.ArticleQueryDao;
import com.cn.periodical.dao.BaseDao;
import com.cn.periodical.dao.BizPeriodicalDao;
import com.cn.periodical.pojo.AuthorQueryDetail;
import com.cn.periodical.pojo.BizPeriodical;
import com.cn.periodical.pojo.EditorQueryArtilces;
import com.cn.periodical.request.ArticleQueryReqDto;
import com.cn.periodical.response.ArticleQueryRespDto;

@Repository("bizPeriodicalDao")
public class BizPeriodicalDaoImpl extends BaseDao implements BizPeriodicalDao {

	public BizPeriodicalDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	public List<BizPeriodical> queryPeriodicalInfosForIssueEditor(BizPeriodical reqDto) {
		// TODO Auto-generated method stub
		return super.getSqlSession().selectList("BizPeriodicalMapper.queryPeriodicalInfosForIssueEditor", reqDto);
	}
}
