package com.yunling.commons.mocktest;

import java.lang.reflect.Field;

import org.easymock.EasyMock;

public class AutoMock {
	
	public static void initMocks(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields = 
			clazz.getDeclaredFields();
		for(Field field : fields) {
			Mock mock = field.getAnnotation(Mock.class);
			if (mock != null) {
				Object mockObj = EasyMock.createMock(field.getType());
				try {
					field.setAccessible(true);
					field.set(obj, mockObj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void resetMocks(Object target) {
		Class<?> clazz = target.getClass();
		Field[] fields = clazz.getFields();
		for(Field field : fields) {
			Mock mock = field.getAnnotation(Mock.class);
			if (mock != null) {
				try {
					Object mockObj = field.get(target);
					if (mockObj!=null) {
						EasyMock.reset(mockObj);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void replayMocks(Object target) {
		Class<?> clazz = target.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			Mock mock = field.getAnnotation(Mock.class);
			if (mock != null) {
				try {
					field.setAccessible(true);
					Object mockObj = field.get(target);
					if (mockObj!=null) {
						EasyMock.replay(mockObj);
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void verifyMocks(Object target) {
		Class<?> clazz = target.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			Mock mock = field.getAnnotation(Mock.class);
			if (mock != null) {
				try {
					field.setAccessible(true);
					Object mockObj = field.get(target);
					if (mockObj!=null) {
						EasyMock.verify(mockObj);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
