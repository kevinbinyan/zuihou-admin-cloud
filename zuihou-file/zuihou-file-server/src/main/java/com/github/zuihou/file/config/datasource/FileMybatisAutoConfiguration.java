package com.github.zuihou.file.config.datasource;


import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.github.zuihou.database.datasource.BaseMybatisConfiguration;
import com.github.zuihou.database.mybatis.auth.DataScopeInnerInterceptor;
import com.github.zuihou.database.properties.DatabaseProperties;
import com.github.zuihou.oauth.api.UserApi;
import com.github.zuihou.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置一些 Mybatis 常用重用拦截器
 *
 * @author zuihou
 * @createTime 2017-11-18 0:34
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
public class FileMybatisAutoConfiguration extends BaseMybatisConfiguration {


    public FileMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

    /**
     * 数据权限插件
     *
     * @return DataScopeInterceptor
     */
    @Override
    protected List<InnerInterceptor> getPaginationBeforeInnerInterceptor() {
        List<InnerInterceptor> list = new ArrayList<>();
        Boolean isDataScope = databaseProperties.getIsDataScope();
        if (isDataScope) {
            list.add(new DataScopeInnerInterceptor(userId -> SpringUtils.getBean(UserApi.class).getDataScopeById(userId)));
        }
        return list;
    }

}