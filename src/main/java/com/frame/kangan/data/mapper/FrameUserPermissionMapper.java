package com.frame.kangan.data.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.frame.kangan.data.po.FrameUserPermission;

/**
 *
 * FrameUserPermission 表数据库控制层接口
 *
 */
public interface FrameUserPermissionMapper extends AutoMapper<FrameUserPermission> {

	List<String> getPermissionCodeSetByUserId(int userId);
}