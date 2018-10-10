package andy.com.springFramework.core.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import rx.annotations.Beta;

@Service("TestService")
public class TestService
{
    public String doJob(String jobName,String content)
    {
        System.out.println(jobName +","+content);
        return jobName +" done";
    }

    @AopAnnotationAround
    public String doOtherJob1(String jobName,String content)
    {
        System.out.println(jobName +","+content);
       //int a = 1/0;
        return jobName +" done(other job)";
    }
}
