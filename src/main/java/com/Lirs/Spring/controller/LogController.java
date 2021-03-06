package com.Lirs.Spring.controller;

import com.Lirs.Spring.annotation.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class LogController {

    @Log(value = "接收单个参数方法")
    @RequestMapping("/testLog")
    public String testLog(String arg){
        return "接收的参数为：" + arg;
    }

    @Log(value = "记录暂停2秒的时间")
    @RequestMapping("/testTime")
    public String testTime(String arg) throws InterruptedException{
        Thread.sleep(2000);
        return arg;
    }
    @Log(value = "测试记录多参数")
    @RequestMapping("/testParams")
    public String testParams(String arg , String username){
        System.out.println("***********************被代理的方法执行**********************");
        return username + " : " + arg;
    }

    @Log(value = "测试异常日志捕获")
    @RequestMapping("/testThrow")
    public String testThrow(String arg){
        throw new RuntimeException();
    }
	
	public String test(){
		return "测试多项目分支合并,test分支";
	}

	public String test2(){
        return "第二次测试";
    }
	
	public String test3(){
		return null;
	}
}
