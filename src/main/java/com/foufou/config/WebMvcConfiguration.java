package com.foufou.config;

import com.foufou.common.json.JacksonObjectMapper;
import com.foufou.interceptor.JwtTokenStudentInterceptor;
import com.foufou.interceptor.JwtTokenTeacherInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private JwtTokenStudentInterceptor jwtTokenStudentInterceptor;
    @Resource
    private JwtTokenTeacherInterceptor jwtTokenTeacherInterceptor;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenStudentInterceptor)
                .addPathPatterns("/api/reset_password")
                .addPathPatterns("/api/student/**")
                .excludePathPatterns("/api/student/login");
        registry.addInterceptor(jwtTokenTeacherInterceptor)
                .addPathPatterns("/api/reset_password")
                .addPathPatterns("/api/teacher/**")
                .excludePathPatterns("/api/teacher/login");
    }

    /**
     * 扩展Spring MVC框架的消息转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //为消息转换器设置一个对象转换器，对象转换器可以将JAVA对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息添加到容器中
        converters.add(0, converter);
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(taskExecutor);
        configurer.setDefaultTimeout(30_000); // 30 秒超时
    }

    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
