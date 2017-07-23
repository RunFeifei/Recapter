package com.fei.root.viewbinder;

import android.util.Log;
import android.view.View;

import com.fei.root.RecapterApp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by PengFeifei on 17-7-12.
 */

public class ViewBinder {

    private static final String TAG = ViewBinder.class.getSimpleName();
    private static Class<?> appRIdclass;

    static {
        try {
            appRIdclass = Class.forName(RecapterApp.getContext().getPackageName() + ".R$id");
        } catch (ClassNotFoundException classNotFoundException) {
            Log.e(TAG, classNotFoundException.getMessage());
        }
    }

    public static void bindViews(Object viewHolder, View rootView) {
        if (viewHolder == null || rootView == null) {
            throwException("viewHolder or rootView is null");
        }
        Class<?> viewHodlderClass = viewHolder.getClass();
        Field[] fields = viewHodlderClass.getDeclaredFields();
        for (Field field : fields) {
            if (field == null) {
                continue;
            }
            Binder binder = field.getAnnotation(Binder.class);
            if (binder == null) {
                continue;
            }
            if (!View.class.isAssignableFrom(field.getType())) {
                throwException(field.getName() + " not View");
            }
            int id = getFieldId(appRIdclass, field.getName(), binder.id());
            View view = findView(rootView, id);
            if (view == null) {
                throwException("invalid id");
            }
            field.setAccessible(true);
            try {
                field.set(viewHolder, view);
            } catch (IllegalAccessException e) {
                throwException(e.getMessage());
            }
            final Method method = getMatchMethod(viewHodlderClass, id);
            if (method == null) {
                continue;
            }
            method.setAccessible(true);
            view.setOnClickListener((v) -> {
                try {
                    method.invoke(viewHolder,v);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    private static int getFieldId(Class<?> moduleRIdclass, String name, int id) {
        if (id > 0) {
            return id;
        }
        Field field = null;
        try {
            field = moduleRIdclass.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throwException("can not find " + name + " in R.java,make sure filed & id has the same name");
        }
        field.setAccessible(true);
        try {
            //static变量不需要实例
            return field.getInt(null);
        } catch (IllegalAccessException e) {
            throwException(e.getMessage());
        }
        return 0;
    }

    private static Method getMatchMethod(Class<?> viewHodlderClass, int id) {
        Method[] methods = viewHodlderClass.getDeclaredMethods();
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick == null) {
                continue;
            }
            if (id != onClick.id()) {
                continue;
            }
            Class<?>[] methodClasses = method.getParameterTypes();
            if (methodClasses == null || methodClasses.length != 1) {
                return null;
            }
            if (!View.class.isAssignableFrom(methodClasses[0])) {
                return null;
            }
            return method;
        }
        return null;
    }

    private static void throwException(String str) throws RuntimeException {
        throw new RuntimeException(str);
    }

    private static <T> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }


}

