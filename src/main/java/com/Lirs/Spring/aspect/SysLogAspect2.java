//package com.Lirs.Spring.aspect;
//
//import com.Lirs.Spring.localMapper.SysLogMapper;
//import com.Lirs.Spring.model.SysLog;
//import com.Lirs.Spring.util.HttpUtil;
//import com.Lirs.Spring.util.UUIDUitl;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.Date;
//
//@Aspect
//@Component
//public class SysLogAspect2 {
//    @Autowired
//    private SysLogMapper mapper;
//
//    @Pointcut("execution(public * com.Lirs.Spring.controller.LogController.*(..))")
//    private void pointcut(){}
//
//   // @Before("pointcut()")
//    public void before(JoinPoint point){
//        System.out.println("模拟校验操作");
//        this.save(point,1);
//    }
//
//    @Around("pointcut()")
//    public Object around(ProceedingJoinPoint point){
//                long begin = System.currentTimeMillis();
//        System.out.println("@Around:模拟around环绕增强");
//        System.out.println("@Around:原本输入的参数" + Arrays.toString(point.getArgs()));
//        Object args[] = point.getArgs();
//        Object result = new Object();
//        if(args != null && args.length > 0){
//            for(int i = 0; i < args.length ; i++){
//                args[i] = "改变原方法的参数";
//            }
//        }
//        System.out.println("参数修改完毕");
//        try{
//            result = point.proceed(args);
//        }catch(Throwable e){
//            e.printStackTrace();
//        }
//        long time = System.currentTimeMillis() - begin;
//        System.out.println("@Around：记录日志");
//        save(point,time);
//        return result;
//    }
//
//    /**
//     * 写入数据库
//     * @param point
//     * @param time
//     * @return
//     */
//    private boolean save(JoinPoint point,long time){
//        try {
//            Signature signature = point.getSignature();
//            SysLog sysLog = new SysLog();
//            //获取类名
//            String className = point.getTarget().getClass().getName();
//            //获取方法名
//            String methodName = signature.getName();
//            sysLog.setMethod(className + "." + methodName);
//            //请求的方法的参数值
//            Object args[] = point.getArgs();
//            //方法中的参数列表
//            if(args != null){
//                StringBuilder sb = new StringBuilder();
//                for(int i = 0;i < args.length;i++){
//                    sb.append(" " + args[i] + " ");
//                }
//                sysLog.setParams(sb.toString());
//            }
//            //获取request
//            HttpServletRequest request =HttpUtil.getHttpServletRequest();
//            /**
//             * 获取ip
//             */
//            sysLog.setIp(HttpUtil.getIp(request));
//            //模拟一个用户
//            sysLog.setUsername("admin");
//            sysLog.setTime((int) time);
//            sysLog.setCreate_time(new Date());
//            //获取UUid
//            String uuid = UUIDUitl.getUUID();
//            sysLog.setId(uuid);
//            sysLog.setOperation("用户操作");
//            mapper.saveLog(sysLog);
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return true;
//    }
//
//}
