package com.changfa.frame.service.jpa.bargaining;

import com.changfa.frame.data.dto.saas.*;
import com.changfa.frame.data.dto.wechat.*;
import com.changfa.frame.data.entity.bargaining.BargainingCommodity;
import com.changfa.frame.data.entity.bargaining.BargainingHelp;
import com.changfa.frame.data.entity.bargaining.BargainingUser;
import com.changfa.frame.data.entity.order.*;
import com.changfa.frame.data.entity.prod.*;
import com.changfa.frame.data.entity.user.AdminUser;
import com.changfa.frame.data.entity.user.Member;
import com.changfa.frame.data.entity.user.UserAddress;
import com.changfa.frame.data.repository.bargaining.BargainingCommodityRepository;
import com.changfa.frame.data.repository.bargaining.BargainingHelpRepository;
import com.changfa.frame.data.repository.bargaining.BargainingUserRepository;
import com.changfa.frame.data.repository.order.*;
import com.changfa.frame.data.repository.prod.*;
import com.changfa.frame.data.repository.user.MemberRepository;
import com.changfa.frame.service.jpa.PicturePathUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/11/9.
 */
@Service
public class BargainingService {
	@Autowired
	private BargainingCommodityRepository bargainingCommodityRepository;
	@Autowired
	private ProdRepository prodRepository;
	@Autowired
	private  ProdLogoRepository prodLogoRepository;
	@Autowired
	private ProdProdSpecRepository prodProdSpecRepository;
	@Autowired
	private ProdSpecRepository prodSpecRepository;
	@Autowired
	private ProdPriceRepository prodPriceRepository;
	@Autowired
	private BargainingUserRepository bargainingUserRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private BargainingHelpRepository bargainingHelpRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderPriceRepository orderPriceRepository;
	@Autowired
	private OrderProdRepository orderProdRepository;
	@Autowired
	private OrderPriceItemRepository orderPriceItemRepository;
	@Autowired
	private OrderAddressRepository orderAddressRepository;



	/**
	 * 砍价商品列表
	 *
	 * @param adminUser
	 * @param input     商品名称
	 * @return
	 */
	public List<BargainingCommodityListDTO> bargainingList(AdminUser adminUser, String input) {
		List<BargainingCommodity> bargainingList = new ArrayList<>();
		bargainingList = bargainingCommodityRepository.findByWineryIdLikeName(adminUser.getWineryId().intValue(), input);
		List<BargainingCommodityListDTO> bcListDTOS = new ArrayList<>();
		for (BargainingCommodity bc : bargainingList) {
			BargainingCommodityListDTO bcListDTO = new BargainingCommodityListDTO();
			bcListDTO.setId(bc.getId());
			Prod prod = prodRepository.getOne(bc.getProdId());
			if(null != prod){
				bcListDTO.setProdName(prod.getName());
			}
			bcListDTO.setStartTime(bc.getStartTime());
			bcListDTO.setEndTime(bc.getEndTime());
			bcListDTO.setBargainingNum(bc.getBargainingNum());
			int bargainingNumEd = bargainingCommodityRepository.findByWineryIdAndIdUser(adminUser.getWineryId().intValue(),bc.getId());
			int noBargainingNum = bc.getBargainingNum() - bargainingNumEd;
			bcListDTO.setNoBargainingNum(noBargainingNum);
			bcListDTO.setBargainingPrice(bc.getBargainingPrice());
			bcListDTO.setStatus(bc.getStatus());
			bcListDTOS.add(bcListDTO);
		}
		return bcListDTOS;
	}

	@Autowired
	private ProdStockRepository prodStockRepository;

