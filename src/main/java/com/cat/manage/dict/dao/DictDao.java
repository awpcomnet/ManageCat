package com.cat.manage.dict.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.dict.domain.Dict;
import com.cat.manage.dict.domain.DictItem;

/**
 * @Description: 字典功能数据访问对象
 * @author 
 * @date 2015年11月17日 上午10:44:05
 */
@Repository
public interface DictDao {
	
	/**
	 * 获得所有字典
	 */
	List<Dict> queryAllDicts();
	
	/**
	 * 根据Id获得字典
	 */
	Dict queryDictById(int id);
	
	/**
	 * 根据Code获得字典
	 */
	Dict queryDictByCode(String code);
	
	/**
	 * 根据Type获得字典
	 */
	List<Dict> queryDictsByType(String type);
	
	/**
	 * 增加一个字典
	 */
	void insertDict(Dict dict);
	
	/**
	 * 删除一个字典
	 */
	void deleteDictById(int id);
	
	/**
	 * 删除多个字典
	 */
	void deleteDictMore(Integer[] id);
	
	/**
	 * 更新一个字典
	 */
	void updateDict(Dict dict);
	
	/**
	 * 根据字典Id查询所有字典项
	 */
	List<DictItem> queryAllItemsByDictId(int id);
	
	/**
	 * 增加一个字典项
	 */
	void insertDictItem(DictItem item);
	
	/**
	 * 删除一个字典项
	 */
	void deleteDictItem(int id);
	
	/**
	 * 更新一个字典项
	 */
	void updateDictItem(DictItem item);
	
	/**
	 * 删除多个字典项
	 */
	void deleteDictItemMore(Integer[] ids);
	
	/**
	 * 根据一个字典ID 删除字典项
	 */
	void deleteDictItemByTypeid(int typeId);
	
	/**
	 * 根据字典ID，删除多个字典项(对应字典项typeid)
	 */
	void deleteDictItemMoreByTypeid(Integer[] typeIds);
	
	/**
	 * 模糊查询字典项
	 */
	List<DictItem> queryFuzzyItem(DictItem item);
 }
