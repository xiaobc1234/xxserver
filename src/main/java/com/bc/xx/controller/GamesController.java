package com.bc.xx.controller;

import com.bc.xx.model.Devices;
import com.bc.xx.model.Games;
import com.bc.xx.repository.DevicesRepository;
import com.bc.xx.repository.GamesRepository;
import com.bc.xx.repository.specification.GamesSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * GamesController
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Controller
@RequestMapping(value = "/games")
public class GamesController extends BaseController{

    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private DevicesRepository devicesRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String getGamesList(ModelMap map){

        List<Games> list = gamesRepository.findAll();
        map.put("list",list);
        map.addAttribute("hello","123");

        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public String index(){
        return "html/index";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/table")
    public String table(){
        return "html/editable_table";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    public String games(ModelMap map){
        Specification<Games> specification= Specifications.where(GamesSpecifications.filterByDelete(0));
        List<Games> list = gamesRepository.findAll(specification,new Sort(new Sort.Order(Sort.Direction.DESC,"id")));
        map.put("list", list);
        return "html/games";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addGames")
    @ResponseBody
    public Map<String,Object> addGames(HttpServletRequest req ,ModelMap map){
        Games games = new Games();
        games.setAppName(req.getParameter("gamesName"));
        games.setAlias(req.getParameter("alias"));
        games.setDelFlag(0);
        gamesRepository.save(games);
//        return new ModelAndView("redirect:/games/games");
        return this.buildResponse(RESPONSE_OK,games);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editGames")
    @ResponseBody
    public Map<String,Object> editGames(HttpServletRequest req ,ModelMap map){
        String id = req.getParameter("id");
        if(id==null || "".equals(id)){
            return this.buildResponse(RESPONSE_ERROR,"更新数据无效");
        }
        Games games = new Games();
        games.setAppName(req.getParameter("gamesName"));
        games.setDelFlag(0);
        games.setId(Integer.parseInt(id));
        gamesRepository.save(games);
        return this.buildResponse(RESPONSE_OK,games);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteGames")
    @ResponseBody
    public Map<String,Object> deleteGames(HttpServletRequest req ,ModelMap map){
        Games games = gamesRepository.findOne(Integer.parseInt(req.getParameter("id")));
        games.setDelFlag(1);
        gamesRepository.save(games);
        return this.buildResponse(RESPONSE_OK,null);
    }

    //----------devices-------------

    @RequestMapping(method = RequestMethod.GET, value = "/devices/{gamesAlias}")
    public String devices(@PathVariable String gamesAlias, ModelMap map){
        List<Devices> list = devicesRepository.findByGamesAlias(gamesAlias);
        map.put("list", list);
        return "html/devices";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/deleteDevices")
    @ResponseBody
    public Map<String,Object> deleteDevices(HttpServletRequest req){
        devicesRepository.delete(Integer.parseInt(req.getParameter("id")));
        return this.buildResponse(RESPONSE_OK,null);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/editDevices")
    @ResponseBody
    public Map<String,Object> editDevices(HttpServletRequest req ,ModelMap map){
        String id = req.getParameter("id");
        if(id==null || "".equals(id)){
            return this.buildResponse(RESPONSE_ERROR,"更新数据无效");
        }
        Devices devices = devicesRepository.findOne(Integer.parseInt(id));
        devices.setAlias(req.getParameter("alias"));
        devicesRepository.save(devices);
        return this.buildResponse(RESPONSE_OK,devices);
    }


}
