package cn.geekhall.starter.auto;

import cn.geekhall.starter.bean.HelloProperties;
import cn.geekhall.starter.service.HelloService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HelloServiceAutoConfigure
 *
 * @author yiny
 */
@Configuration
@EnableConfigurationProperties(HelloProperties.class)   // 默认HelloProperties放在容器中
public class HelloServiceAutoConfiguration {

    @ConditionalOnMissingBean(HelloService.class)
    @Bean
    public HelloService helloService(){
        HelloService helloService = new HelloService();
        return helloService;
    }
}
