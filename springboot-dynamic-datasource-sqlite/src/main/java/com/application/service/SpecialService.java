package com.application.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SpecialService extends IService<String> {

    Object getTerrainType(String type,Integer z, Integer x, Integer y);
}
