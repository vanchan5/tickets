package com.track.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.track.common.enums.manage.order.OrderStateEnum;
import com.track.common.enums.manage.order.SearchOrderStateEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.BigDecimalUtil;
import com.track.common.utils.SnowFlakeUtil;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.order.OmOrderPo;
import com.track.data.domain.po.order.OmOrderRelSeatPo;
import com.track.data.domain.po.order.OmTicketTempPo;
import com.track.data.domain.po.ticket.OmSceneRelGradePo;
import com.track.data.domain.po.ticket.OmTicketGradePo;
import com.track.data.domain.po.ticket.OmTicketSeatPo;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.dto.applet.order.SearchMyOrderDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.mapper.order.OmOrderRelSeatMapper;
import com.track.data.mapper.order.OmTicketTempMapper;
import com.track.data.mapper.ticket.OmSceneRelGradeMapper;
import com.track.data.mapper.ticket.OmTicketGradeMapper;
import com.track.data.mapper.ticket.OmTicketSeatMapper;
import com.track.data.vo.applet.order.MyOrderDetailVo;
import com.track.data.vo.applet.order.MyOrderListVo;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.applet.ticket.SceneRelGradeInfoVo;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmOrderRelSeatService;
import com.track.order.service.IOmOrderService;
import com.track.core.base.service.AbstractService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class OmOrderServiceImpl extends AbstractService<OmOrderMapper, OmOrderPo> implements IOmOrderService {

    @Autowired
    private OmOrderMapper mapper;

    @Autowired
    private OmTicketGradeMapper omTicketGradeMapper;

    @Autowired
    private OmTicketTempMapper omTicketTempMapper;

    @Autowired
    private OmSceneRelGradeMapper omSceneRelGradeMapper;

    @Autowired
    private OmTicketSeatMapper omTicketSeatMapper;

    @Autowired
    private IOmOrderRelSeatService omOrderRelSeatService;

    /**
     * @Author yeJH
     * @Date 2019/10/29 16:08
     * @Description 查询平台订单列表
     *
     * @Update yeJH
     *
     * @param  searchOrderDto  查询条件
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.manage.order.ManageOrderListVo>
     **/
    @Override
    public PageInfo<ManageOrderListVo> searchOrderList(SearchOrderDto searchOrderDto) {

        Integer pageNo = searchOrderDto.getPageNo()==null ? defaultPageNo : searchOrderDto.getPageNo();
        Integer pageSize = searchOrderDto.getPageSize()==null ? defaultPageSize : searchOrderDto.getPageSize();

        PageInfo<ManageOrderListVo> manageOrderListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchOrderList(searchOrderDto));

        manageOrderListVoPageInfo.getList().stream().forEach(manageOrderListVo -> {
            if(Strings.isNotBlank(manageOrderListVo.getSeatStr())) {
                //座位号
                List<String> seatStrList = Splitter.on(",").omitEmptyStrings().splitToList(manageOrderListVo.getSeatStr());
                manageOrderListVo.setSeatStrList(seatStrList);
            }

        });
        return manageOrderListVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/30 17:52
     * @Description 订单结算
     *
     *
     * @Update yeJH
     *
     * @param  orderSettlementDto 选择门票场次，档次，添加数量去结算
     * @return com.track.data.vo.applet.order.OrderSettlementVo
     **/
    @Override
    public OrderSettlementVo settlement(OrderSettlementDto orderSettlementDto) {

        //结算信息
        OrderSettlementVo orderSettlementVo = mapper.settlement(orderSettlementDto);
        if(null == orderSettlementVo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "不存在该数据");
        }
        if(orderSettlementDto.getOrderNum() > orderSettlementVo.getRemainingSum()) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "剩余座位不足");
        }

        //购买票数
        orderSettlementVo.setOrderNum(orderSettlementDto.getOrderNum());

        //总计金额  购买票数 * 单价
        BigDecimal payAmount = BigDecimalUtil.safeMultiply(orderSettlementDto.getOrderNum(),
                orderSettlementVo.getSellPrice());
        orderSettlementVo.setPayAmount(payAmount);

        return orderSettlementVo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/30 22:15
     * @Description 查询我的订单列表
     *
     * @Update yeJH
     *
     * @param  searchMyOrderDto  查询条件
     * @return com.github.pagehelper.PageInfo<com.track.data.vo.applet.order.MyOrderListVo>
     **/
    @Override
    public PageInfo<MyOrderListVo> searchMyOrder(SearchMyOrderDto searchMyOrderDto) {

        if(null == searchMyOrderDto.getOrderState()) {
            //默认全部
            searchMyOrderDto.setOrderState(SearchOrderStateEnum.ALL_LIST);
        }
        Integer pageNo = searchMyOrderDto.getPageNo()==null ? defaultPageNo : searchMyOrderDto.getPageNo();
        Integer pageSize = searchMyOrderDto.getPageSize()==null ? defaultPageSize : searchMyOrderDto.getPageSize();

        PageInfo<MyOrderListVo> myOrderListVoPageInfo = PageHelper.startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> mapper.searchMyOrder(searchMyOrderDto));

        return myOrderListVoPageInfo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/31 10:01
     * @Description 根据订单id获取订单详情
     *
     * @Update yeJH
     *
     * @param  orderId 订单id
     * @return com.track.data.vo.applet.order.MyOrderDetailVo
     **/
    @Override
    public MyOrderDetailVo getOrderDetail(Long orderId) {

        OmOrderPo omOrderPo = mapper.selectById(orderId);
        if(null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单记录不存在");
        }
        MyOrderDetailVo myOrderDetailVo = mapper.getOrderDetail(orderId);
        if(null != myOrderDetailVo && Strings.isNotBlank(myOrderDetailVo.getSeatStr())) {
            //座位号
            List<String> seatStrList = Splitter.on(",").omitEmptyStrings().splitToList(myOrderDetailVo.getSeatStr());
            myOrderDetailVo.setSeatStrList(seatStrList);
        }

        return myOrderDetailVo;
    }

    /**
     * @Author yeJH
     * @Date 2019/10/31 12:04
     * @Description 订单下单提交
     *
     * @Update yeJH
     *
     * @param  orderSubmitDto 档次 场次  取票人  手机尾号  购票数量
     * @param  umUserPo 下单用户
     * @return java.lang.Long   订单id
     **/
    @Override
    public Long submit(OrderSubmitDto orderSubmitDto, UmUserPo umUserPo) {

        //获取用户选择的场次档次的剩余座位数
        Integer remainingSum = omSceneRelGradeMapper.getRemainingSum(orderSubmitDto);
        if(null == remainingSum || remainingSum < orderSubmitDto.getOrderNum()) {
            throw new ServiceException(ResultCode.FAIL, "剩余票数不足");
        }

        //插入订单信息
        OmOrderPo omOrderPo = new OmOrderPo();
        BeanUtils.copyProperties(orderSubmitDto, omOrderPo);
        //票号
        omOrderPo.setTicketNo(SnowFlakeUtil.getFlowIdInstance().nextId());
        //获取单价
        OmTicketGradePo omTicketGradePo = omTicketGradeMapper.selectById(orderSubmitDto.getGradeId());
        //支付金额
        BigDecimal payAmount = BigDecimalUtil.safeMultiply(omTicketGradePo.getSellPrice(), orderSubmitDto.getOrderNum());
        omOrderPo.setPayAmount(payAmount);
        //支付过期时间， 默认为2小时
        omOrderPo.setExpireTime(LocalDateTime.now().plusHours(2));
        //订单状态默认待付款
        omOrderPo.setState(OrderStateEnum.WAIT_PAY.getId());
        //下单用户id
        omOrderPo.setUmUserId(umUserPo.getId());
        mapper.insert(omOrderPo);

        //插入快照信息
        OmTicketTempPo omTicketTempPo = omTicketTempMapper.getTempByOrder(orderSubmitDto);
        //订单id
        omTicketTempPo.setOrderId(omOrderPo.getId());
        //购买票数
        omTicketTempPo.setOrderNum(orderSubmitDto.getOrderNum());
        omTicketTempMapper.insert(omTicketTempPo);

        //分配座位信息（用户提交订单不分配座位，等到支付完成再根据购票数去分配座位）
        //arrangeSeat(orderSubmitDto, omOrderPo.getId());


        return omOrderPo.getId();
    }

    /**
     * @Author yeJH
     * @Date 2019/10/31 17:30
     * @Description 根据下单信息给用户分配座位
     * 1.先查询是否存在哪一排的剩余座位数大于用户购票数  有则直接安排在同一排 没有则执行下一步
     * 2.查询出用户选择的场次、档次下演出门票还有剩余座位的座位区（多排），先安排排数靠前的座位，直到全部安排完成
     *
     * @Update yeJH
     *
     * @param  orderSubmitDto
     * @param  orderId
     * @return void
     **/
    private void arrangeSeat(OrderSubmitDto orderSubmitDto, Long orderId) {
        //查询该场次档次下是否存在某一排剩余座位数大于等于购票数 有则直接安排座位，没有则将用户安排在不同一排座位
        QueryWrapper<OmSceneRelGradePo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OmSceneRelGradePo::getSceneId, orderSubmitDto.getSceneId())
                .eq(OmSceneRelGradePo::getGradeId, orderSubmitDto.getGradeId())
                .ge(OmSceneRelGradePo::getRemainingSum, orderSubmitDto.getOrderNum());
        queryWrapper.lambda().last(" LIMIT 1");
        OmSceneRelGradePo omSceneRelGradePo = omSceneRelGradeMapper.selectOne(queryWrapper);

        List<OmOrderRelSeatPo> omOrderRelSeatPoList = new ArrayList<>();
        if(null != omSceneRelGradePo) {
            //存在满足在同一排座位的情况 给用户分配座位
            OmTicketSeatPo omTicketSeatPo = omTicketSeatMapper.selectById(omSceneRelGradePo.getSeatId());
            for(int i=0; i<orderSubmitDto.getOrderNum(); i++) {
                //安排座位 哪个座位区，哪一排座位，哪一个号码
                OmOrderRelSeatPo omOrderRelSeatPo = getOrderRelSeatPo(omTicketSeatPo, omSceneRelGradePo, orderId, i);
                omOrderRelSeatPoList.add(omOrderRelSeatPo);
            }
            //批量插入
            omOrderRelSeatService.saveBatch(omOrderRelSeatPoList);
        } else {
            //用户座位安排在不同一排
            //查询该场次档次下所有还有剩余座位数的座位区
            QueryWrapper<OmSceneRelGradePo> seatQueryWrapper = new QueryWrapper<>();
            seatQueryWrapper.lambda().eq(OmSceneRelGradePo::getSceneId, orderSubmitDto.getSceneId())
                    .eq(OmSceneRelGradePo::getGradeId, orderSubmitDto.getGradeId())
                    .gt(OmSceneRelGradePo::getRemainingSum, 0);
            List<OmSceneRelGradePo> omSceneRelGradePoList = omSceneRelGradeMapper.selectList(queryWrapper);
            //用户购票的还有多少座位没有安排
            Integer remainingNum = orderSubmitDto.getOrderNum();
            for (OmSceneRelGradePo seat : omSceneRelGradePoList) {
                //算出本排座位需要分配的座位数
                //1.当前排座位剩余座位数 < 剩余未安排的座位数  当前排座位全部分配
                //2.当前排座位剩余座位数 >= 剩余未安排的座位数  当前排座位只安排剩余未安排的座位数
                Integer needArrangeNum = seat.getRemainingSum() < remainingNum ? seat.getRemainingSum() : remainingNum;
                OmTicketSeatPo omTicketSeatPo = omTicketSeatMapper.selectById(seat.getSeatId());
                for (int i = 0; i < needArrangeNum; i++) {
                    //安排座位 哪个座位区，哪一排座位，哪一个号码
                    OmOrderRelSeatPo omOrderRelSeatPo = getOrderRelSeatPo(omTicketSeatPo, seat, orderId, i);
                    remainingNum--;
                    omOrderRelSeatPoList.add(omOrderRelSeatPo);
                }
            }
        }
    }

    /**
     * @Author yeJH
     * @Date 2019/10/31 20:10
     * @Description 根据用户下单信息给用户分配座位  插入OmOrderRelSeatPo
     *
     * @Update yeJH
     *
     * @param  omTicketSeatPo  座位区信息
     * @param  omSceneRelGradePo  座位区下面的某一排座位
     * @param  orderId  订单id
     * @param  arrangedNum  当前排正在安排的第几个座位
     * @return com.track.data.domain.po.order.OmOrderRelSeatPo
     **/
    private OmOrderRelSeatPo getOrderRelSeatPo(OmTicketSeatPo omTicketSeatPo,
                                               OmSceneRelGradePo omSceneRelGradePo,
                                               Long orderId,
                                               Integer arrangedNum) {
        OmOrderRelSeatPo omOrderRelSeatPo = new OmOrderRelSeatPo();
        omOrderRelSeatPo.setOrderId(orderId);
        omOrderRelSeatPo.setSeatId(omTicketSeatPo.getId());
        omOrderRelSeatPo.setSeatRow(omTicketSeatPo.getSeatRow());
        //获取座位号  当前排最大编号 - 剩余座位数 + 1 + i（第几张票）
        Integer seatNum = omTicketSeatPo.getMaxRange() - omSceneRelGradePo.getRemainingSum() + 1 + arrangedNum;
        omOrderRelSeatPo.setSeatNum(seatNum);
        //拼接信息  座位号  几排几座
        String seatStr = omOrderRelSeatPo.getSeatRow() + "排" + omOrderRelSeatPo.getSeatNum() + "座";
        omOrderRelSeatPo.setSeatStr(seatStr);

        return omOrderRelSeatPo;
    }
}
