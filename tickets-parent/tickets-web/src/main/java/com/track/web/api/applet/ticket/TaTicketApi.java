package com.track.web.api.applet.ticket;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.applet.ticket.SearchTicketDto;
import com.track.data.vo.applet.ticket.TicketDetailVo;
import com.track.data.vo.applet.ticket.TicketListVo;
import com.track.security.util.SecurityUtil;
import com.track.ticket.service.IOmTicketService;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author yeJH
 * @since 2019/10/29 17:56
 * <p>
 * //TODO
 */
@Api(tags = "小程序_门票接口")
@RestController
@RequestMapping("/applet/ticket")
@Slf4j
public class TaTicketApi extends BaseWeb {

    @Autowired
    private IOmTicketService service;

    /**
     * @Author yeJH
     * @Date 2019/10/25 11:23
     * @Description 查询演唱会门票列表
     *
     * @Update yeJH
     *
     * @param  searchTicketDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo>
     **/
    @ApiOperation(value = "查询演唱会门票列表", notes = "根据门票名称模糊查询，时间查询，城市查询，综合排序，")
    @PostMapping("/searchTicketList")
    public JsonViewData<PageInfo<TicketListVo>> searchTicketList(
            @ApiParam(required = true, name = "searchTicketDto", value = "查询条件")
            @Validated @RequestBody SearchTicketDto searchTicketDto) {

        searchTicketDto.setCityCodeType(searchTicketDto.getCityCodeList().size());
        if(searchTicketDto.getCityCodeList().size() > 0) {
            searchTicketDto.setCityCode(searchTicketDto.getCityCodeList().get(searchTicketDto.getCityCodeList().size() - 1));
        }

        PageInfo<TicketListVo> ticketListVoPageInfo =
                service.searchTicketList(searchTicketDto);
        return new JsonViewData(ResultCode.SUCCESS, "查找成功",
                ticketListVoPageInfo);

    }

    /**
     * @Author yeJH
     * @Date 2019/10/30 10:36
     * @Description 小程序根据门票id获取演出详情
     *
     * @Update yeJH
     *
     * @param  ticketId 门票id
     * @return com.track.core.interaction.JsonViewData<com.track.data.vo.applet.ticket.TicketDetailVo>
     **/
    @ApiOperation(value="查询演出详情",notes = "编辑时根据门票id获取演出详情")
    @GetMapping("/getTicketDetail/{ticketId}")
    public JsonViewData<TicketDetailVo> getTicketDetail(@PathVariable Long ticketId){

        return setJsonViewData(service.getTicketDetail(ticketId));
    }

}
