package com.cat.manage.order.serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.base.dao.SeriesDao;
import com.cat.manage.base.domain.Series;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SeriesTest {

	@Autowired
	private SeriesDao seriesDao;
	
	@Test
	public void testBatchDao(){
		
		LOG.info("系列测试开始");
		Series series = new Series();
		series.setSeriesEname("anr");
		series.setSeriesName("夜间修");
		series.setOfOrigin("1");
		series.setIsUse("1");
		
		List<Series> list = seriesDao.querySeriesAccurateForName(series);
		for(Series s : list)
			System.out.println(s.toString());
		
		LOG.info("系列测试结束");
	}
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SeriesTest.class);
}
