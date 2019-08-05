package com.changfa.frame.service.deposit;


import com.changfa.frame.data.dto.wechat.UserDepositDetailDTO;
import com.changfa.frame.data.entity.deposit.DepositOrder;
import com.changfa.frame.data.entity.deposit.UserDepositDetail;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.repository.deposit.DepositOrderRepository;
import com.changfa.frame.data.repository.deposit.UserDepositDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDepositDetailService {
    private static Logger log = LoggerFactory.getLogger(UserDepositDetailService.class);

    @Autowired
    private UserDepositDetailRepository userDepositDetailRepository;

    @Autowired
    private DepositOrderRepository depositOrderRepository;

    /* *
     *
     * 储值明细
     * @Author        zyj
     * @Date          2018/10/18 10:50
     * @Description
     * */
    public List<UserDepositDetailDTO> getUserDepositDetail(Member user) {
        List<UserDepositDetail> userDepositDetailList = userDepositDetailRepository.findByUserIdOrderByCreateTimeDesc(user.getId().intValue());
        if (userDepositDetailList != null && userDepositDetailList.size() > 0) {
            List<UserDepositDetailDTO> userDepositDetailDTOList = new ArrayList<>();
            for (UserDepositDetail userDepositDetail : userDepositDetailList) {
                DepositOrder depositOrder = depositOrderRepository.getOne(userDepositDetail.getOrderId());
                UserDepositDetailDTO userDepositDetailDTO = new UserDepositDetailDTO();
                userDepositDetailDTO.setOrderId(userDepositDetail.getOrderId());
                userDepositDetailDTO.setMoney(userDepositDetail.getBalance());
                userDepositDetailDTO.setCreateTime(userDepositDetail.getCreateTime());
                userDepositDetailDTO.setAction(userDepositDetail.getAction());
                userDepositDetailDTO.setRewardMoney(depositOrder.getRewardMoney());
                userDepositDetailDTOList.add(userDepositDetailDTO);
            }
            return userDepositDetailDTOList;
        }
        return null;
    }


}
