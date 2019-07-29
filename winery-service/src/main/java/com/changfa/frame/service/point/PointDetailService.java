package com.changfa.frame.service.point;

import com.changfa.frame.data.dto.wechat.PointDetailDTO;
import com.changfa.frame.data.entity.point.UserPoint;
import com.changfa.frame.data.entity.point.UserPointDetail;
import com.changfa.frame.data.entity.user.User;
import com.changfa.frame.data.repository.point.UserPointDetailRepository;
import com.changfa.frame.data.repository.point.UserPointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointDetailService {

    private static Logger log = LoggerFactory.getLogger(PointDetailService.class);


    @Autowired
    private UserPointRepository userPointRepository;

    @Autowired
    private UserPointDetailRepository userPointDetailRepository;



    /* *
     * 积分明细
     * @Author        zyj
     * @Date          2018/10/16 13:40
     * @Description
     * */
    public Map<String, Object> pointDetailList(User user) {
        //当前积分
        UserPoint userPoint = userPointRepository.findByUserId(user.getId());

        //积分流水
        List<UserPointDetail> userPointDetailList = userPointDetailRepository.findByUserIdOrderByCreateTimeDesc(user.getId());
        if (userPointDetailList != null) {
            List<PointDetailDTO> pointDetailDTOList = new ArrayList<>();
            for (UserPointDetail userPointDetail : userPointDetailList) {
                PointDetailDTO pointDetailDTO = new PointDetailDTO();
                pointDetailDTO.setDate(userPointDetail.getCreateTime());
                pointDetailDTO.setPointChange(userPointDetail.getPoint());
                pointDetailDTO.setStatus(userPointDetail.getAction());
                pointDetailDTO.setUserPointDetailId(userPointDetail.getId());
                if (pointDetailDTO.getPointChange()!=null && pointDetailDTO.getPointChange()!=0) {
                    pointDetailDTOList.add(pointDetailDTO);
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("point", userPoint.getPoint());
            map.put("pointDetail", pointDetailDTOList);
            return map;
        }

        return null;
    }


}
