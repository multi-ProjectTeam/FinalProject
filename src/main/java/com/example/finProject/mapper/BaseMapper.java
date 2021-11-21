package com.example.finProject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.finProject.dto.Enterprise;

@Mapper
public interface BaseMapper {
	@Select("select * from enterprise")
	public Enterprise[] GETenterprises();
}
