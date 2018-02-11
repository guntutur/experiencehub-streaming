package com.wgs.experiencehub.main;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.QueueingConsumer;
import com.wgs.experiencehub.model.EntUserPlayMusicModel;
import com.wgs.experiencehub.service.MusicService;
import com.wgs.experiencehub.util.ApplicationUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.rabbitmq.RabbitMQUtils;

import java.util.HashMap;
import java.util.Map;

public class StreamingMusicJob {

    private static final MusicService music = new MusicService();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("================================ Creating Spark Configuration");
        SparkConf conf = new SparkConf()
                .setAppName(ApplicationUtil.getConfig().getString("spark.app-name"))
                .setMaster(ApplicationUtil.getConfig().getString("spark.master"));

        System.out.println("================================ Creating Streaming Context Configuration");
        JavaStreamingContext streamCtx = new JavaStreamingContext(conf, Durations.seconds(2));

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.OFF);  // ERROR, WARNING, DEBUG, INFO

        System.out.println("================================ Setting Up RabbitMq parameters");
        Map<String, String> rabbitMqConParams = new HashMap<>();
        rabbitMqConParams.put("host", ApplicationUtil.getConfig().getString("rabbitmq.host"));
        rabbitMqConParams.put("queueName", ApplicationUtil.getConfig().getString("rabbitmq.queue.local"));

        // rabbitMqConParams.put("exchangeName", "rabbitmq-exchange"); // don't know when to use this because without this, program is still running
        // rabbitMqConParams.put("vHost", "/"); // permission granted for this user

        rabbitMqConParams.put("userName", ApplicationUtil.getConfig().getString("rabbitmq.user"));
        rabbitMqConParams.put("password", ApplicationUtil.getConfig().getString("rabbitmq.password"));

        Function<QueueingConsumer.Delivery, String> messageHandler = new Function<QueueingConsumer.Delivery, String>() {
            public String call(QueueingConsumer.Delivery message) {
                return new String(message.getBody());
            }
        };

        System.out.println("================================ Trying to connect to RabbitMq");
        JavaReceiverInputDStream<String> messages = RabbitMQUtils.createJavaStream(
                streamCtx,
                String.class,
                rabbitMqConParams,
                messageHandler
        );

        System.out.println("================================ Retrieving Streaming Context from Spark Conf");
        System.out.println("================================ Transforming stream to EntUserPlayMusicModel DStream");
        JavaDStream<EntUserPlayMusicModel> message = messages.map(new Function<String, EntUserPlayMusicModel>() {
            @Override
            public EntUserPlayMusicModel call(String s) throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                return mapper.readValue(s, EntUserPlayMusicModel.class);
            }
        });

        message.foreachRDD(new VoidFunction<JavaRDD<EntUserPlayMusicModel>>() {

            @Override
            public void call(JavaRDD<EntUserPlayMusicModel> playMusic) throws Exception {
                playMusic.foreach(new VoidFunction<EntUserPlayMusicModel>() {
                    @Override
                    public void call(EntUserPlayMusicModel musicModel) throws Exception {
                        if (music.checkActivePackage(
                                ApplicationUtil.getConfig().getString("api.music.check-package.url"),
                                ApplicationUtil.getConfig().getString("api.music.check-package.method"),
                                musicModel.getUserId(),
                                musicModel.getSource())) {
                            music.processMusicStream(musicModel);
                        }
                    }
                });
            }
        });

        messages.print();
        streamCtx.start();
        streamCtx.awaitTermination();
    }
}