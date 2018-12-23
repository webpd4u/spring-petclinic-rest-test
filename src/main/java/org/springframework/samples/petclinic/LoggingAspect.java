package org.springframework.samples.petclinic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	// setup logger

// private Logger logger = Logger.getLogger(getClass().getName());

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// setup pointcut declarations
	@Pointcut("execution(* org.springframework.samples.petclinic.repository.*.*(..))")
	private void forRepositoryPackage() {
	}

	// do the same for service and dao
	@Pointcut("execution(* org.springframework.samples.petclinic.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("execution(* org.springframework.samples.petclinic.rest.*.*(..))")
	private void forRestPackage() {
	}

	@Pointcut("forRepositoryPackage() || forServicePackage() || forRestPackage()")
	private void forAppFlow() {
	}

	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {

		// display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();

		logger.info("=====>> in @Before: calling method: " + theMethod);

		// display the arguments to the method

		// get the arguments

		Object[] args = theJoinPoint.getArgs();

		// loop thru and display args

		for (Object tempArg : args) {

			logger.info("=====>> argument: " + tempArg);

		}

	}

	// add @AfterReturning advice

	@AfterReturning(
			pointcut = "forAppFlow()",
			returning = "theResult"
	)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

		// display method we are returning from

		String theMethod = theJoinPoint.getSignature().toShortString();

		logger.info("=====>> in @AfterReturning: from method: " + theMethod);

		// display data returned

		logger.info("=====>> result: " + theResult);
	}
}