package com.track.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.data.domain.po.order.OmAccountLogPo;
import com.track.data.dto.manage.order.search.SearchAccountLogDto;
import com.track.data.mapper.order.OmAccountLogMapper;
import com.track.data.vo.manage.order.AccountLogVo;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmAccountLogService;
import com.track.core.base.service.AbstractService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 账目流水表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmAccountLogServiceImpl extends AbstractService<OmAccountLogMapper, OmAccountLogPo> implements IOmAccountLogService {

    @Autowired
    private OmAccountLogMapper mapper;

    /**
     * @Author yeJH
     * @Date 2019/11/13 16:11
     * @Description 查询系统流水
     *
     * @Update yeJH
     *
     * @param  searchAccountLogDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.AccountLogVo>
     **/
    @Override
    public PageInfo<AccountLogVo> searchAccountLog(SearchAccountLogDto searchAccountLogDto) {
        Integer pageNo = searchAccountLogDto.getPageNo()==null ? defaultPageNo : searchAccountLogDto.getPageNo();
        Integer pageSize = searchAccountLogDto.getPageSize()==null ? defaultPageSize : searchAccountLogDto.getPageSize();

        PageInfo<AccountLogVo> accountLogVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchAccountLog(searchAccountLogDto));

        return accountLogVoPageInfo;
    }
}
