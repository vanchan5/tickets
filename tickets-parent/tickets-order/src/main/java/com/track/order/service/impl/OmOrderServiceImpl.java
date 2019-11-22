package com.track.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.track.common.constant.RabbitConstants;
import com.track.common.enums.manage.order.LogTypeEnum;
import com.track.common.enums.manage.order.OrderStateEnum;
import com.track.common.enums.manage.order.OrderStateExplainEnum;
import com.track.common.enums.manage.order.SearchOrderStateEnum;
import com.track.common.enums.system.ResultCode;
import com.track.common.utils.BigDecimalUtil;
import com.track.common.utils.SnowFlakeUtil;
import com.track.core.exception.ServiceException;
import com.track.data.domain.po.order.*;
import com.track.data.domain.po.ticket.*;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.applet.order.OrderSettlementDto;
import com.track.data.dto.applet.order.OrderSubmitDto;
import com.track.data.dto.applet.order.SearchMyOrderDto;
import com.track.data.dto.manage.order.search.OrderRefundDto;
import com.track.data.dto.manage.order.search.SearchOrderDto;
import com.track.data.mapper.order.OmAccountLogMapper;
import com.track.data.mapper.order.OmOrderMapper;
import com.track.data.mapper.order.OmRefundFailRecordMapper;
import com.track.data.mapper.order.OmTicketTempMapper;
import com.track.data.mapper.ticket.*;
import com.track.data.vo.applet.order.MyOrderDetailVo;
import com.track.data.vo.applet.order.MyOrderListVo;
import com.track.data.vo.applet.order.OrderSettlementVo;
import com.track.data.vo.manage.order.ManageOrderListVo;
import com.track.order.service.IOmOrderRelSeatService;
import com.track.order.service.IOmOrderService;
import com.track.core.base.service.AbstractService;
import com.track.order.service.IWxService;
import com.track.ticket.service.IOmSceneGradeRelSeatService;
import com.track.ticket.service.IOmSceneRelGradeService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

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
    private OmTicketSceneMapper omTicketSceneMapper;

    @Autowired
    private OmTicketTempMapper omTicketTempMapper;

    @Autowired
    private OmSceneRelGradeMapper omSceneRelGradeMapper;

    @Autowired
    private OmSceneGradeRelSeatMapper omSceneGradeRelSeatMapper;

    @Autowired
    private OmTicketSeatMapper omTicketSeatMapper;

    @Autowired
    private OmAccountLogMapper omAccountLogMapper;

    @Autowired
    private OmRefundFailRecordMapper omRefundFailRecordMapper;

    @Autowired
    private IOmOrderRelSeatService omOrderRelSeatService;

    @Autowired
    private IOmSceneGradeRelSeatService omSceneGradeRelSeatService;

    @Autowired
    private IWxService wxService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
            //对座位进行排序
            if(Strings.isNotBlank(manageOrderListVo.getSeatStr())) {
                List<String> seatStrList = Splitter.on(",").omitEmptyStrings().splitToList(manageOrderListVo.getSeatStr());
                List<String> seatList = new ArrayList(seatStrList);
                /*
                 * int compare(String o1, String o2) 返回一个基本类型的整型，
                 * 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。
                 */
                Collections.sort(seatList, (o1, o2) -> {
                    List<String> list1 = Splitter.on("排").omitEmptyStrings().splitToList(o1);
                    int seatRow1 = Integer.valueOf(list1.get(0));
                    int seatSum1 = Integer.valueOf(list1.get(1).substring(0,list1.get(1).length()-1));
                    List<String> list2 = Splitter.on("排").omitEmptyStrings().splitToList(o2);
                    int seatRow2 = Integer.valueOf(list2.get(0));
                    int seatSum2 = Integer.valueOf(list2.get(1).substring(0,list2.get(1).length()-1));
                    //排数小的小，排数一致座位号小的小
                    if(seatRow1 < seatRow2){
                        return -1;
                    } else if(seatRow1 > seatRow2){
                        return 1;
                    } else {
                        //同一排
                        if(seatSum1 > seatSum2) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                });
                manageOrderListVo.setSeatStrList(seatList);
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

        SearchOrderStateEnum orderState = SearchOrderStateEnum.getById(searchMyOrderDto.getOrderState());
        if(null == orderState) {
            //默认全部
            searchMyOrderDto.setOrderState(SearchOrderStateEnum.ALL_LIST.getId());
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
        //订单状态说明
        String stateExplain = OrderStateExplainEnum.getById(myOrderDetailVo.getOrderState()).getName();
        myOrderDetailVo.setStateExplain(stateExplain);
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

        //获取场次信息
        OmTicketScenePo omTicketScenePo = omTicketSceneMapper.findByRelId(orderSubmitDto.getRelId());
        if(null == omTicketScenePo || omTicketScenePo.getStartTime().compareTo(LocalDateTime.now()) < 0) {
            throw new ServiceException(ResultCode.FAIL, "售票截止");
        }
        //获取用户选择的场次档次关联的记录
        OmSceneRelGradePo omSceneRelGradePo = omSceneRelGradeMapper.getRemainingSum(orderSubmitDto.getRelId());
        if(null == omSceneRelGradePo) {
            throw new ServiceException(ResultCode.FAIL, "不存在该场次，档次");
        }
        if(omSceneRelGradePo.getRemainingSum() < orderSubmitDto.getOrderNum()) {
            throw new ServiceException(ResultCode.FAIL, "剩余票数不足");
        }


        //插入订单信息
        OmOrderPo omOrderPo = new OmOrderPo();
        BeanUtils.copyProperties(orderSubmitDto, omOrderPo);
        //获取单价
        OmTicketGradePo omTicketGradePo = omTicketGradeMapper.selectById(omSceneRelGradePo.getGradeId());
        //支付金额
        BigDecimal payAmount = BigDecimalUtil.safeMultiply(omTicketGradePo.getSellPrice(), orderSubmitDto.getOrderNum());
        omOrderPo.setPayAmount(payAmount);
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

        //扣减座位数
        omSceneRelGradePo.setRemainingSum(omSceneRelGradePo.getRemainingSum() - orderSubmitDto.getOrderNum());
        omSceneRelGradeMapper.updateById(omSceneRelGradePo);


        //过期时间  半小时
        String expireTime = 30 * 60 * 1000 + "";

        // 添加超时未支付自动取消订单延时队列
        this.rabbitTemplate.convertAndSend(RabbitConstants.ORDER_UNPAID_DELAY_EXCHANGE,
                RabbitConstants.DELAY_ROUTING_KEY, omOrderPo.getId(), message -> {

            message.getMessageProperties().setExpiration(expireTime);
            return message;
        });

        return omOrderPo.getId();
    }

    /**
     * @Author yeJH
     * @Date 2019/10/31 17:30
     * @Description 支付成功根据下单信息给用户分配座位
     * 1.先查询是否存在哪一排的剩余座位数大于用户购票数  有则直接安排在同一排 没有则执行下一步
     * 2.查询出用户选择的场次、档次下演出门票还有剩余座位的座位区（多排），先安排排数靠前的座位，直到全部安排完成
     *
     * @Update yeJH
     *
     * @param  omOrderPo
     * @return void
     **/
    private void arrangeSeat(OmOrderPo omOrderPo) {
        //查询该场次档次下是否存在某一排剩余座位数大于等于购票数 有则直接安排座位，没有则将用户安排在不同一排座位

        //查找快照信息
        QueryWrapper<OmTicketTempPo> queryTicketTemp = new QueryWrapper<>();
        queryTicketTemp.lambda().eq(OmTicketTempPo::getOrderId, omOrderPo.getId());
        OmTicketTempPo omTicketTempPo = omTicketTempMapper.selectOne(queryTicketTemp);

        //OmSceneRelGradePo omSceneRelGradePo = omSceneRelGradeMapper.selectById(omTicketTempPo.getRelId());
        //判断是否存在满足给用户安排在同一排座位的座位区
        QueryWrapper<OmSceneGradeRelSeatPo> queryRelSeat = new QueryWrapper<>();
        queryRelSeat.lambda().eq(OmSceneGradeRelSeatPo::getRelId, omTicketTempPo.getRelId())
                .ge(OmSceneGradeRelSeatPo::getRemainingSum, omTicketTempPo.getOrderNum());
        queryRelSeat.last(" LIMIT 1 ");
        OmSceneGradeRelSeatPo omSceneGradeRelSeatPo = omSceneGradeRelSeatMapper.selectOne(queryRelSeat);

        List<OmOrderRelSeatPo> omOrderRelSeatPoList = new ArrayList<>();
        if(null != omSceneGradeRelSeatPo) {
            //加锁
            omSceneGradeRelSeatPo = omSceneGradeRelSeatMapper.selectByIdForUpdate(omSceneGradeRelSeatPo.getId());
            //存在满足在同一排座位的情况 给用户分配座位
            OmTicketSeatPo omTicketSeatPo = omTicketSeatMapper.selectById(omSceneGradeRelSeatPo.getSeatId());
            for(int i=0; i<omTicketTempPo.getOrderNum(); i++) {
                //安排座位 哪个座位区，哪一排座位，哪一个号码
                OmOrderRelSeatPo omOrderRelSeatPo =
                        getOrderRelSeatPo(omTicketSeatPo, omSceneGradeRelSeatPo, omOrderPo.getId(), i);
                omOrderRelSeatPoList.add(omOrderRelSeatPo);
            }

            //批量插入
            omOrderRelSeatService.saveBatch(omOrderRelSeatPoList);
            //修改剩余座位  剩余座位 - 购买票数
            omSceneGradeRelSeatPo.setRemainingSum(omSceneGradeRelSeatPo.getRemainingSum() - omTicketTempPo.getOrderNum());
            omSceneGradeRelSeatMapper.updateById(omSceneGradeRelSeatPo);
        } else {
            //用户座位安排在不同一排
            //查询该场次档次下所有还有剩余座位数的座位区  加锁
            List<OmSceneGradeRelSeatPo> omSceneGradeRelSeatPoList =
                    omSceneGradeRelSeatMapper.selectListForUpdate(omTicketTempPo.getRelId());
            //用户购票的还有多少座位没有安排
            Integer remainingNum = omTicketTempPo.getOrderNum();
            for (OmSceneGradeRelSeatPo seat : omSceneGradeRelSeatPoList) {
                //算出本排座位需要分配的座位数
                //1.当前排座位剩余座位数 < 剩余未安排的座位数  当前排座位全部分配
                //2.当前排座位剩余座位数 >= 剩余未安排的座位数  当前排座位只安排剩余未安排的座位数
                Integer needArrangeNum = seat.getRemainingSum() < remainingNum ? seat.getRemainingSum() : remainingNum;
                OmTicketSeatPo omTicketSeatPo = omTicketSeatMapper.selectById(seat.getSeatId());
                for (int i = 0; i < needArrangeNum; i++) {
                    //安排座位 哪个座位区，哪一排座位，哪一个号码
                    OmOrderRelSeatPo omOrderRelSeatPo = getOrderRelSeatPo(omTicketSeatPo, seat, omOrderPo.getId(), i);
                    remainingNum--;
                    omOrderRelSeatPoList.add(omOrderRelSeatPo);
                }
                //修改剩余座位  剩余座位 - 购买票数
                seat.setRemainingSum(seat.getRemainingSum() - remainingNum);
            }
            //批量插入
            omOrderRelSeatService.saveBatch(omOrderRelSeatPoList);
            omSceneGradeRelSeatService.updateBatchById(omSceneGradeRelSeatPoList);
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
     * @param  omSceneGradeRelSeatPo  座位区下面的某一排座位
     * @param  orderId  订单id
     * @param  arrangedNum  当前排正在安排的第几个座位
     * @return com.track.data.domain.po.order.OmOrderRelSeatPo
     **/
    private OmOrderRelSeatPo getOrderRelSeatPo(OmTicketSeatPo omTicketSeatPo,
                                               OmSceneGradeRelSeatPo omSceneGradeRelSeatPo,
                                               Long orderId,
                                               Integer arrangedNum) {
        OmOrderRelSeatPo omOrderRelSeatPo = new OmOrderRelSeatPo();
        omOrderRelSeatPo.setOrderId(orderId);
        omOrderRelSeatPo.setSeatId(omTicketSeatPo.getId());
        omOrderRelSeatPo.setSeatRow(omTicketSeatPo.getSeatRow());
        //获取座位号  当前排最大编号 - 剩余座位数 + 1 + i（第几张票）
        Integer seatNum = omTicketSeatPo.getMaxRange() - omSceneGradeRelSeatPo.getRemainingSum() + 1 + arrangedNum;
        omOrderRelSeatPo.setSeatNum(seatNum);
        //拼接信息  座位号  几排几座
        String seatStr = omOrderRelSeatPo.getSeatRow() + "排" + omOrderRelSeatPo.getSeatNum() + "座";
        omOrderRelSeatPo.setSeatStr(seatStr);

        return omOrderRelSeatPo;
    }


    /**
     * @Author yeJH
     * @Date 2019/11/2 11:32
     * @Description 删除用户订单
     *
     * @Update yeJH
     *
     * @param  orderId
     * @param  umUserPo
     * @return void
     **/
    @Override
    public void deleteMyOrder(Long orderId, UmUserPo umUserPo) {

        OmOrderPo omOrderPo = mapper.selectById(orderId);
        if(null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单不存在");
        }
        if(omOrderPo.getState().equals(OrderStateEnum.WAIT_PAY.getId()) ||
                omOrderPo.getState().equals(OrderStateEnum.WAIT_CONSUME.getId()) ||
                omOrderPo.getState().equals(OrderStateEnum.REFUNDING.getId())) {
            String state = OrderStateEnum.getById(omOrderPo.getState()).getName();
            throw new ServiceException(ResultCode.PARAM_ERROR, state + "订单不能删除");
        }
        if(!omOrderPo.getUmUserId().equals(umUserPo.getId())) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "订单不能删除");
        }

        mapper.deleteById(orderId);
    }

    /**
     * @Author yeJH
     * @Date 2019/11/2 11:32
     * @Description 用户取消待支付订单
     *
     * @Update yeJH
     *
     * @param  orderId
     * @param  umUserPo
     * @return void
     **/
    @Override
    public void cancelMyOrder(Long orderId, UmUserPo umUserPo) {

        OmOrderPo omOrderPo = mapper.selectById(orderId);
        if(null == omOrderPo) {
            throw new ServiceException(ResultCode.NO_EXISTS, "订单不存在");
        }
        if(!omOrderPo.getState().equals(OrderStateEnum.WAIT_PAY.getId())) {
            String state = OrderStateEnum.getById(omOrderPo.getState()).getName();
            throw new ServiceException(ResultCode.PARAM_ERROR, state + "订单不能取消");
        }
        if(!omOrderPo.getUmUserId().equals(umUserPo.getId())) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "只能取消自己的订单");
        }
        closeOrder(orderId);
    }

    /**
     * @Author yeJH
     * @Date 2019/11/2 17:42
     * @Description 用户下单 在规定时间内没有完成支付，关闭订单，修改订单状态，扣减的座位数再加回去
     *
     * @Update yeJH
     *
     * @param  orderId
     * @return void
     **/
    @Override
    public void closeOrder(Long orderId) {

        //修改订单状态
        OmOrderPo omOrderPo = new OmOrderPo();
        omOrderPo.setId(orderId)
                .setState(OrderStateEnum.ALREADY_CANCEL.getId())
                .setCloseTime(LocalDateTime.now());
        mapper.updateById(omOrderPo);

        //获取快照信息
        QueryWrapper<OmTicketTempPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OmTicketTempPo::getOrderId, orderId);
        OmTicketTempPo omTicketTempPo = omTicketTempMapper.selectOne(queryWrapper);

        OmSceneRelGradePo omSceneRelGradePo = omSceneRelGradeMapper.getRemainingSum(omTicketTempPo.getRelId());
        //加回库存
        omSceneRelGradeMapper.closeOrderReturnStock(omSceneRelGradePo.getId(), omTicketTempPo.getOrderNum());
    }

    /**
     * @Author yeJH
     * @Date 2019/11/20 11:10
     * @Description 支付回调通知 修改订单 安排座位
     *
     * @Update yeJH
     *
     * @param  omOrderPo
     * @param  response  微信支付回调返回参数
     * @return void
     **/
    @Override
    public void wxPayNotify(OmOrderPo omOrderPo, Map<String, String> response) {

        //票号 支付成功才有票号
        omOrderPo.setTicketNo(SnowFlakeUtil.getFlowIdInstance().nextId());
        //微信支付单号
        omOrderPo.setPayOrderNo(response.get("transaction_id"));
        //订单状态改为待消费
        omOrderPo.setState(OrderStateEnum.WAIT_CONSUME.getId());
        //订单支付时间
        omOrderPo.setPayTime(LocalDateTime.now());
        mapper.updateById(omOrderPo);

        //分配座位信息（用户提交订单不分配座位，等到支付完成再根据购票数去分配座位）
        arrangeSeat(omOrderPo);

        //记录流水
        OmAccountLogPo omAccountLogPo = new OmAccountLogPo();
        omAccountLogPo.setUserId(omOrderPo.getUmUserId())
                .setAmount(omOrderPo.getPayAmount())
                .setOrderId(omOrderPo.getId())
                .setLogType(LogTypeEnum.ORDER_PAY.getId());
        omAccountLogMapper.insert(omAccountLogPo);
    }

    /**
     * @Author yeJH
     * @Date 2019/11/20 21:31
     * @Description 根据订单号，单个或者批量退款，也可根据场次将所有订单退款
     *
     * @Update yeJH
     *
     * @param  orderRefundDto
     * @return void
     **/
    @Override
    public void orderRefund(OrderRefundDto orderRefundDto) {
        if(orderRefundDto.getIsAll()) {
            if(null == orderRefundDto.getSceneId()) {
                throw new ServiceException(ResultCode.PARAM_ERROR, "场次id不能为空");
            }

            //该场次对应的所有已经支付的订单改为退款中
            mapper.refundUpdateState(orderRefundDto.getSceneId(), OrderStateEnum.REFUNDING.getId());
            //该场次所有对应的座位数总数恢复
            omSceneRelGradeMapper.orderRefundReturnStock(orderRefundDto.getSceneId());
            //该场次所有对应的座位区剩余座位数恢复
            omSceneGradeRelSeatMapper.orderRefundReturnStock(orderRefundDto.getSceneId());

            //15天之后正式操作退款
            String expireTime = 15 * 24 * 60 * 60 * 1000 + "";

            // 添加退款延时队列
            this.rabbitTemplate.convertAndSend(RabbitConstants.ORDER_REFUND_DELAY_EXCHANGE,
                    RabbitConstants.DELAY_ROUTING_KEY, orderRefundDto, message -> {

                        message.getMessageProperties().setExpiration(expireTime);
                        return message;
            });

        }
    }

    /**
     * @Author yeJH
     * @Date 2019/11/21 22:10
     * @Description 申请退款之后15天完成退款操作
     *
     * @Update yeJH
     *
     * @param  orderRefundDto
     * @return void
     **/
    @Override
    public void achieveRefund(OrderRefundDto orderRefundDto) {
        if(orderRefundDto.getIsAll()) {
            if(null != orderRefundDto.getSceneId()) {
                //需要退款的订单数量
                int orderSum = mapper.getOrderSumBySceneId(orderRefundDto.getSceneId(), orderRefundDto.getOperationTime());
                //一次只处理1000个订单
                if(orderSum > 0) {
                    for (int pageNo = 1; pageNo <= orderSum / 1000 + 1; pageNo++) {
                        PageHelper.startPage(pageNo, 1000);
                        List<Long> orderIdList = mapper.getOrderBySceneId(orderRefundDto.getSceneId(), orderRefundDto.getOperationTime());
                        orderIdList.forEach(orderId -> {
                            try {
                                //微信退款单号
                                String refundId = wxService.refund(orderId);
                                OmOrderPo omOrderPo = new OmOrderPo();
                                omOrderPo.setId(orderId)
                                        .setRefundId(refundId)
                                        .setState(OrderStateEnum.REFUNDED.getId())
                                        .setRefundTime(LocalDateTime.now());
                                mapper.updateById(omOrderPo);
                            } catch (Exception e) {
                                //记录有问题的退款
                                OmRefundFailRecordPo omRefundFailRecordPo = new OmRefundFailRecordPo();
                                omRefundFailRecordPo.setIsReturnStock(true)
                                        .setOperatingTime(orderRefundDto.getOperationTime())
                                        .setOrderId(orderId)
                                        .setSceneId(orderRefundDto.getSceneId())
                                        .setState(false);
                                omRefundFailRecordMapper.insert(omRefundFailRecordPo);
                            }
                        });
                    }
                }

            }
        }
    }

}
