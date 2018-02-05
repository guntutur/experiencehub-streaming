WE USE THIS APPLICATION TO TRANSFORM DATA THAT IS NOT IN RELATIONAL FORM TO A RELATIONAL FORM
USING THIS KIND OF DSL (DOMAIN SPECIFIC LANGUAGE) : ```(a)-[b]->(c)```
IN SUMMARY :
- APACHE SPARK READ DATA BASED ON PARAMETER GIVEN TO MONGODB (scala)
- SPARK GRAPHFRAMES BUILD THE CONNECTION (scala)
- SPARK SEND PROCESSED GRAPHFRAMES DATA TO KAFKA IN A 'PARALLEL' WAY (scala)
- SPARK STREAMING READ PROCESSED DATA FROM PREVIOUS STEP IN KAFKA 
  - BUILD NODE DATA, SAVE TO ARANGODB (java)
  - BUILD EDGE DATA, SAVE TO ARANGODB (java)
- DATA THEN SAVED IN ARANGODB IN A RELATIONAL FORM, AND WE CAN MAKE USE OF IT TO CREATE GRAPH (MOST CASES) ETC.

------------------------------------------------------------------------------------------------------------------------
COMFORTABLE ENVIRONMENT TO MONITOR APPLICATION FLOW PROCESS

tmux running in 192.168.20.122, split to 6 screen : tmux a -t galaxy
- upper left screen : kafka topics, named galaxy : 192.168.20.72
- center left screen : kafka topics, named galaxy_result_object : 192.168.20.122
- lower left corner : kafka topics, named galaxy_result_routing : 192.168.20.122
- upper right corner : spark streaming kafka topic <192.168.20.122> galaxy_result_object to arango <192.168.20.75>
- center right corner : spark streaming kafka topic <192.168.20.122> galaxy_result_object_routing to arango <192.168.20.75>
- lower right corner : spark streaming kafka topic <192.168.20.72> galaxy

------------------------------------------------------------------------------------------------------------------------
STACK INFORMATION

- rest api (jersey-jetty embedded)
1. screen -R galaxy_case_rest_service : ```/home/galaxy/rest_service/dev/case/```
    192.168.180.148 -> ```192.168.180.148:8082/api_email_analysis/v1/documentation```
2. screen -R galaxy_user_rest_service : ```/home/galaxy/rest_service/dev/user/```
    192.168.180.148 -> ```http://192.168.180.148:8810/api_email_analysis/v1/swagger-ui.html```

