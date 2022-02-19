package cn.geekhall.starter.service;

import cn.geekhall.starter.bean.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * HelloService
 * 默认不要放在容器中
 *
 * @author yiny
 */
public class HelloService {

    @Autowired
    HelloProperties helloProperties;

    public String sayHello(String username){
        return helloProperties.getPrefix() + " : " + username + helloProperties.getSuffix();
    }
}
