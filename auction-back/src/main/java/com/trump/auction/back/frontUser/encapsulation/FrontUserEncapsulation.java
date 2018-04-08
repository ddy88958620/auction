package com.trump.auction.back.frontUser.encapsulation;

/**
 * @Author:limingwu
 * @Description: 用户封装类
 * @Date: Create in 10:23 2018/3/28
 * @Modified By:
 */
public class FrontUserEncapsulation {

    /**
     * 用户来源转换
     * @return
     */
    public static String userFromConversion(String from){
        String result = "";
        if(from.equals("1")){
            result = "当前平台";
        }else{
            result = "其他";
        }
        return result;
    }

    /**
     * 用户状态转换
     * @param status
     * @return
     */
    public static String statsuConversion(int status){
        String result = "";
        if(status == 1){
            result = "启用";
        }else if(status == 2){
            result = "注销";
        }else {
            result = "其他";
        }
        return result;
    }

    /**
     * 用户充值类型转换
     * @param type
     * @return
     */
    public static String rechargeTypeConversion(String type){
        String result = "";
        if(type.equals("1")){
            result = "未充值";
        }else if(type.equals("2")){
            result ="首充";
        }else if(type.equals("3")){
            result = "多次";
        }else if(type.equals("4")){
            result = "首冲反币等待中";
        }else if(type.equals("5")){
            result = "首冲反币成功";
        }else if(type.equals("6")){
            result = "首冲拍品成功";
        }else {
            result = "其他";
        }
        return result;
    }

}
