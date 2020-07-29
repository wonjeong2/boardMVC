package com.springbook.board.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	public int join(UserVO param);
}
