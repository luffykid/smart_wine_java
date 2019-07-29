package com.changfa.frame.data.repository.message;

import com.changfa.frame.data.entity.message.SmsTempContent;
import com.changfa.frame.data.repository.AdvancedJpaRepository;

public interface SmsTempContentRepository extends AdvancedJpaRepository<SmsTempContent, Integer> {

    SmsTempContent findBySmsTempId(Integer smsTempId);
}
