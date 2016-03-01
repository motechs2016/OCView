package org.blue.backend.util;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 抽取共性：modeldriven
 * @author ldc4
 * @param <T>
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
	
	private static final long serialVersionUID = 1L;
	
	
	protected T model;
	
	@SuppressWarnings("unchecked")
	public BaseAction(){
		//通过反射获得model的真实实例
		try {
			ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
			Class<T> clazz = (Class<T>)pt.getActualTypeArguments()[0];
			this.model = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public T getModel() {
		return model;
	}

}
