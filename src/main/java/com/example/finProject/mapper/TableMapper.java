package com.example.finProject.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.repository.query.Param;

import com.example.finProject.dto.Table;

@Mapper
public interface TableMapper {

	@Insert("Insert into `table`(`eno`, `seat_num`, `window_seat`) "
			+ "values(#{ENO}, #{SEAT_NUM}, #{WINDOW_SEAT})")
	public void POSTtable(@Param("ENO") int ENO, @Param("SEAT_NUM") int SEAT_NUM,
			@Param("WINDOW_SEAT") char WINDOW_SEAT);
	
	@Select("Select tno from `table` where eno=#{ENO} order by tno desc limit 1")
	public int POSTtableResponse(@Param("ENO") int ENO);
	
	@Select("select * from `table` where TNO=#{TNO}")
	public Table GETtable(@Param("TNO") int TNO);
	
	@Update("Update `table` set eno=#{ENO}, seat_num=#{SEAT_NUM}, window_seat=#{WINDOW_SEAT},"
			+ "`like`=#{LIKE}, `state`=#{STATE} where tno=#{TNO}")
	public void PUTtable(@Param("TNO") int TNO, @Param("ENO") int ENO, @Param("SEAT_NUM") int SEAT_NUM,
			@Param("WINDOW_SEAT") char WINDOW_SEAT,@Param("LIKE") int LIKE,@Param("STATE") char STATE);
	
	@Delete("Delete from `table` where TNO=#{TNO}")
	public void DELETEtable(@Param("TNO") int TNO);
}
