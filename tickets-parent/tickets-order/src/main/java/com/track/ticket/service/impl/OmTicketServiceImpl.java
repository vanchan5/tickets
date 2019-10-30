package com.track.ticket.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.track.common.enums.manage.area.AreaRegionLevelEnum;
import com.track.common.enums.system.ResultCode;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.ticket.*;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.ticket.SearchTicketDto;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.dto.manage.ticket.save.SaveTicketDto;
import com.track.data.dto.manage.ticket.search.SearchManageTicketDto;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.mapper.ticket.*;
import com.track.data.vo.applet.ticket.*;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;
import com.track.data.vo.manage.ticket.TicketGradeInfoVo;
import com.track.data.vo.manage.ticket.TicketSeatInfoVo;
import com.track.ticket.service.IOmSceneRelGradeService;
import com.track.ticket.service.IOmTicketSceneService;
import com.track.ticket.service.IOmTicketSeatService;
import com.track.ticket.service.IOmTicketService;
import com.track.core.base.service.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 门票信息表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmTicketServiceImpl extends AbstractService<OmTicketMapper, OmTicketPo> implements IOmTicketService {

    @Autowired
    private OmTicketMapper mapper;

    @Autowired
    private OmTicketDetailMapper omTicketDetailMapper;

    @Autowired
    private OmTicketSceneMapper omTicketSceneMapper;

    @Autowired
    private OmTicketGradeMapper omTicketGradeMapper;

    @Autowired
    private OmTicketSeatMapper omTicketSeatMapper;

    @Autowired
    private OmSceneRelGradeMapper omSceneRelGradeMapper;

    @Autowired
    private OmOrderMapper omOrderMapper;

    @Autowired
    private AreaRegionMapper areaRegionMapper;

    @Autowired
    private BasicSettingMapper basicSettingMapper;

    @Autowired
    private IOmTicketSceneService omTicketSceneService;

    @Autowired
    private IOmTicketSeatService omTicketSeatService;

    @Autowired
    private IOmSceneRelGradeService omSceneRelGradeService;

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:30
     * @Description 查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchManageTicketDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.ticket.ManageTicketListVo>
     **/
    @Override
    public PageInfo<ManageTicketListVo> searchManageTicketList(SearchManageTicketDto searchManageTicketDto) {

        Integer pageNo = searchManageTicketDto.getPageNo()==null ? defaultPageNo : searchManageTicketDto.getPageNo();
        Integer pageSize = searchManageTicketDto.getPageSize()==null ? defaultPageSize : searchManageTicketDto.getPageSize();

        PageInfo<ManageTicketListVo> manageTicketListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchManageTicketList(searchManageTicketDto));

        return manageTicketListVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/25 12:39
     * @Description 演唱会门票上下架
     *
     * @Update yeJH
     *
     * @param  editEnableDto
     * @return void
     **/
    @Override
    public void publicState(EditEnabledDto editEnableDto) {
        UpdateWrapper<OmTicketPo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().in(OmTicketPo::getId, Arrays.asList(editEnableDto.getIds()))
                .set(OmTicketPo::getPublishState, editEnableDto.getEnabled());

        this.update(updateWrapper);
    }

    /**
     * @Author yeJH
     * @Date 2019/10/25 16:18
     * @Description 编辑时根据门票id获取门票详情
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return com.track.data.vo.manage.ticket.ManageTicketInfoVo
     **/
    @Override
    public ManageTicketInfoVo getTicket(Long ticketId) {

        OmTicketPo omTicketPo = mapper.selectById(ticketId);
        if(null == omTicketPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "门票记录不存在");
        }

        //获取门票基本信息
        ManageTicketInfoVo manageTicketInfoVo = mapper.getTicketInfo(ticketId);

        //获取门票档次及作座位区信息
        List<TicketGradeInfoVo> ticketGradeInfoList = mapper.getTicketGradeInfo(ticketId);
        if(null != ticketGradeInfoList && ticketGradeInfoList.size() > 0) {
            //当前座位号
            int currentSum = 0;
            for(TicketGradeInfoVo ticketGradeInfoVo : ticketGradeInfoList) {
                if(null != ticketGradeInfoVo.getTicketSeatInfoList() && ticketGradeInfoVo.getTicketSeatInfoList().size() > 0) {
                    for(TicketSeatInfoVo ticketSeatInfoVo : ticketGradeInfoVo.getTicketSeatInfoList()) {
                        //当前这排座位的最小编号
                        int minRange = currentSum + 1;
                        //当前这排座位的最大编号
                        int maxRange = currentSum + ticketSeatInfoVo.getSeatSum();
                        ticketSeatInfoVo.setMinRange(minRange);
                        ticketSeatInfoVo.setMaxRange(maxRange);
                        currentSum = maxRange;
                    }
                }
            }
        }
        manageTicketInfoVo.setTicketGradeInfoList(ticketGradeInfoList);

        return manageTicketInfoVo;

    }

    /**
     * @Author yeJH
     * @Date 2019/10/28 18:07
     * @Description 新增/编辑演唱会门票
     * 已经开始的演唱会门票以及已经有用户购买的演唱会门票不允许编辑
     *
     * @Update yeJH
     *
     * @param  saveTicketDto  新增/编辑演唱会门票参数
     * @param  operator  操作用户
     * @return void
     **/
    @Override
    public void saveTicket(SaveTicketDto saveTicketDto, UmUserPo operator) {
        if(null != saveTicketDto.getTicketId()) {
            OmTicketPo omTicketPo = mapper.selectById(saveTicketDto.getTicketId());
            if(null == omTicketPo) {
                throw new ServiceException(ResultCode.NO_EXISTS, "，演唱会门票记录不存在");
            } else if(omTicketPo.getPublishState()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "活动上架中，不能编辑");
            }
            QueryWrapper<OmTicketScenePo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(OmTicketScenePo::getTicketId, omTicketPo.getId())
                    .lt(OmTicketScenePo::getStartTime, LocalDateTime.now());
            if(omTicketSceneMapper.selectCount(queryWrapper) > 0) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "活动时间已开始，不能编辑");
            }
            //判断演唱会门票是否已经有人下单了
            boolean isOrder = omOrderMapper.isOrderByTicketId(omTicketPo.getId());
            if(isOrder) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "演唱会门票已经有用户下单，不能编辑");
            }
            //删除之前的门票关联信息  重新插入数据
            //删除场次信息
            omTicketSceneMapper.delete(new QueryWrapper<OmTicketScenePo>().lambda().and(obj -> obj
                    .eq(OmTicketScenePo::getTicketId, omTicketPo.getId())));
            //删除档次信息
            omTicketGradeMapper.delete(new QueryWrapper<OmTicketGradePo>().lambda().and(obj -> obj
                    .eq(OmTicketGradePo::getTicketId, omTicketPo.getId())));
            //删除座位区信息
            omTicketSeatMapper.delete(new QueryWrapper<OmTicketSeatPo>().lambda().and(obj -> obj
                    .eq(OmTicketSeatPo::getTicketId, omTicketPo.getId())));
            //删除场次座位区关联的座位信息
            omSceneRelGradeMapper.delete(new QueryWrapper<OmSceneRelGradePo>().lambda().and(obj -> obj
                    .eq(OmSceneRelGradePo::getTicketId, omTicketPo.getId())));
            //新增
            insertTicket(saveTicketDto, operator);
        } else {
            //新增
            insertTicket(saveTicketDto, operator);
        }
    }

    /**
     * @Author yeJH
     * @Date 2019/10/28 20:08
     * @Description 新增演唱会门票
     *
     * @Update yeJH
     *
     * @param  saveTicketDto
     * @param  operator
     * @return void
     **/
    private void insertTicket(SaveTicketDto saveTicketDto, UmUserPo operator) {
        OmTicketPo omTicketPo = new OmTicketPo();
        BeanUtils.copyProperties(saveTicketDto, omTicketPo);
        AreaRegionPo areaRegionPo = areaRegionMapper.selectById(omTicketPo);
        if(null != areaRegionPo && AreaRegionLevelEnum.DISTRICT.getId().equals(areaRegionPo.getLevelType())) {
            //查询省市区  地点等级为区（县）级别
            omTicketPo.setAddrName(areaRegionPo.getMergerName().replace(",", "/"));
        } else {
            //地区id  addrId  查询不到记录，或不是区（县）级别
            throw new ServiceException(ResultCode.PARAM_ERROR, "查询不到地区记录，或地区不是区（县）级别");
        }
        //默认下架状态
        omTicketPo.setPublishState(false);
        //操作人员
        omTicketPo.setCreateBy(operator.getId());
        //新增/编辑门票详情信息   购买须知以及详情的 html文本
        OmTicketDetailPo omTicketDetailPo = new OmTicketDetailPo();
        BeanUtils.copyProperties(saveTicketDto, omTicketDetailPo);
        if(null == saveTicketDto.getTicketId()) {
            //新增门票基本信息
            mapper.insert(omTicketPo);
            //门票详情的id = 门票的id
            omTicketDetailPo.setId(omTicketPo.getId());
            omTicketDetailMapper.insert(omTicketDetailPo);
        } else {
            //编辑门票基本信息
            omTicketPo.setId(saveTicketDto.getTicketId());
            mapper.updateById(omTicketPo);
            omTicketDetailPo.setId(omTicketPo.getId());
            omTicketDetailMapper.updateById(omTicketDetailPo);
        }

        //插入关联
        insertTicketRel(saveTicketDto, omTicketPo.getId());
    }

    /**
     * @Author yeJH
     * @Date 2019/10/28 23:10
     * @Description 插入门票关联  场次  档次  座位区  座位数
     *
     * @Update yeJH
     *
     * @param  saveTicketDto
     * @return void
     **/
    private void insertTicketRel(SaveTicketDto saveTicketDto, Long ticketId) {

        //1.保存门票场次信息
        List<OmTicketScenePo> omTicketScenePoList = new ArrayList<>();
        saveTicketDto.getTicketSceneList().stream().forEach(saveTicketSceneDto -> {
            OmTicketScenePo omTicketScenePo = new OmTicketScenePo();
            //场次名称
            omTicketScenePo.setName(saveTicketSceneDto.getSceneName());
            //场次开始时间
            omTicketScenePo.setStartTime(saveTicketSceneDto.getStartTime());
            //门票id
            omTicketScenePo.setTicketId(ticketId);
            omTicketScenePoList.add(omTicketScenePo);
        });
        //批量插入
        omTicketSceneService.saveBatch(omTicketScenePoList);

        //2.保存门票档次以及座位区信息（无法批量插入 座位区信息关联档次id）
        saveTicketDto.getTicketGradeList().stream().forEach(saveTicketGradeDto -> {
            OmTicketGradePo omTicketGradePo = new OmTicketGradePo();
            //门票id
            omTicketGradePo.setTicketId(ticketId);
            //档次名称
            omTicketGradePo.setName(saveTicketGradeDto.getGradeName());
            //销售价
            omTicketGradePo.setSellPrice(saveTicketGradeDto.getSellPrice());
            //该档位有多少排座位
            omTicketGradePo.setRowSum(saveTicketGradeDto.getRowSum());
            //插入档次信息
            omTicketGradeMapper.insert(omTicketGradePo);
            //3 保存该档次下的座位区
            List<OmTicketSeatPo> omTicketSeatPoList = new ArrayList<>();
            saveTicketGradeDto.getTicketSeatList().stream().forEach(saveTicketSeatDto -> {
                OmTicketSeatPo omTicketSeatPo = new OmTicketSeatPo();
                //座位区（第几排）
                omTicketSeatPo.setSeatRow(saveTicketSeatDto.getSeatRow());
                //当前座位区座位数量
                omTicketSeatPo.setSeatSum(saveTicketSeatDto.getSeatSum());
                //档位id
                omTicketSeatPo.setGradeId(omTicketGradePo.getId());
                omTicketSeatPoList.add(omTicketSeatPo);
            });
            //批量插入座位区记录
            omTicketSeatService.saveBatch(omTicketSeatPoList);
        });

        //4.保存座位数  场次跟档次的关联
        List<OmSceneRelGradePo> omSceneRelGradePoList = new ArrayList<>();
        //获取已插入的档次
        QueryWrapper<OmTicketSeatPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OmTicketSeatPo::getTicketId, ticketId);
        List<OmTicketSeatPo> omTicketSeatPoList = omTicketSeatMapper.selectList(queryWrapper);
        omTicketScenePoList.stream().forEach(omTicketScenePo -> {
            omTicketSeatPoList.stream().forEach(omTicketSeatPo -> {
                OmSceneRelGradePo omSceneRelGradePo = new OmSceneRelGradePo();
                //门票id
                omSceneRelGradePo.setTicketId(ticketId);
                //门票场次id
                omSceneRelGradePo.setSceneId(omTicketScenePo.getId());
                //门票档次id
                omSceneRelGradePo.setGradeId(omTicketSeatPo.getGradeId());
                //门票座位区id
                omSceneRelGradePo.setSeatId(omTicketSeatPo.getId());
                //剩余总座位数
                omSceneRelGradePo.setRemainingSum(omTicketSeatPo.getSeatSum());
                omSceneRelGradePoList.add(omSceneRelGradePo);
            });
        });
        //批量插入关联
        omSceneRelGradeService.saveBatch(omSceneRelGradePoList);
    }


    /**
     * @Author yeJH
     * @Date 2019/10/29 20:43
     * @Description 小程序条件查询门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.applet.ticket.TicketListVo>
     **/
    @Override
    public PageInfo<TicketListVo> searchTicketList(SearchTicketDto searchTicketDto) {

        Integer pageNo = searchTicketDto.getPageNo()==null ? defaultPageNo : searchTicketDto.getPageNo();
        Integer pageSize = searchTicketDto.getPageSize()==null ? defaultPageSize : searchTicketDto.getPageSize();

        PageInfo<TicketListVo> ticketListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchTicketList(searchTicketDto));

        return ticketListVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/30 10:38
     * @Description 小程序根据门票id获取演出详情
     *
     * @Update yeJH
     *
     * @param  ticketId 门票id
     * @return com.track.data.vo.applet.ticket.TicketDetailVo
     **/
    @Override
    public TicketDetailVo getTicketDetail(Long ticketId) {

        OmTicketPo omTicketPo = mapper.selectById(ticketId);
        if(null == omTicketPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "门票记录不存在");
        }

        //获取演出详情
        TicketDetailVo ticketDetailVo = mapper.getTicketDetail(ticketId);

        //获取客服电话
        BasicSettingPo basicSettingPo = basicSettingMapper.selectOne(new QueryWrapper<>());
        ticketDetailVo.setPhone(basicSettingPo.getPhone());

        //获取场次信息
        List<TicketSceneBaseVo> ticketSceneBaseList = omTicketSceneMapper.getTicketSceneBase(ticketId);
        ticketDetailVo.setTicketSceneBaseList(ticketSceneBaseList);

        //获取档次信息
        List<TicketGradeBaseVo> ticketGradeBaseList = omTicketGradeMapper.getTicketGradeBase(ticketId);
        ticketDetailVo.setTicketGradeBaseList(ticketGradeBaseList);

        //获取场次跟档次关联的信息（规格）
        List<SceneRelGradeInfoVo> sceneRelGradeInfoList = omSceneRelGradeMapper.getSceneRelGradeInfoList(ticketId);
        Map<String, SceneRelGradeInfoVo> sceneRelGradeInfoMap = new HashMap<>();
        if(null != sceneRelGradeInfoList && sceneRelGradeInfoList.size() > 0) {
            sceneRelGradeInfoList.stream().forEach(sceneRelGradeInfoVo -> {
                //组装场次跟档次，sceneId:gradeId;  作为key，sceneRelGradeInfoVo作为value
                String key = sceneRelGradeInfoVo.getSceneId() + ":" + sceneRelGradeInfoVo.getGradeId() + ";";
                sceneRelGradeInfoMap.put(key, sceneRelGradeInfoVo);
            });
        }
        ticketDetailVo.setSceneRelGradeInfo(sceneRelGradeInfoMap);

        return ticketDetailVo;
    }

}
