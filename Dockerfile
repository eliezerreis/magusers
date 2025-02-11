FROM amazoncorretto:17-alpine-jdk

EXPOSE 8080

VOLUME ["/mnt"]

ADD ./target/magusers.jar magusers.jar

CMD java -jar magusers.jar