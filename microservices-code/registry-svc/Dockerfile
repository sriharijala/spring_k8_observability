#registry-svc
FROM amazoncorretto:21-alpine3.17-jdk

# Copy source to container
RUN mkdir -p /usr/app

ADD target/registry-svc-0.0.1-SNAPSHOT.jar /usr/app/registry-svc-app.jar

WORKDIR /usr/app

# Create a user group 'appgroup'
RUN addgroup -S appgroup

# Create a user 'appuser' under 'appgroup'
RUN adduser -S -D -h /usr/app appuser appgroup

# Chown all the files to the app user.
RUN chown -R appuser:appgroup /usr/app

#set environment vaiables
ENV CONFIG-SERVER=
ENV REGISTRY-SVC=

# Switch to 'appuser'
USER appuser

# Open the mapped port
EXPOSE 8761
ENTRYPOINT [ "java","-jar","registry-svc-app.jar","--spring.profiles.active=prod"]
