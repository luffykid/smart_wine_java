package com.changfa.frame.website.controller.message;

import com.changfa.frame.data.dto.saas.MessageDTO;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.service.jpa.message.MessageService;
import com.changfa.frame.service.jpa.user.AdminUserService;
import com.changfa.frame.website.common.JsonReturnUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {

    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private MessageService messageService;


    /* *
     * 获取当前酒庄所有会员等级（人群）
     * @Author        zyj
     * @Date          2018/11/9 11:44
     * @Description
     * */
    @RequestMapping(value = "/levelList", method = RequestMethod.POST)
    public String addPointRewardRule(@RequestParam("token") String token) {
        try {
            log.info("获取当前酒庄所有会员等级+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Map> mapList = messageService.findAllLevel(adminUser);
                if (mapList != null && mapList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", mapList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


    /* *
     * 添加消息
     * @Author        zyj
     * @Date          2018/11/9 14:02
     * @Description
     * */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody MessageDTO messageDTO) {
        try {
            log.info("添加消息+" + messageDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(messageDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + messageDTO.getToken());
            } else {
                messageService.addMessage(adminUser, messageDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


     /* *
        * 修改站内消息
        * @Author        zyj
        * @Date          2018/11/9 15:24
        * @Description
      * */
   @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody MessageDTO messageDTO) {
        try {
            log.info("修改站内消息+" + messageDTO.getToken());
            AdminUser adminUser = adminUserService.findAdminUserByToken(messageDTO.getToken());
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + messageDTO.getToken());
            } else {
                messageService.updateMessage(adminUser, messageDTO);
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


     /* *
        * @Author        zyj
        * @Date          2018/11/9 16:18
        * @Description
      * */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@RequestParam("token") String token,@RequestParam("status") String status) {
        try {
            log.info("获取站内消息+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<MessageDTO> messageList = messageService.findMessageList(adminUser,status);
                if (messageList != null && messageList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", messageList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
     /* *
        * 根据条件获取站内消息
        * @Author        zyj
        * @Date          2018/11/9 17:00
        * @Description
      * */
    @RequestMapping(value = "/listLike", method = RequestMethod.POST)
    public String listLike(@RequestParam("token") String token,@RequestParam("status") String status,@RequestParam("like") String like,@RequestParam("type")String type) {
        try {
            log.info("根据条件获取站内消息+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<MessageDTO> messageList = messageService.findMessageListLike(adminUser,status,like,type);
                if (messageList != null && messageList.size() > 0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", messageList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

     /* *
        * 消息详情
        * @Author        zyj
        * @Date          2018/11/9 17:42
        * @Description
      * */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(@RequestParam("token") String token,@RequestParam("messageId") Integer messageId) {
        try {
            log.info("消息详情+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                MessageDTO messageDTO = messageService.findMessageDetail(messageId);
                if (messageDTO != null ) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", messageDTO).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("token") String token,@RequestParam("messageId") Integer messageId) {
        try {
            log.info("删除站内消息+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                messageService.deleteMessage(messageId);
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }


     /* *
        * 获取消息创建时间
        * @Author        zyj
        * @Date          2018/11/14 10:56
        * @Description
      * */
    @RequestMapping(value = "/findTime", method = RequestMethod.POST)
    public String findTime(@RequestParam("token") String token,@RequestParam("status") String status) {
        try {
            log.info("获取消息创建时间+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
               List<Map> timeList = messageService.findMessageTime(adminUser,status);
                if (timeList != null && timeList.size()>0) {
                    return JsonReturnUtil.getJsonObjectReturn(0, "200", "查询成功", timeList).toString();
                } else {
                    return JsonReturnUtil.getJsonIntReturn(1, "暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

     /* *
        * 草稿发布
        * @Author        zyj
        * @Date          2018/11/15 10:53  
        * @Description
      * */
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public String enable(@RequestParam("token") String token,@RequestParam("messageId") Integer messageId) {
        try {
            log.info("草稿发布+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                messageService.enableMessage(messageId,adminUser);
                return JsonReturnUtil.getJsonIntReturn(0, "成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

     /* *
        * 获取短信消息模板
        * @Author        zyj
        * @Date          2018/11/30 11:24
        * @Description
      * */
     @RequestMapping(value = "/getSMSTemp", method = RequestMethod.POST)
     public String getSMSTemp(@RequestParam("token") String token) {
         try {
             log.info("获取短信消息模板+" + token);
             AdminUser adminUser = adminUserService.findAdminUserByToken(token);
             if (adminUser == null) {
                 return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
             } else {
                List<MessageDTO> messageDTOList =  messageService.getSMSTempList(adminUser);
                if (messageDTOList!=null && messageDTOList.size()>0){
                    return JsonReturnUtil.getJsonObjectReturn(0,"200","查询成功",messageDTOList).toString();
                }else{
                    return JsonReturnUtil.getJsonReturn(0,"100","暂无");
                }
             }
         } catch (Exception e) {
             e.printStackTrace();
             return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
         }
     }


      /* *
         * @Author        zyj
         * @Date          2018/11/30 14:43
         * @Description
       * */
    @RequestMapping(value = "/getSMSTempParaList", method = RequestMethod.POST)
    public String getSMSTempParaList(@RequestParam("token") String token,@RequestParam("smsTempId")Integer smsTempId) {
        try {
            log.info("获取短信模板参数列表+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<MessageDTO> messageDTOList =  messageService.getSMSTempParaList(smsTempId);
                if (messageDTOList!=null && messageDTOList.size()>0){
                    return JsonReturnUtil.getJsonObjectReturn(0,"200","查询成功",messageDTOList).toString();
                }else{
                    return JsonReturnUtil.getJsonReturn(0,"100","暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }

     /* *
        * 搜索用户
        * @Author        zyj
        * @Date          2018/11/30 16:09  
        * @Description
      * */
    @RequestMapping(value = "/findUserByLike", method = RequestMethod.POST)
    public String findUserByLike(@RequestParam("token") String token,@RequestParam("like")String like) {
        try {
            log.info("获取短信模板参数列表+" + token);
            AdminUser adminUser = adminUserService.findAdminUserByToken(token);
            if (adminUser == null) {
                return JsonReturnUtil.getJsonIntReturn(1, "找不到token" + token);
            } else {
                List<Map<String,Object>> mapList =  messageService.findUserByLike(adminUser,like);
                if (mapList!=null && mapList.size()>0){
                    return JsonReturnUtil.getJsonObjectReturn(0,"200","查询成功",mapList).toString();
                }else{
                    return JsonReturnUtil.getJsonReturn(0,"100","暂无");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonReturnUtil.getJsonIntReturn(500, e.getMessage());
        }
    }
}
