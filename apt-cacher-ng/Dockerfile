FROM debian:bullseye

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update
RUN yes no | apt -y install apt-cacher-ng

EXPOSE 3142

CMD ["/usr/sbin/apt-cacher-ng", "-c", "/etc/apt-cacher-ng", "ForeGround=1"]
