package system.flight.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Pointcut("within(system.flight.controller..*)")
    public void controllerMethods(){};

    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start=System.currentTimeMillis();
        System.out.println("Entering: " + joinPoint.getSignature().toShortString());
        Object result=joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis()-start;

        System.out.println("Exiting: " + joinPoint.getSignature().toShortString() + " | Time taken: " + elapsedTime + " ms" + " | Returned: type " + (result != null ? result.getClass().getSimpleName(): "null"));

        return result;
    }


}
