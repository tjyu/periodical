package com.cn.periodical.service;

import java.util.List;

import com.cn.periodical.response.AuthorArticleQueryRespDto;

public interface AuthorArticleQueryService {
	public List<AuthorArticleQueryRespDto> queryArticleInfo(String userId);
}