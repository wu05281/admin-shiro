package com.wu.admin.web.controller.quartz;

import com.wu.admin.base.Pagination;
import com.wu.admin.pojo.auth.dto.ResponseDTO;
import com.wu.admin.pojo.quartz.entity.JobDetailDO;
import com.wu.admin.service.quartz.JobDetailService;
import com.wu.admin.web.ao.JobAddAO;
import com.wu.admin.web.ao.JobUpdateAO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotEmpty;
import org.quartz.CronExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Objects;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author :  wubo
 * @version :  1.0.0
 *          create time : 2017 10 24 下午5:24
 */
@Controller
public class QuartzCtl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private JobDetailService jobDetailService;

    @RequiresPermissions("job:index")
    @RequestMapping(value = "/job/index", method = RequestMethod.GET)
    public String index() {

        return "/quartz/index";
    }

    @ResponseBody
    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ResponseDTO<Pagination> page() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Integer pageSize = Integer.valueOf(Objects.nonNull(request.getParameter("pageSize")) ? request.getParameter("pageSize") : "20");
        Integer pageNumber = Integer.valueOf(Objects.nonNull(request.getParameter("pageIndex")) ? request.getParameter("pageIndex") : "1");
        String dec = request.getParameter("description");
        Integer state = Integer.valueOf(Objects.nonNull(request.getParameter("state")) ? request.getParameter("state") : "0");
        //
        //TODO 获取过滤条件.
        Pagination<JobDetailDO> page = jobDetailService.pageByConditions(pageNumber, pageSize, dec, state);

        return new ResponseDTO<>(1, "success", page);
    }

    @RequiresPermissions("job:index")
    @RequestMapping(value = "/job/toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return "quartz/add";
    }

    /**
     * 注意：@RequestBody需要把所有请求参数作为json解析，因此，不能包含key=value这样的写法在请求url中，所有的请求参数都是一个json
     *
     */
    @RequiresPermissions("job:index")
    @ResponseBody
    @RequestMapping(value = "job", method = RequestMethod.POST)
    public ResponseDTO add(@RequestBody JobAddAO jobAddAO) {
        try {
            //cron表达式合法性校验
            CronExpression exp = new CronExpression(jobAddAO.getCron());
            JobDetailDO jobDetailDO = new JobDetailDO();
            BeanUtils.copyProperties(jobAddAO, jobDetailDO);
            jobDetailService.addJobDetail(jobDetailDO);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseDTO(0, "failed", null);
        }
        return new ResponseDTO(1, "success", null);

    }

    @ResponseBody
    @RequestMapping(value = "job", method = RequestMethod.PUT)
    public ResponseDTO update(@RequestBody JobUpdateAO ao) {
        //cron表达式合法性校验
        try {
            CronExpression exp = new CronExpression(ao.getNewCron());
            jobDetailService.updateJobDetail(ao.getId(), ao.getNewCron());
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseDTO(0, "failed", null);
        }
        logger.info("请求参数：{}", ao);
        return new ResponseDTO(1, "", null);
    }

    @ResponseBody
    @RequestMapping(value = "job/{id}/{opType}", method = RequestMethod.PUT)
    public ResponseDTO runNow(@PathVariable @NotEmpty String id, @PathVariable @NotEmpty String opType) {
        try {

            logger.info("请求参数：{}, {}", id, opType);
            //立即执行
            if (Objects.equals(opType, "1")) {
                jobDetailService.runNow(Integer.parseInt(id));
            } else if (Objects.equals(opType, "2")) {//暂停
                jobDetailService.stopNow(Integer.parseInt(id));
            } else if (Objects.equals(opType, "3")) {//恢复
                jobDetailService.resumeNow(Integer.parseInt(id));
            } else {
                return new ResponseDTO(0, "failed", null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseDTO(1, "", null);
    }

    @ResponseBody
    @RequestMapping(value = "job/{id}", method = RequestMethod.DELETE)
    public ResponseDTO del(@PathVariable @NotEmpty String id) {
        logger.info("请求参数：{}", id);
        //立即执行
        jobDetailService.delJob(Integer.parseInt(id));
        return new ResponseDTO(1, "", null);
    }
}
