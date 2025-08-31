package vn.wl.mes.mapper.sap;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OworMapper {
	List<Object> getList();
}
