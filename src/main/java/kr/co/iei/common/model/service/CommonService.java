package kr.co.iei.common.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.common.model.dao.CommonDao;
import kr.co.iei.common.model.vo.Common;

@Service
public class CommonService {
	@Autowired
	private CommonDao commonDao;

	public ArrayList<Common> selectAllList() {
		System.out.println("서비스 호출됨");
		ArrayList<Common> list = commonDao.selectAllList();
		System.out.println(list);
		return list;
	}	
}
