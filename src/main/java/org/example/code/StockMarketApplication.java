package org.example.code;

import org.example.code.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@Import(SwaggerConfig.class)
public class StockMarketApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(StockMarketApplication.class, args);
	}
}
