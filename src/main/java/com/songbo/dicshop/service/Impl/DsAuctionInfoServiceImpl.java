package com.songbo.dicshop.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.songbo.dicshop.bean.auction.AuctionResult;
import com.songbo.dicshop.bean.auction.DsAuctionResult;
import com.songbo.dicshop.entity.DsAddr;
import com.songbo.dicshop.entity.DsAuction;
import com.songbo.dicshop.entity.DsAuctionInfo;
import com.songbo.dicshop.mapper.DsAddrMapper;
import com.songbo.dicshop.mapper.DsAuctionInfoMapper;
import com.songbo.dicshop.mapper.DsAuctionMapper;
import com.songbo.dicshop.service.DsAuctionInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author songbo
 * @since 2020-03-16
 */
@Service
public class DsAuctionInfoServiceImpl extends ServiceImpl<DsAuctionInfoMapper, DsAuctionInfo> implements DsAuctionInfoService {
    @Resource
    private DsAuctionInfoMapper dsAuctionInfoMapper;
    @Resource
    private DsAuctionMapper dsAuctionMapper;
    @Resource
    private DsAddrMapper dsAddrMapper;

    @Override
    public void addAuction(DsAuctionInfo dsAuctionInfo) throws Exception {
        //扣积分
        DsAddr byUserId = dsAddrMapper.getByUserId(dsAuctionInfo.getDsAuctionInfoUserId());
        double balance = byUserId.getDsAddrBalance()- dsAuctionInfo.getDsAuctionInfoPrice();
        if (balance > 0) {
            byUserId.setDsAddrBalance(balance);
            dsAddrMapper.updateById(byUserId);
        } else {
            throw new Exception("扣除积分失败");
        }
        dsAuctionInfoMapper.insert(dsAuctionInfo);
    }

    @Override
    public List<DsAuctionResult> getListByAuctionId(int id) {
       return dsAuctionInfoMapper.getDsAuctionResultListByAid(id);

    }

    @Override
    public Double getMaxNow(int auctionId) {
        Double maxPrice = dsAuctionInfoMapper.getMaxPrice(auctionId);
        if (maxPrice == null) {
            maxPrice = dsAuctionMapper.selectById(auctionId).getDsAuctionPrice();
        }
        return maxPrice;
    }

    @Override
    public List<DsAuctionResult> getListByAuctionIdAndUserId(int aid, int uid) {
        return dsAuctionInfoMapper.getDsAuctionResultListByAidAndUid(aid, uid);
    }
}
