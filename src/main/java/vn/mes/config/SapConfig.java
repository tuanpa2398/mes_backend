package vn.mes.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(
    basePackages = "vn.mes.mapper.sap", 
    sqlSessionFactoryRef = "sapSqlSessionFactory"
)
public class SapConfig {
	@Bean
    @ConfigurationProperties("spring.datasource.sap")
    DataSource sapDataSource() {
        return DataSourceBuilder.create().build();
    }
	
    @Bean
    SqlSessionFactory sapSqlSessionFactory(
            @Qualifier("sapDataSource") DataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/sap/*.xml")
        );
        return factory.getObject();
    }

    @Bean
    SqlSessionTemplate sapSqlSessionTemplate(
            @Qualifier("sapSqlSessionFactory") SqlSessionFactory factory
    ) {
        return new SqlSessionTemplate(factory);
    }
}
