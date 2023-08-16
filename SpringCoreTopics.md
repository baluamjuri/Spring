## Topics
### IOC
* What is IOC?
  - Dependency lookup - Target class writes logic to search and get dependent class objects from different resources. Involves obtaining the required dependencies by querying a central service registry or container. (ctx.getBean(-,-))
  - Dependency injection - Target class pulling and dependent class object from different places.(@Autowired)
* Advantages of IOC pattern - Loose coupling, Maintains the life cycle of object

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
* Environment - To get the properties from yml/properties/etc
* MessageSource, MessageSourceAware - Used for I18N support and reads from different sources using ResourceBundleMessageSource
* Spring Boot @ConfiguarationProperties, with prefix
 	https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
* @Value
  - Hardcoded string value
  - from properties file, with default value
  - from system properties
  - list of values(to inject values to an array)
  - using spEL (@Value("#{'${roytuts.list}'.split(',')}"), private List<String> strList2;)
  - accessing beans (@Value("#{someService.someMethod()}"))
  - Assign Null value as default value (@Value("${stuff.value:@null}"))
 
### Scopes
* @Scope
* prototype
* singleton
* request
* session
* application(5)
* websocket(5)

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
* Injecting a prototype-scoped bean into a singleton bean 
* Injecting dependencies procedurally
* @Lookup-annotated methods won't work at all when the surrounding class is @Bean-managed or component scanned.
### Injecting a prototype-scoped bean into a singleton bean
* Using @Lookup but limited - uses CGLIB as well
* Using javax.inject.Provider
* Using java.util.Function
* Using ObjectFactory - part of spring framework
### @Required
### @ComponentScan
### Profiles
* @Profile
* @ActiveProfile
