//package com.wgs.experiencehub.arc;
//
//import com.wgs.experiencehub.model.ObjectModel;
//import com.wgs.experiencehub.model.ObjectRoutingModel;
//import com.wgs.experiencehub.service.ObjectRoutingService;
//import com.wgs.experiencehub.service.ObjectService;
//import kafka.serializer.StringDecoder;
//import com.wgs.experiencehub.model.*;
//import com.wgs.experiencehub.service.*;
//import com.fasterxml.jackson.databind.MapperFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.VoidFunction;
//import org.apache.spark.streaming.Duration;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaPairInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka.KafkaUtils;
//import scala.Tuple2;
//
//import java.io.IOException;
//import java.util.*;
//
//public class ArangoKafkaStreamingJob {
//
//	private static final ObjectService objectService = new ObjectService();
//	private static final ObjectRoutingService objectRoutingService = new ObjectRoutingService();
//
//	public static void main(String[] args) throws InterruptedException, IOException {
//
////		if (args.length < 1) {
////			System.out.println("specify kafka topic to consume from!");
////			System.exit(1);
////		}
//
//		String top = "galaxy_result_object";
//
//		SparkConf sparkConf = new SparkConf()
//				.setAppName("test")
//				.setMaster("local[*]")
//				.set("arangodb.hosts", "192.168.20.236:8530")
//				.set("arangodb.user", "root")
//				.set("arangodb.password", "");
//
//		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf,
//				new Duration(2000));
//
//		Logger rootLogger = Logger.getRootLogger();
//		rootLogger.setLevel(Level.OFF);  // ERROR, WARNING, DEBUG, INFO
//
////		Set<String> topicsSet = new HashSet<>(Arrays.asList(args[0].split(",")));
//		 Set<String> topicsSet = new HashSet<>(Arrays.asList(top.split(",")));
//
//		Map<String, String> kafkaParams = new HashMap<>();
//		kafkaParams.put("metadata.broker.list", "datanode01.cluster4.ph:6667,namenode01.cluster4.ph:6667,namenode02.cluster4.ph:6667");
//
//		// JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, zkHost, groupName, ApplicationUtil.getTopicKafka(args[0]));
//		JavaPairInputDStream<String, String> messages =
//				KafkaUtils.createDirectStream(
//						jssc,
//						String.class,
//						String.class,
//						StringDecoder.class,
//						StringDecoder.class,
//						kafkaParams,
//						topicsSet
//				);
//
//		JavaDStream<String> message = messages.map((Function<Tuple2<String, String>, String>) Tuple2::_2);
//
//		message.print();
//
//		switch (args[0]) {
//		// switch (top) {
//			case "galaxy_result_object":
//				JavaDStream<ObjectModel> object = messages
//						.map(new Function<Tuple2<String, String>, ObjectModel>() {
//							public ObjectModel call(final Tuple2<String, String> data) throws IOException {
//
//								ObjectMapper mapper = new ObjectMapper();
//								mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//
//								return mapper.readValue(data._2, ObjectModel.class);
//							}
//						});
//
//
//				object.foreachRDD(new VoidFunction<JavaRDD<ObjectModel>>() {
//					public void call(JavaRDD<ObjectModel> objectData) throws Exception {
//						objectService.saveNodeToArango(objectData);
//					}
//				});
//				break;
//
//			case "galaxy_result_object_routing":
//				JavaDStream<ObjectRoutingModel> object_routing = messages
//						.map(new Function<Tuple2<String, String>, ObjectRoutingModel>() {
//							public ObjectRoutingModel call(final Tuple2<String, String> data) throws IOException {
//
//								ObjectMapper mapper = new ObjectMapper();
//								mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
//
//								return mapper.readValue(data._2, ObjectRoutingModel.class);
//							}
//						});
//
//
//				object_routing.foreachRDD(new VoidFunction<JavaRDD<ObjectRoutingModel>>() {
//					public void call(JavaRDD<ObjectRoutingModel> objectRoutingData) throws Exception {
//						objectRoutingService.saveEdgeToArango(objectRoutingData);
//					}
//				});
//				break;
//
//			default:
//				System.out.println("sumting wong, telminating proguramu");
//				System.exit(1);
//		}
//
//		jssc.start();
//		jssc.awaitTermination();
//	}
//}