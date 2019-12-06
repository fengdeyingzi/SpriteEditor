/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  java.lang.Object
 *  java.lang.String
 */
package com.xl.game.tool;

import android.content.*;
import android.net.Uri;
import android.telephony.*;
import android.util.*;
import java.io.*;
import java.util.UUID;

import android.util.Log;
import android.os.Build;

public class Tool
{
	/*
	 * Exception decompiling
	 */
	public static int[][] ReadMap(Context context,String mapname)
	{
		DataInputStream input;
		int x,y;

		int[][] r2_int_A_A =null;

		try
		{
			//DataInputStream r12_DataInputStream = r9_DataInputStream;
			DataInputStream r10_DataInputStream = new DataInputStream(context.getResources().getAssets().open(mapname));
			input=r10_DataInputStream;

			r2_int_A_A= new int[input.readInt()][input.readInt()];
			/*
			r2_int_A_A=(int[][] )
			Array.newInstance
			(
			Integer.TYPE,
			new int[]
			{input.readInt(), input.readInt()}
			);
			*/
			Log.e("XL", ""+r2_int_A_A[0].length );

			for(y=0;y<r2_int_A_A.length;y++)
			{
				for(x=0;x<r2_int_A_A[y].length;x++)
				{
					r2_int_A_A[y][x]=input.readInt();
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return r2_int_A_A;
	}

	//获取imei码
	/*

	 */
	public static String getImei(Context context)
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(Build.VERSION.SDK_INT<=28)
			return tm.getDeviceId();
		else
			return getUUID();
		// activity.getSystemService(Context.TELEPHONY_SERVICE).getDeviceId();
//	String myIMSI=android.os.SystemProperties.get(android.telephony.TelephonyProperties.PROPERTY_IMSI);
	}

	public static String getUUID() {

		String serial = null;

		String m_szDevIDShort = "35" +
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

				Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

				Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

				Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

				Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

				Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

				Build.USER.length() % 10; //13 位

		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				serial = android.os.Build.getSerial();
			} else {
				serial = Build.SERIAL;
			}
			//API>=9 使用serial号
			return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
		} catch (Exception exception) {
			//serial需要一个初始化
			serial = "serial"; // 随便一个初始化
		}
		//使用硬件信息拼凑出来的15位号码
		return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
	}


	/*

	 */
	//获取imsi码
	public static String getImsi(Context context)
	{
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSubscriberId();

	}

	//设置剪切板内容
	public static void clipSet(Context context,CharSequence text)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText(text);
		}
		else
		{
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText(text);
		}
	}

	//获取剪切板内容
	public static CharSequence clipGet(Context context)
	{
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			return clipboardManager.getText();
			//StringBuffer text = new StringBuffer(getText());
		}
		else
		{
			android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		/*
			android.content.ClipData clipData = clipboardManager.getPrimaryClip();
			if (clipData != null && clipData.getItemCount() > 0)
			{
				return clipData.getItemAt(0).coerceToText(context);
				//StringBuffer text = new StringBuffer(getText());
			}
			*/
			return clipboardManager.getText();
		}
	}

}
