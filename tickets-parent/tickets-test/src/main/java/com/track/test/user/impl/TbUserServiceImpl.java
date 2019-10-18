package com.track.test.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.track.common.enums.system.ResultCode;
import com.track.core.base.service.AbstractService;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.test.TbUserPo;
import com.track.data.dto.test.save.SaveTestUsersDto;
import com.track.data.dto.test.select.SearchUsersDto;
import com.track.data.mapper.test.TbUserMapper;
import com.track.data.vo.test.SearchUsersVo;
import com.track.test.user.ITbUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-09-01
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TbUserServiceImpl extends AbstractService<TbUserMapper,TbUserPo> implements ITbUserService {

    @Autowired
    private TbUserMapper mapper;

    @Override
    public void test(String username) {

        TbUserPo user = mapper.selectOne(new QueryWrapper<TbUserPo>()
                .lambda().eq(TbUserPo::getName,username));
        System.out.println(new Gson().toJson(user));
    }

    /**
     * 测试分页查询、数据校验
     *
     * @param searchUsersDto
     * @return
     */
    @Override
    public PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto) {

        //测试mybatis-plus结合lambda表达式使用
        List<TbUserPo> userPos = mapper.selectList(new QueryWrapper<TbUserPo>().lambda()
                .and(obj->obj.eq(TbUserPo::getType,searchUsersDto.getType())
                .like(TbUserPo::getName,searchUsersDto.getName())));

        log.error(userPos.toString());

        Integer pageNo = searchUsersDto.getPageNo()==null ? defaultPageNo : searchUsersDto.getPageNo();
        Integer pageSize = searchUsersDto.getPageSize()==null ? defaultPageSize : searchUsersDto.getPageSize();

        //使用mybatis-plus分页插件进行分页查询
        PageInfo<SearchUsersVo> searchUsersVoPageInfo = PageHelper.startPage(pageNo,pageSize,defaultSoft)
                .doSelectPageInfo(()->mapper.searchUsers(searchUsersDto));

        //对分页后的数据进行处理
        searchUsersVoPageInfo.getList().forEach(a->{
            //逻辑处理
            if (a.getId() == 2){
                a.setAge(1000);
            }
        });
        return searchUsersVoPageInfo;
    }

    /**
     * 保存用户--测试事务和异常处理
     *
     * @param saveTestUsersDto
     * @return
     */
    @Override
    public void saveUsers(SaveTestUsersDto saveTestUsersDto) {

        //测试异常处理
        List<String> nameList = mapper.selectList(null).stream()
                .map(a->a.getName()).collect(Collectors.toList());

        if (nameList.contains(saveTestUsersDto.getName())){
            throw new ServiceException(ResultCode.DUPLICATION,String.format("已经存在名称为【%s】的用户，请检查", saveTestUsersDto.getName()));
        }

        TbUserPo userPo = new TbUserPo();
        BeanUtils.copyProperties(saveTestUsersDto,userPo);

        mapper.insert(userPo);

        //测试事务回滚

//        int a = 1/0;
//
//        log.error(String.valueOf(a));
    }
}
