package com.bc.xx.controller;

import com.bc.xx.model.Devices;
import com.bc.xx.model.Tasks;
import com.bc.xx.model.TasksLog;
import com.bc.xx.model.TasksQueue;
import com.bc.xx.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CommonController
 *
 * @author xiaobc
 * @date 17/6/3
 */
@Controller
@RequestMapping(value="/client")
public class ClientController extends BaseController{


    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private DevicesRepository devicesRepository;
    @Autowired
    private TasksLogRepository tasksLogRepository;
    @Autowired
    private TasksQueueRepository tasksQueueRepository;
    @Autowired
    private TasksRepository tasksRepository;


    /**
     * 根据设备id  请求下一个任务
     * @param devicesId
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/tasksQueue/{devicesId}")
    @ResponseBody
    public Map<String,Object> tasksQueue(@PathVariable String devicesId, ModelMap map) {
        List<TasksQueue> list = tasksQueueRepository.findByDeviceIdOrderBySortAsc(devicesId);
        TasksQueue tq = null;
        Tasks t = null;
        if(list!=null && list.size()>0){
            tq = list.get(0);
            t= tasksRepository.findOne(tq.getTaskId());
            if(t==null){
                return this.buildResponse(RESPONSE_ERROR, "没有对应的任务");
            }
        }
        // 将传过去的任务转移到日志中去
        TasksLog log = new TasksLog();
        log.setCreateDate(new Date());
        log.setDeviceAlias(tq.getDeviceAlias());
        log.setDeviceId(tq.getDeviceId());
        log.setTaskId(tq.getTaskId());
        log.setTaskName(tq.getTaskName());
        tasksLogRepository.save(log);
        //删除任务队列该节点
        tasksQueueRepository.delete(tq.getId());

        return this.buildResponse(RESPONSE_OK, t.getMethodName());
    }

    /**
     * 打开脚本的时候请求一次，记录下设备id
     * @param gamesAlias
     * @param devicesId
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/devices/{gamesAlias}/{devicesId}")
    @ResponseBody
    public Map<String,Object> tasksQueue(@PathVariable String gamesAlias,@PathVariable String devicesId) {
        if(gamesAlias==null || devicesId==null){
            return this.buildResponse(RESPONSE_ERROR, "参数错误！");
        }
        List<Devices> devices = devicesRepository.findByDeviceId(devicesId);
        if(devices==null || devices.size()<=0){
            Devices dev = new Devices();
            dev.setDeviceId(devicesId);
            dev.setGamesAlias(gamesAlias);
            devicesRepository.save(dev);
        }
        return this.buildResponse(RESPONSE_OK, null);
    }



}
