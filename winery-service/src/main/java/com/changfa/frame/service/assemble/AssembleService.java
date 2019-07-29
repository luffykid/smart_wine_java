package com.changfa.frame.service.assemble;

import com.changfa.frame.data.dto.saas.*;
import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.assemble.AssembleCommodity;
import com.changfa.frame.data.entity.assemble.AssembleList;
import com.changfa.frame.data.entity.assemble.AssembleUser;
import com.changfa.frame.data.entity.banner.Banner;
import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.entity.order.*;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.theme.ThemeProd;
import com.changfa.frame.data.entity.user.*;
import com.changfa.frame.data.entity.voucher.UserVoucher;
import com.changfa.frame.data.entity.voucher.Voucher;
import com.changfa.frame.data.entity.voucher.VoucherInst;
import com.changfa.frame.data.repository.assemble.AssembleCommodityRepository;
import com.changfa.frame.data.repository.assemble.AssembleListRepository;
import com.changfa.frame.data.repository.assemble.AssembleUserRepository;
import com.changfa.frame.data.repository.order.*;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.theme.ThemeProdRepository;
import com.changfa.frame.data.repository.user.MemberLevelRepository;
import com.changfa.frame.data.repository.user.MemberUserRepository;
import com.changfa.frame.data.repository.user.UserRepository;
import com.changfa.frame.data.repository.voucher.UserVoucherRepository;
import com.changfa.frame.data.repository.voucher.VoucherInstRepository;
import com.changfa.frame.service.PicturePathUntil;
import com.changfa.frame.service.order.OrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/11/9.
 */
@Service
public class AssembleService {

    @Autowired
    private ProdSpecGroupRepository prodSpecGroupRepository;
    @Autowired
    private ProdSpecRepository prodSpecRepository;
    @Autowired
    private ProdCategoryRepository prodCategoryRepository;
    @Autowired
    private ProdBrandRepository prodBrandRepository;
    @Autowired
    private ProdProdSpecRepository prodProdSpecRepository;
    @Autowired
    private ProdRepository prodRepository;
    @Autowired
    private ProdLogoRepository prodLogoRepository;
    @Autowired
    private ProdStockRepository prodStockRepository;
    @Autowired
    private ProdChangedRepository prodChangedRepository;
    @Autowired
    private ProdPriceLevelRepository prodPriceLevelRepository;
    @Autowired
    private MemberLevelRepository memberLevelRepository;
    @Autowired
    private ProdPriceRepository prodPriceRepository;
    @Autowired
    private ThemeProdRepository themeProdRepository;

    @Autowired
    private ProdContentRepository prodContentRepository;
	@Autowired
	private AssembleCommodityRepository assembleCommodityRepository;
	@Autowired
	private  AssembleListRepository assembleListRepository;
	@Autowired
	private AssembleUserRepository assembleUserRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderPriceRepository orderPriceRepository;
	@Autowired
	private OrderProdRepository orderProdRepository;
	@Autowired
	private MemberUserRepository memberUserRepository;
	@Autowired
	private OrderPriceItemRepository orderPriceItemRepository;
	@Autowired
	private OrderSettleRepository orderSettleRepository;
	@Autowired
	private VoucherInstRepository voucherInstRepository;
	@Autowired
	private UserVoucherRepository userVoucherRepository;
	@Autowired
	private OrderAddressRepository orderAddressRepository;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserRepository userRepository;


	/**
	 * 选择的拼团商品列表
	 *
	 * @param adminUser
	 * @param input     商品名称
	 * @return
	 */
	public List<ProdAssembleDTO> prodList(AdminUser adminUser, String input) {
		List<Prod> prodList = new ArrayList<>();
		prodList = prodRepository.findByWineryIdLikeName(adminUser.getWineryId(), input);
		List<ProdAssembleDTO> prodListDTOS = new ArrayList<>();
		for (Prod prod : prodList) {
			ProdAssembleDTO prodListDTO = new ProdAssembleDTO();
			prodListDTO.setProdId(prod.getId());
			prodListDTO.setProdName(prod.getName());
			prodListDTO.setProdTitle(prod.getTitle());
			ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
			if (prodLogo != null) {
				prodListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
			}
			ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
			if (prodSpec != null) {
				ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
				if (spec != null) {
					prodListDTO.setProdSpecName(spec.getSpecValue());
				}
			}
			ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
			if (price != null) {
				prodListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
			}
			prodListDTOS.add(prodListDTO);
		}
		return prodListDTOS;
	}


	//新增拼团
	public void addAssemble(AdminUser adminUser, AssembleCommodityDTO assembleCommodityDTO) {
		AssembleCommodity assembleCommodity = new AssembleCommodity();
		assembleCommodity.setWineryId(adminUser.getWineryId());
		assembleCommodity.setProdId(assembleCommodityDTO.getProdId());
		assembleCommodity.setAssemblePrice(assembleCommodityDTO.getAssemblePrice());
		assembleCommodity.setAssembleAllnum(assembleCommodityDTO.getAssembleAllnum());
		assembleCommodity.setAssemblePreson(assembleCommodityDTO.getAssemblePreson());
		assembleCommodity.setStartTime(assembleCommodityDTO.getStartTime());
		assembleCommodity.setEndTime(assembleCommodityDTO.getEndTime());
		assembleCommodity.setExistTimes(assembleCommodityDTO.getExistTimes());
		assembleCommodity.setCreateTime(new Date());
		assembleCommodity.setTopTime(assembleCommodity.getCreateTime());
		assembleCommodity.setUserId(adminUser.getId());
		assembleCommodity.setIsDelete(1);
		assembleCommodityRepository.saveAndFlush(assembleCommodity);
	}

