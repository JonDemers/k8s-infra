
FROM linuxserver/webtop:ubuntu-mate

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update
RUN apt -y dist-upgrade
RUN apt -y autoremove

RUN apt -y install net-tools
