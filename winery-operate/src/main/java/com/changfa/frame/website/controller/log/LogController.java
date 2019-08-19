package com.changfa.frame.website.controller.log;

import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Role;
import com.changfa.frame.data.entity.user.SysLog;
import com.changfa.frame.data.repository.user.RoleRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.service.jpa.user.LogService;
import com.changfa.frame.service.jpa.user.RoleService;
import com.changfa.frame.service.jpa.winery.WineryService;
import com.changfa.frame.website.utils.JsonReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "日志管理", tags = "日志管理")
@RestController
@RequestMapping("/log")
public class LogController {

    private static Logger log = LoggerFactory.getLogger(LogController.class);

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private WineryRepository wineryRepository;
    @Autowired
    private WineryService wineryService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    private LogService logService;



    @ApiOperation(value = "添加日志记录测试", notes = "添加日志记录测试")
    @RequestMapping(value = "/addLog", method = RequestMethod.POST)
    public String addLog(@RequestParam("token") String token) {
        try {
            log.info("添加日志记录测试:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            Role role = null;
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            }
            logService.addSysLog(request, adminUser, "测试", "测试添加日志接口" );
            return JsonReturnUtil.getJsonIntReturn(0, "添加日志记录测试成功");

        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }

    }

    @ApiOperation(value = "日志列表", notes = "日志列表")
    @RequestMapping(value = "/logList", method = RequestMethod.POST)
    public String logList(@RequestParam("token") String token) {
        try {
            log.info("日志列表:token:" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken2(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<SysLog>  logList = logService.logList();
                return JsonReturnUtil.getJsonObjectReturn(0, "", "", logList).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
















    //********************************************************************************************************************


}
