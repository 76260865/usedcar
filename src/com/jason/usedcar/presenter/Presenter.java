package com.jason.usedcar.presenter;

import com.jason.usedcar.interfaces.Ui;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for Presenters.
 */
public abstract class Presenter<U extends Ui> {

    private U mUi;

    /**
     * Called after the UI view has been created. That is when
     * fragment.onViewCreated() is called.
     * 
     * @param ui
     *            The Ui implementation that is now ready to be used.
     */
    public void onUiReady(U ui) {
        mUi = ui;
    }

    /**
     * Called when the UI view is destroyed in Fragment.onDestroyView().
     */
    public final void onUiDestroy(U ui) {
        onUiUnready(ui);
        mUi = null;
    }

    /**
     * To be overriden by Presenter implementations. Called when the fragment is
     * being destroyed but before ui is set to null.
     */
    public void onUiUnready(U ui) {
    }

    public U getUi() {
        return mUi;
    }

    public static Map<String, String> object2Map(Object obj) {
        return object2Map(obj, false);
    }

    public static Map<String, String> object2Map(Object obj, boolean nullable) {
        Map<String, String> result = new HashMap<String, String>();
        Method[] methods = obj.getClass().getMethods();
        try {
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object value = method.invoke(obj, null);
                    if (!nullable && value == null) {
                        continue;
                    }
                    char[] fieldNameArray = methodName.substring(3).toCharArray();
                    fieldNameArray[0] = Character.toLowerCase(fieldNameArray[0]);
                    result.put(String.valueOf(fieldNameArray),
                        (value == null) ? "" : String.valueOf(value));
                }
            }
        } catch (InvocationTargetException e) {
            result.clear();
        } catch (IllegalAccessException e) {
            result.clear();
        }
        return result;
    }
}
