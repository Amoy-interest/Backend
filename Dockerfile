FROM java:8
ADD demo-0.0.1-SNAPSHOT.jar /usr/local/jar/
# 修正 docker 容器里面的时间
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
#声明暴漏端口，这里一般不强制，因为我们通常用 docker -p port:port 来映射端口，以达到访问容器内部的目的
EXPOSE 8082
CMD ["java","-jar","/usr/local/jar/demo-0.0.1-SNAPSHOT.jar"]
