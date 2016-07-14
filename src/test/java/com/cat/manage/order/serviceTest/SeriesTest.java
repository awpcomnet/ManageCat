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
import com.cat.manage.base.dao.SeriesHistoryDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.SeriesHistory;
import com.cat.manage.base.service.SeriesService;
import com.cat.manage.check.domain.Check;
import com.google.common.collect.Lists;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class SeriesTest {

	@Autowired
	private SeriesDao seriesDao;
	
	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private SeriesHistoryDao shd;
	
	
	
	public void testSeriesHis(){
		LOG.info("系列历史测试开始");
		Series series = new Series();
		series.setSeriesEname("Pomegranate");
		List<Series> seriesList = seriesService.querySeriesAll(series, "");
		System.out.println(seriesList.get(0).toString());
		
		System.out.println("添加系列历史");
		shd.addSeriesHistory(seriesList.get(0), "0");
		
		System.out.println("查询系列历史");
		SeriesHistory shis = new SeriesHistory();
		shis = shd.querySeriesHistoryBySeriesId(seriesList.get(0).getSeriesId());
		System.out.println(shis.toString());
		
		System.out.println("修改系列历史");
		shis.setNeedSynchronization("1");
		shd.updateSeriesHistory(shis);
		
		System.out.println("查询系列历史2");
		shis = shd.querySeriesHistoryBySeriesHisId(shis.getSeriesHisId());
		System.out.println(shis.toString());
		
		System.out.println("删除系列历史");
		shd.deleteSeriesHistory(shis);
		
		LOG.info("系列历史测试结束");
	}
	
	
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
	
	@Test
	public void testSeriesService(){
		List<Check> checkAffect = Lists.newArrayList();
		List<Check> shipAffect = Lists.newArrayList();
		List<Check> storeAffect = Lists.newArrayList();
		List<Check> sellAffect = Lists.newArrayList();
		
		LOG.info("系列测试开始");
		Series series = new Series();
		series.setSeriesEname("ANR");
		
		List<Series> list = seriesService.querySeriesAll(series, null);
		System.out.println(list.get(0).toString());
		
		List<Check> checks = seriesService.queryAffectCheck(list.get(0));
		for(Check c : checks){
			System.out.println(c.toString());
			
			switch(c.getOrderStatus()){
				case "0"://下单
					checkAffect.add(c);
					break;
				case "1"://邮寄
					checkAffect.add(c);
					shipAffect.add(c);
					break;
				case "2"://入库
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					break;
				case "3"://售出
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					sellAffect.add(c);
					break;
				case "4"://销售中
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					sellAffect.add(c);
					break;
				case "5"://售出（补损）
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					sellAffect.add(c);
					break;
				case "7"://部分入库
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					break;
				case "98"://已损坏
					checkAffect.add(c);
					shipAffect.add(c);
					storeAffect.add(c);
					sellAffect.add(c);
					break;
			}
		}
		
		System.out.println("一共["+checks.size()+"]记录，影响下单清单记录["+checkAffect.size()+"]，影响邮寄清单记录["+shipAffect.size()+"]，"
				+ "影响入库清单记录["+storeAffect.size()+"]，影响售出清单记录["+sellAffect.size()+"]");
		
		LOG.info("系列测试结束");
	}
	
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SeriesTest.class);
}
