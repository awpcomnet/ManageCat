package com.cat.manage.order.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.base.dao.SingleproductDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SingleproductTest {

	@Autowired
	private SingleproductDao singleDao;
	
	@Test
	public void testBatchDao(){
		
		LOG.info("单品测试开始");
		Singleproduct single = new Singleproduct();
		single.setIsUse("1");
		single.setOfOrigin("11");
		single.setCapacity("4");//规格
		single.setSingleName("变色润唇膏");//变色润唇膏
		single.setSingleEname("lipS");//lips
		
		List<Singleproduct> list = singleDao.querySingleproductsAccurateForName(single);
		if(list.size() < 1){
			System.out.println("<<<<<<无记录");
		}
		for(Singleproduct s : list)
			System.out.println("##=>"+s.toString());
		
		LOG.info("单品测试结束");
	}
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SingleproductTest.class);
}
