package com.lmy.eblog.config;
/**
 * @Project eblog
 * @Package com.lmy.eblog.config
 * @author Administrator
 * @date 2020/9/30 10:51
 * @version V1.0
 */

/**
 * @author Lmy
 * @ClassName MybatisPlusConfig
 * @Description MybatisPlus 配置信息
 * @date 2020/9/30 10:51
 **/

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.lmy.eblog.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

}
