package com.wgs.experiencehub.util;

import com.typesafe.config.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zer0 , The Maverick Hunter
 * On 16/03/17 - 17:04
 */
public class ApplicationUtil {

    private static Config conf;

    public static Map<String, Integer> getTopicKafka(String kafka_topic) {
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topics = kafka_topic.split(",");
        for (String topic : topics) {
            topicMap.put(topic, 1);
        }
        return topicMap;
    }

    public static Config getConfig() {

        if (conf == null) {
            conf = ConfigFactory.load();
        }

        return conf;
    }
}
