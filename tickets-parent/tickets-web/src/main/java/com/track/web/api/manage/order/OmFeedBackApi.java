package com.track.web.api.manage.order;


import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.manage.feedBack.search.SearchFeedBackDto;
import com.track.data.vo.manage.feedBack.SearchFeedBackVo;
import com.track.order.service.IOmFeedBackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;

import com.track.web.base.BaseWeb;

import java.util.List;

/**
 * <p>
 * 意见反馈表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-10-25
 */
@Api(tags = "后台_意见反馈接口")
@RestController
@RequestMapping("/manage/feedBack")
@Slf4j
public class OmFeedBackApi extends BaseWeb {

    @Autowired
    private IOmFeedBackService service;

    /**
     * @Author chauncy
     * @Date 2019-10-30 22:33
     * @Description //条件分页查询反馈信息
     *
     * @Update chauncy
     *
     * @param  searchFeedBackDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<SearchFeedBackVo>>
     **/
    @PostMapping("/searchFeedBack")
    @ApiOperation("条件分页查询反馈信息")
    public JsonViewData<PageInfo<SearchFeedBackVo>>  searchFeedBack(@RequestBody @ApiParam(required = true,name = "searchFeedBackVo",value = "条件分页查询反馈信息")
                                                                    @Validated SearchFeedBackDto searchFeedBackDto){

        return setJsonViewData(service.searchFeedBack(searchFeedBackDto));
    }
    
    /***
     * @Author chauncy
     * @Date 2019-10-30 23:26
     * @Description //批量处理反馈信息
     *
     * @Update chauncy
     *       
     * @param  ids
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/dealWithFeedBack/{ids}")
    @ApiOperation("批量处理反馈信息")
    public JsonViewData dealWithFeedBack(@PathVariable List<Long> ids){
        
        service.dealWithFeedBack(ids);
        return setJsonViewData(ResultCode.SUCCESS,"处理成功!");
    }
}
