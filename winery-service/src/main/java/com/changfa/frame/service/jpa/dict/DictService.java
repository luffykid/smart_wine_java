package com.changfa.frame.service.jpa.dict;


import com.changfa.frame.data.entity.common.Dict;
import com.changfa.frame.data.repository.common.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DictService {


    @Autowired
    private DictRepository dictRepository;

    /**
     * 通过表名、列名获取所有的字典信息,MAP形式
     *
     * @param tableName
     * @param columnName
     * @return
     */
    public Map<String, String> findMapByTableNameAndColumnName(String tableName, String columnName) {

        List<Dict> dictList = findByTableNameAndColumnName(tableName, columnName);

        Map<String, String> map = new HashMap<String, String>();

        if (null != dictList) {
            for (Dict dict : dictList) {
                map.put(dict.getStsId(), dict.getStsWords());
            }
        }
        return map;
    }


    /**
     * 通过表名、列名获取所有的字典信息
     *
     * @param tableName
     * @param columnName
     * @return
     */
    public List<Dict> findByTableNameAndColumnName(String tableName, String columnName) {

        List<Dict> dictList = new ArrayList<Dict>();
        //List<Dict> cacheDictList = CacheUtil.getDictList();
        List<Dict> cacheDictList = dictRepository.findAll();

        if (cacheDictList != null && !cacheDictList.isEmpty()) {
            for (Dict dict : cacheDictList) {
                if (tableName.equals(dict.getTableName()) && columnName.equals(dict.getColumnName())) {
                    dictList.add(dict);
                }
            }
            return dictList;
        } else {
            return dictRepository.findByTableNameAndColumnNameOrderBySeqAsc(tableName, columnName);
        }
    }

    /**
     * 通过表名、列名、取值获取的字典信息
     *
     * @param tableName
     * @param columnName
     * @param stsId
     * @return
     */
    public Dict findStatus(String tableName, String columnName, String stsId) {
        Dict dict = new Dict();
        //List<Dict> cacheDictList = CacheUtil.getDictList();
        List<Dict> cacheDictList = dictRepository.findAll();
        if (cacheDictList != null && !cacheDictList.isEmpty()) {
            for (Dict cacheDict : cacheDictList) {
                if (tableName.equals(cacheDict.getTableName()) && columnName.equals(cacheDict.getColumnName()) && stsId.equals(cacheDict.getStsId())) {
                    dict = cacheDict;
                    break;
                }
            }
            return dict;
        } else {
            return dictRepository.findByTableNameAndColumnNameAndStsId(tableName, columnName, stsId);
        }
    }

    /**
     * 通过表名、列名、取值获取的字典值名字
     *
     * @param tableName
     * @param columnName
     * @param stsId
     * @return
     */
    public String findStatusName(String tableName, String columnName, String stsId) {
        Dict dict = findStatus(tableName, columnName, stsId);

        if (dict != null) {
            return dict.getStsWords();
        } else {
            return dictRepository.findNameByTableNameAndColumnNameAndStsId(tableName, columnName, stsId);
        }
    }


    public List<DictLabel> findListByTableNameAndColumnName(String tableName, String columnName) {

        List<Dict> dictList = findByTableNameAndColumnName(tableName, columnName);

        List<DictLabel> list = new ArrayList();

        dictList.forEach(d -> list.add(new DictLabel(d.getStsWords(), d.getStsId())));

        return list;
    }

}

class DictLabel {

    String Label, Value;

    public DictLabel(String label, String value) {
        Label = label;
        Value = value;
    }

    public String getLabel() {
        return Label;
    }

    public String getValue() {
        return Value;
    }
}