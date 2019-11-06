package com.track.web.api.sys.elk;

import com.github.pagehelper.PageInfo;
import com.track.common.enums.system.ResultCode;
import com.track.core.interaction.JsonViewData;
import com.track.data.dto.elk.SearchLogDto;
import com.track.elk.po.EsLogDo;
import com.track.elk.service.EsLogService;
import com.track.elk.service.ISysLogService;
import com.track.elk.vo.SearchLogVo;
import com.track.web.base.BaseWeb;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author cheng
 * @create 2019-11-05 22:28
 */
@Slf4j
@RestController
@Api(tags = "日志_日志管理接口")
@RequestMapping("/sys/log")
public class LogApi extends BaseWeb {

    @Value("${tickets.logRecord.es}")
    private Boolean esRecord;

    @Autowired
    private EsLogService esLogService;

    @Autowired
    private ISysLogService sysLogService;

    /**
     * @Author chauncy
     * @Date 2019-11-06 17:28
     * @Description //条件分页查询日志
     *
     * elasticSearch只能使用Pageable
     *
     * @Update chauncy
     *
     * @param  searchLogDto
     * @return com.track.core.interaction.JsonViewData<com.github.pagehelper.PageInfo<com.track.elk.vo.SearchLogVo>>
     **/
    @PostMapping("/searchLog")
    @ApiOperation("条件分页查询日志")
    public JsonViewData<PageInfo<SearchLogVo>> searchLog(@RequestBody @ApiParam(required = true,name = "searchLogDto",value = "分页查询日志信息")
                                               @Validated SearchLogDto searchLogDto){

        if (esRecord){
            Page<EsLogDo>  esLogDoPage = esLogService.searchEsLog(searchLogDto);
            return setJsonViewData(esLogDoPage);
        }else {
            return setJsonViewData(sysLogService.searchLog(searchLogDto));
        }

    }

    /**
     * @Author chauncy
     * @Date 2019-11-06 10:43
     * @Description //全部删除日志
     *
     * @Update chauncy
     *
     * @param
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/delAll")
    @ApiOperation(value = "全部删除")
    public JsonViewData delAll(){

        if (esRecord){
            esLogService.deleteAll();
        }else {
            sysLogService.deleteAll();
        }

        return setJsonViewData(ResultCode.SUCCESS,"删除成功!");
    }

    /**
     * @Author chauncy
     * @Date 2019-11-06 11:15
     * @Description //批量删除日志
     *
     * @Update chauncy
     *
     * @param  ids
     * @return com.track.core.interaction.JsonViewData
     **/
    @GetMapping("/delByIds/{ids}")
    @ApiOperation("批量删除日志")
    public JsonViewData delByIds(@PathVariable List<Long> ids){
        if (esRecord){
            esLogService.deleteLog(ids);
        }else {
            sysLogService.delByIds(ids);
        }
        return setJsonViewData(ResultCode.SUCCESS,"批量删除成功");
    }

}
