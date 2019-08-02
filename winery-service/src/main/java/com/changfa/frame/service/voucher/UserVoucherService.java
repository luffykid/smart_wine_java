package com.changfa.frame.service.voucher;


import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserVoucherService {

    private static Logger log = LoggerFactory.getLogger(UserVoucherService.class);

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

     /* *
        * 获取每个用户有效的券
        * @Author        zyj
        * @Date          2018/10/19 15:11
        * @Description
      * */
     public List<VoucherInst> findEffective(Integer userId){
        List<UserVoucher> userVoucherList =  userVoucherRepository.findEffective(userId);
        if (userVoucherList!=null && userVoucherList.size()>0){
            List<VoucherInst> voucherInstList = new ArrayList<>();
            for (UserVoucher userVoucher:userVoucherList){
               VoucherInst voucherInst =  voucherInstRepository.getOne(userVoucher.getVoucherInstId());
               if (voucherInst.getEffectiveTime().before(new Date())){
                   voucherInstList.add(voucherInst);
               }
            }
            return voucherInstList;
        }
        return null;
     }



      /* *
         * 获取所在适用范围的优惠券
         * voucherInstList:可用优惠券
         * @Author        zyj
         * @Date          2018/10/19 15:54
         * @Description
       * */
      public List<VoucherInst> findScopeVoucher(Integer userId,String orderType){
          List<VoucherInst> voucherInstList = findEffective(userId);
          if (voucherInstList!=null && voucherInstList.size()>0){
              List<VoucherInst> voucherInstListScope = new ArrayList<>();
              for (VoucherInst voucherInst : voucherInstList){
                  if (orderType.equals("A")){
                      if (voucherInst.getScope().equals("A") || voucherInst.getScope().equals("C")){
                          voucherInstListScope.add(voucherInst);
                      }
                  }
                  if (orderType.equals("P")){
                      if (voucherInst.getScope().equals("B") || voucherInst.getScope().equals("C")){
                          voucherInstListScope.add(voucherInst);
                      }
                  }
              }
              return voucherInstListScope;
          }
          return null;
      }


       /* *
          * 获取订单可用的优惠券
          * @Author        zyj
          * @Date          2018/10/19 16:11
          * @Description
        * */
      public List<VoucherInst> findCanUseVoucher(Integer userId,String orderType, BigDecimal orderPrice){
          List<VoucherInst> voucherInstList = findScopeVoucher(userId,orderType);
          if (voucherInstList!=null){
              List<VoucherInst> voucherInstListScope = new ArrayList<>();
              for (VoucherInst voucherInst : voucherInstList){
                  if (orderPrice.compareTo(voucherInst.getEnableMoney()) >= 0){
                      voucherInstListScope.add(voucherInst);
                  }
              }
              return voucherInstListScope;
          }
          return null;
      }

    public UserVoucher checkVoucherId(Integer voucherId) {
          return userVoucherRepository.getOne(voucherId);
    }
}
