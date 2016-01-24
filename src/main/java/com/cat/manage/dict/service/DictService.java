package com.cat.manage.dict.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.dict.dao.DictDao;
import com.cat.manage.dict.domain.Dict;
import com.cat.manage.dict.domain.DictItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * @Description: 字典服务实现类
 * @author 
 * @date 2015年11月17日 上午10:40:44
 */
@Service
public class DictService{

	@Autowired
	private DictDao dictDao;

	
	public List<Dict> getDicts() {
		return dictDao.queryAllDicts();
	}

	
	public List<Dict> getDictsByType(String type) {
		return dictDao.queryDictsByType(type);
	}

	
	public Dict getDictById(int id) {
		return dictDao.queryDictById(id);
	}

	
	public Dict getDictByIdWithItems(int id) {
		Dict dict = dictDao.queryDictById(id);

		if (dict == null) {
			return null;
		}

		List<DictItem> dictItems = dictDao.queryAllItemsByDictId(id);
		if (dictItems != null) {
			dict.setItems(dictItems);
		}

		return dict;
	}

	
	public Dict getDictByCode(String code) {
		return dictDao.queryDictByCode(code);
	}

	
	public Dict getDictByCodeWithItems(String code) {
		Dict dict = dictDao.queryDictByCode(code);

		if (dict == null)
			return null;

		List<DictItem> items = dictDao.queryAllItemsByDictId(dict.getId());
		if (items != null)
			dict.setItems(items);

		return dict;
	}

	
	public void addDict(Dict dict) {
		dictDao.insertDict(dict);
	}

	
	public void addDictItem(DictItem item) {
		dictDao.insertDictItem(item);
	}

	
	public void deleteDict(Integer[] id) {
		if (id.length == 1) {
			dictDao.deleteDictById(id[0]);
			dictDao.deleteDictItemByTypeid(id[0]);

		} else {
			dictDao.deleteDictMore(id);
			dictDao.deleteDictItemMoreByTypeid(id);
		}

	}

	
	public void deleteDictItem(Integer[] id) {
		if (id.length == 1) {
			dictDao.deleteDictItem(id[0]);
		} else {
			dictDao.deleteDictItemMore(id);
		}

	}

	
	public void updateDict(Dict dict) {
		dictDao.updateDict(dict);
	}

	
	public void updateDictItem(DictItem item) {
		dictDao.updateDictItem(item);
	}

	
	public PageInfo<DictItem> queryFuzzyItem(DictItem item, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		List<DictItem> items = dictDao.queryFuzzyItem(item);
		PageInfo<DictItem> page = new PageInfo<DictItem>(items);
		
		return page;
	}

    
    public String queryDictItemValue(String dictCode, String dictItemName) {
        Dict dict = getDictByCodeWithItems(dictCode);
        if (dict == null)
            throw new BusinessException("2", "查询的字典不存在");
        
        if (Strings.isNullOrEmpty(dictItemName)) {
            throw new BusinessException("1", "查询的字典项名称不能为空");
        }
        
        List<DictItem> items = dict.getItems();
        for (Iterator<DictItem> it = items.iterator(); it.hasNext();) {
            DictItem item = it.next();
            if (dictItemName.equals(item.getName())) {
                return item.getValue();
            }
        }
        
        return "";
    }

    
    public String queryDictItemName(String dictCode, String dictItemValue) {
        Dict dict = getDictByCodeWithItems(dictCode);
        if (dict == null)
            throw new BusinessException("2", "查询的字典不存在");
        
        if (Strings.isNullOrEmpty(dictItemValue)) {
            throw new BusinessException("1", "查询的字典项值不能为空");
        }
        
        List<DictItem> items = dict.getItems();
        for (Iterator<DictItem> it = items.iterator(); it.hasNext();) {
            DictItem item = it.next();
            if (dictItemValue.equals(item.getValue())) {
                return item.getName();
            }
        }
        
        return "";
    }
}
