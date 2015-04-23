package cn.tj.ykt.financialoffice.fw.util;

import java.util.prefs.Preferences;

/**
 * <pre>
 * 功能描述：注册表信息处理类
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class PreferencesUtil {

    Preferences pref = Preferences.userRoot().node("/ykt/financialoffice");

    public String get(String key) {
        return pref.get(key, "");
    }

    public void put(String key, String value) {
        pref.put(key, value);
    }
}
