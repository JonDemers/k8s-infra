FROM debian:bullseye

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update && \
    apt -y dist-upgrade && \
    apt -y install \
        bzflag-server && \
    apt -y autoremove && \
    apt clean

EXPOSE 5154 5154/udp

CMD ["/usr/games/bzfs", "-d", "-a", "50", "38"]