	/**
	 * 拼团商品列表
	 *
	 * @param adminUser
	 * @param input     商品名称
	 * @return
	 */
	public List<AssembleCommodityListDTO> assembleList(AdminUser adminUser, String input) {
		List<AssembleCommodity> assembleList = new ArrayList<>();
		assembleList = assembleCommodityRepository.findByWineryIdLikeName(adminUser.getWineryId(), input);
		List<AssembleCommodityListDTO> acListDTOS = new ArrayList<>();
		for (AssembleCommodity ac : assembleList) {
			AssembleCommodityListDTO acListDTO = new AssembleCommodityListDTO();
			acListDTO.setId(ac.getId());
			Prod prod = prodRepository.findOne(ac.getProdId());
			if(null != prod){
				acListDTO.setProdName(prod.getName());
			}
			acListDTO.setAssemblePrice(ac.getAssemblePrice());
			acListDTO.setAssembleAllnum(ac.getAssembleAllnum());
			int assembleNum = assembleCommodityRepository.findByWineryIdAndIdUser(adminUser.getWineryId(),ac.getId());
			acListDTO.setAssembleNum(assembleNum);
			acListDTO.setAssemblePreson(ac.getAssemblePreson());
			int teamNum = assembleCommodityRepository.findByWineryIdAndIdTeam(adminUser.getWineryId(),ac.getId());
			acListDTO.setTeamNum(teamNum);
			acListDTO.setStartTime(ac.getStartTime());
			acListDTO.setEndTime(ac.getEndTime());
			acListDTOS.add(acListDTO);
		}
		return acListDTOS;
	}

	/**
	 * 拼团商品详情
	 *
	 * @param assembleId     拼团商品表id
	 * @return
	 */
	public AssembleCommodityDTO assembleDetail(Integer assembleId) {
		AssembleCommodityDTO acDTO = new AssembleCommodityDTO();
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if (assembleCommodity != null) {
			acDTO.setId(assembleCommodity.getId());
			Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
			if(null != prod){
				acDTO.setProd(prod);
			}
			acDTO.setAssemblePrice(assembleCommodity.getAssemblePrice());
			acDTO.setAssembleAllnum(assembleCommodity.getAssembleAllnum());
			acDTO.setAssemblePreson(assembleCommodity.getAssemblePreson());
			acDTO.setStartTime(assembleCommodity.getStartTime());
			acDTO.setEndTime(assembleCommodity.getEndTime());
			acDTO.setExistTimes(assembleCommodity.getExistTimes());
		}
		return acDTO;
	}


	//检查是否可以编辑(在活动时间内，不允许编辑。)
	public Boolean checkisUpdate(Integer assembleId) {
		Boolean ret = false;
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if(null != assembleCommodity ){
			if(assembleCommodity.getStartTime().getTime() <= System.currentTimeMillis()  &&
				System.currentTimeMillis() <= assembleCommodity.getEndTime().getTime() ){
				ret =  true;
			}else{
				ret =  false;
			}
		}
		return ret;
	}


