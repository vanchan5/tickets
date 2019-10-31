package com.track.order.service;

import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.order.OmFeedBackPo;
import com.track.core.base.service.Service;
import com.track.data.dto.manage.feedBack.search.SearchFeedBackDto;
import com.track.data.vo.manage.feedBack.SearchFeedBackVo;

import java.util.List;

/**
 * <p>
 * 意见反馈表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface IOmFeedBackService extends Service<OmFeedBackPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-30 22:23
     * @Description //content
     *
     * @Update chauncy
     *
     * @param  content
     * @return void
     **/
    void saveFeedBack(String content);

    /**
     * @Author chauncy
     * @Date 2019-10-30 22:45
     * @Description //条件分页查询反馈信息
     *
     * @Update chauncy
     *
     * @param  searchFeedBackDto
     * @return java.lang.Object
     **/
    PageInfo<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto);

    /**
     * @Author chauncy
     * @Date 2019-10-30 23:26
     * @Description //批量处理反馈信息
     *
     * @Update chauncy
     *
     * @param  ids
     * @return void
     **/
    void dealWithFeedBack(List<Long> ids);
}
