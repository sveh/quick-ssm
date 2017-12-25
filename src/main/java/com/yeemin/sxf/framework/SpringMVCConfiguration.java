/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━感觉萌萌哒━━━━━━
 */
package com.yeemin.sxf.framework;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.List;

/**
 * description MVC相关配置, 继承 WebMvcConfigurerAdatper
 * author yeemin [yeemin_shon@163.com]
 * time 2017/12/22 - 5:45
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan()
public class SpringMVCConfiguration extends WebMvcConfigurerAdapter {

    /**
     * @description 视图解析器
     * @methodName viewResolver
     * @param
     * @return org.springframework.web.servlet.view.UrlBasedViewResolver
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:19
     */
    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        // 设置文件名前缀
        viewResolver.setPrefix("/WEB-INF/views/");
        // 设置文件名后缀
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    /**
     * @description 手动添加纯页面的控制器映射
     * @methodName addViewControllers
     * @param registry registry
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:20
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/hello");
    }

    /**
     * @description 设置静态资源文件路径
     *                  js, css, images等
     * @methodName addResourceHandlers
     * @param registry registry
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:21
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /**
     * @description 防止IE浏览器执行Ajax时下载文件
     * @methodName mappingJacksonHttpMessageConverter
     * @return org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:27
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        List<MediaType> supportMediaTypes = Lists.newArrayList();
        supportMediaTypes.add(MediaType.TEXT_HTML);
        supportMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportMediaTypes);
        return mappingJackson2HttpMessageConverter;
    }

    /**
     * @description 添加视图解析器
     * @methodName extendMessageConverters
     * @param converters converters
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:29
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJacksonHttpMessageConverter());
    }

    /**
     * @description 文件上传
     * @methodName multipartResolver
     * @return org.springframework.web.multipart.MultipartResolver
     * @author william [yeemin_shon@163.com]
     * @time 2017/12/24 0:31
     */
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(10485760000L);
        resolver.setDefaultEncoding("UTF-8");
        resolver.setMaxInMemorySize(40960);
        return resolver;
    }

}
