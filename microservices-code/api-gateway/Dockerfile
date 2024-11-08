#api-gateway
FROM amazoncorretto:21-alpine3.17-jdk

# Copy source to container
RUN mkdir -p /usr/app

ADD target/api-gateway-0.0.1-SNAPSHOT.jar /usr/app/api-gateway-app.jar

WORKDIR /usr/app

# Create a user group 'appgroup'
RUN addgroup -S appgroup

# Create a user 'appuser' under 'appgroup'
RUN adduser -S -D -h /usr/app appuser appgroup

# Chown all the files to the app user.
RUN chown -R appuser:appgroup /usr/app

# Switch to 'appuser'
USER appuser

#set environment vaiables
ENV CONFIG-SERVER=
ENV REGISTRY-SVC=

# Open the mapped port
EXPOSE 9000
ENTRYPOINT [ "java","-jar","api-gateway-app.jar","--spring.profiles.active=prod"]
