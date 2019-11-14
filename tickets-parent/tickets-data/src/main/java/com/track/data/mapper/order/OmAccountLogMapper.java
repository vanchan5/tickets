package com.track.data.mapper.order;

import com.track.data.domain.po.order.OmAccountLogPo;
import com.track.data.dto.manage.order.search.SearchAccountLogDto;
import com.track.data.mapper.base.IBaseMapper;
import com.track.data.vo.manage.order.AccountLogVo;

import java.util.List;

/**
 * <p>
 * 账目流水表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
public interface OmAccountLogMapper extends IBaseMapper<OmAccountLogPo> {

    /**
     * @Author yeJH
     * @Date 2019/11/13 16:16
     * @Description 查询系统流水
     *
     * @Update yeJH
     *
     * @param  searchAccountLogDto
     * @return void
     **/
    List<AccountLogVo> searchAccountLog(SearchAccountLogDto searchAccountLogDto);
}
