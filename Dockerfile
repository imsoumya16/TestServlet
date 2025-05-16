FROM tomcat:9.0-jdk21

# Clean default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Set workdir for temporary copy
WORKDIR /tmp/app

# Copy WAR and config
COPY dist/TestServlet.war .
COPY src/java/database.properties .

# Unpack WAR into ROOT app folder and inject config
RUN mkdir /usr/local/tomcat/webapps/ROOT && \
    cd /usr/local/tomcat/webapps/ROOT && \
    jar -xf /tmp/app/TestServlet.war && \
    cp /tmp/app/database.properties WEB-INF/classes/

# Expose port and run Tomcat
EXPOSE 8080
CMD ["catalina.sh", "run"]
