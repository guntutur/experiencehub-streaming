USE OF JAVA AND APACHE SPARK TO STREAM DATA FROM RABBITMQ AND DO SOME CONDITION CHECK WITH DATA FROM MONGODB

HOW TO :
- CONFIGURATION FILE IS USING HOCON STYLE FROM TYPESAFE CONFIG, LOCATED IN `resource/application.conf` DIR
- JAR IS PACKAGED UBER, ANY CHANGES MADE IN `application.conf`, PACKAGE MUST BE REBUILD
- BUILD USING SIMPLE `mvn clean package` COMMAND
- TO RUN, FIRST LOCATE `spark-submit` BINARY, THEN : `./spark-submit --class com.wgs.experiencehub.main.<jobclass> /path/to/uber/jar/usually/in/target/with/suffix/jar-with-dependencies.jar`
