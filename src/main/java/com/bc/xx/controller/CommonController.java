package com.bc.xx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * CommonController
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Controller
@RequestMapping
public class CommonController extends BaseController{



    @RequestMapping(method = RequestMethod.GET, value = "/error")
    public String error(){
        return "html/500";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/404")
    public String error404(){
        return "html/404";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ModelAndView index(){
        return new ModelAndView("redirect:/games/games");
    }


}
