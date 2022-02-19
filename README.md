# geekhall-spring-boot-starter
SpringBoot 2 starter demo.


## 自定义Starter以及起步依赖原理

### 1. Starter 启动原理

* starter-pom引入 autoconfigure包 -> 引入SpringBootStarter
* autoconfigure包中配置使用META-INF/spring.factories中EnableAutoConfiguration的值，使得项目启动加载指定的自动配置类。
* 编写自动配置类xxxAutoConfiguration -> xxxProperties
    - @Configuration
    - @Confitional
    - @EnableConfigurationProperties
    - @Bean
    - ...
* 引入Starter --- xxxAutoConfiguration --- 容器中放入组件 --- 绑定xxxProperties  --- 配置项

### 2. 实际工程
1. 创建一个空的starter场景工程
2. 创建一个自动配置工程
3. 场景工程依赖自动配置工程
```xml
<dependency>
  <groupId>cn.geekhall</groupId>
  <artifactId>geekhall-spring-boot-starter-autoconfigure</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency> 
```
4. 在自动配置工程中创建HelloService、HelloProperties、HelloServiceAutoConfiguration类实现自动装载功能
5. 在resources下新建META-INF/spring.factories文件添加如下内容：

用于定义项目启动时加载哪个自动配置类
```properties
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
cn.geekhall.starter.auto.HelloServiceAutoConfiguration
```

6. 执行自动配置工程的Maven生命周期中的clean，install
   这里需要注意需要把pom文件中的<build>和<plugin>部分删掉，否则maven编译时会报错找不到main方法。
7. 执行场景starter工程（父工程）的自动配置工程
8. 使用场景启动器的项目在自己的pom文件中添加如下依赖，项目就可以使用HelloService的功能了。
```xml
<dependency>
    <groupId>cn.geekhall</groupId>
    <artifactId>geekhall-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
8. 使用场景启动器的项目还需要在自己的spring配置文件中添加如下内容来配置项目中使用到的信息
```yaml
geekhall:
  hello:
    prefix: geekhall
    suffix: 666
```


### 执行顺序：
1. 应用工程pom.xml文件中使用了场景工程的依赖
2. 场景工程自己子模块的自动配置模块
3. 子模块自动配置工程中的`META-INF/spring.factories`中指定了自动配置类`HelloServiceAutoConfiguration`
4. `HelloServiceAutoConfiguration`使用了`@ConditionalOnMissingBean({HelloService.class})`
   表示"当容器中没有HelloService组件的时候，会使用@Bean注解为我们自动配置一个HelloService组件"
5. `HelloService`组件中使用`@Autowired`自动注入了`HelloProperties`类
6. `HelloProperties`组件使用`@ConfigurationProperties("geekhall.hello")`与spring配置文件中的`geekhall.hello`配置属性绑定。
7. 应用工程可以使用`geekhall.hello.prefix`和`geekhall.hello.suffix`来修改配置属性。使得场景工程中的逻辑生效。
8. 如果应用工程中自己放一个`HelloService`组件的话，就不会调用场景工程中的HelloService了。
