package com.wgs.experiencehub.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by zer0 , The Maverick Hunter
 * On 16/03/17 - 17:04
 */
public class ApplicationUtil {

    private static Config conf;

    public static Config getConfig() {

        if (conf == null) {
            conf = ConfigFactory.load();
        }

        return conf;
    }
}
