package com.trump.auction.cust.api;


public interface SendSmsStubService {

    /**
     * 根据用户ID，发送短信
     * <p>
     * userId 用户Id，userPhone 用户手机号，content 短信内容<br>
     *
     * @return true and false
     */
    Boolean sendSmsByUserId(Integer userId, String phone, String content);

    /**
     * 根据用户手机号userPhone 发送短信
     *
     * @param userPhone 用户手机号，content 短信内容<br>
     * @return true and false
     */
    Boolean sendSmsByUserPhone(String userPhone, String content);

    /**
     * 根据用户手机号userPhone 发送短信(针对营销群发)
     *
     * @param userPhone 用户手机号，content 短信内容<br>
     * @return true and false
     */
    String groupSendSmsByUserPhone(String userPhone ,String content);

    /**
     * 根据用户ID 发送短信
     *
     * @param id 用户id，content 短信内容<br>
     * @return true and false
     */
    Boolean sendSmsByUserId(Integer id, String content);


    /**
     * 根据用户id,获取appTitle
     *
     * @param userId
     * @return
     */

    String getAppTitileByUserId(Integer userId);

    /**
     * 根据用户userPhone,获取appTitle
     *
     * @param userPhone
     * @return
     */

    String getAppTitileByUserPhone(String userPhone);

}
