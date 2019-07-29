package com.xl.tool;
import com.xl.game.tool.*;
import android.os.*;
/*
网络get post请求封装
*/

public class PostGetInfo extends Thread
{
	private String url;
	private String param;
	PostGetInfoListener listener;
	Handler handler;
	public void run()
	{
		String text = null;
		if(url.startsWith("https://"))
		{
			if(param!=null)
				text = HttpUtil.HttpsPost(url,param,null);
			else
				text = HttpUtil.HttpsPost(url,null,null);
		}
		else
		{
			if(param!=null)
				text = HttpUtil.post(url,param,null);
			else
				text = HttpUtil.get(url);
		}

		Message m=new Message();
		m.what=1;
		m.obj=text;
		handler.sendMessage(m);
	}
	//创建post请求
	public PostGetInfo(String url,String param,final PostGetInfoListener listener)
	{
		super();
		this.url=url;
		this.param=param;
		this.listener = listener;
		this.handler=new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				if(msg.what==1)
				{
					if(listener!=null)
						listener.onPostGetText((String)msg.obj);
				}
			}
		};
	}

	//创建get请求
	public PostGetInfo(String url,PostGetInfoListener listener)
	{
		this(url,null,listener);
	}

	/*
	监听器
	*/
	public interface PostGetInfoListener
	{
		public void onPostGetText(String text);
	}

}
