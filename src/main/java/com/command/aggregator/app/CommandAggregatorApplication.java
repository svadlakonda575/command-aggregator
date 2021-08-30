package com.command.aggregator.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.command.aggregator"
})
@EntityScan(basePackages = { "com.command.aggregator.persistence.data"})
@EnableJpaRepositories(basePackages =  {
        "com.command.aggregator.persistence.dao"
}
)
public class CommandAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandAggregatorApplication.class, args);
    }

}
