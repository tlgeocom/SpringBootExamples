package com.demo.module.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Title DruidStatController
 * <p> @Description 获取druid监控数据
 *
 * @author ACGkaka
 * @date 2023/7/9 0:24
 */
@RestController
public class DruidStatController {

    @GetMapping("/druidStat")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，
        // 除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
