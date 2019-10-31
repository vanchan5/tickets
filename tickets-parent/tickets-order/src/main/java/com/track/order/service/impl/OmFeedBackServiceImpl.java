package com.track.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.manage.user.UserTypeEnum;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.order.OmFeedBackPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.manage.feedBack.search.SearchFeedBackDto;
import com.track.data.mapper.order.OmFeedBackMapper;
import com.track.data.vo.manage.feedBack.SearchFeedBackVo;
import com.track.order.service.IOmFeedBackService;
import com.track.core.base.service.AbstractService;
import com.track.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 意见反馈表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmFeedBackServiceImpl extends AbstractService<OmFeedBackMapper, OmFeedBackPo> implements IOmFeedBackService {

    @Autowired
    private OmFeedBackMapper mapper;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @param content
     * @return void
     * @Author chauncy
     * @Date 2019-10-30 22:23
     * @Description //content
     * @Update chauncy
     **/
    @Override
    public void saveFeedBack(String content) {

        UmUserPo userPo = securityUtil.getSysCurrUser();
        if (userPo == null){
            throw new ServiceException(ResultCode.FAIL,"用户不存在!");
        }
        if (userPo.getUserType() != UserTypeEnum.WECHAT_USER.getId()){
            throw new ServiceException(ResultCode.FAIL,"您不是小程序用户!");
        }

        OmFeedBackPo feedBackPo = new OmFeedBackPo();
        feedBackPo.setId(null).setContent(content).setCreateBy(userPo.getId());
        mapper.insert(feedBackPo);

    }

    /**
     * @Author chauncy
     * @Date 2019-10-30 22:46
     * @Description //条件分页查询反馈信息
     *
     * @Update chauncy
     *
     * @param  searchFeedBackDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.feedBack.SearchFeedBackVo>
     **/
    @Override
    public PageInfo<SearchFeedBackVo> searchFeedBack(SearchFeedBackDto searchFeedBackDto) {

        Integer pageNo = searchFeedBackDto.getPageNo() == null ? defaultPageNo : searchFeedBackDto.getPageNo();
        Integer pageSize = searchFeedBackDto.getPageSize() == null ? defaultPageSize : searchFeedBackDto.getPageSize();

        if (searchFeedBackDto.getStartTime().isAfter(searchFeedBackDto.getEndTime())){
            throw new ServiceException(ResultCode.FAIL,"查询条件:反馈开始时间不能大于反馈截止时间");
        }

        PageInfo<SearchFeedBackVo> feedBackVoPageInfo = PageHelper.startPage(pageNo,pageSize)
                .doSelectPageInfo(()->mapper.searchFeedBack(searchFeedBackDto));

        return feedBackVoPageInfo;
    }

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
    @Override
    public void dealWithFeedBack(List<Long> ids) {

        UmUserPo umUserPo = securityUtil.getSysCurrUser();
        if (umUserPo.getUserType() == UserTypeEnum.WECHAT_USER.getId()){
            throw new ServiceException(ResultCode.FAIL,"您不是平台用户!");
        }

        ids.forEach(a->{
            OmFeedBackPo feedBackPo = mapper.selectById(a);
            if (feedBackPo == null){
                throw new ServiceException(ResultCode.FAIL,String.format("id为【%s】的反馈信息不存在!",a));
            }
            feedBackPo.setState(1);
            feedBackPo.setUpdateBy(umUserPo.getId());
            mapper.updateById(feedBackPo);
        });

    }
}
