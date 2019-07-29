package com.changfa.frame.data.repository.cart;

import com.changfa.frame.data.entity.cart.Cart;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2018/10/11 0011.
 */
public interface CartRepository extends AdvancedJpaRepository<Cart,Integer> {

    @Query(value = "select * from cart where user_id = ?1",nativeQuery = true)
    List<Cart> findByUserId(Integer uid);

    @Query(value = "select * from cart where user_id = ?1 and prod_id = ?2 and prod_spec_id = ?3 limit 1",nativeQuery = true)
    Cart findByUserIdAndProdIdAndSpecId(Integer id, Integer id1, Integer id2);

    @Query(value = "select * from cart where id  in ?1",nativeQuery = true)
    List<Cart> findByCartIds(List<Integer> cartIds);

    @Query(value = "select sum(c.amount) from cart c left JOIN prod p on p.id=c.prod_id where user_id = ?1 and p.id is not null",nativeQuery = true)
    BigDecimal findMyCartCount(Integer uid);
}
