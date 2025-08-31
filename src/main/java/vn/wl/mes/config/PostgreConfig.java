package vn.wl.mes.config;

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
    basePackages = "vn.wl.mes.mapper.postgre", 
    sqlSessionFactoryRef = "postgresSqlSessionFactory"
)
public class PostgreConfig {
	@Bean
    @ConfigurationProperties("spring.datasource.postgre")
    DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }
	
    @Bean
    SqlSessionFactory postgresSqlSessionFactory(
            @Qualifier("postgresDataSource") DataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setMapperLocations(
            new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/postgre/*.xml")
        );
        return factory.getObject();
    }

    @Bean
    SqlSessionTemplate postgresSqlSessionTemplate(
            @Qualifier("postgresSqlSessionFactory") SqlSessionFactory factory
    ) {
        return new SqlSessionTemplate(factory);
    }
}