	//修改拼团
	public void updateAssemble(AdminUser adminUser, AssembleCommodityDTO assembleCommodityDTO) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleCommodityDTO.getId());
		if (assembleCommodity == null) {
			assembleCommodity = new AssembleCommodity();
			assembleCommodity.setCreateTime(new Date());
			assembleCommodity.setTopTime(assembleCommodity.getCreateTime());
			assembleCommodity.setUserId(adminUser.getId());
		}
		assembleCommodity.setWineryId(adminUser.getWineryId());
		assembleCommodity.setProdId(assembleCommodityDTO.getProdId());
		assembleCommodity.setAssemblePrice(assembleCommodityDTO.getAssemblePrice());
		assembleCommodity.setAssembleAllnum(assembleCommodityDTO.getAssembleAllnum());
		assembleCommodity.setAssemblePreson(assembleCommodityDTO.getAssemblePreson());
		assembleCommodity.setStartTime(assembleCommodityDTO.getStartTime());
		assembleCommodity.setEndTime(assembleCommodityDTO.getEndTime());
		assembleCommodity.setExistTimes(assembleCommodityDTO.getExistTimes());
		assembleCommodityRepository.saveAndFlush(assembleCommodity);
	}

	//拼团商品置顶
	public void topAssemble(Integer assembleId) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if (assembleCommodity != null) {
			assembleCommodity.setTopTime(new Date());
			assembleCommodityRepository.saveAndFlush(assembleCommodity);
		}
	}

	//拼团商品取消置顶
	public void untopAssemble(Integer assembleId) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if (assembleCommodity != null) {
			assembleCommodity.setTopTime(assembleCommodity.getCreateTime());
			assembleCommodityRepository.saveAndFlush(assembleCommodity);
		}
	}

	//删除拼团商品
	public void delAssemble(Integer assembleId) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if (assembleCommodity != null) {
			assembleCommodity.setIsDelete(0);
			assembleCommodityRepository.saveAndFlush(assembleCommodity);
		}
	}

	/**
	 * 团购列表
	 *
	 * @param adminUser
	 * @param input     商品名称
	 * @return
	 */
	public List<AssembleListListDTO> assembleListList(AdminUser adminUser, String input, Integer assembleStatus) {
		List<AssembleListListDTO> assembleListReturn = new ArrayList<>();

		List<AssembleList> assembleListList = assembleListRepository.findByAssembleStatus(adminUser.getWineryId(),input,assembleStatus);
		for (AssembleList aList : assembleListList) {
			AssembleListListDTO acListDTO = new AssembleListListDTO();
			acListDTO.setId(aList.getId());
			acListDTO.setAssembleNo(aList.getAssembleNo());
			User user = userRepository.findOne(aList.getMaster());
			if(null != user){
				acListDTO.setMasterName(user.getName());
			}
			AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(aList.getAssembleCommodity());
			if(null != assembleCommodity){
				Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
				if(null != prod){
					acListDTO.setProdName(prod.getName());
				}
				acListDTO.setAssemblePreson(assembleCommodity.getAssemblePreson());
			}
			int assemblePresonEd = assembleCommodityRepository.findByList(aList.getId());//同一个团队的拼团人数
			acListDTO.setAssemblePresonNow(assemblePresonEd);
			acListDTO.setCreateTime(aList.getCreateTime());
			acListDTO.setAssembleStatus(aList.getAssembleStatus());

			assembleListReturn.add(acListDTO);
		}
		return assembleListReturn;
	}


	//删除拼团列表
	public void delAssembleList(Integer assembleListId) {
		AssembleList assembleList = assembleListRepository.findOne(assembleListId);
		if (assembleList != null) {
			assembleList.setIsDelete(0);
			assembleListRepository.saveAndFlush(assembleList);
		}
	}


	/**
	 * Saas端 团队中的用户列表信息
	 *
	 * @param assembleListId
	 * @return
	 */
	public List<AssembleUserListDTO> assembleUserList (Integer assembleListId) {
		List<AssembleUser> assembleUserList = assembleUserRepository.findAssembleUsersByAssembleListIs(assembleListId);
		List<AssembleUserListDTO> auwldList = new ArrayList<>();
		if(null != assembleUserList && assembleUserList.size()>0){
			for(AssembleUser asUser : assembleUserList){
				AssembleUserListDTO auwld = new AssembleUserListDTO();
				auwld.setId(asUser.getId());
				User user = userRepository.findOne(asUser.getUserId());
				if(null != user){
					auwld.setUserID(user.getId());
					auwld.setIcon(user.getUserIcon());
					auwld.setName(user.getName());
				}
				auwld.setCreateTime(asUser.getCreateTime());
				Order order = orderRepository.getOne(asUser.getOrderId());
				if(null != order){
					auwld.setOrderNo(order.getOrderNo());
					auwld.setStatus(order.getStatus());
				}
				auwldList.add(auwld);
			}
		}
		return auwldList;
	}



	/**
	 * 团购详情
	 *
	 * @param assembleListId
	 * @return
	 */
	public AssembleListDetailDTO assemblelistDetail(Integer assembleListId) {
		AssembleList assembleList = assembleListRepository.findOne(assembleListId);
		if(null != assembleList){
			AssembleCommodity ac = assembleCommodityRepository.findOne(assembleList.getAssembleCommodity());
			if(null != ac){
				AssembleListDetailDTO acListDTO = new AssembleListDetailDTO();
				acListDTO.setId(assembleList.getId());
				acListDTO.setAssembleNo(assembleList.getAssembleNo());
				acListDTO.setAssembleStatus(assembleList.getAssembleStatus());
				User user = userRepository.findOne(assembleList.getMaster());
				if(null != user){
					acListDTO.setMasterName(user.getName());
				}
				acListDTO.setCreateTime(assembleList.getCreateTime());

				Prod prod = prodRepository.findOne(ac.getProdId());
				if(null != prod){
					acListDTO.setProdName(prod.getName());
				}

				List<AssembleUserListDTO> userList = assembleUserList(assembleList.getId());
				if(null != userList && userList.size()>0){
					acListDTO.setUserList(userList);
				}

				return acListDTO;
			}
		}
		return null;
	}





    //*****************小程序端*****************************


	/**
	 * 拼团商品列表
	 *
	 * @param user
	 * @param input     商品名称
	 * @return
	 */
	public List<AssembleWeichatListDTO> assembleListWechat(User user, String input) {
		List<AssembleCommodity> assembleList = new ArrayList<>();
		assembleList = assembleCommodityRepository.findByWineryIdLikeName(user.getWineryId(), input);
		List<AssembleWeichatListDTO> acListDTOS = new ArrayList<>();
		for (AssembleCommodity ac : assembleList) {
			AssembleWeichatListDTO acListDTO = new AssembleWeichatListDTO();
			acListDTO.setId(ac.getId());
			Prod prod = prodRepository.findOne(ac.getProdId());
			if(null != prod){
				ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
				if (prodLogo != null) {
					acListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
				}
				acListDTO.setProdName(prod.getName());
				acListDTO.setProdTitle(prod.getTitle());
				ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
				if (price != null) {
					acListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
				}
				ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
				if (prodSpec != null) {
					ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
					if (spec != null) {
						acListDTO.setProdSpecName(spec.getSpecValue());
					}
				}
			}
			acListDTO.setAssemblePreson(ac.getAssemblePreson());
			acListDTO.setAssemblePrice(ac.getAssemblePrice());
			acListDTOS.add(acListDTO);
		}
		return acListDTOS;
	}



	//去开团按钮
	public AssembleWeichatDTO assembleButton(User user,Integer assembleId) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleId);
		if(null != assembleCommodity){
			AssembleWeichatDTO assembleWeichatDTO = new AssembleWeichatDTO();
			assembleWeichatDTO.setId(assembleCommodity.getId());
			assembleWeichatDTO.setUserLogo(user.getUserIcon());
			Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
			if(null != prod){
				ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
				if (prodLogo != null) {
					assembleWeichatDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
				}
				assembleWeichatDTO.setProdTitle(prod.getTitle());
				ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
				if (prodSpec != null) {
					ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
					if (spec != null) {
						assembleWeichatDTO.setProdSpecName(spec.getSpecValue());
					}
				}
			}
			assembleWeichatDTO.setAssemblePrice(assembleCommodity.getAssemblePrice());
			assembleWeichatDTO.setProdNum(1);
			assembleWeichatDTO.setDelivery("快递 免运费");
			assembleWeichatDTO.setTotalPrie(assembleCommodity.getAssemblePrice());
			assembleWeichatDTO.setProdPrie(assembleCommodity.getAssemblePrice());
			assembleWeichatDTO.setFreight(new BigDecimal(0));
			return assembleWeichatDTO;

		}
		return null;
	}











	//生成团购编号
	public String getOrderNoByMethod(User user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String tempUserId = String.format("%02d", user.getId());
		String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
		return format;
	}

	//检查剩余团购数量是否满足
	public Integer checkAssemblenum(AssembleCommodity assembleCommodity) {
		int assembleNum = assembleCommodityRepository.findByWineryIdAndIdUser(assembleCommodity.getWineryId(),assembleCommodity.getId());//已经拼团的数量
		int remaining= assembleCommodity.getAssembleAllnum() - assembleNum; //剩余的数量
		if (assembleCommodity.getAssemblePreson() > remaining) {
			return 1;
		}
		return 0;
	}
	//检查剩余库存是否满足
	public Integer checkStockOne(AssembleCommodity assembleCommodity) {
		ProdStock stock = prodStockRepository.findByProdId(assembleCommodity.getProdId());
		if (stock != null) {
			if (assembleCommodity.getAssemblePreson() > stock.getProdStock()) {
				return 1;
			}
		}
		return 0;
	}


	/**
	 * 创建开团订单
	 *
	 * @param user
	 * @param assembleCommodity     拼团商品
	 * @return
	 */
	public void createAssemble(User user, UserAddress userAddress, UserVoucher userVoucher, String descri, AssembleCommodity assembleCommodity) {
		//拼团订单
		Order order = new Order();
		order.setWineryId(user.getWineryId());
		order.setUserId(user.getId());
		//订单号
		String format = getOrderNoByMethod(user);
		order.setOrderNo(format);
		order.setStatus("P");//待支付P,代发货F,待收货H,待评价R,已完成S,已取消D,已失效E
		order.setStatusTime(new Date());
		order.setCreateTime(new Date());
		order.setDescri(descri);
		order.setOrderOri(1);//订单来源（0普通订单，1拼团订单，2砍价订单）
		if (userAddress == null) {
			order.setType("T");
		} else {
			order.setType("E");
		}
		orderRepository.saveAndFlush(order);
		//订单价格
		OrderPrice orderPrice = new OrderPrice();
		orderPrice.setWineryId(user.getWineryId());
		orderPrice.setOrderId(order.getId());
		orderPrice.setCarriageExpense(new BigDecimal(0));
		orderPriceRepository.saveAndFlush(orderPrice);

		Double totalPrice = 0.0;
		Integer prodNum = 0;
		//订单商品
		OrderProd orderProd = new OrderProd();
		orderProd.setWineryId(user.getWineryId());
		orderProd.setOrderId(order.getId());
		orderProd.setProdId(assembleCommodity.getProdId());
		orderProd.setQuantity(1);  //同一拼团订单中 每一个用户  拼团商品数量 只能是1
		orderProdRepository.saveAndFlush(orderProd);
		ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(assembleCommodity.getProdId());
		if (prodPrice != null) {
			Prod prod = prodRepository.findOne(prodPrice.getProdId());
			double oneprice = 0;
			if (prod.getMemberDiscount().equals("Y")) {
				MemberUser user1 = memberUserRepository.findByUserId(user.getId());
				if (user1 != null) {
					ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
					if (levelList != null) {
//                            oneprice = prodPrice.getFinalPrice().doubleValue() * levelList.getDiscount().doubleValue();
						// TODO 如果商品存在折扣，则价格为原价，否则为优惠价
						if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
							oneprice = prodPrice.getOriginalPrice().doubleValue();
						} else {
							oneprice = levelList.getDiscountPrice().doubleValue();
						}
					} else {
						oneprice = prodPrice.getFinalPrice().doubleValue() * 1;
					}
				}
			} else {
				oneprice = prodPrice.getFinalPrice().doubleValue();
			}
			//订单价格项
			OrderPriceItem orderPriceItem = new OrderPriceItem();
			orderPriceItem.setWineryId(user.getWineryId());
			orderPriceItem.setOrderPriceId(orderPrice.getId());
			orderPriceItem.setOrderId(order.getId());
			orderPriceItem.setProdId(assembleCommodity.getProdId());
			orderPriceItem.setQuantity(1);
			orderPriceItem.setOrigUnitPrice(prodPrice.getOriginalPrice());
			orderPriceItem.setOrigTotalPrice(prodPrice.getOriginalPrice().multiply(new BigDecimal(1)));
			orderPriceItem.setFinalUnitPrice(new BigDecimal(oneprice));
			orderPriceItem.setFinalTotalPrice(new BigDecimal(oneprice).multiply(new BigDecimal(1)));
			orderPriceItemRepository.saveAndFlush(orderPriceItem);
			totalPrice = orderPriceItem.getFinalTotalPrice().doubleValue();
			prodNum = 1;
			ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(assembleCommodity.getProdId());
			if (prodSpec != null) {
				orderPriceItem.setProdSpecId(prodSpec.getProdSpecId());
				orderProd.setProdSpecId(prodSpec.getProdSpecId());
			}
		}
		orderPrice.setProdOrigPrice(new BigDecimal(totalPrice));
		if (userVoucher != null && userVoucher.getUseTime() == null) {
			//订单结算
			OrderSettle orderSettle = new OrderSettle();
			orderSettle.setOrderId(order.getId());
			orderSettle.setUserId(user.getId());
			orderSettle.setUseVoucherId(userVoucher.getId());
			orderSettle.setUsePoint(0);

			orderSettle.setCreateTime(new Timestamp(System.currentTimeMillis()));
			orderSettleRepository.saveAndFlush(orderSettle);
			VoucherInst inst = voucherInstRepository.findOne(userVoucher.getVoucherInstId());
			if (inst != null && inst.getType().equals("M")) {
				totalPrice -= inst.getMoney().doubleValue();
			} else if (inst != null && inst.getType().equals("D")) {
				totalPrice *= (inst.getDiscount().doubleValue() / 100);
			} else if (inst != null && inst.getType().equals("G")) {
				if (assembleCommodity.getProdId().equals(inst.getExchangeProdId()) ) {
					 prodPrice = prodPriceRepository.findProdPriceByProdId(inst.getExchangeProdId());
					Double price1 = 0.0;
					if (prodPrice != null) {
						price1 = prodPrice.getFinalPrice().doubleValue();
					}
					Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
					if (prod != null && prod.getMemberDiscount().equals("Y")) {
						MemberUser user1 = memberUserRepository.findByUserId(user.getId());
						if (user1 != null) {
							ProdPriceLevel level = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
							if (level != null) {
								if (level != null) {
									// TODO 如果商品存在折扣，则价格为原价，否则为优惠价
									if (level.getDiscount() != null && !level.getDiscount().equals("")) {
										price1 = prodPrice.getOriginalPrice().doubleValue();
									} else {
										price1 = level.getDiscountPrice().doubleValue();
									}
//                                        price1 = prodPrice.getFinalPrice().doubleValue() * level.getDiscount().doubleValue();
								} else {
									price1 = prodPrice.getFinalPrice().doubleValue() * 1;
								}
							}
						}
					}
					totalPrice -= price1;
				}
			}
			//作废券
			userVoucher.setUseTime(new Date());
			userVoucherRepository.save(userVoucher);
			inst.setStatus("U");
			inst.setStatusTime(new Date());
			inst.setOrderId(order.getId());
			inst.setOrderType("O");
			voucherInstRepository.save(inst);
		}
		orderPrice.setProdFinalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		orderPrice.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		order.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		order.setProdNum(prodNum);
		//订单地址
		if (userAddress != null) {
			OrderAddress orderAddress = new OrderAddress();
			orderAddress.setOrderId(order.getId());
			orderAddress.setWineryId(order.getWineryId());
			orderAddress.setUserAddressId(userAddress.getId());
			orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
			orderAddressRepository.saveAndFlush(orderAddress);
		}

		//拼团列表
		AssembleList assembleList = new AssembleList();
		String format2 = getOrderNoByMethod(user);
		assembleList.setAssembleNo(format2);//团购编号
		assembleList.setAssembleCommodity(assembleCommodity.getId());
		assembleList.setMaster(user.getId());
		assembleList.setAssembleStatus(1);//1拼团中，2拼团成功，3拼团失败（待退款），4 拼团失败（已退款）
		assembleList.setCreateTime(new Date());
		assembleList.setIsDelete(1);
		assembleListRepository.saveAndFlush(assembleList);

		//拼团用户
		AssembleUser assembleUser = new AssembleUser();
		assembleUser.setUserId(user.getId());
		assembleUser.setIsMaster(1);//是否为团长 0否，1是
		assembleUser.setAssembleList(assembleList.getId());
		assembleUser.setOrderId(order.getId());//生成订单的编号
		assembleUser.setCreateTime(assembleList.getCreateTime());
		assembleUser.setAssembleCommodity(assembleCommodity.getId());
		assembleUserRepository.saveAndFlush(assembleUser);
	}



	/**
	 * 拼团详情  商品信息
	 *
	 * @param assembleListId
	 * @return
	 */
	public AssembleWeichatListDTO assembleDetailWechat(Integer assembleListId) {
		AssembleList assembleList = assembleListRepository.findOne(assembleListId);
		if(null != assembleList){
			AssembleCommodity ac = assembleCommodityRepository.findOne(assembleList.getAssembleCommodity());
			if(null != ac){
				AssembleWeichatListDTO acListDTO = new AssembleWeichatListDTO();
				acListDTO.setId(ac.getId());
				acListDTO.setAssembleListId(assembleList.getId());
				Prod prod = prodRepository.findOne(ac.getProdId());
				if(null != prod){
					ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
					if (prodLogo != null) {
						acListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
					}
					acListDTO.setProdName(prod.getName());
					acListDTO.setProdTitle(prod.getTitle());
					ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
					if (price != null) {
						acListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
					}
					ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
					if (prodSpec != null) {
						ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
						if (spec != null) {
							acListDTO.setProdSpecName(spec.getSpecValue());
						}
					}
				}
				acListDTO.setAssemblePreson(ac.getAssemblePreson());
				acListDTO.setAssemblePrice(ac.getAssemblePrice());
				int assemblePresonEd = assembleCommodityRepository.findByList(assembleList.getId());//同一个团队的拼团人数
				acListDTO.setAssemblePresonEd(assemblePresonEd);
				//创建时间 + 团队存在的时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endTime1 = orderService.addDateMinut(format.format(assembleList.getCreateTime()), ac.getExistTimes());
				try {
					Date endTime = format.parse(endTime1);
					acListDTO.setEndTime(endTime);
				}catch (ParseException e){
					e.printStackTrace();
				}
				return acListDTO;
			}
		}

		return null;
	}


	/**
	 * 拼团详情 用户信息
	 *
	 * @param assembleListId
	 * @return
	 */
	public List<AssembleUserWeichatListDTO> assembleUserWechat(Integer assembleListId) {
		List<AssembleUser> assembleUserList = assembleUserRepository.findAssembleUsersByAssembleListIs(assembleListId);
		List<AssembleUserWeichatListDTO> auwldList = new ArrayList<>();
		if(null != assembleUserList && assembleUserList.size()>0){
			for(AssembleUser asUser : assembleUserList){
				AssembleUserWeichatListDTO auwld = new AssembleUserWeichatListDTO();
				auwld.setId(asUser.getId());
				User user = userRepository.findOne(asUser.getUserId());
				if(null != user){
					auwld.setUserID(user.getId());
					auwld.setIcon(user.getUserIcon());
				}
				auwld.setIsMaster(asUser.getIsMaster());
				Order order = orderRepository.getOne(asUser.getOrderId());
				if(null != order){
					auwld.setStatus(order.getStatus());
				}
				auwldList.add(auwld);
			}
		}
		return auwldList;
	}

	/**
	 * 拼团详情 更多拼团商品
	 *
	 * @param user
	 * @param assembleListId
	 * @return
	 */
	public List<AssembleWeichatListDTO> assembleListWechat(User user, Integer assembleListId) {

		AssembleList assembleList = assembleListRepository.findOne(assembleListId);
		List<AssembleWeichatListDTO> acListDTOS = new ArrayList<>();
		if(null != assembleList){
			AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleList.getAssembleCommodity());
			List<AssembleCommodity> assembleCommodityList  = assembleCommodityRepository.findByWineryIdAndId(user.getWineryId(), assembleCommodity.getId());
			for (AssembleCommodity ac : assembleCommodityList) {
				AssembleWeichatListDTO acListDTO = new AssembleWeichatListDTO();
				acListDTO.setId(ac.getId());
				Prod prod = prodRepository.findOne(ac.getProdId());
				if(null != prod){
					ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
					if (prodLogo != null) {
						acListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
					}
					acListDTO.setProdName(prod.getName());
					acListDTO.setProdTitle(prod.getTitle());
					ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
					if (price != null) {
						acListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
					}
					ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
					if (prodSpec != null) {
						ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
						if (spec != null) {
							acListDTO.setProdSpecName(spec.getSpecValue());
						}
					}
				}

				int assemblePresonEd = assembleCommodityRepository.findByWineryIdAndIdUser(ac.getWineryId(),ac.getId());//已经拼团的数量
				acListDTO.setAssemblePresonEd(assemblePresonEd);
				acListDTO.setAssemblePreson(ac.getAssemblePreson());
				acListDTO.setAssemblePrice(ac.getAssemblePrice());
				acListDTOS.add(acListDTO);
			}

		}
		return acListDTOS;
	}



	/**
	 * 团购商品页面   拼团商品信息
	 *
	 * @param user
	 * @param assembleId
	 * @return
	 */
	public AssemblePordDetailWeichatDTO assembleProdWchat(User user, Integer assembleId) {
		AssemblePordDetailWeichatDTO acListDTO = new AssemblePordDetailWeichatDTO();
		if(null != assembleId){

			AssembleCommodity ac = assembleCommodityRepository.findOne(assembleId);
			acListDTO.setId(ac.getId());
			Prod prod = prodRepository.findOne(ac.getProdId());
			if(null != prod){
				ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
				if (price != null) {
					acListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
				}
				ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
				if (prodSpec != null) {
					ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
					if (spec != null) {
						acListDTO.setProdSpecName(spec.getSpecValue());
					}
				}
			}
			int assemblePresonEd = assembleCommodityRepository.findByWineryIdAndIdUser(ac.getWineryId(),ac.getId());//已经拼团的数量
			acListDTO.setAssemblePresonEd(assemblePresonEd);
			acListDTO.setAssemblePreson(ac.getAssemblePreson());
			acListDTO.setAssemblePrice(ac.getAssemblePrice());
			acListDTO.setEndTime(ac.getEndTime());

		}
		return acListDTO;
	}


	/**
	 * 团购商品页面   已经参团且人数未满的列表
	 *
	 * @param assembleId
	 * @return
	 */
	public List<AssembleListWeichatDTO> assembleProdWchat( AssembleCommodity ac, Integer assembleId) {
		List<AssembleList> assembleListList = assembleListRepository.findByAssembleCommodity(assembleId);
		if(null != assembleListList && assembleListList.size()>0){
			List<AssembleListWeichatDTO> assembleListWeichatDTOList = new ArrayList<>();
			for(AssembleList assembleList : assembleListList){
				AssembleListWeichatDTO apdwd = new AssembleListWeichatDTO();
				apdwd.setId(assembleList.getId());
				//创建时间 + 团队存在的时间
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String endTime1 = orderService.addDateMinut(format.format(assembleList.getCreateTime()), ac.getExistTimes());
				try {
					Date endTime = format.parse(endTime1);
					apdwd.setEndTime(endTime);
				}catch (ParseException e){
					e.printStackTrace();
				}
				int assemblePresonEd = assembleCommodityRepository.findByList(assembleList.getId());//同一个团队的拼团人数
				apdwd.setAssemblePreson(ac.getAssemblePreson()-assemblePresonEd);
				List<AssembleUserWeichatListDTO>  assembleUserWeichatListDTOList =  assembleUserWechat(assembleList.getId());
				apdwd.setUserList(assembleUserWeichatListDTOList);
				assembleListWeichatDTOList.add(apdwd);
			}

			return assembleListWeichatDTOList;
		}
		return  null;

	}


	//我要参团按钮
	public AssembleWeichatDTO joinAssembleButton(User user,Integer assembleListId) {
//
		if(null != assembleListId){
			AssembleList assembleList = assembleListRepository.findOne(assembleListId);
			if(null != assembleList){
				AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleList.getAssembleCommodity());
				if(null != assembleCommodity){
					AssembleWeichatDTO assembleWeichatDTO = new AssembleWeichatDTO();
					assembleWeichatDTO.setId(assembleCommodity.getId());
					assembleWeichatDTO.setUserLogo(user.getUserIcon());
					Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
					if(null != prod){
						ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
						if (prodLogo != null) {
							assembleWeichatDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
						}
						assembleWeichatDTO.setProdTitle(prod.getTitle());
						ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
						if (prodSpec != null) {
							ProdSpec spec = prodSpecRepository.findOne(prodSpec.getProdSpecId());
							if (spec != null) {
								assembleWeichatDTO.setProdSpecName(spec.getSpecValue());
							}
						}
					}
					assembleWeichatDTO.setAssemblePrice(assembleCommodity.getAssemblePrice());
					assembleWeichatDTO.setProdNum(1);
					assembleWeichatDTO.setDelivery("快递 免运费");
					assembleWeichatDTO.setTotalPrie(assembleCommodity.getAssemblePrice());
					assembleWeichatDTO.setProdPrie(assembleCommodity.getAssemblePrice());
					assembleWeichatDTO.setFreight(new BigDecimal(0));
					List<AssembleUserWeichatListDTO>  assembleUserWeichatListDTOList =  assembleUserWechat(assembleList.getId());
					assembleWeichatDTO.setUserList(assembleUserWeichatListDTOList);
					return assembleWeichatDTO;
				}
			}

		}
		return null;
	}


	/**
	 * 参团订单
	 *
	 * @param user
	 * @param assembleList     拼团商品
	 * @return
	 */
	public void joinAssemble(User user, UserAddress userAddress, UserVoucher userVoucher, String descri, AssembleList assembleList) {
		AssembleCommodity assembleCommodity = assembleCommodityRepository.findOne(assembleList.getAssembleCommodity());
		//拼团订单
		Order order = new Order();
		order.setWineryId(user.getWineryId());
		order.setUserId(user.getId());
		//订单号
		String format = getOrderNoByMethod(user);
		order.setOrderNo(format);
		order.setStatus("P");//待支付P,代发货F,待收货H,待评价R,已完成S,已取消D,已失效E
		order.setStatusTime(new Date());
		order.setCreateTime(new Date());
		order.setDescri(descri);
		if (userAddress == null) {
			order.setType("T");
		} else {
			order.setType("E");
		}
		orderRepository.saveAndFlush(order);
		//订单价格
		OrderPrice orderPrice = new OrderPrice();
		orderPrice.setWineryId(user.getWineryId());
		orderPrice.setOrderId(order.getId());
		orderPrice.setCarriageExpense(new BigDecimal(0));
		orderPriceRepository.saveAndFlush(orderPrice);

		Double totalPrice = 0.0;
		Integer prodNum = 0;
		//订单商品
		OrderProd orderProd = new OrderProd();
		orderProd.setWineryId(user.getWineryId());
		orderProd.setOrderId(order.getId());
		orderProd.setProdId(assembleCommodity.getProdId());
		orderProd.setQuantity(1);  //同一拼团订单中 每一个用户  拼团商品数量 只能是1
		orderProdRepository.saveAndFlush(orderProd);
		ProdPrice prodPrice = prodPriceRepository.findProdPriceByProdId(assembleCommodity.getProdId());
		if (prodPrice != null) {
			Prod prod = prodRepository.findOne(prodPrice.getProdId());
			double oneprice = 0;
			if (prod.getMemberDiscount().equals("Y")) {
				MemberUser user1 = memberUserRepository.findByUserId(user.getId());
				if (user1 != null) {
					ProdPriceLevel levelList = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
					if (levelList != null) {
//                            oneprice = prodPrice.getFinalPrice().doubleValue() * levelList.getDiscount().doubleValue();
						// TODO 如果商品存在折扣，则价格为原价，否则为优惠价
						if (levelList.getDiscount() != null && !levelList.getDiscount().equals("")) {
							oneprice = prodPrice.getOriginalPrice().doubleValue();
						} else {
							oneprice = levelList.getDiscountPrice().doubleValue();
						}
					} else {
						oneprice = prodPrice.getFinalPrice().doubleValue() * 1;
					}
				}
			} else {
				oneprice = prodPrice.getFinalPrice().doubleValue();
			}
			//订单价格项
			OrderPriceItem orderPriceItem = new OrderPriceItem();
			orderPriceItem.setWineryId(user.getWineryId());
			orderPriceItem.setOrderPriceId(orderPrice.getId());
			orderPriceItem.setOrderId(order.getId());
			orderPriceItem.setProdId(assembleCommodity.getProdId());
			orderPriceItem.setQuantity(1);
			orderPriceItem.setOrigUnitPrice(prodPrice.getOriginalPrice());
			orderPriceItem.setOrigTotalPrice(prodPrice.getOriginalPrice().multiply(new BigDecimal(1)));
			orderPriceItem.setFinalUnitPrice(new BigDecimal(oneprice));
			orderPriceItem.setFinalTotalPrice(new BigDecimal(oneprice).multiply(new BigDecimal(1)));
			orderPriceItemRepository.saveAndFlush(orderPriceItem);
			totalPrice = orderPriceItem.getFinalTotalPrice().doubleValue();
			prodNum = 1;
			ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(assembleCommodity.getProdId());
			if (prodSpec != null) {
				orderPriceItem.setProdSpecId(prodSpec.getProdSpecId());
				orderProd.setProdSpecId(prodSpec.getProdSpecId());
			}
		}
		orderPrice.setProdOrigPrice(new BigDecimal(totalPrice));
		if (userVoucher != null && userVoucher.getUseTime() == null) {
			//订单结算
			OrderSettle orderSettle = new OrderSettle();
			orderSettle.setOrderId(order.getId());
			orderSettle.setUserId(user.getId());
			orderSettle.setUseVoucherId(userVoucher.getId());
			orderSettle.setUsePoint(0);

			orderSettle.setCreateTime(new Timestamp(System.currentTimeMillis()));
			orderSettleRepository.saveAndFlush(orderSettle);
			VoucherInst inst = voucherInstRepository.findOne(userVoucher.getVoucherInstId());
			if (inst != null && inst.getType().equals("M")) {
				totalPrice -= inst.getMoney().doubleValue();
			} else if (inst != null && inst.getType().equals("D")) {
				totalPrice *= (inst.getDiscount().doubleValue() / 100);
			} else if (inst != null && inst.getType().equals("G")) {
				if (assembleCommodity.getProdId().equals(inst.getExchangeProdId()) ) {
					prodPrice = prodPriceRepository.findProdPriceByProdId(inst.getExchangeProdId());
					Double price1 = 0.0;
					if (prodPrice != null) {
						price1 = prodPrice.getFinalPrice().doubleValue();
					}
					Prod prod = prodRepository.findOne(assembleCommodity.getProdId());
					if (prod != null && prod.getMemberDiscount().equals("Y")) {
						MemberUser user1 = memberUserRepository.findByUserId(user.getId());
						if (user1 != null) {
							ProdPriceLevel level = prodPriceLevelRepository.findByProdIdAndLevelId(prod.getId(), user1.getMemberLevelId());
							if (level != null) {
								if (level != null) {
									// TODO 如果商品存在折扣，则价格为原价，否则为优惠价
									if (level.getDiscount() != null && !level.getDiscount().equals("")) {
										price1 = prodPrice.getOriginalPrice().doubleValue();
									} else {
										price1 = level.getDiscountPrice().doubleValue();
									}
//                                        price1 = prodPrice.getFinalPrice().doubleValue() * level.getDiscount().doubleValue();
								} else {
									price1 = prodPrice.getFinalPrice().doubleValue() * 1;
								}
							}
						}
					}
					totalPrice -= price1;
				}
			}
			//作废券
			userVoucher.setUseTime(new Date());
			userVoucherRepository.save(userVoucher);
			inst.setStatus("U");
			inst.setStatusTime(new Date());
			inst.setOrderId(order.getId());
			inst.setOrderType("O");
			voucherInstRepository.save(inst);
		}
		orderPrice.setProdFinalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		orderPrice.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		order.setTotalPrice(totalPrice < 0 ? new BigDecimal(0) : new BigDecimal(totalPrice));
		order.setProdNum(prodNum);
		//订单地址
		if (userAddress != null) {
			OrderAddress orderAddress = new OrderAddress();
			orderAddress.setOrderId(order.getId());
			orderAddress.setWineryId(order.getWineryId());
			orderAddress.setUserAddressId(userAddress.getId());
			orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
			orderAddressRepository.saveAndFlush(orderAddress);
		}

		//拼团用户
		AssembleUser assembleUser = new AssembleUser();
		assembleUser.setUserId(user.getId());
		assembleUser.setIsMaster(0);//是否为团长 0否，1是
		assembleUser.setAssembleList(assembleList.getId());
		assembleUser.setOrderId(order.getId());//生成订单的编号
		assembleUser.setCreateTime(new Date());
		assembleUser.setAssembleCommodity(assembleCommodity.getId());
		assembleUserRepository.save(assembleUser);

		//拼团列表   如果人数齐了 状态为2拼团成功
		int assemblePresonEd = assembleCommodityRepository.findByList(assembleList.getId());//同一个团队的拼团人数
		if(Integer.valueOf(assemblePresonEd).equals(assembleCommodity.getAssemblePreson())){
			assembleList.setAssembleStatus(2);//1拼团中，2拼团成功，3拼团失败（待退款），4 拼团失败（已退款）
			assembleListRepository.saveAndFlush(assembleList);
		}
	}









}