package com.wgs.experiencehub.service;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wgs.experiencehub.model.EntUserFeedbackModel;
import com.wgs.experiencehub.util.ApplicationUtil;
import org.bson.Document;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements Serializable {

    private static MongoClient mongo = new MongoClient(ApplicationUtil.getConfig().getString("mongo.host"), ApplicationUtil.getConfig().getInt("mongo.port"));
    private static MongoDatabase database = mongo.getDatabase(ApplicationUtil.getConfig().getString("mongo.db"));

    public void processStream(EntUserFeedbackModel feedbackModel) {

        System.out.println("got email : "+feedbackModel.getEmail()+", querying records...");

        Document query = new Document("email", feedbackModel.getEmail());

        MongoCollection<Document> coll = database.getCollection(ApplicationUtil.getConfig().getString("mongo.collection.feedback"));

        List<Document> users = coll.find(query).sort(new Document("_id", -1)).limit(2).into(new ArrayList<>());
        if (users.size() == 2) {
            System.out.println("records count met condition, continue process...");
            System.out.println("collecting and comparing previous rating with current rating : "+users.get(1).get("rating")+","+feedbackModel.getRating());

            if ((users.get(1).getInteger("rating") <= 2) && (feedbackModel.getRating() <= 2) ) {
                String api_key = ApplicationUtil.getConfig().getString("api.key");
                String urlString = ApplicationUtil.getConfig().getString("api.feedback");

                System.out.println("condition met, simulating http request to : "+urlString+" ...");

                try {

                    URL url = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept", "*/*");
                    con.setRequestProperty("api_key", api_key);
                    System.out.println("Returned : "+ con.getResponseCode());
                    System.out.println("Reason : "+ con.getResponseMessage());
                    con.disconnect();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // since mongodb handles connection pooling by itself and naturally behaviour as a singleton object, we need not to close the connection explicitly
        // but if the object is type of DBCursor, it has to be closed to clean up resource and avoid garbage collection
    }

//        Block<Document> printBlock = new Block<Document>() {
//            @Override
//            public void apply(Document document) {
//                System.out.println(document.toJson());
//            }
//        };
//        coll.find().forEach(printBlock);
//        FindIterable<Document> users = coll.find(query).sort(new Document("_id", -1)).limit(2);
//        users.forEach(printBlock);
}