package com.Lirs.Spring.aspect;

import com.Lirs.Spring.annotation.Log;
import com.Lirs.Spring.localMapper.SysLogMapper;
import com.Lirs.Spring.localMapper.SysThrowMapper;
import com.Lirs.Spring.model.SysLog;
import com.Lirs.Spring.model.SysThrow;
import com.Lirs.Spring.util.HttpUtil;
import com.Lirs.Spring.util.UUIDUitl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogMapper mapper;
    @Autowired
    private SysThrowMapper throwMapper;

//    @Pointcut("@annotation(com.Lirs.Spring.annotation.Log)")
//    public void pointcut() {}
    @Pointcut("execution(public * com.Lirs.Spring.Controller.LogController.*(..))")
    private void pointcut(){}

    /**
     * 环绕增强
     * @param point
     * @return
     */
     //   @Around(value = "execution(public * com.Lirs.Spring.Controller.*.*(..))")
    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        System.out.println("=========================Around======================================");
        long begin = System.currentTimeMillis();
        System.out.println("@Around:模拟around环绕增强");
        System.out.println("@Around:原本输入的参数" + Arrays.toString(point.getArgs()));
        Object args[] = point.getArgs();
        Object result = new Object();
        if(args != null && args.length > 0){
            for(int i = 0; i < args.length ; i++){
                args[i] = "改变原方法的参数";
            }
        }
        System.out.println("参数修改完毕");
        result = point.proceed(args);
//        try{
//            result = point.proceed(args);
//        }catch(Throwable e){
//            e.printStackTrace();
//        }
        long time = System.currentTimeMillis() - begin;
        System.out.println("@Around：记录日志");
        saveLog(point,time);
        System.out.println("=========================Around======================================");
        return result;
    }

    /**
     * 前置增强 增强mapper下的SysLogMapper
     * @param point
     * @return
     */
    //@Before("execution(public * com.Lirs.Spring.Controller.UserController.*(..))")
    @Before("pointcut()")
    public void before(JoinPoint point){
        System.out.println("=========================Before======================================");
        long begin = System.currentTimeMillis();
        System.out.println("@Before:模拟权限检查。。。");
        System.out.println("@Before:目标方法为：" +
                point.getSignature().getName());
        System.out.println("@Before: 参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@Before:被织入的目标对象为：" + point.getTarget());
        System.out.println("=========================Before======================================");
    }

    @AfterReturning(value = "pointcut()",returning = "returnValue")
    public void afterReturing(JoinPoint point,Object returnValue){
        System.out.println("=========================AfterReturing===============================");
        System.out.println("@AferReturing:模拟方法执行完成后");
        System.out.println("@AferReturing:方法名为：" +
                point.getSignature().getName());
        System.out.println("@AferReturing:方法的参数为：" + Arrays.toString(point.getArgs()));
        System.out.println("@AferReturing:方法的返回值为：" + returnValue);
        System.out.println("=========================AfterReturing===============================");
    }

    @After("pointcut()")
    public void afterFinall(JoinPoint joinPoint){
        System.out.println("@After:模拟释放资源");
    }

    @AfterThrowing(value = "execution(public * com.Lirs.Spring.Controller.UserController.*(..))",throwing = "ex")
    //@AfterThrowing(value = "pointcut()",throwing = "ex")
    public void afterThrowing(JoinPoint point,Throwable ex){
        System.out.println("=========================AfterThrowing===============================");
        System.out.println("@AfterThrowing：捕获到了异常：" + ex.toString());
        System.out.println("@AfterThrowing:记录日志");
        saveThrow(point,ex);
        System.out.println("=========================AfterThrowing===============================");
    }

    /**
     * 将日志记录在数据库中
     * @param point
     * @param time
     */
    private void saveLog(ProceedingJoinPoint point,long time){
        MethodSignature signature =(MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if(logAnnotation != null){
            //填充注解上的值
            sysLog.setOperation(logAnnotation.value());
        }
        //获取类名
        String className = point.getTarget().getClass().getName();
        //获取方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName);
        //请求的方法的参数值
        Object args[] = point.getArgs();
        //方法中的参数列表
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String params[] = u.getParameterNames(method);
        if(args != null && params != null){
            StringBuilder sb = new StringBuilder("");
            for(int i = 0;i < args.length;i++){
                sb.append(args[i]).append(":").append(params[i]).append("\t");
            }
            sysLog.setParams(sb.toString());
        }
        //获取request
        HttpServletRequest request =HttpUtil.getHttpServletRequest();
        /**
         * 获取ip
         */
        sysLog.setIp(HttpUtil.getIp(request));
        //模拟一个用户
        sysLog.setUsername("admin");
        sysLog.setTime((int) time);
        sysLog.setCreate_time(new Date());
        //获取UUid
        String uuid = UUIDUitl.getUUID();
        sysLog.setId(uuid);
        mapper.saveLog(sysLog);
    }


    private void saveThrow(JoinPoint point,Throwable ex){
        SysThrow sysThrow = new SysThrow();
        //设置id
        sysThrow.setId(UUIDUitl.getUUID());
        //填充异常信息
        sysThrow.setException(ex.toString());
        //填入异常发生时间
        sysThrow.setTime(new Date().toString());
        //填入异常发生的方法
        sysThrow.setMethod(point.getSignature().getName());
        //填入异常方法接收的参数
        sysThrow.setParams(Arrays.toString(point.getArgs()));
        HttpServletRequest request = HttpUtil.getHttpServletRequest();
        //填入客户端IP地址
        sysThrow.setIp(HttpUtil.getIp(request));
        throwMapper.saveThrow(sysThrow);

    }

}
