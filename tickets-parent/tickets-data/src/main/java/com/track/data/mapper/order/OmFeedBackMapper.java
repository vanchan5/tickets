package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmFeedBackPo;
import com.track.data.dto.manage.feedBack.search.SearchFeedBackDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.manage.feedBack.SearchFeedBackVo;

import java.util.List;

/**
 * <p>
 * 意见反馈表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
public interface OmFeedBackMapper extends IBaseMapper<OmFeedBackPo> {

    /**
     * @Author chauncy
     * @Date 2019-10-30 23:08
     * @Description //条件分页查询反馈信息
     *
     * @Update chauncy
     *
     * @param searchFeedBackDto
     * @return void
     **/
    List<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto);
}
