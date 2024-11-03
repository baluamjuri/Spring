Query by method:
====================
https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/repositories.html#repositories.query-methods
https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.sample-app.finders.strategies

T findOne(ID primaryKey);
Iterable<T> findAll();
List<User> findByName(String name)
  List<User> findByNameIs(String name);
  List<User> findByNameEquals(String name);
List<User> findByNameIsNot(String name);
findByNameIsNull, findByNameIsNotNull
findByLastnameIgnoreCase
findByActiveTrue
List<User> findByNameStartingWith(String prefix); List<User> findByNameEndingWith(String suffix); List<User> findByNameContaining(String infix);
List<User> findByAgeLessThan(Integer age);List<User> findByAgeLessThanEqual(Integer age);List<User> findByAgeGreaterThan(Integer age); List<User> findByAgeGreaterThanEqual(Integer age); List<User> findByAgeBetween(Integer startAge, Integer endAge);
List<User> findTop3ByAge()
List<User> findByAgeIn(Collection<Integer> ages);
List<User> findByBirthDateAfter(ZonedDateTime birthDate);List<User> findByBirthDateBefore(ZonedDateTime birthDate);
List<User> findByNameOrBirthDateAndActive(String name, ZonedDateTime birthDate, Boolean active);
List<User> findByNameOrderByNameAsc(String name);List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
findByFirstnameLike
findDistinctPeopleByLastnameOrFirstname
repository.existsById(id)
List<Person> findByAddress_ZipCode(ZipCode zipCode);(when Address is a instance object of Person and zipcode is Address's property)
Page<T> findAll(Pageable pageable);
repository.findAll(new PageRequest(1, 20));

Query by Example:
=================
=================
@Query
@Query(value="", nativeQuery= true)
@Modifying
@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
@Converter, AttributeConverter - Automatically converts db column value to your value

Ways to Improve JPA Performance
=================================
Eager fetching
Lazy fetching
Pagination - q.setMaxResults(5), q.setFirstResult(0)
Smart Column select
Using DTO projections - 
public interface AuthorRepository extends JpaRepository<Author, Long> {
 
    List<AuthorValueIntf> findByFirstName(String firstName);
 
}

Exception:
HibernateException: The general superclass for all Hibernate-specific exceptions.

NonUniqueResultException: Thrown when a query execution returns more than one result where only one result was expected.

QueryException: Thrown when a query is syntactically incorrect or when there is a problem with the query execution.
### Query by method:
https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/repositories.html#repositories.query-methods
https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html#jpa.sample-app.finders.strategies

```
T findOne(ID primaryKey);
Iterable<T> findAll();
List<User> findByName(String name)
  List<User> findByNameIs(String name);
  List<User> findByNameEquals(String name);
List<User> findByNameIsNot(String name);
findByNameIsNull, findByNameIsNotNull
findByLastnameIgnoreCase
findByActiveTrue
List<User> findByNameStartingWith(String prefix); List<User> findByNameEndingWith(String suffix); List<User> findByNameContaining(String infix);
List<User> findByAgeLessThan(Integer age);List<User> findByAgeLessThanEqual(Integer age);List<User> findByAgeGreaterThan(Integer age); List<User> findByAgeGreaterThanEqual(Integer age); List<User> findByAgeBetween(Integer startAge, Integer endAge);
List<User> findTop3ByAge()
List<User> findByAgeIn(Collection<Integer> ages);
List<User> findByBirthDateAfter(ZonedDateTime birthDate);List<User> findByBirthDateBefore(ZonedDateTime birthDate);
List<User> findByNameOrBirthDateAndActive(String name, ZonedDateTime birthDate, Boolean active);
List<User> findByNameOrderByNameAsc(String name);List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
findByFirstnameLike
findDistinctPeopleByLastnameOrFirstname
repository.existsById(id)
List<Person> findByAddress_ZipCode(ZipCode zipCode);(when Address is a instance object of Person and zipcode is Address's property)
Page<T> findAll(Pageable pageable);
repository.findAll(new PageRequest(1, 20));
```

### Query by Example:
Need to udpate...

### Few annotations of JPA to recall
```
@Query
@Query(value="", nativeQuery= true)
@Modifying
@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
@Converter, AttributeConverter - Automatically converts db column value to your value
```
### Ways to Improve JPA Performance
* Eager fetching
* Lazy fetching
* Pagination - q.setMaxResults(5), q.setFirstResult(0)
* Smart Column select
* Using DTO projections - 
```
 public interface AuthorRepository extends JpaRepository<Author, Long> {
  
     List<AuthorValueIntf> findByFirstName(String firstName);
  
 }
```

### Exception:
**HibernateException**: The general superclass for all Hibernate-specific exceptions.

**NonUniqueResultException**: Thrown when a query execution returns more than one result where only one result was expected.

**QueryException**: Thrown when a query is syntactically incorrect or when there is a problem with the query execution.

**LazyInitializationException**: Thrown when you attempt to access uninitialized lazy-loaded collections or associations outside the session scope.

**ObjectNotFoundException**: Thrown when an object is not found in the database or is no longer available.

**PersistentObjectException**: Thrown when there is a problem with persistent objects, such as re-associating a transient object with an already associated session.

**StaleStateException**: As mentioned earlier, this exception is thrown in optimistic locking scenarios when an entity's version is outdated due to concurrent updates.

**TransientObjectException**: Thrown when you try to save an object with a null identifier (primary key) or when you attempt to save an already persistent object as transient.

**PropertyValueException**: Thrown when there is a problem with the properties of an entity, such as null or invalid values for required properties.

**ConstraintViolationException**: This is a subclass of HibernateException and indicates that a constraint violation occurred when persisting or updating an entity, such as unique constraint violations.

**ObjectDeletedException**: Thrown when you try to access a deleted object that is no longer available in the session.

**UnresolvableObjectException**: Thrown when there is an attempt to resolve an unknown or unassociated object

### We can do batch update in different ways:

**1. Using NamedParameterJdbcTemplate with native query:**

**POSTGRES**
```
queries:
  saveOrUpdateLocationLanguages: "INSERT INTO Table (COL1, COL2, .., COLn) VALUES (:V1, :V2, .., Vn) 
  ON CONFLICT (uniqueColumn) 
  DO UPDATE SET colx = EXCLUDED.Vx, coly = EXCLUDED.Vy, colz = EXCLUDED.Vz..."
```
**MySql**
```
queries:
  saveOrUpdateLocationLanguages: "INSERT INTO Table (COL1, COL2, .., COLn) VALUES (:V1, :V2, .., Vn) 
  ON DUPLICATE KEY UPDATE colx = :Vx, coly = :Vy, colz = :Vz..."
```
**Spring**
```
@Value("${queries.batch-update.chunk-size: 500}")
private int chunkSize;
	
	
@Transactional
@Override
public <T> BatchUpdateStatus batchSaveOrUpdate(String query, final List<T> entities) {
	int[] updateCounts = new int[0];
	BatchUpdateStatus batchUpdateStatus = new BatchUpdateStatus();
	batchUpdateStatus.setFailedCount(0L);
	for (int i = 0; i < entities.size(); i += chunkSize) {
		List<T> chunk = entities.subList(i, Math.min(entities.size(), i + chunkSize));
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(chunk);
		log.info("Save or update entities with chunk size: {}", chunk.size());
		int[] chunkUpdateCounts = namedParameterJdbcTemplate.batchUpdate(query, batch);
		long failedCount = Arrays.stream(chunkUpdateCounts)
				.filter(statusIndex -> Statement.EXECUTE_FAILED == statusIndex)
				.count();
		if(failedCount>0) {
			batchUpdateStatus.setFailedCount(batchUpdateStatus.getFailedCount()+failedCount);
			batchUpdateStatus.setLabel(PARTIALLY_FAILED);
			log.warn("Batch update is partially successful. Failed count/Total:{} / {} ", failedCount, chunkUpdateCounts.length);
		}
		updateCounts = concatenate(updateCounts, chunkUpdateCounts);
	}

	log.info("Batch update of size {} is completed", Arrays.toString(updateCounts));

	return batchUpdateStatus;
}
```

**2. Using Spring JPA:**
```
spring:
  jpa:
    properties:
      hibernate:
        jdbc:
          batch_size: 50
          order_inserts: true
          order_updates: true
		  
FeedRepository.saveAll(feedContent)
```
