package com.xl.game.tool;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 影子封装的网络请求库 已过时
 * 采用OConnect代替
 */
public class XConnect extends Thread {
    private String url;
    //private String param;
    PostGetInfoListener listener;
    public static boolean isShowConnect=true;
    private final static int CONNENT_TIMEOUT = 2500;
    private final static int READ_TIMEOUT = 13000;
    private static String ua= "Dalvik/1.6.0 (Linux; U; Android 4.4.4; MI 4LTE MIUI/V6.6.2.0.KXDCNCF)";
	private HashMap<String,String> fileMap;
	private HashMap<String,String> postMap;
	private HashMap<String,String> getMap;
	private HashMap<String,String> headMap;
    Handler handler;
    static String TAG = "XConnect";
    private int min_time=200;








    public void run()
    {
        long start_time= System.currentTimeMillis();
        String text = null;


        text = postFileByForm(getUrl(), postMap, fileMap);


        if(start_time-System.currentTimeMillis()<min_time){
            try {
                Thread.sleep(min_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Message m=new Message();
        m.what=1;
        m.obj=text;

        if(isShowConnect){
            String type = "GET";
            if(postMap.isEmpty()){
                type = "GET:"+getUrl();
            }
            else{
                type = "POST:"+getUrl()+" Body:"+postInfo();
            }

            Log.i(TAG,type+" -> \n"+text);
        }

        handler.sendMessage(m);
    }
    //创建post请求
    public XConnect(String url, String param, final PostGetInfoListener listener)
    {
        super();
		this.postMap = new HashMap<String,String>();
		this.fileMap = new HashMap<String,String>();
		this.getMap = new HashMap<String,String>();
		this.headMap = new HashMap<>();
        this.url=url;
        //this.param=param;
        this.listener = listener;
        if(param!=null) {
            String list[] = param.split("&");
            for (String item : list) {
                String ii[] = item.split("=");
                if (ii.length == 2)
                    postMap.put(ii[0], ii[1]);
            }
        }
        this.handler=new Handler(Looper.getMainLooper())
        {
            public void handleMessage(Message msg)
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
    public XConnect(String url, PostGetInfoListener listener)
    {
        this(url,null,listener);
    }

	//添加post文件
	public void addPostFile(String name,String fileName){
		fileMap.put(name, fileName);
	}

	//添加post参数
	public void addPostParmeter(String name,String value){
		postMap.put(name,value);
	}

	public void addPostParmeter(String name,int value){
        postMap.put(name,""+value);
    }

    public void addHeader(String name,String value){
        headMap.put(name,value);
    }

	//
    public  String getUrl() {
		if(url.indexOf('?')>0){
			return url + "&"+getInfo();
		}
		else
			return url + "?" + getInfo();
    }

    public void setMinTime(int min_time){
        this.min_time= min_time;
    }

	//
	public void addGetParmeter(String name,String value){
		getMap.put(name,value);
	}

	public void addGetParmeter(String name,int value){
        getMap.put(name,""+value);
    }

	//获取get内容
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        Iterator iter = getMap.entrySet().iterator();
        boolean isStart = true;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            try {
                builder.append(key + "=" + URLEncoder.encode(val, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
            isStart = false;
            if (!isStart) {
                builder.append("&");
            }


        }
        String re = builder.toString();
        if(re.length()!=0)
			re = re.substring(0, re.length() - 1);
        return re;
    }

	//获取post
    public String postInfo() {
        StringBuilder builder = new StringBuilder();
        Iterator iter = postMap.entrySet().iterator();
        boolean isStart = true;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            /*
            try {
                builder.append(key + "=" + URLEncoder.encode(val, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            */
            //xldebug 后台不能解码。。故修改

            builder.append(key+"="+val);
            isStart = false;
            if (!isStart) {
                builder.append("&");
            }


        }
        String re = builder.toString();
        re = re.substring(0, re.length() - 1);
        return re;
    }




    static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session)
        {
            return true;
        }
    };
    /*
     *
     * @function trustAllHosts
     * @Description 信任所有主机-对于任何证书都不做检查
     */
    public static void trustAllHosts()
    {
        TrustManager[] arrayOfTrustManager = new TrustManager[1];
        //实现自己的信任管理器类
        arrayOfTrustManager[0] = new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException
            {
                // TODO Auto-generated method stub

            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException
            {
                // TODO Auto-generated method stub

            }
            @Override
            public X509Certificate[] getAcceptedIssuers()
            {
                // TODO Auto-generated method stub
                return new X509Certificate[0];
            }

        };
        try
        {
            SSLContext localSSLContext = SSLContext.getInstance("TLS");
            localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());
            return;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
        }
    }




	
	/**
     * 
     * @param urlStr  url地址
     * @param postMap 附带信息
     * @param fileMap 文件列表   
     * @return 返回json的报文 如果失败，则为空
     */

	public String postFileByForm(String urlStr, Map<String, String> postMap, Map<String, String> fileMap){
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------WebKitFormBoundaryvQdJRYhxZtA2ZkYN"; //boundary就是request头和上传文件内容的分隔符  
        try {  
            URL url = new URL(urlStr);
            // 判断是http请求还是https请求
            if (url.getProtocol().toLowerCase().equals("https"))
            {
                trustAllHosts();
                conn = (HttpsURLConnection) url.openConnection();
                ((HttpsURLConnection) conn).setHostnameVerifier(DO_NOT_VERIFY);// 对所有主机都进行确认
            }
            else
            {
                conn = (HttpURLConnection) url.openConnection();
            }

            conn.setConnectTimeout(CONNENT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", ua);
//			if(BaseConfig.token!=null)
//				conn.setRequestProperty("token", BaseConfig.token);
            Iterator iter1 = headMap.entrySet().iterator();
            while (iter1.hasNext()) {
                Map.Entry entry = (Map.Entry) iter1.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if(value!=null){
                    conn.setRequestProperty(key,value);
                }
            }
            // text  


            // file
            if(fileMap.isEmpty()){
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                if(postMap.isEmpty()){
                    conn.setRequestMethod("GET");
//                    Log.i(TAG, "postFileByForm: "+"get");
                    conn.setDoInput(true);
                }
                else{
                    conn.setRequestMethod("POST");
//                    Log.i(TAG, "postFileByForm: "+"post");
                    //写入post数据
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    String postinfo = postInfo();
                    out.write(postinfo.getBytes("UTF-8"));
                    out.flush();
                    out.close();
                }

            }
            else{
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());

                if (postMap != null) {
                    StringBuffer strBuf = new StringBuffer();
                    Iterator iter = postMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        String inputName = (String) entry.getKey();
                        String inputValue = (String) entry.getValue();
                        if (inputValue == null) {
                            continue;
                        }
                        strBuf.append("\r\n").append("--").append(BOUNDARY).append(
                                "\r\n");
                        strBuf.append("Content-Disposition: form-data; name=\""
                                + inputName + "\"\r\n\r\n");
                        strBuf.append(inputValue);
                    }
                    out.write(strBuf.toString().getBytes("UTF-8"));
                }

                Iterator iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
                    String contentType = null;                    
                    if (contentType == null || contentType.equals("")) {  
                        contentType = "application/octet-stream";  
                    }  

                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
						"\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\""  
								  + inputName + "\"; filename=\"" + filename  
								  + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  

                    out.write(strBuf.toString().getBytes("UTF-8"));

                    DataInputStream in = new DataInputStream(  
						new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }
                byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
                out.write(endData);
                out.flush();
                out.close();
            }

            // 读取返回数据  
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = null;

            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {             
			//日志处理
            res = "";
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  

        return res;  
    }  

    /*
    监听器
    */
    public interface PostGetInfoListener
    {
        public void onPostGetText(String text);
    }




}