	//新增砍价商品
	public void addBargaining(AdminUser adminUser, BargainingCommodityDTO bargainingCommodityDTO) {
		BargainingCommodity bargainingCommodity = new BargainingCommodity();
		bargainingCommodity.setWineryId(adminUser.getWineryId().intValue());
		bargainingCommodity.setProdId(bargainingCommodityDTO.getProdId());
		bargainingCommodity.setBargainingPrice(bargainingCommodityDTO.getBargainingPrice());
		bargainingCommodity.setBargainingNum(bargainingCommodityDTO.getBargainingNum());
		//判断当前时间在不在活动时间范围，如果不在未开始；如果在，进行中；如果在，并且商品库存数量小于0，进行中（售罄）
		if(System.currentTimeMillis() < bargainingCommodityDTO.getStartTime().getTime() ){
			bargainingCommodity.setStatus(0);
		}else if(System.currentTimeMillis() >= bargainingCommodityDTO.getStartTime().getTime() && System.currentTimeMillis() <= bargainingCommodityDTO.getEndTime().getTime()){
			ProdStock stock = prodStockRepository.findByProdId(bargainingCommodityDTO.getProdId());
			if (stock != null && stock.getProdStock() < 0) {
				bargainingCommodity.setStatus(2);
			}else{
				bargainingCommodity.setStatus(1);
			}
		}
		bargainingCommodity.setStartTime(bargainingCommodityDTO.getStartTime());
		bargainingCommodity.setEndTime(bargainingCommodityDTO.getEndTime());
		bargainingCommodity.setCreateTime(new Date());
		bargainingCommodity.setTopTime(bargainingCommodity.getCreateTime());
		bargainingCommodity.setUserId(adminUser.getId().intValue());
		bargainingCommodity.setIsDelete(1);
		bargainingCommodityRepository.saveAndFlush(bargainingCommodity);
	}

	/**
	 * 砍价商品详情
	 *
	 * @param bargainingId     砍价商品表id
	 * @return
	 */
	public BargainingCommodityDTO bargainingDetail(Integer bargainingId) {
		BargainingCommodityDTO bcDTO = new BargainingCommodityDTO();
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);

