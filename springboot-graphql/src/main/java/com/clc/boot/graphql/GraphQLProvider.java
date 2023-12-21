package com.clc.boot.graphql;

import com.clc.boot.bean.User;
import com.clc.boot.bean.Card;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * 实现功能：将GraphQL对象载入待Spring容器，并且完成GraphQL对象初始化的功能
 *
 * @author chengluchao
 */
@Component
public class GraphQLProvider {


    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    /**
     * 加载schema
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        File file = ResourceUtils.getFile("classpath:user.graphqls");
        GraphQLSchema graphQLSchema = buildGraphQLSchema(file);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildGraphQLSchema(File file) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, buildWiring());
        return graphQLSchema;
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    /**
     * 业务实现，demo版
     * 如果是开发实战，这一点的设计是重点，需要考虑如何根据graphql中定义的方法来执行java代码
     *
     * @return
     */
    private RuntimeWiring buildWiring() {
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("UserQuery", builder ->
                        builder.dataFetcher("queryUserById", environment -> {
                            Long id = Long.parseLong(environment.getArgument("id"));
                            Card card = new Card("123456", id);
                            return new User(18, id, "user0" + id, card);
                        })
                )
                .build();
        return wiring;
    }
}

