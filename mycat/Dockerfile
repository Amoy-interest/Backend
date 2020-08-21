#Mycat
FROM centos:7
COPY ./Mycat-server-1.6.7.4-release-20200105164103-linux.tar.gz /usr/local/
RUN yum install -y java-1.8.0-openjdk-devel.x86_64 gettext nmap-ncat openssl telnet \
 && yum clean all -y

#Mycat版本
# ENV MYCAT_VERSION 1.6.7.3

# ENV MYCAT_HOME /home/mycat

# RUN curl https://github.com/MyCATApache/Mycat-download/blob/master/1.6-RELEASE/Mycat-server-1.6-RELEASE-20161028204710-linux.tar.gz -o mycat.tar.gz \
#   && tar -zxvf mycat.tar.gz \
#   && rm -f mycat.tar.gz \
#   && mv mycat /home/ \
#   && chmod u+x ${MYCAT_HOME}

# EXPOSE 8066 9066

# ENV PATH=$PATH:$MYCAT_HOME/bin

# WORKDIR ${MYCAT_HOME}/bin

# CMD ["./mycat","console"]
RUN tar -zxvf /usr/local/Mycat-server-1.6.7.4-release-20200105164103-linux.tar.gz -C /usr/local/
ENV MYCAT_HOME=/usr/local/mycat
#VOLUME ["/usr/local/mycat/conf","/usr/local/mycat/log"]
RUN source /etc/profile
RUN source ~/.bash_profile
EXPOSE 8066
CMD ["/usr/local/mycat/bin/mycat", "console"]