package com.cn.periodical.manager;

import com.cn.periodical.pojo.PeriodicalChongtouLog;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuguodong
 * Date: 16-1-18
 * Time: 上午10:25
 * To change this template use File | Settings | File Templates.
 */
public interface PeriodicalChongtouLogManager {

    public int insert(PeriodicalChongtouLog periodicalChongtouLog);

    public List<PeriodicalChongtouLog> selectByCondition(PeriodicalChongtouLog periodicalChongtouLog);


}
