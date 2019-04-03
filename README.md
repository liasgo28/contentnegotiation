# SpringBoot - Content Negotiation

Create a web application with SpringBoot web (MVC) making available a content negotiation backend to response (html,json or xml)

## Let's start:

Enjoy this!

### Create a new Maven Project

	* Click: File -> New -> MavenProject

	* Check: Create a simple project(skp archtype selection)

	* Click Next

	* Type Group id, Artifact Id and click Finish

### POM Dependencies

Open pom.xml file and add the next code after the </project> tag

```
<dependencies>  	
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-web</artifactId>
	    <version>2.1.3.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-thymeleaf</artifactId>
	    <version>2.1.3.RELEASE</version>
	</dependency>
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-core</artifactId>
	    <version>2.9.8</version>
	</dependency>
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>2.9.8</version>
	</dependency>
	<dependency>
	    <groupId>com.fasterxml.jackson.dataformat</groupId>
	    <artifactId>jackson-dataformat-xml</artifactId>
	    <version>2.9.8</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-thymeleaf</artifactId>
	    <version>2.1.3.RELEASE</version>
	</dependency>
</dependencies>
```

### Create start class
* create new class file 
* add the annotation @SpringBootApplication before the class
* create a new main method and push this SpringApplication.run(Application.class, args);
* the final code will look like this

```
package br.com.diego.contentnegotiation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class,args);
	}
}
```

Run main method and see in console application was been started!

...
  o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
...

### Create a Model
* create new class file 
* the final code will look like this

```
package br.com.diego.contentnegotiation.model;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 28L;
	private Long id;	
	private String name;	
	private String email;

	public Person(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}

```

### Create a Repository
* create new class file 
* add the annotation @Repository before the class
* create a new method with String return and before the method put @RequestMapping("/") and @ResponseBody annotations
* return a List<ModelClass> string
* the final code will look like this

```
package br.com.diego.contentnegotiation.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.diego.contentnegotiation.model.Person;

@Repository
public class PersonRepository {
	
	public final List<Person> getAllPersons() {
		List<Person> persons= new ArrayList<Person>();		
		persons.add(new Person(1L,"Name One", "teste@teste.com.br"));
		persons.add(new Person(2L,"Name Two", "teste2@teste.com.br"));
		
		return persons;
	}
	
}
```


### Create a Controller
* create new class file 
* add the annotation @Controller before the class
* create a new method return ModelAndView 
* the final code will look like this


```
package br.com.diego.contentnegotiation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.diego.contentnegotiation.repository.PersonRepository;

@Controller
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;

	@RequestMapping( method=RequestMethod.GET)
	public ModelAndView getAllPersons(){		
		ModelAndView modelAndView = new ModelAndView("persons/index");
		modelAndView.addObject("persons", personRepository.getAllPersons());
		return modelAndView;		
		
		
		
	}
}
```

### Create template page
 * in src -> main -> resources create a new folder with name templates
 * create a new folder called persons in template forder
 * in persons folder create a nem html file (index.html)
 * open html file and paste this code
 
	```
	<!DOCTYPE html>
	<html xmlns:th="http://www.thymeleaf.org">
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>Content Configuration</title>
		
	</head>
	<body>
	    <div class="container">
	        <div class="jumbotron" align="center" style="margin-top: 50px;">
	            <h1>List of Persons</h1>            
	        </div>
	        
	        <div id="listPersons">
			    <table class="table table-hover">
			        <thead>
			        <tr>
			            <th>Name</th>
			            <th>email</th>
			        </tr>
			        </thead>
			        <tr th:each="person : ${persons}">
			            <td> <span th:text="${person.name}"></span> </td>
			            <td> <span th:text="${person.email}"></span> </td>
			        </tr>
			    </table>
			</div>
	    </div>
	 
	    
	</body>
	</html>
	```

Stop application if it's running
Run main method and see in console application was been started!

In the browser open http://localhost:8080/persons and see the page.


### Configure Content Negotiation
* create new class file 
* add the annotation @Configuration before the class
* extends WebMvcConfigurationSupport
* override the configureContentNegotiation method to define your default content
* override the addResourceHandlers method to permit access to css and js files bootstrap
* create a method contentNegotiationViewResolver e annotation @Bean before the method
* the final code will look like this

```
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

```

#### Create JsonViewResolver
* create new class file 
* implements ViewResolver
* the final code will look like this

```
package br.com.diego.contentnegotiation.config;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolver implements ViewResolver {

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setPrettyPrint(true);
        return jsonView;
	}

}
```


#### Create XmlViewResolver
* create new class file 
* implements ViewResolver
* the final code will look like this

```
package br.com.diego.contentnegotiation.config;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

public class XmlViewResolver implements ViewResolver {

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		MappingJackson2XmlView xmlView = new MappingJackson2XmlView();
		xmlView.setPrettyPrint(true);
        return xmlView;
	}

}
```

Stop application if it's running
Run main method and see in console application was been started!

In the browser open http://localhost:8080/persons to see the page.
In the browser open http://localhost:8080/persons.json to see the page.
In the browser open http://localhost:8080/persons.xml to see the page.

### Bootstrap

* in src -> main -> resources create a new folder with name static
* Get bootstrap https://getbootstrap.com/
* Unzip file and rename folder to bootstrap and move to static folder
* add import tag css before close head tag
* add import tags jquery, and js before close body
* the final code will look like this

```
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Content Configuration</title>
	<link rel="stylesheet" type="text/css" th:href="@{/resource/bootstrap/css/bootstrap.min.css}"/>
</head>
<body>
    <div class="container">
        <div class="jumbotron" align="center" style="margin-top: 50px;">
            <h1>List of Persons</h1>            
        </div>
        
        <div id="listPersons">
		    <table class="table table-hover">
		        <thead>
		        <tr>
		            <th>Name</th>
		            <th>email</th>
		        </tr>
		        </thead>
		        <tr th:each="person : ${persons}">
		            <td> <span th:text="${person.name}"></span> </td>
		            <td> <span th:text="${person.email}"></span> </td>
		        </tr>
		    </table>
		</div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" th:src="@{/resource/bootstrap/js/bootstrap.min.js}"></script>
    
</body>
</html>
```
Stop application if it's running
Run main method and see in console application was been started!

In the browser open http://localhost:8080/persons and see the page.


	
# YWC -> You are Welcome!
