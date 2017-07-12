package com.fei.root.recapter;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

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
            field.setAccessible(true);
            try {
                field.set(viewHolder, view);
            } catch (IllegalAccessException e) {
                throwException(e.getMessage());
            }
        }
    }

    private static Integer getFieldId(Class<?> moduleRIdclass, String name, int id) {
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

    private static void throwException(String str) throws RuntimeException {
        throw new RuntimeException(str);
    }

    private static <T> T findView(View view, int id) {
        return (T) view.findViewById(id);
    }


}
