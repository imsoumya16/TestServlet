# Use official Tomcat image with JDK 21
FROM tomcat:9.0-jdk21

# Remove default web applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy your WAR file from the 'dist' folder and rename it to ROOT.war
COPY dist/TestServlet.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 (Tomcat default)
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
