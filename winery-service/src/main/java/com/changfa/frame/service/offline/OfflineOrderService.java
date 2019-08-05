package com.changfa.frame.service.offline;


import com.changfa.frame.data.dto.wechat.OfflineOrderDetailDTO;
import com.changfa.frame.data.dto.wechat.VoucherInstDTO;
import com.changfa.frame.data.entity.deposit.UserBalance;
import com.changfa.frame.data.entity.deposit.UserDepositDetail;
import com.changfa.frame.data.entity.offline.OfflineOrder;
import com.changfa.frame.data.entity.offline.OfflineOrderPrice;
import com.changfa.frame.data.entity.offline.OfflineOrderVoucher;
import com.changfa.frame.data.entity.order.OrderPay;
import com.changfa.frame.data.entity.prod.Prod;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.deposit.UserBalanceRepository;
import com.changfa.frame.data.repository.deposit.UserDepositDetailRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderPriceRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderRepository;
import com.changfa.frame.data.repository.offline.OfflineOrderVoucherRepository;
import com.changfa.frame.data.repository.order.OrderPayRepository;
import com.changfa.frame.data.repository.prod.ProdRepository;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.dict.DictService;
import com.changfa.frame.service.point.PointDetailService;
import com.changfa.frame.service.point.PointRewardRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OfflineOrderService {

    private static Logger log = LoggerFactory.getLogger(PointDetailService.class);

    @Autowired
    private OfflineOrderRepository offlineOrderRepository;

    @Autowired
    private OfflineOrderPriceRepository offlineOrderPriceRepository;

    @Autowired
    private OfflineOrderVoucherRepository offlineOrderVoucherRepository;

    @Autowired
    private UserVoucherRepository userVoucherRepository;

    @Autowired
    private VoucherInstRepository voucherInstRepository;

    @Autowired
    private DictService dictService;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserDepositDetailRepository userDepositDetailRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderPayRepository orderPayRepository;

    @Autowired
    private PointRewardRuleService pointRewardRuleService;

    @Autowired
    private ProdRepository prodRepository;

    /* *
     * 获取所有线下可用的优惠券
     * @Author        zyj
     * @Date          2018/10/31 16:56
     * @Description
     * */
    public List<VoucherInstDTO> findOfflineVoucherList(Member user) {
        List<UserVoucher> userVoucherList = userVoucherRepository.findEffective(Integer.valueOf(user.getId().toString()));
        if (userVoucherList != null) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (UserVoucher userVoucher : userVoucherList) {
                VoucherInst voucherInst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
                if (voucherInst.getScope().equals("A") || voucherInst.getScope().equals("C")) {
                    if (!voucherInst.getType().equals("G")) {
                        voucherInstDTOList.add(getVoucherInstDTO(voucherInst));
                    }
                }
            }
            return voucherInstDTOList;
        }
        return null;
    }

    public VoucherInstDTO getVoucherInstDTO(VoucherInst voucherInst) {
        VoucherInstDTO voucherInstDTO = new VoucherInstDTO();
        voucherInstDTO.setName(voucherInst.getName());
        voucherInstDTO.setRule(dictService.findStatusName("voucher_inst", "enable_type", voucherInst.getEnableType()) + voucherInst.getEnableMoney() + "元可用");
        voucherInstDTO.setScope(voucherInst.getScope());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        voucherInstDTO.setUsefulTime(formatter.format(voucherInst.getIneffectiveTime()));
        voucherInstDTO.setVoucherInstId(voucherInst.getId());
        voucherInstDTO.setType(voucherInst.getType());
        if (voucherInst.getType().equals("D")) {
            voucherInstDTO.setDisCount(voucherInst.getDiscount().divide(new BigDecimal(100)).toString());
        }
        if (voucherInst.getType().equals("G")) {
            Prod prod = prodRepository.getOne(voucherInst.getExchangeProdId());
            voucherInstDTO.setGift(prod.getId().toString());
        }
        voucherInstDTO.setMoney(voucherInst.getMoney());
        return voucherInstDTO;
    }

    /* *
     * 获取所有线下优惠券最大优惠券
     * @Author        zyj
     * @Date          2018/10/31 17:28
     * @Description
     * */
    public VoucherInstDTO findOfflineMaxVoucher(Member user) {
        List<VoucherInstDTO> voucherInstDTOList = findOfflineVoucherList(user);
        if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
            for (int i = 0; i < voucherInstDTOList.size() - 1; i++) {
                for (int j = 0; j < voucherInstDTOList.size() - 1 - i; j++) {
                    if ((voucherInstDTOList.get(i).getMoney() == null ? new BigDecimal(0) : voucherInstDTOList.get(i).getMoney()).compareTo(voucherInstDTOList.get(j + 1 + i).getMoney() == null ? new BigDecimal(0) : voucherInstDTOList.get(j + 1 + i).getMoney()) < 0) {
                        Collections.swap(voucherInstDTOList, i, j + 1 + i);
                    }
                }
            }
            return voucherInstDTOList.get(0);
        }
        return null;
    }

    /* *
     * 获取所有消费可使用得券
     * @Author        zyj
     * @Date          2018/10/31 17:32
     * @Description
     * */
    public List<VoucherInstDTO> findOfflineVoucherListCanUse(Member user, BigDecimal price) {
        List<UserVoucher> userVoucherList = userVoucherRepository.findEffective(Integer.valueOf(user.getId().toString()));
        if (userVoucherList != null) {
            List<VoucherInstDTO> voucherInstDTOList = new ArrayList<>();
            for (UserVoucher userVoucher : userVoucherList) {
                VoucherInst voucherInst = voucherInstRepository.getOne(userVoucher.getVoucherInstId());
                if (voucherInst.getScope().equals("A") || voucherInst.getScope().equals("C")) {
                    if (!voucherInst.getType().equals("G")) {
                        if (voucherInst.getEffectiveTime().before(new Date()) && price.compareTo(voucherInst.getEnableMoney()) >= 0) {
                            voucherInstDTOList.add(getVoucherInstDTO(voucherInst));
                        }
                    }
                }
            }
            return voucherInstDTOList;
        }
        return null;
    }


    public Map<String, Object> findOfflineMaxVoucherCanUse(Member user, BigDecimal price) {
        Map<String, Object> map = new HashMap<>();
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        map.put("userBalance", userBalance.getBalance());
        List<VoucherInstDTO> voucherInstDTOList = findOfflineVoucherListCanUse(user, price);
        if (voucherInstDTOList != null && voucherInstDTOList.size() > 0) {
            for (int i = 0; i < voucherInstDTOList.size() - 1; i++) {
                for (int j = 0; j < voucherInstDTOList.size() - 1 - i; j++) {
                    if ((voucherInstDTOList.get(i).getMoney() == null ? new BigDecimal(0) : (voucherInstDTOList.get(i).getMoney())).compareTo((voucherInstDTOList.get(j + 1 + i).getMoney()) == null ? new BigDecimal(0) : (voucherInstDTOList.get(j + 1 + i).getMoney())) < 0) {
                        Collections.swap(voucherInstDTOList, i, j + 1 + i);
                    }
                }
            }
            map.put("voucherInst", voucherInstDTOList.get(0));
        }
        return map;
    }


    /* *
     * 线下买单生成订单
     * @Author        zyj
     * @Date          2018/11/1 10:01
     * @Description
     * */
    public OfflineOrder addOrder(Member user, Map<String, Object> map, String payTye) {
        BigDecimal price = new BigDecimal(Double.valueOf(map.get("price").toString()));
        VoucherInst voucherInst = null;
        if (map.get("voucherInstId") != null && !map.get("voucherInstId").equals("")) {
            Integer voucherInstId = Integer.valueOf(map.get("voucherInstId").toString());
            voucherInst = voucherInstRepository.getOne(voucherInstId);
        }
        //生成订单
        OfflineOrder offlineOrder = new OfflineOrder();
        offlineOrder.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        offlineOrder.setUserId(Integer.valueOf(user.getId().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String tempUserId = String.format("%02d", user.getId());
        String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
        System.out.println(format);
        offlineOrder.setOrderNo(format);
        offlineOrder.setOrderType("W");
        offlineOrder.setPayType(payTye);
        offlineOrder.setStatus("B");
        if (voucherInst != null) {
            if (voucherInst.getType().equals("M")) {
                offlineOrder.setTotalPrice(price.subtract(voucherInst.getMoney()));
            } else {
                offlineOrder.setTotalPrice(price.multiply(voucherInst.getDiscount().divide(new BigDecimal(100))));
            }
        } else {
            offlineOrder.setTotalPrice(price);
        }
        offlineOrder.setStatusTime(new Date());
        offlineOrder.setCreateTime(new Date());
        OfflineOrder offlineOrderSave = offlineOrderRepository.saveAndFlush(offlineOrder);
        //添加订单价格详情记录
        OfflineOrderPrice offlineOrderPrice = new OfflineOrderPrice();
        offlineOrderPrice.setOfflineOrderId(offlineOrderSave.getId());
        offlineOrderPrice.setOrigPrice(price);
        offlineOrderPrice.setFinalPrice(price);
        offlineOrderPrice.setTotalPrice(offlineOrderSave.getTotalPrice());
        offlineOrderPriceRepository.saveAndFlush(offlineOrderPrice);
        if (voucherInst != null) {
            //添加优惠券信息
            OfflineOrderVoucher offlineOrderVoucher = new OfflineOrderVoucher();
            offlineOrderVoucher.setOfflineOrderId(offlineOrderSave.getId());
            offlineOrderVoucher.setVoucherInstId(voucherInst.getId());
            offlineOrderVoucherRepository.saveAndFlush(offlineOrderVoucher);
        }
        return offlineOrderSave;
    }

    /* *
     * 线下买单
     * @Author        zyj
     * @Date          2018/11/1 10:59
     * @Description
     * */
    public void balancePay(Member user, Map<String, Object> map) {
        OfflineOrder offlineOrder = addOrder(user, map, "D");
        //修改订单状态
        offlineOrder.setStatus("P");
        offlineOrder.setStatusTime(new Date());
        offlineOrderRepository.saveAndFlush(offlineOrder);
        //修改储值金额
        UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
        userBalance.setBalance(userBalance.getBalance().subtract(offlineOrder.getTotalPrice()));
        userBalance.setUpdateTime(new Date());
        UserBalance userBalanceSave = userBalanceRepository.saveAndFlush(userBalance);
        OrderPay orderPay = new OrderPay();
        orderPay.setOrderNo(offlineOrder.getOrderNo());
        orderPay.setTotalPrice(offlineOrder.getTotalPrice());
        orderPay.setOrderId(offlineOrder.getId());
        orderPay.setOrderType("F");
        orderPay.setPayType("D");
        orderPay.setUserId(offlineOrder.getUserId());
        orderPay.setTotalPrice(offlineOrder.getTotalPrice());
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        //添加储值流水
        UserDepositDetail userDepositDetail = new UserDepositDetail();
        userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
        userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
        userDepositDetail.setAction("A");
        userDepositDetail.setBalance(offlineOrder.getTotalPrice());
        userDepositDetail.setBalanceType("M");
        userDepositDetail.setLatestBalance(userBalanceSave.getBalance());
        userDepositDetail.setOrderId(offlineOrder.getId());
        userDepositDetail.setOrderNo(offlineOrder.getOrderNo());
        userDepositDetail.setCreateTime(new Date());
        userDepositDetailRepository.saveAndFlush(userDepositDetail);
        //作废优惠券
        OfflineOrderVoucher offlineOrderVoucher = offlineOrderVoucherRepository.findByOfflineOrderId(offlineOrder.getId());
        if (offlineOrderVoucher != null) {
            VoucherInst voucherInst = voucherInstRepository.getOne(offlineOrderVoucher.getVoucherInstId());
            voucherInst.setStatus("U");
            voucherInst.setStatusTime(new Date());
            voucherInstRepository.saveAndFlush(voucherInst);
            UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
            userVoucher.setUseTime(new Date());
            userVoucherRepository.saveAndFlush(userVoucher);
        }
        pointRewardRuleService.updateUserPoint(user, offlineOrder.getTotalPrice(), "W", offlineOrder.getOrderNo(), offlineOrder.getId());
    }


    /* *
     * 微信支付成功后回调接口
     * @Author        zyj
     * @Date          2018/11/1 11:27
     * @Description
     * */
    public void paySuccess(String orderNo, Map<String, String> map, String type) {
        OfflineOrder offlineOrder = offlineOrderRepository.findByOrderNo(orderNo);
        Member user = memberRepository.getOne(offlineOrder.getUserId());
        OrderPay orderPay = new OrderPay();
        //使用余额支付不够的用微信
        if (type.equals("B")) {
            UserBalance userBalance = userBalanceRepository.findByUserId(Integer.valueOf(user.getId().toString()));
            UserDepositDetail userDepositDetail = new UserDepositDetail();
            userDepositDetail.setBalance(userBalance.getBalance());
            orderPay.setTotalPrice(offlineOrder.getTotalPrice().subtract(userBalance.getBalance()));
            orderPay.setPayType("DW");
            userBalance.setBalance(new BigDecimal(0));
            userBalance.setUpdateTime(new Date());
            userBalanceRepository.saveAndFlush(userBalance);
            userDepositDetail.setWineryId(Integer.valueOf(user.getWineryId().toString()));
            userDepositDetail.setUserId(Integer.valueOf(user.getId().toString()));
            userDepositDetail.setAction("A");
            userDepositDetail.setBalanceType("M");
            userDepositDetail.setLatestBalance(new BigDecimal(0));
            userDepositDetail.setOrderId(offlineOrder.getId());
            userDepositDetail.setOrderNo(offlineOrder.getOrderNo());
            userDepositDetail.setCreateTime(new Date());
            userDepositDetailRepository.saveAndFlush(userDepositDetail);
        } else {
            orderPay.setTotalPrice(offlineOrder.getTotalPrice());
            orderPay.setPayType("W");
            orderPay.setPlatformOrderNo(map.get("transaction_id"));
        }
        //添加支付信息
        orderPay.setOrderNo(orderNo);
        orderPay.setOrderId(offlineOrder.getId());
        orderPay.setOrderType("F");
        orderPay.setUserId(offlineOrder.getUserId());
        orderPay.setNotifyStatus("A");
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("A");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
        offlineOrder.setStatus("P");
        offlineOrder.setStatusTime(new Date());
        //修改订单状态
        offlineOrderRepository.saveAndFlush(offlineOrder);
        //将订单使用的券作废
        OfflineOrderVoucher offlineOrderVoucher = offlineOrderVoucherRepository.findByOfflineOrderId(offlineOrder.getId());
        if (offlineOrderVoucher != null) {
            VoucherInst voucherInst = voucherInstRepository.getOne(offlineOrderVoucher.getVoucherInstId());
            voucherInst.setStatus("U");
            voucherInst.setStatusTime(new Date());
            voucherInstRepository.saveAndFlush(voucherInst);
            UserVoucher userVoucher = userVoucherRepository.findByVoucherInstId(voucherInst.getId());
            userVoucher.setUseTime(new Date());
            userVoucherRepository.saveAndFlush(userVoucher);
        }

        //计算积分
        pointRewardRuleService.updateUserPoint(user, offlineOrder.getTotalPrice(), "W", offlineOrder.getOrderNo(), offlineOrder.getId());
    }

    /* *
     * 显示线下支付列表
     * @Author        zyj
     * @Date          2018/11/1 15:06
     * @Description
     * */
    public List<OfflineOrderDetailDTO> findOfflineList(Member user) {
        List<OfflineOrder> offlineOrderList = offlineOrderRepository.findByUserIdAndStatusOrderByCreateTimeDesc(Integer.valueOf(user.getId().toString()), "P");
        if (offlineOrderList != null && offlineOrderList.size() > 0) {
            List<OfflineOrderDetailDTO> offlineOrderDetailDTOList = new ArrayList<>();
            for (OfflineOrder offlineOrder : offlineOrderList) {
                OfflineOrderDetailDTO offlineOrderDetailDTO = new OfflineOrderDetailDTO();
                offlineOrderDetailDTO.setOrderId(offlineOrder.getId());
                offlineOrderDetailDTO.setPayType(offlineOrder.getPayType());
                offlineOrderDetailDTO.setPrice(offlineOrder.getTotalPrice());
                offlineOrderDetailDTO.setCreateTime(offlineOrder.getCreateTime());
                if (offlineOrder.getConsumeType() != null) {
                    if (offlineOrder.getConsumeType().equals("1")) {
                        offlineOrderDetailDTO.setConsumeType("菜品");
                    } else if (offlineOrder.getConsumeType().equals("2")) {
                        offlineOrderDetailDTO.setConsumeType("酒品");
                    }
                }
                offlineOrderDetailDTOList.add(offlineOrderDetailDTO);
            }
            return offlineOrderDetailDTOList;
        }
        return null;
    }

    public OfflineOrder findById(Integer id) {
        return offlineOrderRepository.getOne(id);
    }

    public void offlinePay(OfflineOrder activityOrder) {
        OrderPay orderPay = new OrderPay();
        orderPay.setTotalPrice(activityOrder.getTotalPrice());
        orderPay.setOrderNo(activityOrder.getOrderNo());
        orderPay.setOrderId(activityOrder.getId());
        orderPay.setOrderType("F");
        orderPay.setPayType("O");
        orderPay.setUserId(activityOrder.getUserId());
        orderPay.setNotifyTime(new Date());
        orderPay.setPayStatus("W");
        orderPay.setPayTime(new Date());
        orderPayRepository.saveAndFlush(orderPay);
    }

}

