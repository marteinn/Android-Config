package se.marteinn.utils.config.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;

public class Config {
    public static final String TAG = Config.class.toString();

    // Members
    private Properties mProperties;
    private static Config sInstance;

    static public Config getInstance() {
        return sInstance;
    }

    /**
     * Creates singleton class instance.
     * @param context
     * @param debugResource
     * @param liveResource
     * @return
     */
    static public Config createInstance() {
        sInstance = new Config();
        return sInstance;
    }

    /**
     * Loads and parses config from resource, uses debug resource if debuggable.
     * @param context
     * @param debugResource
     * @param liveResource
     * @return
     */
    public void load(Context context,  int debugResource,
            int liveResource) {

        int resourceId = 0;

        // Check either live or debug resource
        resourceId = ! isDebuggable(context) ? liveResource : debugResource;
        load(context, resourceId);
    }

    /**
     * Loads and parses config from resource.
     * is on.
     * @param context
     * @param debugResource
     * @param liveResource
     * @return
     */
    public void load(Context context, int resourceId) {
        Properties parsedProperties = null;

        // Load and parse resource
        try {
            InputStream rawResource;
            rawResource = context.getResources().openRawResource(resourceId);

            parsedProperties = new Properties();
            parsedProperties.load(rawResource);
        } catch (NotFoundException e) {
            System.err.println("Could not find iconfig resource: "+e);
        } catch (IOException e) {
            System.err.println("Failed to parse config file: "+e);
        }

        mProperties = parsedProperties;
    }

    /**
     * Checks if param exist.
     * @param name
     * @return
     */
    public Boolean hasParam(String name) {
        return mProperties.containsKey(name);
    }

    /**
     * Get parameter.
     * @param name
     * @return
     */
    public String getParam(String name) {
        return (String) mProperties.get(name);
    }

    /**
     * Get parameter as int.
     * @param name
     * @return
     */
    public int getIntParam(String name) {
        return Integer.parseInt(getParam(name));
    }

    /**
     * Get parameter as int.
     * @param name
     * @return
     */
    public Boolean getBooleanParam(String name) {
        return getIntParam(name) == 1;
    }

    /**
     * Check if app is running with under debug.
     * Inspiration/code from: http://bit.ly/1gmLMm3
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;
        PackageManager pm = context.getPackageManager();

        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(
                    context.getPackageName(), 0);

            debuggable = (0 != (appinfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        } catch(NameNotFoundException e) {
            /*debuggable variable will remain false*/
        }

        return debuggable;
    }
}
