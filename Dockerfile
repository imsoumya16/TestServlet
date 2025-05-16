# Use official Tomcat image with JDK 21
FROM tomcat:9.0-jdk21

# Remove default web apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Set workdir
WORKDIR /tmp/app

# Copy WAR and properties file
COPY dist/TestServlet.war .
COPY src/java/database.properties .

# Unpack WAR, inject properties file into WEB-INF/classes, and repackage
RUN mkdir unpacked && \
    cd unpacked && \
    jar -xf ../TestServlet.war && \
    mkdir -p WEB-INF/classes && \
    cp ../database.properties WEB-INF/classes/ && \
    jar -cf /usr/local/tomcat/webapps/ROOT.war *

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
