# exception-handing-spring-boot
learning exception handling in spring boot application


There are two ways to handle exceptions in a spring boot application:

	1. Creating a Base Controller, which all the other RestControllers will extend. The base controller contains methods annotated with @ExceptionHandler which handles exceptions 
	
	2. The best way is to create a Seperate Class annotated with @RestControllerAdvice or @ControllerAdvice which contains methods annotated with @ExceptionHandler which handles exceptions.
