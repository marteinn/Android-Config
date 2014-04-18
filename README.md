Android-Config
================

Android-Config helps to manage the different build settings of your app.

## How it works

Config loads and parses .properties files placed in the **res/raw** folder of your application (it loads a debug file if debuggable is true), then exposes a class with a set of getParam methods. It's built around the same principle as [Android-Features](https://github.com/marteinn/Android-Features).

## Implementation

First create a folder in your res folder named raw, then place two files there, one for the live build and one for the debug/dev (I usually name them config_app_debug.properties and config_app.properties)

Then open the files and specify your settings using the following format (we parse them with java.util.Properties).

    URL = http://marteinn.se
    AGE = 3600
    TRACKING = 1
    
(Integer values 1 and 0 can also be treated as booleans).


After that you need to include the utility and run createInstance when your app starts, I recommend you place it in application onCreate, like this:


    public class YourApplication extends Application {
        @Override
        public void onCreate() {
            Config.createInstance().load(this,
                R.raw.config_app_debug,
                R.raw.config_app);
        }
    }

You can also skip the validation part and load the configuration file directly.

    public class YourApplication extends Application {
        @Override
        public void onCreate() {
            Config.createInstance().load(this, R.raw.config_app_debug);
        }
    }




Then retrive the various settings with these methods:

	Config.getInstance().getParam("URL"); // http://marteinn.se
	Config.getInstance().getIntParam("AGE"); // 3600
	Config.getInstance().getBooleanParam("TRACKING"); // true
	
	
## Contributing

Want to contribute? Awesome. Just send a pull request.


## License

Android-Features is released under the [MIT License](http://www.opensource.org/licenses/MIT).