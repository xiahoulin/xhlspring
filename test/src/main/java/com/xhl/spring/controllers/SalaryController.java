package com.xhl.spring.controllers;

import com.xhl.spring.web.mvc.Controller;
import com.xhl.spring.web.mvc.RequestMapping;
import com.xhl.spring.web.mvc.RequestParam;

@Controller
public class SalaryController {
    @RequestMapping("/getSalary")
    public Integer getSalary(@RequestParam("name") String name, @RequestParam("experience") String experience){
        return 10000;
    }
}
