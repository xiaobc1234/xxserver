package com.bc.xx.controller;

import com.bc.xx.model.Devices;
import com.bc.xx.model.Games;
import com.bc.xx.model.Tasks;
import com.bc.xx.model.TasksQueue;
import com.bc.xx.repository.DevicesRepository;
import com.bc.xx.repository.GamesRepository;
import com.bc.xx.repository.TasksQueueRepository;
import com.bc.xx.repository.TasksRepository;
import com.bc.xx.repository.specification.GamesSpecifications;
import com.bc.xx.repository.specification.TasksQueueSpecifications;
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
public class GamesController extends BaseController {

    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private TasksRepository tasksRepository;
    @Autowired
    private TasksQueueRepository tasksQueueRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public String getGamesList(ModelMap map) {

        List<Games> list = gamesRepository.findAll();
        map.put("list", list);
        map.addAttribute("hello", "123");

        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/index")
    public String index() {
        return "html/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/table")
    public String table() {
        return "html/editable_table";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    public String games(ModelMap map) {
        Specification<Games> specification = Specifications.where(GamesSpecifications.filterByDelete(0));
        List<Games> list = gamesRepository.findAll(specification, new Sort(new Sort.Order(Sort.Direction.DESC, "id")));
        map.put("list", list);
        return "html/games";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addGames")
    @ResponseBody
    public Map<String, Object> addGames(HttpServletRequest req, ModelMap map) {
        Games games = new Games();
        games.setAppName(req.getParameter("gamesName"));
        games.setAlias(req.getParameter("alias"));
        games.setDelFlag(0);
        gamesRepository.save(games);
//        return new ModelAndView("redirect:/games/games");
        return this.buildResponse(RESPONSE_OK, games);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editGames")
    @ResponseBody
    public Map<String, Object> editGames(HttpServletRequest req, ModelMap map) {
        String id = req.getParameter("id");
        if (id == null || "".equals(id)) {
            return this.buildResponse(RESPONSE_ERROR, "更新数据无效");
        }
        Games games = new Games();
        games.setAppName(req.getParameter("gamesName"));
        games.setDelFlag(0);
        games.setId(Integer.parseInt(id));
        gamesRepository.save(games);
        return this.buildResponse(RESPONSE_OK, games);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteGames")
    @ResponseBody
    public Map<String, Object> deleteGames(HttpServletRequest req, ModelMap map) {
        Games games = gamesRepository.findOne(Integer.parseInt(req.getParameter("id")));
        games.setDelFlag(1);
        gamesRepository.save(games);
        return this.buildResponse(RESPONSE_OK, null);
    }

    //----------devices-------------

    @RequestMapping(method = RequestMethod.GET, value = "/devices/{gamesAlias}")
    public String devices(@PathVariable String gamesAlias, ModelMap map) {
        List<Devices> list = devicesRepository.findByGamesAlias(gamesAlias);
        map.put("list", list);
        map.put("gamesAlias", gamesAlias);
        return "html/devices";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteDevices")
    @ResponseBody
    public Map<String, Object> deleteDevices(HttpServletRequest req) {
        devicesRepository.delete(Integer.parseInt(req.getParameter("id")));
        return this.buildResponse(RESPONSE_OK, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editDevices")
    @ResponseBody
    public Map<String, Object> editDevices(HttpServletRequest req, ModelMap map) {
        String id = req.getParameter("id");
        if (id == null || "".equals(id)) {
            return this.buildResponse(RESPONSE_ERROR, "更新数据无效");
        }
        Devices devices = devicesRepository.findOne(Integer.parseInt(id));
        devices.setAlias(req.getParameter("alias"));
        devicesRepository.save(devices);
        return this.buildResponse(RESPONSE_OK, devices);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insertDevicesTasks")
    @ResponseBody
    public Map<String, Object> insertDevicesTasks(HttpServletRequest req, ModelMap map) {
        String tasksId = req.getParameter("tasksId");
        String type = req.getParameter("type");//1表示队首，2表示队尾
        String devIds = req.getParameter("devIds");

        if (tasksId == null || "".equals(tasksId)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择任务");
        }
        if (type == null || "".equals(type)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择插入类型");
        }
        if (devIds == null || "".equals(devIds)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择设备");
        }
        String[] devIdArray = devIds.split(",");
        for (int i = 0; i < devIdArray.length; i++) {
            int devId = Integer.parseInt(devIdArray[i]);
            if (devId > 0) {
                Devices devices = devicesRepository.findOne(devId);
                Tasks tasks = tasksRepository.findOne(Integer.parseInt(tasksId));
                int sort = 100;//默认排序100
                if (type.equals("1")) {
                    Specifications tq = Specifications.where(TasksQueueSpecifications.filterByDevicesId(devices.getDeviceId()));
                    List<TasksQueue> tqs = tasksQueueRepository.findAll(tq, new Sort(new Sort.Order(Sort.Direction.ASC, "sort")));
                    if (tqs != null && tqs.size() > 0) {
                        TasksQueue tqItem = tqs.get(0);
                        sort = tqItem.getSort() - 1;
                    }
                } else {
                    //队尾
                    Specifications tq = Specifications.where(TasksQueueSpecifications.filterByDevicesId(devices.getDeviceId()));
                    List<TasksQueue> tqs = tasksQueueRepository.findAll(tq, new Sort(new Sort.Order(Sort.Direction.DESC, "sort")));
                    if (tqs != null && tqs.size() > 0) {
                        TasksQueue tqItem = tqs.get(0);
                        sort = tqItem.getSort() + 1;
                    }
                }
                TasksQueue tasksQueue = new TasksQueue();
                tasksQueue.setDeviceAlias(devices.getAlias());
                tasksQueue.setDeviceId(devices.getDeviceId());
                tasksQueue.setSort(sort);
                tasksQueue.setTaskId(tasks.getId());
                tasksQueue.setTaskName(tasks.getTaskName());
                tasksQueueRepository.save(tasksQueue);
            }
        }
        return this.buildResponse(RESPONSE_OK, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getTaskByGamesAlias")
    @ResponseBody
    public Map<String, Object> getTaskByGamesAlias(HttpServletRequest req) {
        String gamesAlias = req.getParameter("gamesAlias");
        if (gamesAlias == null || "".equals(gamesAlias)) {
            return this.buildResponse(RESPONSE_ERROR, "查询条件无效");
        }
        List<Games> games = gamesRepository.findByAlias(gamesAlias);
        if (games != null && games.size() > 0) {
            Games game = games.get(0);
            List<Tasks> tasks = tasksRepository.findByGamesIdAndDelFlag(game.getId(), 0);
            return this.buildResponse(RESPONSE_OK, tasks);
        }
        return this.buildResponse(RESPONSE_OK, null);
    }


    //----------tasks-------------

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{gamesId}")
    public String tasks(@PathVariable Integer gamesId, ModelMap map) {

        List<Tasks> list = tasksRepository.findByGamesIdAndDelFlag(gamesId, 0);
        map.put("list", list);
        Games games = gamesRepository.findOne(gamesId);
        map.put("gamesId", gamesId);
        map.put("gamesName", games.getAppName());
        return "html/tasks";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteTasks")
    @ResponseBody
    public Map<String, Object> deleteTasks(HttpServletRequest req) {
        Tasks tasks = tasksRepository.findOne(Integer.parseInt(req.getParameter("id")));
        tasks.setDelFlag(1);
        tasksRepository.save(tasks);
        return this.buildResponse(RESPONSE_OK, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addTasks")
    @ResponseBody
    public Map<String, Object> addTasks(HttpServletRequest req, ModelMap map) {
        Integer gamesId = Integer.parseInt(req.getParameter("gamesId"));
        Games games = gamesRepository.findOne(gamesId);
        Tasks obj = new Tasks();
        obj.setTaskName(req.getParameter("tasksName"));
        obj.setGamesId(games.getId());
        obj.setGamesName(games.getAppName());
        obj.setDelFlag(0);
        obj.setMethodName(req.getParameter("methodName"));
        tasksRepository.save(obj);
        return this.buildResponse(RESPONSE_OK, obj);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editTasks")
    @ResponseBody
    public Map<String, Object> editTasks(HttpServletRequest req, ModelMap map) {
        String id = req.getParameter("id");
        if (id == null || "".equals(id)) {
            return this.buildResponse(RESPONSE_ERROR, "更新数据无效");
        }
        Tasks obj = tasksRepository.findOne(Integer.parseInt(id));
        obj.setTaskName(req.getParameter("tasksName"));
        obj.setMethodName(req.getParameter("methodName"));
        tasksRepository.save(obj);
        return this.buildResponse(RESPONSE_OK, obj);
    }


    //----------tasks-------------

    @RequestMapping(method = RequestMethod.GET, value = "/tasksQueue/{gamesAlias}/{devicesId}")
    public String tasksQueue(@PathVariable String gamesAlias, @PathVariable String devicesId, ModelMap map) {

        List<TasksQueue> list = tasksQueueRepository.findByDeviceIdOrderBySortAsc(devicesId);
        map.put("list", list);
        map.put("devicesId", devicesId);
        map.put("gamesAlias", gamesAlias);
        List<Devices> devices = devicesRepository.findByDeviceId(devicesId);
        if (devices != null && devices.size() > 0) {
            map.put("devicesAlias", devices.get(0).getAlias());
        }
        return "html/tasksQueue";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteTasksQueue")
    @ResponseBody
    public Map<String, Object> deleteTasksQueue(HttpServletRequest req) {
        tasksQueueRepository.delete(Integer.parseInt(req.getParameter("id")));
        return this.buildResponse(RESPONSE_OK, null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addTasksQueue")
    @ResponseBody
    public Map<String, Object> addTasksQueue(HttpServletRequest req) {
        String tasksId = req.getParameter("tasksId");
        String type = req.getParameter("type");//1表示队首，2表示队尾
        String devicesId = req.getParameter("devicesId");

        if (tasksId == null || "".equals(tasksId)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择任务");
        }
        if (type == null || "".equals(type)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择插入类型");
        }
        if (devicesId == null || "".equals(devicesId)) {
            return this.buildResponse(RESPONSE_ERROR, "请先选择设备");
        }
        List<Devices> list = devicesRepository.findByDeviceId(devicesId);
        if(list==null || list.size()<=0){
            return this.buildResponse(RESPONSE_ERROR, "设备已被删除");
        }
        Devices devices = list.get(0);
        Tasks tasks = tasksRepository.findOne(Integer.parseInt(tasksId));
        int sort = 100;//默认排序100
        if (type.equals("1")) {
            Specifications tq = Specifications.where(TasksQueueSpecifications.filterByDevicesId(devices.getDeviceId()));
            List<TasksQueue> tqs = tasksQueueRepository.findAll(tq, new Sort(new Sort.Order(Sort.Direction.ASC, "sort")));
            if (tqs != null && tqs.size() > 0) {
                TasksQueue tqItem = tqs.get(0);
                sort = tqItem.getSort() - 1;
            }
        } else {
            //队尾
            Specifications tq = Specifications.where(TasksQueueSpecifications.filterByDevicesId(devices.getDeviceId()));
            List<TasksQueue> tqs = tasksQueueRepository.findAll(tq, new Sort(new Sort.Order(Sort.Direction.DESC, "sort")));
            if (tqs != null && tqs.size() > 0) {
                TasksQueue tqItem = tqs.get(0);
                sort = tqItem.getSort() + 1;
            }
        }
        TasksQueue tasksQueue = new TasksQueue();
        tasksQueue.setDeviceAlias(devices.getAlias());
        tasksQueue.setDeviceId(devices.getDeviceId());
        tasksQueue.setSort(sort);
        tasksQueue.setTaskId(tasks.getId());
        tasksQueue.setTaskName(tasks.getTaskName());
        tasksQueueRepository.save(tasksQueue);
        return this.buildResponse(RESPONSE_OK, null);
    }


}