- websocket
1. screen -R galaxy_kafka_socket (the dir is deleted, but here's some artifact for you)
    192.168.20.72 -> ```ws://192.168.20.72:7080/v2/broker/?topics=galaxy_result_test```
    subscribe to kafka topics, named galaxy_result_test in kafka cluster 192.168.20.122
2. screen -R galaxy_socket
    192.168.180.148 -> ```ws://192.168.180.148:7080/stream/mongoStreamer```
    from mongo 192.168.180.119 database galaxy

------------------------------------------------------------------------------------------------------------------------

- running from cluster 4 :

we can run the application in a few way :
1. running directly from ide (IntelliJ)
2. running with slim jar
    we build application with maven plugin (you can find the plugin in pom.xml)
    this plugin will pull all dependency needed for this project
    scp or save all the dependency to another location (eg : where spark cluster running)
    and then run the application with spark submit and give parameter to find the location of where the jar needed
3. running uber jar
    we build application with maven plugin (you can find the plugin in pom.xml)
    this plugin will create package and all the data needed to form an uber jar
    we can then scp the uber jar to spark cluster and submit it via spark submit (beware of its size though, it will be enormous because uber)

all of the sample running above can be found below

1. GalaxyStreamingJob (spark streaming consume message from kafka topic galaxy [20.72], process through spark graphframes, send result to kafka topic [20.122] : galaxy_result_test, galaxy_result_object, galaxy_result_object_routing)
```
sudo -u hdfs spark-submit --master yarn --class main.scala.com.bdg.ebdesk.galaxy_streaming.main.GalaxyStreamingJob galaxy-streaming-job-1.0-jar-with-dependencies.jar
```

2. ArangoKafkaStreamingJob (spark streaming consume from kafka topic galaxy_result_object, save streaming data to arangodb with specified database in length code)
```
sudo -u hdfs spark-submit --master yarn --class main.java.com.bdg.ebdesk.galaxy_streaming.main.ArangoKafkaStreamingJob galaxy-streaming-job-1.0-jar-with-dependencies.jar galaxy_result_object
```

3. ArangoKafkaStreamingJob (spark streaming consume from kafka topic galaxy_result_object_routing, save streaming data to arangodb with specified database in length code)
```
sudo -u hdfs spark-submit --master yarn --class main.java.com.bdg.ebdesk.galaxy_streaming.main.ArangoKafkaStreamingJob galaxy-streaming-job-1.0-jar-with-dependencies.jar galaxy_result_object_rounting
```

debuging mode, to run slim jar, pull all maven dependencies (in pom specified) move dir containing puled dependencies to readable path

```
sudo -u hdfs spark-submit --master yarn --jars $(echo pulled-dependencies/*.jar | tr ' ' ',') --class main.scala.com.bdg.ebdesk.galaxy_streaming.main.GalaxyStreamingJob galaxy-streaming-job-1.0.jar graphframes
```

------------------------------------------------------------------------------------------------------------------------

-- kafka cheatsheet

192.168.20.72
/usr/hdp/2.5.0.0-1245/kafka/bin

- create topic
```
./kafka-topics.sh --create --zookeeper master02.cluster2.ph:2181 --replication-factor 3 --partitions 5 --topic galaxy_result
```

- delete topic
```
./kafka-topics.sh --zookeeper master02.cluster2.ph:2181 --delete --topic testing1
```

- list topic
```
./kafka-topics.sh --list --zookeeper master02.cluster2.ph:2181
```

- consumer console
```
./kafka-console-consumer.sh --zookeeper master02.cluster2.ph:2181 --topic testing1 --from-beginning
```

- producer console
```
./kafka-console-producer.sh --broker-list master02.cluster2.ph:6667 --topic nama_topic
```

------------------------------------------------------------------------------------------------------------------------

-- NEW STRUCTURE
-- still in consideration phase
1. from FE case

2. upon save case, rest will create new case collection in arangodb,
the return data {success or error } then will initiate kafka producer to run from within the rest itself
 a. if collection name is available, and creation of collection (object and object_routing) is successful, send data to kafka topic [galaxy] as follows
```
{"case_id" : "some_id", "case_title" : "some_case", "object_id" : ["object1", "object2", "object3"] }
```

 b. else if collection name is preserved, return to FE with confirmation message : replace or cancel
    b-1. if replace : delete first the given collection (object and object_routing) and continue with process [2](a)
    b-2. else if cancel : do nothing and stay in FE

3. spark streaming kafka topic [galaxy] then will check if there is new message in kafka from process [2]

4. for every message received by spark streamer, spark then will process the object_id using graphframes
 and built the graph data

 proposed result data for node :
 ```
 {"case_title" : "some_case", "an":{"a7a4b4287fd34fe1812e488473a81921":{"img":"avatar.png","label":"wachid.ridwan@gmail.com","type":"email_address"}}}
 ```

 proposed result data for edge :
 ```
 {"case_title" : "some_case", "ae":{"a7a4b4287fd34fe1812e488473a81921;0a8a5eafd23641d493fa336a3f3c7b1e":{"length":"a7a4b4287fd34fe1812e488473a81921","type":"cc","target":"0a8a5eafd23641d493fa336a3f3c7b1e"}}}
 ```

 where <case_title> will be our parameter to save the given data to the target collection

5. the graph data then will be send to another kafka topic [galaxy_result_object] for every node created

6. the graph data then will be send to another kafka topic [galaxy_result_object_routing] for every edge created

7. action number [5] and [6] will perform after the other, but in the same time frame

8. spark streaming kafka topic [galaxy_result_object] then will process the message and save to collection <case_title> in arangodb

9. spark streaming kafka topic [galaxy_result_object_routing] then will process the message and save to collection <case_title> in arangodb

side note : the separation process of object and object routing is being made because try catch or if else transformation
in spark streaming is expensive in resource, we gotta lot of work to do, but need to do it fast, so better to separate the process

------------------------------------------------------------------------------------------------------------------------