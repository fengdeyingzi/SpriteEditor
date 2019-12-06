package com.xl.game.tool;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.xl.game.math.Str;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class FileUtils
{
	private static final String TAG = "FileUtils";
	  //获取文件base64码
		public static String encodeBase64File(String path) throws Exception {
			File file = new File(path);
			FileInputStream inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		}

		//将base64解码为文件
		public static void decoderBase64File(String base64Code, String savePath) throws Exception {
			//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
			byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
			FileOutputStream out = new FileOutputStream(savePath);
			out.write(buffer);
			out.flush();
			out.close();
		}
	
	/**
	 * 向文件中文本
	 * 
	 * @param info
	 */
	public static void writeText(String filename,String info) {
		File file = new File(filename);
		
		
		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (Exception e)
		{}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 向文件中文本
	 *
	 * @param info
	 */
	public static void writeText(String filename,String info,String coding) {
		File file = new File(filename);


		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (Exception e)
		{}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			fileOutputStream.write(info.getBytes(coding));
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//读取文本
	public static String readText(File file) throws FileNotFoundException, IOException
	{
		String content = null;
		if(file.isFile())
		{
			FileInputStream input= new FileInputStream(file);

			byte [] buf=new byte[input.available()];
			input.read(buf);
			content = new String(buf);
		}
		return content;
	}

	public static String readText(String path) throws IOException
	{
		return FileUtils.readText(new File(path));
	}

	
	//获取sd卡
	public static String getSDPath()
	{
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		if(sdCardExist)
		{
			sdDir=Environment.getExternalStorageDirectory();//获取sd卡目录
		}
		else 
		{
			return null;
		}
		return sdDir.getPath();
	}

	//获取文件后缀
	public static String getFileEndName(String path){
		int index = Str.strrchr(path,'.');
		if(index>0){
			return path.substring(index);
		}
		return null;
	}
	
	//拼接目录
	public static String getPath(String path,String name)
	{
		if(path.endsWith(File.separator))
		{
			return path+name;
		}
		return path+File.separator+name;
	}
	
	//获取文件名
	public static String getName(String filepath)
	{
		/*
		if(filepath==null)
			return null;
		return new File(filepath).getName();
		*/
		int i=0;
		int start = 0;
		int end=filepath.length();
		for( i=0;i<filepath.length();i++){
			char c = filepath.charAt(i);
			if(c=='/' || c=='\\'){
				start = i+1;
			}
		}
		for(int j=start;i<filepath.length();j++){
			char c = filepath.charAt(j);
			if(c==':' || c=='?' || c=='&' || c=='*' || c=='\"' || c=='<' || c=='>'){
				end=j;
				break;
			}
		}
		
		return filepath.substring(start,end);
	}

	//将路径解析到list
	public static ArrayList<String> splitPath(String path) {
		ArrayList<String> dirs = new ArrayList<>();
		int i = 0;
		int start = 0;
		int end = 0;
		int type = 0;
		int len = 0;
		String item = null;
		int itemsize = 0;
		char c = 0;
		dirs.clear();
		if (path == null) return dirs;
		len = path.length();
		while (i < len) {
			c = path.charAt(i);

			if (c == '/' || c == '\\') {
				if (itemsize > 0) {
					item = path.substring(start, i);
					itemsize = 0;
//					addDir(item);
					dirs.add(item);
				}
				start = i + 1;

			} else {
				itemsize++;
				if (i == len - 1) {
					item = path.substring(start);
					itemsize = 0;
//					addDir(item);
					dirs.add(item);
				}
			}

			i++;
		}
		return dirs;
	}

	public static String getFilePathByUri(Context context, Uri uri) {
		String path = null;
		// 以 file:// 开头的
		if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
			path = uri.getPath();
			return path;
		}
		// 以 content:// 开头的，比如 content://media/extenral/images/media/17766
		if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					if (columnIndex > -1) {
						path = cursor.getString(columnIndex);
					}
				}
				cursor.close();
			}
			return path;
		}
		// 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
		if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (DocumentsContract.isDocumentUri(context, uri)) {
				if (isExternalStorageDocument(uri)) {
					// ExternalStorageProvider
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];
					if ("primary".equalsIgnoreCase(type)) {
						path = Environment.getExternalStorageDirectory() + "/" + split[1];
						return path;
					}
				} else if (isDownloadsDocument(uri)) {
					// DownloadsProvider
					final String id = DocumentsContract.getDocumentId(uri);
					final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
							Long.valueOf(id));
					path = getDataColumn(context, contentUri, null, null);
					return path;
				} else if (isMediaDocument(uri)) {
					// MediaProvider
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];
					Uri contentUri = null;
					if ("image".equals(type)) {
						contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
					} else if ("video".equals(type)) {
						contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
					} else if ("audio".equals(type)) {
						contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
					}
					final String selection = "_id=?";
					final String[] selectionArgs = new String[]{split[1]};
					path = getDataColumn(context, contentUri, selection, selectionArgs);
					return path;
				}
			}
		}
		return null;
	}
	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {column};
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}
	private static boolean isMediaDocument(Uri uri) {  return "com.android.providers.media.documents".equals(uri.getAuthority());
	}


	//获取目录下所有的指定文件
	public static Collection<File> listFiles(File file, String[] miniType, boolean ischeck)
	{
		ArrayList<File> filelist = new ArrayList();
		Log.i(TAG, "listFiles: "+file);
		File[] files = file.listFiles();
		if(files==null){
			return filelist;
		}
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isFile())
			{
				for(String type:miniType)
					if(files[i].getPath().endsWith(type))
					{
						filelist.add(files[i]);
						break;
					}
			}
			else
			{
				Collection<File> filelist2 = listFiles(files[i],miniType,ischeck);
				for(File f:filelist2)
					filelist.add(f);
			}
		}
		return filelist;
	}
	
}
