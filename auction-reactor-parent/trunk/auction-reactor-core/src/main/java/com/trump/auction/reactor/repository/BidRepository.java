package com.trump.auction.reactor.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.trump.auction.reactor.api.model.AuctionContext;
import com.trump.auction.reactor.api.model.BidRequest;
import com.trump.auction.reactor.api.model.BidResponse;
import com.trump.auction.reactor.api.model.Bidder;
import com.trump.auction.reactor.api.model.BidStatistics;
import com.trump.auction.reactor.api.model.MultiBid;

/**
 * 出价仓储
 * <p>
 * 提供出价相关的数据操作
 * </p>
 *
 * @author Owen
 * @since 2018/1/15
 */
public interface BidRepository {

	/**
	 * 获取待排队的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return {@link Map}
	 * 
	 *         <pre>
	 *         key
	 *         </pre>
	 * 
	 *         为 {@link Bidder#getId()}
	 * 
	 *         <pre>
	 *         val
	 *         </pre>
	 * 
	 *         为 {@link MultiBid}
	 */
	Map<String, MultiBid> getWaitBid(String auctionNo);

	/**
	 * 判断是否有待排队的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return <code>true</code> 表示有待排队的出价
	 */
	boolean hasWaitBid(String auctionNo);

	/**
	 * 判断出价人是否有待排队的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bidder
	 *            出价人
	 * @return <code>true</code> 表示有待排队的出价
	 */
	boolean isWaitBid(String auctionNo, Bidder bidder);

	/**
	 * 添加待排队的出价
	 * 
	 * @param multiBid
	 *            出价信息
	 */
	void putWaitBid(MultiBid multiBid);

	/**
	 * 批量添加待排队的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bids
	 *            出价信息
	 */
	void putWaitBid(String auctionNo, List<MultiBid> bids);

	/**
	 * 批量添加待排队的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bidMap
	 *            出价信息
	 */
	void putWaitBid(String auctionNo, Map<String, MultiBid> bidMap);

	/**
	 * 删除待排队的出价
	 * <p>
	 * 用户委托多次出价，次数已用完时调用
	 * </p>
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bids
	 *            出价信息
	 */
	void removeWaitBid(String auctionNo, List<MultiBid> bids);

	/**
	 * 向出价队列批量添加出价信息
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bids
	 *            出价信息
	 */
	void pushQueue(String auctionNo, List<BidRequest> bids);

	/**
	 * 向出价队列添加出价信息
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bid
	 *            出价信息
	 */
	void pushQueue(String auctionNo, BidRequest bid);

	/**
	 * 从出价队列头部取第一个出价信息
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return 出价信息
	 */
	Optional<BidRequest> popQueue(String auctionNo);

	/**
	 * 向出价队列添加出价信息
	 * <p>
	 * 向队列头部位置添加；用于委托出价失败时重新入队
	 * </p>
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param bidRequest
	 *            出价信息
	 */
	void rightPushQueue(String auctionNo, BidRequest bidRequest);

	/**
	 * 查看出价队列头部第一个出价信息
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return 出价信息
	 */
	Optional<BidRequest> peekQueue(String auctionNo);

	/**
	 * 判断出价队列是否为空
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return <code>true</code> 表示出价队列为空
	 */
	boolean isQueueEmpty(String auctionNo);

	/**
	 * 查看排队中的出价
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return 排队中的出价
	 */
	List<BidRequest> getBidQueue(String auctionNo);

	/**
	 * 查看是否在排队中
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return <code>true</code> 表示在排队出价中
	 */
	boolean isInQueue(String auctionNo, Bidder bidder);

	/**
	 * 保存竞拍信息的上下文
	 * 
	 * @param context
	 *            竞拍上下文
	 */
	void saveContext(AuctionContext context);

	/**
	 * 保存竞拍信息的上下文
	 * <p>
	 * 当上下文不存在时才会保存
	 * </p>
	 * 
	 * @param context
	 *            竞拍上下文
	 * @return <code>true</code> 表示保存成功
	 */
	Boolean saveContextIfAbsent(AuctionContext context);

	/**
	 * 获取竞拍信息上下文
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @return 竞拍信息上下文
	 */
	AuctionContext getContext(String auctionNo);

	/**
	 * 保存出价结果
	 * 
	 * @param bidResp
	 *            出价结果
	 */
	void saveBidResult(BidResponse bidResp);

	/**
	 * 从出价结果队列中取 <code>count</code> 个元素
	 * <p>
	 * 只查询元素，并不移除元素
	 * </p>
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param count
	 *            要取的元素个数
	 * @return 取出的元素信息
	 */
	List<BidResponse> getBidResult(String auctionNo, int count);

	/**
	 * 从出价结果队列中删除 <code>count</code> 个元素
	 * 
	 * @param auctionNo
	 *            竞拍编号
	 * @param count
	 *            要删除的元素个数
	 */
	void removeBidResult(String auctionNo, int count);

	/**
	 * 是否有出价信息
	 * 
	 * @param auctionNo
	 * @param userId
	 * @return
	 */
	boolean hasBidStatistics(String auctionNo,String userId);

	/**
	 * 获取出价详情
	 * 
	 * @param auctionNo
	 * @param userId
	 * @return
	 */
	BidStatistics getBidStatistics(String auctionNo, String userId);

	/**
	 * 存入出价
	 * 
	 * @param auctionNo
	 * @param bid
	 */
	void putBidStatistics(String auctionNo, BidStatistics bid);

}
