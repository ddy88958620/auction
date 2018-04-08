package com.trump.auction.back.push.utils;

import com.trump.auction.back.push.model.NotificationRecord;
import com.trump.auction.back.push.utils.android.*;
import com.trump.auction.back.push.utils.ios.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class UmengPushUtil {

	/**
	 * 全量推
	 * @param obj
	 * @throws Exception
	 */
	public static void sendBroadcast(NotificationRecord obj) throws Exception {
		new UmengPushUtil(UmKeys.APPKEY_IOS,UmKeys.APPMASTERSECRET_IOS).sendIOSBroadcast(obj);
		new UmengPushUtil(UmKeys.APPKEY_ANDROID,UmKeys.APPMASTERSECRET_ANDROID).sendAndroidBroadcast(obj);
	}

	/**
	 * 单个推
	 * @param obj
	 * @param deviceToken
	 * @throws Exception
	 */
	public static void sendUnicast(NotificationRecord obj, String deviceToken) throws Exception {
		new UmengPushUtil(UmKeys.APPKEY_IOS,UmKeys.APPMASTERSECRET_IOS).sendIOSUnicast(obj,deviceToken);
		new UmengPushUtil(UmKeys.APPKEY_ANDROID,UmKeys.APPMASTERSECRET_ANDROID).sendAndroidUnicast(obj,deviceToken);
	}


	private String appkey = null;
	private String appMasterSecret = null;
	private String timestamp = null;
	private PushClient client = new PushClient();
	
	public UmengPushUtil(String key, String secret) {
		try {
			appkey = key;
			appMasterSecret = secret;
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(1);
		}
	}
	
	public void sendAndroidBroadcast(NotificationRecord obj) throws Exception {
		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		broadcast.setTicker(obj.getTitle());
		broadcast.setTitle(obj.getTitle());
		broadcast.setText(obj.getContent());
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
		// Set customized fields

		if (obj.getTimeType() == 2 && obj.getSendTime() != null) {
			String sendTime = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(obj.getSendTime());
			broadcast.setStartTime(sendTime);
		}

		broadcast.setExtraField("notiType", obj.getNotiType()+"");
		switch (obj.getNotiType()){
			case 1:{broadcast.setExtraField("url", obj.getUrl());}break;
			case 2:{broadcast.setExtraField("activityId", obj.getActivityId()+"");}break;
			case 3:{broadcast.setExtraField("productId", obj.getProductId()+"");}break;
		}
		client.send(broadcast);
	}
	
	public void sendAndroidUnicast(NotificationRecord obj, String deviceToken) throws Exception {
		AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken(deviceToken);
		unicast.setTicker(obj.getTitle());
		unicast.setTitle(obj.getTitle());
		unicast.setText(obj.getContent());
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		unicast.setProductionMode();
		// Set customized fields
		if (obj.getTimeType() == 2 && obj.getSendTime() != null) {
			String sendTime = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(obj.getSendTime());
			unicast.setStartTime(sendTime);
		}
		unicast.setExtraField("notiType", obj.getNotiType()+"");
		switch (obj.getNotiType()){
			case 1:{unicast.setExtraField("url", obj.getUrl());}break;
			case 2:{unicast.setExtraField("activityId", obj.getActivityId()+"");}break;
			case 3:{unicast.setExtraField("productId", obj.getProductId()+"");}break;
			default:break;
		}
		client.send(unicast);
	}
	
	public void sendAndroidGroupcast() throws Exception {
		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}
	
	public void sendAndroidCustomizedcast() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidCustomizedcastFile() throws Exception {
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
		customizedcast.setFileId(fileId, "alias_type");
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "中文的title");
		customizedcast.setText(   "Android customizedcast text");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public void sendAndroidFilecast() throws Exception {
		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setTicker( "Android filecast ticker");
		filecast.setTitle(  "中文的title");
		filecast.setText(   "Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}
	
	public void sendIOSBroadcast(NotificationRecord obj) throws Exception {
		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);

		broadcast.setAlert(obj.getContent());
		broadcast.setBadge(1);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setTestMode();
		// Set customized fields
		if (obj.getTimeType() == 2 && obj.getSendTime() != null) {
			String sendTime = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(obj.getSendTime());
			broadcast.setStartTime(sendTime);
		}
		broadcast.setCustomizedField("notiType", obj.getNotiType()+"");
		switch (obj.getNotiType()){
			case 1:{broadcast.setCustomizedField("url", obj.getUrl());}break;
			case 2:{broadcast.setCustomizedField("activityId", obj.getActivityId()+"");}break;
			case 3:{broadcast.setCustomizedField("productId", obj.getProductId()+"");}break;
		}
		client.send(broadcast);
	}
	
	public void sendIOSUnicast(NotificationRecord obj, String deviceToken) throws Exception {
		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		// TODO Set your device token
		unicast.setDeviceToken( deviceToken);
		unicast.setAlert(obj.getContent());
		unicast.setBadge( 1);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		if (obj.getTimeType() == 2 && obj.getSendTime() != null) {
			String sendTime = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").format(obj.getSendTime());
			unicast.setStartTime(sendTime);
		}
		unicast.setCustomizedField("notiType", obj.getNotiType()+"");
		switch (obj.getNotiType()){
			case 1:{unicast.setCustomizedField("url", obj.getUrl());}break;
			case 2:{unicast.setCustomizedField("activityId", obj.getActivityId()+"");}break;
			case 3:{unicast.setCustomizedField("productId", obj.getProductId()+"");}break;
			default:break;
		}
		client.send(unicast);
	}
	
	public void sendIOSGroupcast() throws Exception {
		IOSGroupcast groupcast = new IOSGroupcast(appkey,appMasterSecret);
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge( 0);
		groupcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		groupcast.setTestMode();
		client.send(groupcast);
	}
	
	public void sendIOSCustomizedcast() throws Exception {
		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey,appMasterSecret);
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
	public void sendIOSFilecast() throws Exception {
		IOSFilecast filecast = new IOSFilecast(appkey,appMasterSecret);
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge( 0);
		filecast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		filecast.setTestMode();
		client.send(filecast);
	}
	
	public static void main(String[] args) {
		String deviceToken = "AmSrUKAk4-ecbnNXagLJ1h5lxlWxAkiZZGefVaqof-o6";
		try {
			NotificationRecord obj = new NotificationRecord();
			obj.setTitle("11111");
			obj.setContent("22233");
			obj.setNotiType(1);
			obj.setUrl("http://www.baidu.com");
			//sendUnicast(obj,deviceToken);
			sendBroadcast(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
