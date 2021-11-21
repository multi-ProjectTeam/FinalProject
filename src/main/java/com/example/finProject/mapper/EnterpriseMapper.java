package com.example.finProject.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.finProject.dto.Enterprise;

@Mapper
public interface EnterpriseMapper {

	@Insert("Insert into enterprise(" + "`PASSWORD`,`ENAME`,`POSTCODE`,`ROAD_ADDRESS`,`JIBUN_ADDRESS`,"
			+ "`DETAIL_ADDRESS`,`PHONE`,`EMAIL`,`INTRODUCTION`,`OPEN1`,`CLOSE1`,"
			+ "`OPEN2`,`CLOSE2`,`EIMAGE`,`ECATEGORY`)" + " values"
			+ "(#{PASSWORD},#{ENAME},#{POSTCODE},#{ROAD_ADDRESS},#{JIBUN_ADDRESS},"
			+ "#{DETAIL_ADDRESS},#{PHONE},#{EMAIL},#{INTRODUCTION},#{OPEN1},#{CLOSE1},"
			+ "#{OPEN2},#{CLOSE2},#{EIMAGE},#{ECATEGORY})")
	public void POSTenterprise(@Param("PASSWORD") String PASSWORD, @Param("ENAME") String ENAME,
			@Param("POSTCODE") String POSTCODE, @Param("ROAD_ADDRESS") String ROAD_ADDRESS,
			@Param("JIBUN_ADDRESS") String JIBUN_ADDRESS, @Param("DETAIL_ADDRESS") String DETAIL_ADDRESS,
			@Param("PHONE") String PHONE, @Param("EMAIL") String EMAIL, @Param("INTRODUCTION") String INTRODUCTION,
			@Param("OPEN1") int OPEN1, @Param("CLOSE1") int CLOSE1, @Param("OPEN2") int OPEN2,
			@Param("CLOSE2") int CLOSE2, @Param("EIMAGE") String EIMAGE, @Param("ECATEGORY") String ECATEGORY);

	@Select("select eno from enterprise where PASSWORD=#{PASSWORD} and ENAME=#{ENAME} order by MODIFICATION_TIME desc limit 1")
	public int POSTenterpriseResponse(@Param("PASSWORD") String PASSWORD, @Param("ENAME") String ENAME);

	@Select("select * from enterprise where ENO=#{ENO} order by MODIFICATION_TIME desc limit 1")
	public Enterprise GETenterprise(@Param("ENO") int ENO);

	@Update("Update enterprise set " + "`PASSWORD`=#{PASSWORD},`ENAME`=#{ENAME},`POSTCODE`=#{POSTCODE},"
			+ "`ROAD_ADDRESS`=#{ROAD_ADDRESS},`JIBUN_ADDRESS`=#{JIBUN_ADDRESS},`DETAIL_ADDRESS`=#{DETAIL_ADDRESS},"
			+ "`PHONE`=#{PHONE},`EMAIL`=#{EMAIL},`CONFIRM`=#{CONFIRM},`INTRODUCTION`=#{INTRODUCTION},"
			+ "`OPEN1`=#{OPEN1},`CLOSE1`=#{CLOSE1},`OPEN2`=#{OPEN2},`CLOSE2`=#{CLOSE2},`VALID`=#{VALID},"
			+ "`EIMAGE`=#{EIMAGE},`ECATEGORY`=#{ECATEGORY} where ENO=#{ENO}")
	public void PUTenterprise(@Param("ENO") int ENO,@Param("PASSWORD") String PASSWORD, @Param("ENAME") String ENAME,
			@Param("POSTCODE") String POSTCODE, @Param("ROAD_ADDRESS") String ROAD_ADDRESS,
			@Param("JIBUN_ADDRESS") String JIBUN_ADDRESS, @Param("DETAIL_ADDRESS") String DETAIL_ADDRESS,
			@Param("PHONE") String PHONE, @Param("EMAIL") String EMAIL, @Param("CONFIRM") String CONFIRM,
			@Param("INTRODUCTION") String INTRODUCTION, @Param("OPEN1") int OPEN1, @Param("CLOSE1") int CLOSE1,
			@Param("OPEN2") int OPEN2, @Param("CLOSE2") int CLOSE2, @Param("VALID") String VALID,
			@Param("EIMAGE") String EIMAGE, @Param("ECATEGORY") String ECATEGORY);
	
	@Delete("Delete from enterprise where ENO=#{ENO}")
	public void DELETEenterprise(@Param("ENO") int ENO);
}
