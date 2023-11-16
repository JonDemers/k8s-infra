
FROM linuxserver/webtop:ubuntu-mate

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update && \
    apt -y dist-upgrade && \
    apt -y autoremove && \
    apt clean

RUN apt -y install net-tools
