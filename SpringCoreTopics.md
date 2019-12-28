## Topics
### IOC
* What is IOC? (Dependency lookup, Dependency injection)
* Advantages of IOC pattern

### Injection 
* setter injection
* constructor injection
* field injection
* @Autowire(required=true/false)(=@Inject)
* By Type
* @Qualifier(=@Named)(By name)
* @Resource(Autowire+Qualifier)(Inject+Named)
* @Inject, @Named, @Resource are J2EE annotations

  
### Read from properties
* @PropertySource(value="",ignoreResourceNotFound=true, classpath:,Ignoring exceptions, ignoreUnresolvablePlaceholders) 
* Environment
* MessageSource, MessageSourceAware
* Spring Boot @ConfiguarationProperties, with prefix
 	https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
* @Value
  - Hardcoded string value
  - from properties file, with default value
  - from system properties
  - list of values(to inject values to an array)
  - using spEL
  - Assign Null value as default value
 
### Scopes
* @Scope
* prototype
* singleton
* request
* session)

### @Lazy
### Stereotypes 
* @Component
* @Configuration
- @Bean
* @Controller
* @Service
* @Repository

### Containers
* BeanFactory
* ApplicationContext
- AnnotationConfigApplicationContext 
`````````   
no cofiguration file, just register config classes 
Ex: ctx.register(AppConfig.class); ctx.register(DaoConfig.class))
`````````
* ApplicationContext VS BeanFactory
* Making ApplicationContext lazy load 
```````
beanFactory.getBeanDefinition(beanName).setLazyInit(false);
```````

### Aware interfaces
* BeanNameAware
* BeanFactoryAware
* ApplicationContextAware
* ServletContextAware
* ServletConfigAware

### Life Cycle
* Bean Life cycle
````
Follows the below order:
	Constructor
	autowiring
	Aware injection
	BeanPostProcessor's postProcessBeforeInitialization
	@PostConstruct
	InitializingBean.afterPropertiesSet()
	custom init-method in xml config
	BeanPostProcessor's postProcessAfterInitialization
	@PreDestroy
	DisposableBean.destroy()
	custom destroy in xml config
````
* @Bean(initMethod = "init", destroyMethod = "cleanup" )

### Events
* ApplicationEvent
* customized ApplicationEvent
* ApplicationListener
* ApplicationEventPublisher
* ApplicationEventPublisherAware

### @Import
* https://www.tutorialspoint.com/spring/spring_java_based_configuration.htm

### @Lookup
* Injecting a prototype-scoped bean into a singleton bean (similar to Provider)
* Injecting dependencies procedurally
### @Required
### @ComponentScan
### Profiles
* @Profile
* @ActiveProfile
