package com.demo.module.config.sharding;

import com.demo.module.utils.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.infra.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.mode.manager.ContextManager;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p> @Title ShardingAlgorithmTool
 * <p> @Description 按月分片算法工具
 *
 * @author ACGkaka
 * @date 2022/12/20 14:03
 */
@Slf4j
public class ShardingAlgorithmTool {

    /** 表分片符号，例：t_user_202201 中，分片符号为 "_" */
    private static final String TABLE_SPLIT_SYMBOL = "_";

    /** 数据库配置 */
    private static final Environment ENV = SpringUtil.getApplicationContext().getEnvironment();
    private static final String DATASOURCE_URL = ENV.getProperty("my.sharding.create-table.url");
    private static final String DATASOURCE_USERNAME = ENV.getProperty("my.sharding.create-table.username");
    private static final String DATASOURCE_PASSWORD = ENV.getProperty("my.sharding.create-table.password");

    /** 配置文件路径 */
    private static final String CONFIG_FILE = "sharding-tables.yaml";

    /**
     * 获取所有表名
     * @return 表名集合
     * @param logicTableName 逻辑表
     */
    public static List<String> getAllTableNameBySchema(String logicTableName) {
        List<String> tableNames = new ArrayList<>();
        if (StringUtils.isEmpty(DATASOURCE_URL) || StringUtils.isEmpty(DATASOURCE_USERNAME) || StringUtils.isEmpty(DATASOURCE_PASSWORD)) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，URL:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
            throw new IllegalArgumentException("数据库连接配置有误，请稍后重试");
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
             Statement st = conn.createStatement()) {
            try (ResultSet rs = st.executeQuery("show TABLES like '" + logicTableName + TABLE_SPLIT_SYMBOL + "%'")) {
                while (rs.next()) {
                    String tableName = rs.getString(1);
                    // 匹配分表格式 例：^(t\_contract_\d{6})$
                    if (tableName != null && tableName.matches(String.format("^(%s\\d{6})$", logicTableName + TABLE_SPLIT_SYMBOL))) {
                        tableNames.add(rs.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据库连接失败，请稍后重试");
        }
        return tableNames;
    }


    // --------------------------------------------------------------------------------------------------------------
    // 私有方法
    // --------------------------------------------------------------------------------------------------------------

    /**
     * 获取数据源
     */
//    private static Map<String, DataSource> getDataSourceMap() {
//        return getActualDataSources().entrySet().stream().filter(entry -> ACTUAL_DATA_SOURCE_NAMES.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    /**
     * 获取数据源配置
     */
    private static File getShardingYAMLFile() {
        return new File(Objects.requireNonNull(
                ShardingAlgorithmTool.class.getClassLoader().getResource(CONFIG_FILE), String.format("File `%s` is not existed.", CONFIG_FILE)).getFile());
    }

    /**
     * 刷新ActualDataNodes
     */
    private static void updateShardRuleActualDataNodes(ShardingSphereDataSource dataSource, String logicTableName, String newActualDataNodes) throws NoSuchFieldException {
        // Context manager.
        Field contextManagerField = dataSource.getClass().getDeclaredField("contextManager");
        ContextManager contextManager = (ContextManager) ReflectionUtils.getField(contextManagerField, dataSource);

        // Rule configuration.
        ShardingSphereMetaData shardingSphereMetaData = contextManager
                .getMetaDataContexts()
                .getMetaData();
        Collection<RuleConfiguration> newRuleConfigList = new LinkedList<>();
        Collection<RuleConfiguration> oldRuleConfigList = shardingSphereMetaData
                .getGlobalRuleMetaData()
                .getConfigurations();

        for (RuleConfiguration oldRuleConfig : oldRuleConfigList) {
            if (oldRuleConfig instanceof ShardingRuleConfiguration) {

                // Algorithm provided sharding rule configuration
                ShardingRuleConfiguration oldAlgorithmConfig = (ShardingRuleConfiguration) oldRuleConfig;
                ShardingRuleConfiguration newAlgorithmConfig = new ShardingRuleConfiguration();

                // Sharding table rule configuration Collection
                Collection<ShardingTableRuleConfiguration> newTableRuleConfigList = new LinkedList<>();
                Collection<ShardingTableRuleConfiguration> oldTableRuleConfigList = oldAlgorithmConfig.getTables();

                oldTableRuleConfigList.forEach(oldTableRuleConfig -> {
                    if (logicTableName.equals(oldTableRuleConfig.getLogicTable())) {
                        ShardingTableRuleConfiguration newTableRuleConfig = new ShardingTableRuleConfiguration(oldTableRuleConfig.getLogicTable(), newActualDataNodes);
                        newTableRuleConfig.setTableShardingStrategy(oldTableRuleConfig.getTableShardingStrategy());
                        newTableRuleConfig.setDatabaseShardingStrategy(oldTableRuleConfig.getDatabaseShardingStrategy());
                        newTableRuleConfig.setKeyGenerateStrategy(oldTableRuleConfig.getKeyGenerateStrategy());

                        newTableRuleConfigList.add(newTableRuleConfig);
                    } else {
                        newTableRuleConfigList.add(oldTableRuleConfig);
                    }
                });

                newAlgorithmConfig.setTables(newTableRuleConfigList);
                newAlgorithmConfig.setAutoTables(oldAlgorithmConfig.getAutoTables());
                newAlgorithmConfig.setBindingTableGroups(oldAlgorithmConfig.getBindingTableGroups());
                newAlgorithmConfig.setBroadcastTables(oldAlgorithmConfig.getBroadcastTables());
                newAlgorithmConfig.setDefaultDatabaseShardingStrategy(oldAlgorithmConfig.getDefaultDatabaseShardingStrategy());
                newAlgorithmConfig.setDefaultTableShardingStrategy(oldAlgorithmConfig.getDefaultTableShardingStrategy());
                newAlgorithmConfig.setDefaultKeyGenerateStrategy(oldAlgorithmConfig.getDefaultKeyGenerateStrategy());
                newAlgorithmConfig.setDefaultShardingColumn(oldAlgorithmConfig.getDefaultShardingColumn());
                newAlgorithmConfig.setShardingAlgorithms(oldAlgorithmConfig.getShardingAlgorithms());
                newAlgorithmConfig.setKeyGenerators(oldAlgorithmConfig.getKeyGenerators());

                newRuleConfigList.add(newAlgorithmConfig);
            }
        }

        // update context
        String schemaName = "logic_db";
        contextManager.alterRuleConfiguration(schemaName, newRuleConfigList);
    }

    /**
     * 创建分表2
     * @param logicTableName  逻辑表
     * @param resultTableName 真实表名，例：t_user_202201
     * @return 创建结果（true创建成功，false未创建）
     */
    public static boolean createShardingTable(String logicTableName, String resultTableName) {
        // 根据日期判断，当前月份之后分表不提前创建
        String month = resultTableName.replace(logicTableName + TABLE_SPLIT_SYMBOL,"");
        YearMonth shardingMonth = YearMonth.parse(month, DateTimeFormatter.ofPattern("yyyyMM"));
        if (shardingMonth.isAfter(YearMonth.now())) {
            return false;
        }

        synchronized (logicTableName.intern()) {
            // 缓存中无此表，则建表并添加缓存
            executeSql(Collections.singletonList("CREATE TABLE IF NOT EXISTS `" + resultTableName + "` LIKE `" + logicTableName + "`;"));
        }
        return true;
    }

    /**
     * 执行SQL
     * @param sqlList SQL集合
     */
    private static void executeSql(List<String> sqlList) {
        if (StringUtils.isEmpty(DATASOURCE_URL) || StringUtils.isEmpty(DATASOURCE_USERNAME) || StringUtils.isEmpty(DATASOURCE_PASSWORD)) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接配置有误，请稍后重试，URL:{}, username:{}, password:{}", DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD);
            throw new IllegalArgumentException("数据库连接配置有误，请稍后重试");
        }
        try (Connection conn = DriverManager.getConnection(DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD)) {
            try (Statement st = conn.createStatement()) {
                conn.setAutoCommit(false);
                for (String sql : sqlList) {
                    st.execute(sql);
                }
            } catch (Exception e) {
                conn.rollback();
                log.error(">>>>>>>>>> 【ERROR】数据表创建执行失败，请稍后重试，原因：{}", e.getMessage(), e);
                throw new IllegalArgumentException("数据表创建执行失败，请稍后重试");
            }
        } catch (SQLException e) {
            log.error(">>>>>>>>>> 【ERROR】数据库连接失败，请稍后重试，原因：{}", e.getMessage(), e);
            throw new IllegalArgumentException("数据库连接失败，请稍后重试");
        }
    }

}
