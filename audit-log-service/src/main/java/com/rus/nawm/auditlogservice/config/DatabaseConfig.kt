package com.rus.nawm.auditlogservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource


@Configuration
open class DatabaseConfig {
    @Bean
    open fun jdbcTemplate(dataSource: DataSource?): JdbcTemplate {
        return JdbcTemplate(dataSource!!)
    }
}