//package com.wgs.experiencehub.service;
//
//import com.arangodb.ArangoCollection;
//import com.arangodb.ArangoDB;
//import com.arangodb.ArangoDatabase;
//import com.wgs.experiencehub.model.ObjectRoutingEntity;
//import com.wgs.experiencehub.model.ObjectRoutingModel;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.VoidFunction;
//
//import java.io.Serializable;
//
///**
// * Created by zer0 , The Maverick Hunter
// * On 16/03/17 - 20:54
// */
//public class ObjectRoutingService implements Serializable {
//    private static ArangoDB arango = new ArangoDB.Builder()
//            .host("192.168.20.128")
//            .port(8530)
//            .user("root")
//            .password("")
//            .build();
//
//    private static ArangoDatabase db = arango.db("galaxy");
//
//    /*
//    * make dynamic the commented code below, like we can use parameter for it
//    * */
//    public void saveEdgeToArango(JavaRDD<ObjectRoutingModel> data) {
//        data.foreach(new VoidFunction<ObjectRoutingModel>() {
//            public void call(ObjectRoutingModel data) throws Exception {
//                try {
//                    String case_id = data.getAe().values().iterator().next().get("case_id");
//                    String message_id = data.getAe().values().iterator().next().get("message_id");
//                    String source = data.getAe().values().iterator().next().get("source");
//                    String target = data.getAe().values().iterator().next().get("target");
//                    String type = data.getAe().values().iterator().next().get("type");
//
//                    // append case id to collection
//                    ArangoCollection collection = db.collection(
//                            "galaxy2_object_routing_update"
//                    );
//
//                    ObjectRoutingEntity DataEdge = new ObjectRoutingEntity(
//                            "galaxy2_object_update/"+source,
//                            "galaxy2_object_update/"+target,
//                            data.getAe(),
//                            case_id,
//                            message_id,
//                            source,
//                            target,
//                            type
//                    );
//
//                    collection.insertDocument(DataEdge);
//                    System.out.println("Edge Saved");
//                } catch (Exception e) {
//                    System.out.println("Failed : "+e.getMessage());
//                }
//            }
//        });
//    }
//}
