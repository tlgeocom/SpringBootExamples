package com.clc.boot.controller;

import com.alibaba.fastjson.JSONObject;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * ClassName: GraphQLController<br/>
 * Description: <br/>
 * date: 2019/6/28 5:38 PM<br/>
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@RequestMapping("graphql")
@Controller
@Slf4j
public class GraphQLController {
    @Autowired
    private GraphQL graphQL;

    @PostMapping("query")
    @ResponseBody
    public Object query(@RequestBody JSONObject request) {
        ExecutionResult result = this.graphQL.execute((String)request.get("query"));
        return result.toSpecification();
    }
}


