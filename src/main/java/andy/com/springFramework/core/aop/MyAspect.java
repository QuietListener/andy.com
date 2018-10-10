package andy.com.springFramework.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义一个切面
 */
@Aspect
@Component
public class MyAspect {

    /**
     * 顶一个一个pointCut
     */
    @Pointcut("execution(* andy.com.springFramework.core.aop.TestService.*(..))")// the pointcut expression
    private void anyOldTransfer() {}// the pointcut signature


    //@Around("andy.com.springFramework.core.aop.MyAspect.anyOldTransfer()")
    @Around("execution(* andy.com.springFramework.core.aop.TestService.*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        System.out.println("in Aspect");
        return retVal;
    }



    /**
     *  args 提供参数
     */
    //@Around("andy.com.springFramework.core.aop.MyAspect.anyOldTransfer()")
    @Around(value ="@annotation(andy.com.springFramework.core.aop.AopAnnotationAround)  && args(jobName1,..)")
    public Object dojob(ProceedingJoinPoint pjp,String jobName1) throws Throwable {
        System.out.println("jobName:"+jobName1);

        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        System.out.println("in Aspect annotation");
        return retVal;
    }
}
