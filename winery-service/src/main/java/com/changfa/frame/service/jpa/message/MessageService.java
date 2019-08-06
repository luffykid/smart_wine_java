package com.changfa.frame.service.jpa.message;

import com.aliyuncs.exceptions.ClientException;
import com.changfa.frame.data.dto.saas.MessageDTO;
import com.changfa.frame.data.entity.message.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.MemberLevel;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.repository.message.*;
import com.changfa.frame.data.repository.user.AdminUserRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.data.repository.user.MemberWechatRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.service.jpa.util.SMSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageService {
    private static Logger log = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MemberLevelRepository memberLevelRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRangeRepository messageRangeRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private SmsTempRepository smsTempRepository;

    @Autowired
    private SmsTempParaRepository smsTempParaRepository;

    @Autowired
    private MemberWechatRepository memberWechatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SmsTempContentRepository smsTempContentRepository;

    @Autowired
    private MessageDetailRepository messageDetailRepository;


    /* *
     * 获取所有会员等级（即人群）
     * @Author        zyj
     * @Date          2018/11/9 11:50
     * @Description
     * */
    public List<Map> findAllLevel(AdminUser adminUser) {
        List<MemberLevel> memberLevelList = memberLevelRepository.findByWineryIdAndStatusOrderByUpgradeExperienceAsc(adminUser.getWineryId(), "A");
        List<Map> mapList = new ArrayList<>();
        if (memberLevelList != null) {
            for (MemberLevel memberLevel : memberLevelList) {
                Map<String, Object> map = new HashMap<>();
                map.put("levelId", memberLevel.getId());
                map.put("levelName", memberLevel.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }

    /* *
     * 添加站内消息
     * @Author        zyj
     * @Date          2018/11/9 14:17
     * @Description
     * */
    public void addMessage(AdminUser adminUser, MessageDTO messageDTO) throws ClientException {
        SmsTemp smsTemp = smsTempRepository.getOne(messageDTO.getSmsTempId());
        List<SmsTempPara> smsTempParaList = smsTempParaRepository.findBySmsTempId(messageDTO.getSmsTempId());
        SmsTempContent smsTempContent = smsTempContentRepository.findBySmsTempId(messageDTO.getSmsTempId());
        //添加消息
        Message message = new Message();
        message.setAdminUserId(adminUser.getId());
        message.setTitle(smsTemp.getName());
        message.setWineryId(adminUser.getWineryId());
        message.setRangType(messageDTO.getRangeType());
        message.setSendType(messageDTO.getSendType());
        message.setStatus(messageDTO.getStatus());
        message.setCreateTime(new Date());
        message.setStatusTime(new Date());
        message.setSmsTempId(messageDTO.getSmsTempId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", adminUser.getId());
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        message.setOrderNo(format);
        message.setContent(smsTempContent.getContent());
        Message messageSave = messageRepository.saveAndFlush(message);
        //添加消息详情
        if (smsTempParaList != null && smsTempParaList.size() > 0) {
            for (SmsTempPara smsTempPara : smsTempParaList) {
                if (smsTempPara.getIsPublic().equals("Y")) {
                    MessageDetail messageDetail = new MessageDetail();
                    messageDetail.setMessageId(messageSave.getId());
                    messageDetail.setSmsTempParaId(smsTempPara.getId());
                    messageDetail.setWineryId(adminUser.getWineryId());
                    messageDetail.setContent(messageDTO.getSmsTempParaNameList().get(smsTempPara.getCode()).toString());
                    messageDetailRepository.saveAndFlush(messageDetail);
                }
            }
        }
        //添加消息人群
        if (messageDTO.getRangeType().equals("L")) {
            List<MessageRange> messageRangeList = new ArrayList<>();
            if (messageDTO.getLevelList() != null && messageDTO.getLevelList().length > 0) {
                String[] levelIdList = messageDTO.getLevelList();
                for (String levelId : levelIdList) {
                    MessageRange messageRange = new MessageRange();
                    messageRange.setMemberLevelId(Integer.valueOf(levelId));
                    messageRange.setMessageId(messageSave.getId());
                    messageRangeList.add(messageRange);
                }
                messageRangeRepository.saveAll(messageRangeList);
            }
        } else {
            List<MessageRange> messageRangeList = new ArrayList<>();
            if (messageDTO.getPhoneList() != null && messageDTO.getPhoneList().length > 0) {
                String[] phoneList = messageDTO.getPhoneList();
                for (String phone : phoneList) {
                    Member user = memberRepository.findByPhone(phone);
                    MessageRange messageRange = new MessageRange();
                    messageRange.setUserId(Integer.valueOf(user.getId().toString()));
                    messageRange.setMessageId(messageSave.getId());
                    messageRangeList.add(messageRange);
                }
                messageRangeRepository.saveAll(messageRangeList);
            }
        }
        if (messageDTO.getStatus().equals("A")) {
            if (messageDTO.getRangeType().equals("L")) {
                if (messageDTO.getLevelList() != null) {
                    List<Object[]> userList = memberRepository.findByLevel(messageDTO.getLevelList());
                    if (userList != null && userList.size() > 0) {
                        sendSMS(userList, smsTempContent, smsTempParaList, messageDTO.getSmsTempParaNameList(), smsTemp, adminUser, messageSave);
                    }
                }

            } else {
                if (messageDTO.getPhoneList() != null && messageDTO.getPhoneList().length > 0) {
                    List<Object[]> userList = memberRepository.findUserListByPhone(messageDTO.getPhoneList());
                    if (userList != null && userList.size() > 0) {
                        sendSMS(userList, smsTempContent, smsTempParaList, messageDTO.getSmsTempParaNameList(), smsTemp, adminUser, messageSave);
                    }
                }
            }
        }
    }


    public void sendSMS(List<Object[]> userList, SmsTempContent smsTempContent, List<SmsTempPara> smsTempParaList, Map<String, Object> smsTempParaNameList, SmsTemp smsTemp, AdminUser adminUser, Message message) throws ClientException {
        for (Object[] objects : userList) {
            StringBuffer param = new StringBuffer("{");
            String userMessage = smsTempContent.getContent();
            for (int i = 0; i < smsTempParaList.size(); i++) {
                if (smsTempParaList.get(i).getCode().equals("name")) {
                    if (objects[1] != null && !objects[1].equals("")) {
                        param.append("'" + smsTempParaList.get(i).getCode() + "':'" + objects[1] + "'");
                        userMessage = userMessage.replace("${" + smsTempParaList.get(i).getCode() + "}", objects[1].toString());
                    } else {
                        param.append("'" + smsTempParaList.get(i).getCode() + "':'" + objects[2] + "'");
                        userMessage = userMessage.replace("${" + smsTempParaList.get(i).getCode() + "}", objects[2].toString());
                    }
                } else {
                    param.append("'" + smsTempParaList.get(i).getCode() + "':'" + smsTempParaNameList.get(smsTempParaList.get(i).getCode()) + "'");
                    userMessage = userMessage.replace("${" + smsTempParaList.get(i).getCode() + "}", (smsTempParaNameList.get(smsTempParaList.get(i).getCode())).toString());
                }
                if (i != smsTempParaList.size() - 1) {
                    param.append(",");
                }
                if (i == smsTempParaList.size() - 1) {
                    param.append("}");
                }
            }
            if (objects[3] != null) {
                String result = SMSUtil.sendRemindSMS(objects[3].toString(), SMSUtil.signName, smsTemp.getCode(), param);
                log.info(result.equals("200") ? "发送短信成功" : "发送短信失败");
                if (result.equals("200")) {
                    addSendMessage(Integer.valueOf(objects[0].toString()), userMessage, adminUser.getId(), message.getSendType(), message.getTitle(), adminUser.getWineryId());
                }
            }
        }
    }

    public void addSendMessage(Integer userId, String content, Integer createUserId, String sendType, String title, Integer wineryId) {
        UserMessage userMessage = new UserMessage();
        userMessage.setAdminUserId(createUserId);
        userMessage.setContent(content);
        userMessage.setCreateTime(new Date());
        userMessage.setSendType(sendType);
        userMessage.setStatus("N");
        userMessage.setStatusTime(new Date());
        userMessage.setTitle(title);
        userMessage.setWineryId(wineryId);
        userMessage.setUserId(userId);
        userMessageRepository.saveAndFlush(userMessage);
    }

    /* *
     * 修改站内消息
     * @Author        zyj
     * @Date          2018/11/9 15:35
     * @Description
     * */
    public void updateMessage(AdminUser adminUser, MessageDTO messageDTO) throws ClientException {
        Message message = messageRepository.getOne(messageDTO.getId());
        SmsTemp smsTemp = null;
        List<SmsTempPara> smsTempParaList = null;
        SmsTempContent smsTempContent = null;
        if (messageDTO.getSmsTempId() != null) {
            smsTemp = smsTempRepository.getOne(messageDTO.getSmsTempId());
            smsTempParaList = smsTempParaRepository.findBySmsTempId(messageDTO.getSmsTempId());
            smsTempContent = smsTempContentRepository.findBySmsTempId(messageDTO.getSmsTempId());
            message.setSmsTempId(messageDTO.getSmsTempId());
            message.setContent(smsTempContent.getContent());
            message.setTitle(smsTemp.getName());
        } else {
            smsTemp = smsTempRepository.getOne(message.getSmsTempId());
            smsTempParaList = smsTempParaRepository.findBySmsTempId(message.getSmsTempId());
            smsTempContent = smsTempContentRepository.findBySmsTempId(message.getSmsTempId());
        }
        //添加消息
        /*if (messageDTO.getTitle() != null && !messageDTO.getTitle().equals("")) {
            message.setTitle(messageDTO.getTitle());
        }*/
        if (messageDTO.getRangeType() != null && !messageDTO.getRangeType().equals("")) {
            message.setRangType(messageDTO.getRangeType());
        }
        if (messageDTO.getSendType() != null && !messageDTO.getSendType().equals("")) {
            message.setSendType(messageDTO.getSendType());
        }
        if (messageDTO.getStatus() != null && !messageDTO.getStatus().equals("")) {
            message.setStatus(messageDTO.getStatus());
            message.setStatusTime(new Date());
        }
        Message messageSave = messageRepository.saveAndFlush(message);

        if (messageDTO.getSmsTempParaNameList() != null && messageDTO.getSmsTempParaNameList().size() > 0) {
            messageDetailRepository.deleteByMessageId(messageDTO.getId());
            if (smsTempParaList != null && smsTempParaList.size() > 0) {
                for (SmsTempPara smsTempPara : smsTempParaList) {
                    if (smsTempPara.getIsPublic().equals("Y")) {
                        MessageDetail messageDetail = new MessageDetail();
                        messageDetail.setMessageId(messageSave.getId());
                        messageDetail.setSmsTempParaId(smsTempPara.getId());
                        messageDetail.setWineryId(adminUser.getWineryId());
                        messageDetail.setContent(messageDTO.getSmsTempParaNameList().get(smsTempPara.getCode()).toString());
                        messageDetailRepository.saveAndFlush(messageDetail);
                    }
                }
            }
        }

        //添加消息人群
        if (messageDTO.getLevelList() != null && messageDTO.getLevelList().length > 0) {
            messageRangeRepository.deleteByMessageId(messageDTO.getId());
            if (messageDTO.getRangeType().equals("L")) {
                List<MessageRange> messageRangeList = new ArrayList<>();
                if (messageDTO.getLevelList() != null) {
                    String[] levelIdList = messageDTO.getLevelList();
                    for (String levelId : levelIdList) {
                        MessageRange messageRange = new MessageRange();
                        messageRange.setMemberLevelId(Integer.valueOf(levelId));
                        messageRange.setMessageId(messageSave.getId());
                        messageRangeList.add(messageRange);
                    }
                    messageRangeRepository.saveAll(messageRangeList);
                }
            }
        }
        if (messageDTO.getPhoneList() != null && messageDTO.getPhoneList().length > 0) {
            messageRangeRepository.deleteByMessageId(messageDTO.getId());
            List<MessageRange> messageRangeList = new ArrayList<>();
            if (messageDTO.getPhoneList() != null) {
                String[] phoneList = messageDTO.getPhoneList();
                for (String phone : phoneList) {
                    Member user = memberRepository.findByPhone(phone);
                    MessageRange messageRange = new MessageRange();
                    messageRange.setUserId(Integer.valueOf(user.getId().toString()));
                    messageRange.setMessageId(messageSave.getId());
                    messageRangeList.add(messageRange);
                }
                messageRangeRepository.saveAll(messageRangeList);
            }
        }
        if (messageDTO.getStatus().equals("A")) {
            if (messageDTO.getRangeType().equals("L")) {
                if (messageDTO.getLevelList() != null) {
                    List<Object[]> userList = memberRepository.findByLevel(messageDTO.getLevelList());
                    if (userList != null && userList.size() > 0) {
                        sendSMS(userList, smsTempContent, smsTempParaList, messageDTO.getSmsTempParaNameList(), smsTemp, adminUser, messageSave);
                    }
                }

            } else {
                if (messageDTO.getPhoneList() != null && messageDTO.getPhoneList().length > 0) {
                    List<Object[]> userList = memberRepository.findUserListByPhone(messageDTO.getPhoneList());
                    if (userList != null && userList.size() > 0) {
                        sendSMS(userList, smsTempContent, smsTempParaList, messageDTO.getSmsTempParaNameList(), smsTemp, adminUser, messageSave);
                    }
                }
            }
        }
    }


    public List<MessageDTO> getMessageDTOList(List<Message> messagesList) {
        List<MessageDTO> messageDTOList = new ArrayList<>();
        if (messagesList != null && messagesList.size() > 0) {
            for (Message message : messagesList) {
                SmsTemp smsTemp = smsTempRepository.getOne(message.getSmsTempId());
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setId(message.getId());
                messageDTO.setAdminUserId(message.getAdminUserId());
                AdminUser adminUser = adminUserRepository.getOne(message.getAdminUserId());
                messageDTO.setCreateUserName(adminUser.getUserName());
                messageDTO.setTitle(smsTemp.getName());
                messageDTO.setSendType(message.getSendType());
                messageDTO.setCreateTime(message.getCreateTime());
                if (message.getRangType().equals("L")) {
                    messageDTO.setLevelName(messageRangeRepository.findLevelName(message.getId()));
                } else {
                    messageDTO.setUserName(messageRangeRepository.findUserName(message.getId()));
                }
                messageDTOList.add(messageDTO);
            }
        }
        return messageDTOList;
    }

    /* *
     * 获取已发送消息
     * @Author        zyj
     * @Date          2018/11/9 16:09
     * @Description
     * */
    public List<MessageDTO> findMessageList(AdminUser adminUser, String status) {
        List<Message> messagesList = messageRepository.findByWineryIdAndStatus(adminUser.getWineryId(), status);
        return getMessageDTOList(messagesList);
    }

    /* *
     * 根据条件筛查
     * @Author        zyj
     * @Date          2018/11/9 16:58
     * @Description
     * */
    public List<MessageDTO> findMessageListLike(AdminUser adminUser, String status, String like, String type) {
        List<Message> messagesList = new ArrayList<>();
        if (type.equals("T")) {
            messagesList = messageRepository.findByWineryIdAndTitleLike(like, adminUser.getWineryId(), status);
            if (like == null || like.equals("")) {
                messagesList = messageRepository.findByWineryIdAndType(adminUser.getWineryId(), status);
            }
        } else {
            messagesList = messageRepository.findByWineryIdAndCreateTimeLike(like, adminUser.getWineryId(), status);
            if (like == null || like.equals("")) {
                messagesList = messageRepository.findByWineryIdAndType(adminUser.getWineryId(), status);
            }
        }
        return getMessageDTOList(messagesList);
    }

    public MessageDTO findMessageDetail(Integer messageId) {
        Message message = messageRepository.getOne(messageId);
        SmsTemp smsTemp = smsTempRepository.getOne(message.getSmsTempId());
        if (message != null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setAdminUserId(message.getAdminUserId());
            AdminUser adminUser = adminUserRepository.getOne(message.getAdminUserId());
            messageDTO.setCreateUserName(adminUser.getUserName());
            messageDTO.setTitle(smsTemp.getName());
            messageDTO.setSendType(message.getSendType());
            messageDTO.setCreateTime(message.getCreateTime());
            if (message.getRangType().equals("L")) {
                messageDTO.setLevelName(messageRangeRepository.findLevelName(message.getId()));
            } else {
                messageDTO.setUserName(messageRangeRepository.findUserName(message.getId()));
            }
            messageDTO.setContent(message.getContent());
            return messageDTO;
        }
        return null;
    }

    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
        messageRangeRepository.deleteByMessageId(messageId);
    }

    public void enableMessage(Integer messageId, AdminUser adminUser) throws ClientException {
        Message message = messageRepository.getOne(messageId);
        SmsTempContent smsTempContent = smsTempContentRepository.findBySmsTempId(message.getSmsTempId());
        List<SmsTempPara> smsTempParaList = smsTempParaRepository.findBySmsTempId(message.getSmsTempId());
        SmsTemp smsTemp = smsTempRepository.getOne(message.getSmsTempId());
        Map<String, Object> smsTempParaDetail = new HashMap<>();
        if (smsTempParaList != null && smsTempParaList.size() > 0) {
            for (SmsTempPara smsTempPara : smsTempParaList) {
                if (smsTempPara.getIsPublic().equals("Y")) {
                    smsTempParaDetail.put(smsTempPara.getCode(), messageDetailRepository.findByMessageIdAndSmsTempParaId(messageId, smsTempPara.getId()).getContent());
                }
            }
        }
        message.setStatus("A");
        if (message.getRangType().equals("L")) {
            List<Integer> levelIdList = messageRangeRepository.findLevelByMessageId(messageId);
            if (levelIdList != null && levelIdList.size() > 0) {
                List<Object[]> userList = memberRepository.findByLevel(levelIdList);
                if (userList != null && userList.size() > 0) {
                    sendSMS(userList, smsTempContent, smsTempParaList, smsTempParaDetail, smsTemp, adminUser, message);
                }
            }
        } else {
            List<String> phoneList = messageRangeRepository.findPhoneByMessageId(messageId);
            if (phoneList != null && phoneList.size() > 0) {
                List<Object[]> userList = memberRepository.findUserListByPhone(phoneList);
                if (userList != null && userList.size() > 0) {
                    sendSMS(userList, smsTempContent, smsTempParaList, smsTempParaDetail, smsTemp, adminUser, message);
                }
            }
        }
        message.setStatusTime(new Date());
        messageRepository.saveAndFlush(message);
    }

    public List<Map> findMessageTime(AdminUser adminUser, String status) {
        List<Date> timeList = messageRepository.findCreateTimeByWineryIdAndStatus(adminUser.getWineryId(), status);
        List<Map> mapList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        if (timeList != null) {
            for (Date date : timeList) {
                Map map = new HashMap();
                map.put("time", formatter.format(date));
                mapList.add(map);
            }
        }
        return mapList;
    }


    /* *
     * 获取所有短信模板
     * @Author        zyj
     * @Date          2018/11/30 11:31
     * @Description
     * */
    public List<MessageDTO> getSMSTempList(AdminUser adminUser) {
        List<SmsTemp> smsTempList = smsTempRepository.findByWineryId(1);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        if (smsTempList != null && smsTempList.size() > 0) {
            for (SmsTemp smsTemp : smsTempList) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setSmsTempId(smsTemp.getId());
                messageDTO.setSmsTempName(smsTemp.getName());
                messageDTOList.add(messageDTO);
            }
        }
        return messageDTOList;
    }

    public List<MessageDTO> getSMSTempParaList(Integer SMSTempId) {
        List<SmsTempPara> smsTempParaList = smsTempParaRepository.findBySmsTempIdAndIsPublic(SMSTempId);
        List<MessageDTO> messageDTOList = new ArrayList<>();
        if (smsTempParaList != null && smsTempParaList.size() > 0) {
            for (SmsTempPara smsTempPara : smsTempParaList) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setSmsTempParaName(smsTempPara.getName());
                messageDTO.setSmsTempParaCode(smsTempPara.getCode());
                messageDTOList.add(messageDTO);
            }
        }
        return messageDTOList;
    }

    /* *
     * 根据电话或名称查找用户
     * @Author        zyj
     * @Date          2018/11/30 15:54
     * @Description
     * */
    public List<Map<String, Object>> findUserByLike(AdminUser adminUser, String like) {
        List<Object[]> userList = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (like != null && !like.equals("")) {
            userList.addAll(memberWechatRepository.findByNickNameLike(adminUser.getWineryId().longValue(), like));
            userList.addAll(memberRepository.findByWineryIdAndPhonelike(adminUser.getWineryId(), like));
        }
        if (userList != null && userList.size() > 0) {
            for (Object[] objects : userList) {
                Map<String, Object> map = new HashMap<>();
                map.put("nickName", objects[0]);
                map.put("phone", objects[1]);
                map.put("value", objects[0]);
                mapList.add(map);
            }
        }
        return mapList;
    }

}
