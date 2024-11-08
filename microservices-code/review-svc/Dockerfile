#reviews-svc
FROM amazoncorretto:21-alpine3.17-jdk

# Copy source to container
RUN mkdir -p /usr/app

ADD target/review-svc-0.0.1-SNAPSHOT.jar /usr/app/review-svc-app.jar

WORKDIR /usr/app

# Create a user group 'appgroup'
RUN addgroup -S appgroup

# Create a user 'appuser' under 'appgroup'
RUN adduser -S -D -h /usr/app appuser appgroup

# Chown all the files to the app user.
RUN chown -R appuser:appgroup /usr/app

#set environment vaiables
ENV DB_HOST=
ENV DB_PORT=
ENV DB_USER=
ENV DB_PASSWORD=
ENV DB_DATABASE=
ENV APP_CONFIG_DIR=
ENV SPRING_DATASOURCE_URL=
ENV CONFIG-SERVER=
ENV REGISTRY-SVC=
ENV ZIPKIN=


# Switch to 'appuser'
USER appuser

# Open the mapped port
EXPOSE 8081
ENTRYPOINT [ "java","-jar","review-svc-app.jar","--spring.profiles.active=prod"]
