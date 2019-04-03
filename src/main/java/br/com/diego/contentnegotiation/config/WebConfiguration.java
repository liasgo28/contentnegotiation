package br.com.diego.contentnegotiation.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

   @Override
   public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
       configurer.ignoreAcceptHeader(true).      
       defaultContentType(MediaType.TEXT_HTML);
      
   }
   
   @Bean
   public ViewResolver contentNegotiationViewResolver(ContentNegotiationManager manager){
       List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
       viewResolvers.add(new JsonViewResolver());
       viewResolvers.add(new XmlViewResolver());

       ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
       resolver.setViewResolvers(viewResolvers);
       resolver.setContentNegotiationManager(manager);
       return resolver;
   } 
 
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
           registry.addResourceHandler("/resource/**")
                   .addResourceLocations("classpath:/static/");
   }

}
