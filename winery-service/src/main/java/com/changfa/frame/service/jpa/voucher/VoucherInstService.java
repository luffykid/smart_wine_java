package com.changfa.frame.service.jpa.voucher;

import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.jpa.dict.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherInstService {


    private static Logger log = LoggerFactory.getLogger(VoucherInstService.class);


    @Autowired
    public UserVoucherRepository userVoucherRepository;

    @Autowired
    public VoucherInstRepository voucherInstRepository;

    @Autowired
    public DictService dictService;

    @Autowired
    private ProdRepository prodRepository;

    public List<VoucherInstDTO> getVoucherInstList(Member user, String status) {
        List<UserVoucher> userVoucherList = null;
        if (status.equals("A")) {
            userVoucherList = userVoucherRepository.findEffective(user.getId().intValue());
        }
        if (status.equals("U")) {
            userVoucherList = userVoucherRepository.findUseVoucher(user.getId().intValue());
        }
        if (status.equals("P")) {
            userVoucherList = userVoucherRepository.findIneffective(user.getId().intValue());
        }
        if (userVoucherList != null) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (UserVoucher userVoucher : userVoucherList) {
                VoucherInst voucherInst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
                if (voucherInst.getType().equals("G")) {
                    Prod prod = prodRepository.getOne(voucherInst.getExchangeProdId());
                    if (prod != null) {
                        voucherInstDTOList.add(getVoucherInstDTO(voucherInst, status, prod));
                    }
                } else {
                    voucherInstDTOList.add(getVoucherInstDTO(voucherInst, status, null));
                }
            }
            return voucherInstDTOList;
        }
        return null;
    }

    public VoucherInstDTO getVoucherInstDTO(VoucherInst voucherInst, String status, Prod prod) {
        VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
        if (voucherInst.getType().equals("M")) {
            voucherInstDTO.setName(voucherInst.getName() + "(" + voucherInst.getMoney() + "元)");
        }
        if (voucherInst.getType().equals("D")) {
            voucherInstDTO.setName(voucherInst.getName() + "(" + voucherInst.getDiscount().divide(new BigDecimal(10)) + "折)");
        }
        voucherInstDTO.setRule(dictService.findStatusName("voucher_inst", "enable_type", voucherInst.getEnableType()) + voucherInst.getEnableMoney() + "元可用");
        voucherInstDTO.setScope(voucherInst.getScope());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        voucherInstDTO.setUsefulTime(formatter.format(voucherInst.getIneffectiveTime()));
        voucherInstDTO.setVoucherInstId(voucherInst.getId());
        voucherInstDTO.setMoney(voucherInst.getMoney());
        voucherInstDTO.setType(voucherInst.getType());
        voucherInstDTO.setStatus(status);
        voucherInstDTO.setCanPresent(voucherInst.getCanPresent());
        if (voucherInst.getType().equals("D")) {
            voucherInstDTO.setDisCount(voucherInst.getDiscount().toString());
        }
        if (voucherInst.getType().equals("G")) {
            if(prod!=null){
                voucherInstDTO.setName(voucherInst.getName() + "(" +prod.getName()+")");
            }else{
                voucherInstDTO.setName(voucherInst.getName());
            }
            voucherInstDTO.setGift(prod.getId().toString());
            voucherInstDTO.setRule("");
        }
        return voucherInstDTO;
    }

}
