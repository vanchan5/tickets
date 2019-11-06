package com.track.elk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.elk.SysLogPo;
import com.track.data.dto.elk.SearchLogDto;
import com.track.data.mapper.elk.SysLogMapper;
import com.track.elk.service.ISysLogService;
import com.track.core.base.service.AbstractService;
import com.track.elk.vo.SearchLogVo;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 日志管理 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysLogServiceImpl extends AbstractService<SysLogMapper,SysLogPo> implements ISysLogService {

 @Autowired
 private SysLogMapper mapper;


 /**
  * @Author chauncy
  * @Date 2019-11-01 10:41
  * @Description //删除全部本地日志
  *
  * @Update chauncy
  *
  * @param
  * @return void
  **/
 @Override
 public void deleteAll() {

  mapper.delete(new QueryWrapper<SysLogPo>());
 }

 /**
  * @Author chauncy
  * @Date 2019-11-01 11:12
  * @Description //批量删除本地日志
  *
  * @Update chauncy
  *
  * @param  ids
  * @return void
  **/
 @Override
 public void delByIds(List<Long> ids) {

  ids.forEach(a->{
   SysLogPo sysLogPo = mapper.selectById(a);
   if (sysLogPo == null){
    throw new ServiceException(ResultCode.FAIL,String.format("id为【%s】的日志记录不存在，请刷新测试",a));
   }
  });
  mapper.deleteBatchIds(ids);
 }

 /**
  * @Author chauncy
  * @Date 2019-11-01 11:39
  * @Description //分页条件查询本地日志
  *
  * @Update chauncy
  *
  * @param  searchLogDto
  * @return com.github.pagehelper.PageInfo<com.track.elk.vo.SearchLogVo>
  **/
 @Override
 public PageInfo<SearchLogVo> searchLog(SearchLogDto searchLogDto) {

  Integer pageNo = searchLogDto.getPageNo() == null ? defaultPageNo : searchLogDto.getPageNo();
  Integer pageSize = searchLogDto.getPageSize() == null ? defaultPageSize : searchLogDto.getPageSize();

  PageInfo<SearchLogVo> logVoPageInfo = PageHelper.startPage(pageNo,pageSize,defaultSoft)
          .doSelectPageInfo(()->mapper.searchLog(searchLogDto));

  return logVoPageInfo;
 }
}

