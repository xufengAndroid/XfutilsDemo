package com.nj.xufeng.xfutils.utils;

import java.io.IOException;






import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
* @Title: JsonTool.java 
* @Package com.xufeng.xflibrary.tool 
* @Description: TODO(Json) 
* @author 徐峰004245  (QQ：284535970)
* @version V1.0
 */
public class JacksonUtils {
	
	private JacksonUtils(){}
	
	
	public static <T>T ObjToClasz(Object fromValue, Class<T> toValueType){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			return mapper.convertValue(fromValue, toValueType);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json转对像
	 * @param json
	 * @param clzss
	 * @return
	 */
	public static <T>T jsonToObj(String json,Class<T> clzss){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			T obj = mapper.readValue(json,clzss);
			return obj;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对像转json串
	 * @param value
	 * @return
	 */
	public static String objToJson(Object value){
		String str = "";
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		try {
			str = mapper.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}