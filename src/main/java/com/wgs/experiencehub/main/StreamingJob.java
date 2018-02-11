package com.wgs.experiencehub.main;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.rabbitmq.client.QueueingConsumer;

import com.wgs.experiencehub.model.EntUserFeedbackModel;
import com.wgs.experiencehub.service.FeedbackService;

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

public class StreamingJob {

    private static final FeedbackService feedback = new FeedbackService();

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
        System.out.println("================================ Transforming StreamingJob data into EntUserFeedbackModel DStream");
        JavaDStream<EntUserFeedbackModel> message = messages.map(new Function<String, EntUserFeedbackModel>() {
            @Override
            public EntUserFeedbackModel call(String s) throws Exception {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                return mapper.readValue(s, EntUserFeedbackModel.class);
            }
        });

        message.foreachRDD(new VoidFunction<JavaRDD<EntUserFeedbackModel>>() {

            @Override
            public void call(JavaRDD<EntUserFeedbackModel> userFeedback) throws Exception {
                userFeedback.foreach(new VoidFunction<EntUserFeedbackModel>() {
                    @Override
                    public void call(EntUserFeedbackModel feedback) throws Exception {
                        StreamingJob.feedback.processStream(feedback);
                    }
                });
            }
        });

        messages.print();
        streamCtx.start();
        streamCtx.awaitTermination();
    }
}