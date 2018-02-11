//package com.wgs.experiencehub.service;
//
//import com.arangodb.ArangoCollection;
//import com.arangodb.ArangoDB;
//import com.arangodb.ArangoDatabase;
//import com.wgs.experiencehub.model.ObjectEntity;
//import com.wgs.experiencehub.model.ObjectModel;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.VoidFunction;
//
//import java.io.Serializable;
//
///**
// * Created by zer0 , The Maverick Hunter
// * On 16/03/17 - 20:54
// */
//public class ObjectService implements Serializable {
//    static ArangoDB arango = new ArangoDB.Builder()
//            .host("192.168.20.128")
//            .port(8530)
//            .user("root")
//            .password("")
//            .build();
//
//    static ArangoDatabase db = arango.db("galaxy");
//
//    public void saveNodeToArango(JavaRDD<ObjectModel> data) {
//        data.foreach(new VoidFunction<ObjectModel>() {
//            public void call(ObjectModel data) throws Exception {
//                try {
//                    String case_id = data.getAn().values().iterator().next().get("case_id");
//                    String _key = data.getAn().keySet().toArray()[0].toString();
//                    String img = data.getAn().values().iterator().next().get("img");
//                    String label = data.getAn().values().iterator().next().get("label");
//                    String type = data.getAn().values().iterator().next().get("type");
//
//                    ArangoCollection collection = db.collection(
//                            "galaxy2_object_update"
//                    );
//
//                    ObjectEntity DataNode = new ObjectEntity(
//                            _key,
//                            data.getAn(),
//                            _key,
//                            case_id,
//                            img,
//                            label,
//                            type
//                    );
//
//                    collection.insertDocument(DataNode);
//                    System.out.println("Node Saved");
//                } catch (Exception e) {
//                    System.out.println("Failed : "+e.getMessage());
//                }
//            }
//        });
//    }
//}
