package com.trump.auction.pals.dao;

import org.springframework.stereotype.Repository;

import com.trump.auction.pals.domain.ThirdPartyAsk;


@Repository
public interface ThirdPartyAskDao {
	/**
	 * 发出请求
	 *
	 * @param orders
	 * @return
	 */
	public int insert(ThirdPartyAsk orders);

	/**
	 * 更新
	 *
	 * @param orders
	 * @return
	 */
	public int update(ThirdPartyAsk orders);

	/**
	 * 更新
	 *
	 * @param orders
	 * @return
	 */
	public int updateByOrderNo(ThirdPartyAsk orders);

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	public ThirdPartyAsk findById(Integer id);

	/**
	 * 查询
	 *
	 * @param orderNo
	 * @return
	 */
	public ThirdPartyAsk findByOrderNo(String orderNo);


	/**
	 * 通过表名发出请求
	 *
	 * @param orders
	 * @return
	 */
	public int insertByTableLastName(ThirdPartyAsk orders);
	/**
	 * 通过表名更新
	 * @param orders
	 * @return
	 */
	public int updateByTableLastName(ThirdPartyAsk orders);

	/**
	 * 更新
	 *
	 * @param orders
	 * @return
	 */
	public int updateByOrderNoByTableLastName(ThirdPartyAsk orders);

	/**
	 * 根据主键查询
	 *
	 * @param id
	 * @return
	 */
	public ThirdPartyAsk findByIdByTableLastName(Integer id,  String tablelastName);
	/**
	 * 查询
	 *
	 * @param orderNo
	 * @return
	 */
	public ThirdPartyAsk findByOrderNoByTableLastName( String orderNo, String tablelastName);


	/**
	 * 查询
	 *
	 * @param orderNo
	 * @return
	 */
	public ThirdPartyAsk findByAct(String orderNo);
}
