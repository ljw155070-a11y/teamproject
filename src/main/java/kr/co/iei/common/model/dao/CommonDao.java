package kr.co.iei.common.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import kr.co.iei.common.model.vo.Common;

@Mapper
public interface CommonDao {

	ArrayList<Common> selectAllList();

}
