package com.cat.manage.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Habit;
import com.cat.manage.base.service.HabitService;
import com.cat.manage.common.model.Srm;

@RestController
@RequestMapping("/habit")
public class HabitController {
	@Autowired
	private HabitService habitService;
	
	@RequestMapping("/sellingPrice")
	public Srm queryHabit(){
		Habit habit = new Habit();
		habit.setType(HabitService.TYPESINGLE);
		List<Habit> list = habitService.queryHabit(habit);
		return new Srm().setResultCode("0").setResultMessage("查询偏好售价完成").addAll(list);
	}
	
	
}
