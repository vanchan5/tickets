package com.track.web.api.manage.ticket;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.domain.po.user.UmUserPo;
import com.track.data.dto.base.EditEnabledDto;
import com.track.data.dto.manage.ticket.save.SaveTicketDto;
import com.track.data.dto.manage.ticket.search.SearchManageTicketDto;
import com.track.data.vo.manage.ticket.ManageTicketInfoVo;
import com.track.data.vo.manage.ticket.ManageTicketListVo;
import com.track.security.util.SecurityUtil;
import com.track.ticket.service.IOmTicketService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.track.web.base.BaseWeb;

/**
 * <p>
 * 门票信息表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "后台_门票管理接口")
@RestController
@RequestMapping("/manage/ticket")
@Slf4j
public class OmTicketApi extends BaseWeb {

    @Autowired
    private IOmTicketService service;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:23
     * @Description 查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchManageTicketDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo>
     **/
    @ApiOperation(value = "查询演唱会门票列表", notes = "根据门票开始时间范围，门票名称，上下架状态查询")
    @PostMapping("/searchTicketList")
    public JsonViewData<PageInfo<ManageTicketListVo>> searchTicketList(
            @ApiParam(required = true, name = "searchManageTicketDto", value = "查询条件")
            @Validated @RequestBody SearchManageTicketDto searchManageTicketDto) {

        PageInfo<ManageTicketListVo> manageTicketListVoPageInfo =
                service.searchManageTicketList(searchManageTicketDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                manageTicketListVoPageInfo);

    }

    /**
     * @Author yeJH
     * @Date 2019/10/25 12:38
     * @Description 演唱会门票上下架
     *
     * @Update yeJH
     *
     * @param  editEnableDto  批量修改状态参数
     * @return com.track.core.interaction.JsonViewData
     **/
    @ApiOperation(value = "演唱会门票上下架", notes = "演唱会门票上下架批量操作")
    @PostMapping("/publishState")
    public JsonViewData publishState(
            @ApiParam(required = true, name = "editEnableDto", value = "批量修改状态参数")
            @Validated @RequestBody EditEnabledDto editEnableDto) {

        service.publicState(editEnableDto);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

    /**
     * @Author yeJH
     * @Date 2019/10/25 16:17
     * @Description 编辑时根据门票id获取门票详情
     *
     * @Update yeJH
     *
     * @param  ticketId
     * @return com.track.core.interaction.JsonViewData<com.track.data.vo.manage.ticket.ManageTicketInfoVo>
     **/
    @ApiOperation(value="查询门票详情",notes = "编辑时根据门票id获取门票详情")
    @GetMapping("/getTicket/{ticketId}")
    public JsonViewData<ManageTicketInfoVo> getTicket(@PathVariable Long ticketId){

        return setJsonViewData(service.getTicket(ticketId));
    }


    /**
     * @Author yeJH
     * @Date 2019/10/28 17:58
     * @Description 新增/编辑演唱会门票
     *
     * @Update yeJH
     *
     * @param  saveTicketDto  新增/编辑演唱会门票参数
     * @return com.track.core.interaction.JsonViewData
     **/
    @ApiOperation(value = "新增/编辑演唱会门票", notes = "新增/编辑演唱会门票")
    @PostMapping("/saveTicket")
    public JsonViewData saveTicket(
            @ApiParam(required = true, name = "saveTicketDto", value = "新增/编辑演唱会门票参数")
            @Validated @RequestBody SaveTicketDto saveTicketDto) {

        //操作人员
        UmUserPo operator =securityUtil.getSysCurrUser();

        service.saveTicket(saveTicketDto, operator);
        return new JsonViewData(ResultCode.SUCCESS, "操作成功");

    }

}
