Spring Core:
=============
What is IOC? (Dependency lookup, Dependency injection)
Advantages of IOC pattern
@Autowire(required=true/false)
	setter injection
	constructor injection
	field injection
	By Name
	By Type
@Qualifier(By name)
@Resource(autowire+qualifier)
@Value
	Hardcoded string value
	from properties file, with default value
	from system properties
	list of values(to inject values to an array)
	using spEL
	Assign Null value as default value
@PropertySource(value="",ignoreResourceNotFound=true, classpath:,Ignoring exceptions, ignoreUnresolvablePlaceholders)
Environment
Spring Boot @ConfiguarationProperties, with prefix
	https://www.mkyong.com/spring-boot/spring-boot-configurationproperties-example/
@Scope(prototype, singleton, request, session)
@Lazy
Stereotypes (@Component, @Configuration, @Controller, @Service, @Repository)
@Configuration, @Bean
BeanFactory
ApplicationContext
AnnotationConfigApplicationContext (no cofiguration file, just register config classes ctx.register(AppConfig.class); ctx.register(DaoConfig.class))
BeanNameAware, BeanFactoryAware, ApplicationContextAware, ServletContextAware, ServletConfigAware
BeanPostProcessor, BeanFactoryPostProcessor
Bean Life cycle
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
ApplicationEvent, customized ApplicationEvent, ApplicationListener, ApplicationEventPublisher, ApplicationEventPublisherAware(custom events)
@Import

17)ApplicationContext VS BeanFactory
19)lazy-init attribute(@lazy)
20)default-lazy-init attribute
21)Making ApplicationContext lazy load (beanFactory.getBeanDefinition(beanName).setLazyInit(false);)
23)lookup method injection(@Lookup)
24)Controlling the Order of Creation of Beans - depends-on attribute
25)replaced-method
26)MessageSourceAware , MessageSource
27)propertyplaceholderconfigurer(classpath:,Ignoring exceptions, ignoreResourceNotFound, ignoreUnresolvablePlaceholders)
28)P and C namespaces
29)Importing xmls into spring bean definition xmls(@Import)
https://www.tutorialspoint.com/spring/spring_java_based_configuration.htm
@Bean(initMethod = "init", destroyMethod = "cleanup" )
30)AnnotationConfigApplicationContext (no cofiguration file, just register config classes ctx.register(AppConfig.class); ctx.register(DaoConfig.class))
31)@Component, @Service, @Repository, @Required
32)<context:component-scan base-package="..." />
33)<context:annotation-config/>
34)<context:component-scan base-package="..." /> VS <context:annotation-config/>
35)@ComponentScan
36)@PropertySource, propertyplaceholderconfigurer with static keyword(To resolve @value, so it should load very early)
37)Environment (https://www.mkyong.com/spring/spring-propertysources-example/) (Spring recommends to use Environment to get the property values)
38)@Profile, profile attribute, @ActiveProfile
