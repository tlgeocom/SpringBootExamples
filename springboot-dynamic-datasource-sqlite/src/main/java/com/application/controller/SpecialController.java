package com.application.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.application.service.SpecialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * SpecialController
 * @Author: chaobo
 * 2023-10-15 14:23
 */
@RestController
@CrossOrigin(origins = "*")
@Api(value = "SpecialController", tags = { "SpecialController" })
public class SpecialController{
    @Autowired
    private SpecialService specialService;

    /**
     * 获取地形terrain瓦片
     * @param
     * @return
     */
    @ApiOperation("获取地形terrain瓦片")
    //@UserLoginToken
    @ResponseBody
    @GetMapping(value="/terrain/{type}/{z}/{x}/{y}.terrain",produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Object getTerrainType(@PathVariable String type, @PathVariable Integer z, @PathVariable Integer x, @PathVariable Integer y){
        Object tileRes = specialService.getTerrainType(type,z,x,y);
        return tileRes;
    }
}
