package com.changfa.frame.data.repository.common;

import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.repository.AdvancedJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DictRepository extends AdvancedJpaRepository<Dict, Integer> {

    List<Dict> findByTableNameAndColumnNameOrderBySeqAsc(String tableName, String columnName);

    Dict findByTableNameAndColumnNameAndStsId(String tableName, String columnName, String stsId);

    @Query("select s.stsWords from Dict as s  where s.tableName = :tableName and s.columnName = :columnName and s.stsId = :stsId")
    String findNameByTableNameAndColumnNameAndStsId(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("stsId") String stsId);




}
