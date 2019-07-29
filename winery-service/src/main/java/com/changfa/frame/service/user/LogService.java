package com.changfa.frame.service.user;

import com.changfa.frame.core.util.NetWorkUtil;
import com.changfa.frame.data.dto.operate.RoleDTO;
import com.changfa.frame.data.dto.operate.RoleDetailDTO;
import com.changfa.frame.data.dto.operate.RoleListDTO;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.winery.Winery;
import com.changfa.frame.data.repository.user.MenuRepository;
import com.changfa.frame.data.repository.user.RoleMenuRepository;
import com.changfa.frame.data.repository.user.RoleRepository;
import com.changfa.frame.data.repository.user.SysLogRepository;
import com.changfa.frame.data.repository.winery.WineryRepository;
import org.apache.catalina.connector.Request;
import org.apache.log4j.net.SyslogAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
@Service
public class LogService {

    private static Logger log = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private  RoleMenuRepository roleMenuRepository;
    @Autowired
    private SysLogRepository sysLogRepository;

    //添加日志
    public void addSysLog(HttpServletRequest request, AdminUser adminUser, String operation, String content ) {
        SysLog sysLog = new SysLog();
        sysLog.setOperation(operation);
        sysLog.setContent(content);
        sysLog.setLoginName(adminUser.getUserName());
        sysLog.setIp(NetWorkUtil.getLoggableAddress(request));
        sysLog.setCreateTime(new Date());
        sysLogRepository.saveAndFlush(sysLog);
    }


    //角色列表
    public List<SysLog> logList() {
        List<SysLog>  logList = sysLogRepository.findAll();

        return logList;
    }









}
