FROM tomcat:8-jre8

MAINTAINER MotoDev OpenMTS <motodevnet@gmail.com>

RUN apt-get update -y
RUN apt-get install -y tzdata locales
RUN echo "Europe/Istanbul" > /etc/timezone
RUN rm -f /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata
RUN mkdir -p /opt/app/config

COPY config/* /opt/app/config/
COPY config/web.xml /usr/local/tomcat/conf/web.xml


RUN rm -rf /usr/local/tomcat/webapps/*

COPY build/libs/restapi.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080