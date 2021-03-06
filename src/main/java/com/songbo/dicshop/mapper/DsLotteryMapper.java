package com.songbo.dicshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songbo.dicshop.entity.DsLottery;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author songbo
 * @since 2020-03-06
 */
public interface DsLotteryMapper extends BaseMapper<DsLottery> {
    List<DsLottery> getListByStatus(int status);
}
