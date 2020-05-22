package com.zhhome.xunjian.utils;


import android.content.Context;
import android.widget.Toast;

public class AlertUtils {
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	public static final int[] MovieRecorderView = {
			0x7f010000, 0x7f010001, 0x7f010002, 0x7f010003
	};
	/**
	 <p>
	 @attr description
	 视频分辨率高度


	 <p>Must be an integer value, such as "<code>100</code>".
	 <p>This may also be a reference to a resource (in the form
	 "<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
	 theme attribute (in the form
	 "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
	 containing a value of this type.
	 <p>This is a private symbol.
	 @attr name com.example.wechatvideorecorddemo:height
	 */
	public static final int MovieRecorderView_height = 3;
	/**
	 <p>
	 @attr description
	 开始是否打开摄像头


	 <p>Must be a boolean value, either "<code>true</code>" or "<code>false</code>".
	 <p>This may also be a reference to a resource (in the form
	 "<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
	 theme attribute (in the form
	 "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
	 containing a value of this type.
	 <p>This is a private symbol.
	 @attr name com.example.wechatvideorecorddemo:is_open_camera
	 */
	public static final int MovieRecorderView_is_open_camera = 0;
	/**
	 <p>
	 @attr description
	 一次拍摄最长时间


	 <p>Must be an integer value, such as "<code>100</code>".
	 <p>This may also be a reference to a resource (in the form
	 "<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
	 theme attribute (in the form
	 "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
	 containing a value of this type.
	 <p>This is a private symbol.
	 @attr name com.example.wechatvideorecorddemo:record_max_time
	 */
	public static final int MovieRecorderView_record_max_time = 1;
	/**
	 <p>
	 @attr description
	 视频分辨率宽度


	 <p>Must be an integer value, such as "<code>100</code>".
	 <p>This may also be a reference to a resource (in the form
	 "<code>@[<i>package</i>:]<i>type</i>:<i>name</i></code>") or
	 theme attribute (in the form
	 "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>")
	 containing a value of this type.
	 <p>This is a private symbol.
	 @attr name com.example.wechatvideorecorddemo:width
	 */
	public static final int MovieRecorderView_width = 2;

}
