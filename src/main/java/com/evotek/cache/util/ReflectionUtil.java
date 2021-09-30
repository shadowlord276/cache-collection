/**
 * 
 */
package com.evotek.cache.util;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LinhLH
 *
 */
@Slf4j
public class ReflectionUtil {
	public static boolean hasProperty(Class<?> c, String name) {
		try {
			Field[] fields = c.getDeclaredFields();

			for (Field field : fields) {
				if (field.getName().equalsIgnoreCase(name)) {
					return true;
				}
			}
		} catch (Exception e) {
			_log.error("Error get field: ", e);
		}

		return false;
	}
	
	public static Object getFieldValue(Object ob, String name) {
		try {
			Field field = ob.getClass().getDeclaredField(name);

			field.setAccessible(true);

			return field.get(ob);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			_log.error("Cannot get field value {} ", name);
		}

		return null;
	}
}
