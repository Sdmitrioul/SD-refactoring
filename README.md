# Refactoring Task

Repository was created for Software Design course in ITMO (CT) university by Skroba Dmitriy.

### Purpose:
Gain practical experience in applying refactoring techniques.

### What`s has been done:

1. Created tests for all servlets(after all commits made only if code is passing them).
2. Extracted constant values to static fields in Main class.
3. Created [Product](src/main/java/ru/akirakozov/sd/refactoring/model/Product.java) model with helper functions.
4. Extracted Dao layer:
    * Created [abstract class](src/main/java/ru/akirakozov/sd/refactoring/dao/AbstractDao.java) for executing sql statement in sqlite driver.
    * Created interface [ProductDao](src/main/java/ru/akirakozov/sd/refactoring/dao/ProductDao.java) (with its [implementation](src/main/java/ru/akirakozov/sd/refactoring/dao/ProductDaoSQLite.java)) with essential functionality.
5. Divide testing and working dao, by defining new name for it.
6. Create special enum handler for QueryServlet ([enum class](src/main/java/ru/akirakozov/sd/refactoring/handler/QueryProductHandler.java)). After to create new handled command, it is enough to define to enum value with one function and fields.