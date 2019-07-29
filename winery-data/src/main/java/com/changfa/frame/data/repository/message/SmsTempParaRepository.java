package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.SmsTempPara;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsTempParaRepository extends AdvancedJpaRepository<SmsTempPara, Integer> {

    @Query(value = "select * from sms_temp_para where sms_temp_id = ?1 and is_public = 'Y' order by seq asc ",nativeQuery = true)
    List<SmsTempPara> findBySmsTempIdAndIsPublic(Integer sid);

    @Query(value = "select * from sms_temp_para where sms_temp_id = ?1 order by seq asc ",nativeQuery = true)
    List<SmsTempPara> findBySmsTempId (Integer sid);
}
