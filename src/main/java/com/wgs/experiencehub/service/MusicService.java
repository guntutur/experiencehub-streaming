package com.wgs.experiencehub.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.wgs.experiencehub.mapping.response.BaseResponse;
import com.wgs.experiencehub.model.EntUserPlayMusicModel;
import com.wgs.experiencehub.util.ApplicationUtil;
import org.bson.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class MusicService implements Serializable {

    private static MongoClient mongo = new MongoClient(ApplicationUtil.getConfig().getString("mongo.host"), ApplicationUtil.getConfig().getInt("mongo.port"));
    private static MongoDatabase database = mongo.getDatabase(ApplicationUtil.getConfig().getString("mongo.db"));
    private static MusicService musicService = new MusicService();

    public void processMusicStream(EntUserPlayMusicModel musicModel) {

//        System.out.println("got email : "+musicModel.getEmail()+", querying records...");
//        Document query = new Document("email", musicModel.getEmail());

        MongoCollection<Document> coll = database.getCollection(ApplicationUtil.getConfig().getString("mongo.collection.feedback"));

//        List<Document> users = coll.find(query).sort(new Document("_id", -1)).limit(2).into(new ArrayList<>());
//        if (users.size() == 2) {
            System.out.println("records count met condition, continue process...");
//            System.out.println("collecting and comparing previous rating with current rating : "+users.get(1).get("rating")+","+musicModel.getRating());

//            if ((users.get(1).getInteger("rating") <= 2) && (musicModel.getRating() <= 2) ) {

//                try {
                    // preparing parameter
                    String urlString = ApplicationUtil.getConfig().getString("api.feedback.url");
                    String requestKeyParam = ApplicationUtil.getConfig().getString("api.feedback.param");
                    String requestMethod = ApplicationUtil.getConfig().getString("api.feedback.method");

                    System.out.println("condition met, simulating http request to : "+urlString+" ...");

//                    String json = "{\""+requestKeyParam+"\":\""+musicModel.getEmail()+"\"}";
//                    musicService.sendFeedbackTicket(urlString, requestMethod, json);

//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // since mongodb handles connection pooling by itself and naturally behaviour as a singleton object, we need not to close the connection explicitly
        // but if the object is type of DBCursor, it has to be closed to clean up resource and avoid garbage collection
    }

    public Boolean checkActivePackage(String urlString, String method, String userId, String source) throws IOException {

        Boolean status = false;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "*/*");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(method);

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

        BaseResponse response = mapper.readValue(result, BaseResponse.class);

        System.out.println("Code: "+ response.getMeta().getCode());
        System.out.println("Status: "+ response.getMeta().getStatus());
        System.out.println("Reason : "+ response.getMeta().getMessage());

        if (Objects.equals(response.getMeta().getCode(), 201)) {
            status = true;
        }

        in.close();
        conn.disconnect();

        return true;
    }

    /**
     * void for now
     * */
    private void sendFeedbackTicket(String urlString, String method, String json) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(method);

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes("UTF-8"));
        os.close();

        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

        BaseResponse response = mapper.readValue(result, BaseResponse.class);

        System.out.println("Code: "+ response.getMeta().getCode());
        System.out.println("Status: "+ response.getMeta().getStatus());
        System.out.println("Reason : "+ response.getMeta().getMessage());
        in.close();
        conn.disconnect();
    }
}