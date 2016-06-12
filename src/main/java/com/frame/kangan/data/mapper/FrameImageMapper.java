package com.frame.kangan.data.mapper;

import com.frame.kangan.data.po.FrameImage;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * FrameImage 表数据库控制层接口
 *
 */
@Mapper
public interface FrameImageMapper extends AutoMapper<FrameImage> {
	FrameImage selectTest();

}