		if (bargainingCommodity != null) {
			bcDTO.setId(bargainingCommodity.getId());
			Prod prod = prodRepository.getOne(bargainingCommodity.getProdId());
			if(null != prod){
				bcDTO.setProd(prod);
			}
			bcDTO.setBargainingPrice(bargainingCommodity.getBargainingPrice());
			bcDTO.setBargainingNum(bargainingCommodity.getBargainingNum());
			bcDTO.setStartTime(bargainingCommodity.getStartTime());
			bcDTO.setEndTime(bargainingCommodity.getEndTime());
		}
		return bcDTO;
	}

	//检查是否可以编辑(在活动时间内，不允许编辑。)
	public Boolean checkisUpdate(Integer bargainingId) {
		Boolean ret = false;
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
		if(null != bargainingCommodity ){
			if(bargainingCommodity.getStartTime().getTime() <= System.currentTimeMillis()  &&
					System.currentTimeMillis() <= bargainingCommodity.getEndTime().getTime() ){
				ret =  true;
			}else{
				ret =  false;
			}
		}
		return ret;
	}


	//修改砍价商品
	public void updateBargaining(AdminUser adminUser, BargainingCommodityDTO bargainingCommodityDTO) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingCommodityDTO.getId());
		if (bargainingCommodity == null) {
			bargainingCommodity = new BargainingCommodity();
			bargainingCommodity.setCreateTime(new Date());
			bargainingCommodity.setTopTime(bargainingCommodity.getCreateTime());
			bargainingCommodity.setUserId(adminUser.getId().intValue());
		}
		bargainingCommodity.setWineryId(adminUser.getWineryId().intValue());
		bargainingCommodity.setProdId(bargainingCommodityDTO.getProdId());
		bargainingCommodity.setBargainingPrice(bargainingCommodityDTO.getBargainingPrice());
		bargainingCommodity.setBargainingNum(bargainingCommodityDTO.getBargainingNum());
		bargainingCommodity.setStartTime(bargainingCommodityDTO.getStartTime());
		bargainingCommodity.setEndTime(bargainingCommodityDTO.getEndTime());
		//判断当前时间在不在活动时间范围，如果不在未开始；如果在，进行中；如果在，并且商品库存数量小于0，进行中（售罄）
		if(System.currentTimeMillis() < bargainingCommodityDTO.getStartTime().getTime() ){
			bargainingCommodity.setStatus(0);
		}else if(System.currentTimeMillis() >= bargainingCommodityDTO.getStartTime().getTime() && System.currentTimeMillis() <= bargainingCommodityDTO.getEndTime().getTime()){
			ProdStock stock = prodStockRepository.findByProdId(bargainingCommodityDTO.getProdId());
			if (stock != null && stock.getProdStock() < 0) {
				bargainingCommodity.setStatus(2);
			}else{
				bargainingCommodity.setStatus(1);
			}
		}
		bargainingCommodityRepository.saveAndFlush(bargainingCommodity);
	}

	//砍价商品置顶
	public void topBargaining(Integer bargainingId) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
		if (bargainingCommodity != null) {
			bargainingCommodity.setTopTime(new Date());
			bargainingCommodityRepository.saveAndFlush(bargainingCommodity);
		}
	}

	//砍价商品取消置顶
	public void untopBargaining(Integer bargainingId) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
		if (bargainingCommodity != null) {
			bargainingCommodity.setTopTime(bargainingCommodity.getCreateTime());
			bargainingCommodityRepository.saveAndFlush(bargainingCommodity);
		}
	}

	//删除砍价商品
	public void delBargaining(Integer bargainingId) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
		if (bargainingCommodity != null) {
			bargainingCommodity.setIsDelete(0);
			bargainingCommodityRepository.saveAndFlush(bargainingCommodity);
		}
	}

	//******************************小程序端******************************************************************


	/**
	 * 砍价商品列表
	 *
	 * @param user
	 * @param input     商品名称
	 * @return
	 */
	public List<BargainingWeichatListDTO> bargainingListWechat(Member user, String input) {
		List<BargainingCommodity> bargainingList = new ArrayList<>();
		bargainingList = bargainingCommodityRepository.findByWineryIdLikeName(Integer.valueOf(user.getWineryId().toString()), input);
		List<BargainingWeichatListDTO> bcListDTOS = new ArrayList<>();
		for (BargainingCommodity bc : bargainingList) {
			BargainingWeichatListDTO bcListDTO = new BargainingWeichatListDTO();
			bcListDTO.setId(bc.getId());
			Prod prod = prodRepository.getOne(bc.getProdId());
			if(null != prod){
				ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
				if (prodLogo != null) {
					bcListDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
				}
				bcListDTO.setProdName(prod.getName());
				bcListDTO.setProdTitle(prod.getTitle());
				ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
				if (prodSpec != null) {
					ProdSpec spec = prodSpecRepository.getOne(prodSpec.getProdSpecId());
					if (spec != null) {
						bcListDTO.setProdSpecName(spec.getSpecValue());
					}
				}
				ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
				if (price != null) {
					bcListDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
				}
			}
			bcListDTO.setBargainingPrice(bc.getBargainingPrice());
			bcListDTOS.add(bcListDTO);
		}
		return bcListDTOS;
	}

	/**
	 *  砍价商品详情
	 *
	 * @param user
	 * @param bargainingId
	 * @return
	 */
	public BargainingPordDetailWeichatDTO bargainingProdWchat(Member user, Integer bargainingId) {
		BargainingPordDetailWeichatDTO bcDetailDTO = new BargainingPordDetailWeichatDTO();
		if(null != bargainingId){
			BargainingCommodity bc = bargainingCommodityRepository.getOne(bargainingId);
			bcDetailDTO.setId(bc.getId());
			Prod prod = prodRepository.getOne(bc.getProdId());
			if(null != prod){
				bcDetailDTO.setProdName(prod.getName());
				bcDetailDTO.setProdTitle(prod.getTitle());
				ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
				if (price != null) {
					bcDetailDTO.setProdPrice(String.valueOf(price.getFinalPrice()));
				}
				ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
				if (prodSpec != null) {
					ProdSpec spec = prodSpecRepository.getOne(prodSpec.getProdSpecId());
					if (spec != null) {
						bcDetailDTO.setProdSpecName(spec.getSpecValue());
					}
				}
			}
			bcDetailDTO.setBargainingPrice(bc.getBargainingPrice());
			bcDetailDTO.setEndTime(bc.getEndTime());
			int bargainingNumEd = bargainingCommodityRepository.findByWineryIdAndIdUser(Integer.valueOf(user.getWineryId().toString()),bc.getId());//已经发起砍价的数量
			bcDetailDTO.setBargainingNumEd(bargainingNumEd);
		}
		return bcDetailDTO;
	}






	/**
	 *  发起砍价按钮（去砍价按钮）
	 *
	 * @param user
	 * @param bargainingId
	 * @return
	 */
	public Integer bargainingButton(Member user, Integer bargainingId) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingId);
		if(null != bargainingCommodity){
			BargainingUser bargainingUser = new BargainingUser();
			bargainingUser.setUserId(Integer.valueOf(user.getId().toString()));
			bargainingUser.setBargainingCommodity(bargainingCommodity.getId());
			bargainingUser.setBuyPrice(null);
			bargainingUser.setOrderId(null);
			bargainingUser.setCreateTime(new Date());
			bargainingUser.setStatus(0);
			bargainingUserRepository.saveAndFlush(bargainingUser);
			return bargainingUser.getId();
		}
		return null;
	}

	/**
	 *  发起砍价用户   砍价详情页面
	 *
	 * @param user
	 * @param bargainingUserId
	 * @return
	 */
	public BargainingDetailWeichatDTO bargainingDetail(Member user, Integer bargainingUserId) {
		BargainingUser bargainingUser = bargainingUserRepository.getOne(bargainingUserId);
		BargainingDetailWeichatDTO bdwd = new BargainingDetailWeichatDTO();
		if(null != bargainingUser){
			bdwd.setId(bargainingUser.getId());
			bdwd.setUserId(bargainingUser.getUserId());
			Member startUser = memberRepository.getOne(bargainingUser.getUserId());
			if(null != user){
				bdwd.setUserLogo(startUser.getUserIcon());
			}
			BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingUser.getBargainingCommodity());
			if(null != bargainingCommodity){
				bdwd.setBargainingPrice(bargainingCommodity.getBargainingPrice());
				Prod prod = prodRepository.getOne(bargainingCommodity.getProdId());
				if(null != prod){
					bdwd.setProdName(prod.getName());
					ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
					if (prodLogo != null) {
						bdwd.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
					}
					bdwd.setProdTitle(prod.getTitle());
					ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
					if (price != null) {
						bdwd.setProdPrice(String.valueOf(price.getFinalPrice()));
					}
				}
			}
			List<BargainingHelp> bHelpList =  bargainingHelpRepository.findByBargainingUser(bargainingUser.getId());
			List<BargainingUserWeichatListDTO> userList = new ArrayList<>();
			List<Integer> helpUserIdList = new ArrayList<>();
			BigDecimal lessPrice = new BigDecimal(0); //一共砍掉的价格
			if(null != bHelpList && bHelpList.size()>0){
				for(BargainingHelp bargainingHelp : bHelpList){
					BargainingUserWeichatListDTO buwld = new BargainingUserWeichatListDTO();
					buwld.setId(bargainingHelp.getId());
					Member helpUser = memberRepository.getOne(bargainingHelp.getUserId());
					if(null != helpUser){
						buwld.setUserID(Integer.valueOf(helpUser.getId().toString()));
						buwld.setIcon(helpUser.getUserIcon());
					}
					buwld.setLessPrice(bargainingHelp.getLessPrice());
					buwld.setCreateTime(bargainingHelp.getCreateTime());
					userList.add(buwld);
					helpUserIdList.add(buwld.getUserID());
					lessPrice = lessPrice.add(buwld.getLessPrice()); //帮砍用户砍掉的价格相加
				}
			}
			BigDecimal prodPrice=new BigDecimal(bdwd.getProdPrice()); //商品的原价格
			BigDecimal nowPrice = prodPrice.subtract(lessPrice); //商品原价格 减去 砍掉的价格 等于 当前可购买的价格
			bdwd.setNowPrice(nowPrice);
			bdwd.setUserList(userList);
			bdwd.setHelpUserIdList(helpUserIdList);
		}
		return bdwd;
	}


	/**
	 *  帮ta砍一刀按钮
	 *
	 * @param user
	 * @param bargainingUserId
	 * @return
	 */
	public void bargainingHelp(Member user, Integer bargainingUserId) {
		BargainingUser bargainingUser = bargainingUserRepository.getOne(bargainingUserId);
		if(null != bargainingUser){
			BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingUser.getBargainingCommodity());
			BigDecimal prodPrice = new BigDecimal(0); //商品的原价格
			BigDecimal lessPrice = new BigDecimal(0); //一共砍掉的价格
			Integer knifeNum = 0;  //需要帮砍多少刀看到最低价
			Integer knifeNumEd = 0; //已经帮砍价的人数
			BigDecimal bargainingPrice = new BigDecimal(0); //购买的最低价格
			if(null != bargainingCommodity){
				knifeNum = bargainingCommodity.getKnifeNum();
				bargainingPrice = bargainingCommodity.getBargainingPrice();
				Prod prod = prodRepository.getOne(bargainingCommodity.getProdId());
				if(null != prod){
					ProdPrice price = prodPriceRepository.findProdPriceByProdId(prod.getId());
					if (price != null) {
						prodPrice = price.getFinalPrice();
					}
				}
			}
			List<BargainingHelp> bHelpList =  bargainingHelpRepository.findByBargainingUser(bargainingUser.getId());

			if(null != bHelpList && bHelpList.size()>0){
				knifeNumEd = bHelpList.size();
				for(BargainingHelp bargainingHelp : bHelpList){
					lessPrice = lessPrice.add(bargainingHelp.getLessPrice()); //帮砍用户砍掉的价格相加
				}
			}
			//砍价逻辑**********
			BigDecimal allPrice = prodPrice.subtract(bargainingPrice); //可以砍掉的总共价钱  原价1500 底价1000  这个值就是500

            //此次砍价的最低钱数（总价-已砍总价/总次数-已砍次数）（相当于是向上随机）（转换为单位分）
			Integer min = (allPrice.subtract(lessPrice)).divide(new BigDecimal(knifeNum).subtract(new BigDecimal(knifeNumEd)),2,BigDecimal.ROUND_UP).multiply(new BigDecimal(100)).intValue();
			//此次砍价的最高钱数（最低价格的2倍）
			//这个倍数越高，砍价的幅度跳动越大。建议设置到1-2.（不能超过2.因为有可到导致总刀数不准确）
			Integer max = min*2;
			//此次砍的价格（最低钱数到最高钱数的随机）
			BigDecimal cutPrice = new BigDecimal(min + new Random().nextInt(max-min)).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_UP);
			System.out.println(cutPrice);
			//最后一刀保证价格准确
			if(knifeNumEd==(knifeNum-1)){
				cutPrice = allPrice.subtract(lessPrice);
			}

			//帮砍表
			BargainingHelp bargainingHelp = new BargainingHelp();
			bargainingHelp.setUserId(Integer.valueOf(user.getId().toString()));
			bargainingHelp.setBargainingUser(bargainingUser.getId());
			bargainingHelp.setLessPrice(cutPrice);  //帮砍掉的价格
			bargainingHelp.setCreateTime(new Date());
			bargainingUserRepository.saveAndFlush(bargainingUser);


		}
	}



	/**
	 *  当前价购买按钮
	 *
	 * @param user
	 * @param bargainingUserId
	 *  @param nowPrice
	 * @return
	 */
	public BargainingWeichatDTO buyNowPrice(Member user, Integer bargainingUserId, BigDecimal nowPrice) {
		BargainingUser bargainingUser = bargainingUserRepository.getOne(bargainingUserId);
		if(null != bargainingUser){
			BargainingWeichatDTO bargainingWeichatDTO = new BargainingWeichatDTO();
			BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingUser.getBargainingCommodity());
			bargainingWeichatDTO.setId(bargainingUser.getId());
			if(null != bargainingCommodity){
				Prod prod = prodRepository.getOne(bargainingCommodity.getProdId());
				if(null != prod){
					ProdLogo prodLogo = prodLogoRepository.findDefaultLogo(prod.getId());
					if (prodLogo != null) {
						bargainingWeichatDTO.setProdLogo(PicturePathUntil.SERVER + prodLogo.getLogo());
					}
					bargainingWeichatDTO.setProdTitle(prod.getTitle());
					ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(prod.getId());
					if (prodSpec != null) {
						ProdSpec spec = prodSpecRepository.getOne(prodSpec.getProdSpecId());
						if (spec != null) {
							bargainingWeichatDTO.setProdSpecName(spec.getSpecValue());
						}
					}
				}
			}
			bargainingWeichatDTO.setBargainingPrice(nowPrice);

			bargainingWeichatDTO.setProdNum(1);
			bargainingWeichatDTO.setDelivery("快递 免运费");

			bargainingWeichatDTO.setTotalPrie(bargainingWeichatDTO.getBargainingPrice());
			bargainingWeichatDTO.setProdPrie(bargainingWeichatDTO.getBargainingPrice());
			bargainingWeichatDTO.setFreight(new BigDecimal(0));
			return bargainingWeichatDTO;

		}
		return null;
	}


	//检查剩余库存是否满足
	public Integer checkStockOne(BargainingCommodity bargainingCommodity) {
		ProdStock stock = prodStockRepository.findByProdId(bargainingCommodity.getProdId());
		if (stock != null) {
			if (stock.getProdStock() <= 0) {
				return 1;
			}
		}
		return 0;
	}

	//生成团购编号
	public String getOrderNoByMethod(Member user) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String tempUserId = String.format("%02d", user.getId());
		String format = sdf.format(new Date()) + String.format("%02d", new Random().nextInt(99)) + tempUserId.substring(tempUserId.length() - 2);
		return format;
	}

	/**
	 * 创建砍价订单
	 *
	 * @param user
	 * @param bargainingUser    发起砍价用户表
	 * @return
	 */
	public void createBargaining(Member user, UserAddress userAddress, String descri, BargainingUser bargainingUser, BigDecimal nowPrice) {
		BargainingCommodity bargainingCommodity = bargainingCommodityRepository.getOne(bargainingUser.getBargainingCommodity());
		//砍价订单
		Order order = new Order();
		order.setWineryId(Integer.valueOf(user.getWineryId().toString()));
		order.setUserId(Integer.valueOf(user.getId().toString()));
		//订单号
		String format = getOrderNoByMethod(user);
		order.setOrderNo(format);
		order.setStatus("P");//待支付P,代发货F,待收货H,待评价R,已完成S,已取消D,已失效E
		order.setStatusTime(new Date());
		order.setCreateTime(new Date());
		order.setDescri(descri);
		order.setOrderOri(2);//订单来源（0普通订单，1拼团订单，2砍价订单）
		if (userAddress == null) {
			order.setType("T");
		} else {
			order.setType("E");
		}
		orderRepository.saveAndFlush(order);
		//订单价格
		OrderPrice orderPrice = new OrderPrice();
		orderPrice.setWineryId(Integer.valueOf(user.getWineryId().toString()));
		orderPrice.setOrderId(order.getId());
		orderPrice.setCarriageExpense(new BigDecimal(0));
		orderPriceRepository.saveAndFlush(orderPrice);

		//订单商品
		OrderProd orderProd = new OrderProd();
		orderProd.setWineryId(Integer.valueOf(user.getWineryId().toString()));
		orderProd.setOrderId(order.getId());
		orderProd.setProdId(bargainingCommodity.getProdId());
		orderProd.setQuantity(1);  //同一拼团订单中 每一个用户  拼团商品数量 只能是1
		orderProdRepository.saveAndFlush(orderProd);

		//订单价格项
		OrderPriceItem orderPriceItem = new OrderPriceItem();
		orderPriceItem.setWineryId(Integer.valueOf(user.getWineryId().toString()));
		orderPriceItem.setOrderPriceId(orderPrice.getId());
		orderPriceItem.setOrderId(order.getId());
		orderPriceItem.setProdId(bargainingCommodity.getProdId());
		orderPriceItem.setQuantity(1);

		orderPriceItem.setOrigUnitPrice(nowPrice);
		orderPriceItem.setOrigTotalPrice(nowPrice);
		orderPriceItem.setFinalUnitPrice(nowPrice);
		orderPriceItem.setFinalTotalPrice(nowPrice);
		orderPriceItemRepository.saveAndFlush(orderPriceItem);

		ProdProdSpec prodSpec = prodProdSpecRepository.findByProdId(bargainingCommodity.getProdId());
		if (prodSpec != null) {
			orderPriceItem.setProdSpecId(prodSpec.getProdSpecId());
			orderProd.setProdSpecId(prodSpec.getProdSpecId());
		}
		orderPrice.setProdOrigPrice(nowPrice);
		orderPrice.setProdFinalPrice(nowPrice);
		orderPrice.setTotalPrice(nowPrice);
		order.setTotalPrice(nowPrice);
		order.setProdNum(1);

		//订单地址
		if (userAddress != null) {
			OrderAddress orderAddress = new OrderAddress();
			orderAddress.setOrderId(order.getId());
			orderAddress.setWineryId(order.getWineryId());
			orderAddress.setUserAddressId(userAddress.getId());
			orderAddress.setCreateTime(new Timestamp(System.currentTimeMillis()));
			orderAddressRepository.saveAndFlush(orderAddress);
		}

		//砍价商品列表
		bargainingCommodity.setStatus(3); //结束
		bargainingCommodityRepository.saveAndFlush(bargainingCommodity);

		//砍价用户
		bargainingUser.setBuyPrice(nowPrice);
		bargainingUser.setOrderId(order.getId());
		bargainingUser.setStatus(1);    //0砍价中，1砍价成功，2砍价失败
		bargainingUserRepository.saveAndFlush(bargainingUser);
	}






}