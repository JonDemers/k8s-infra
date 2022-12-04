
FROM debian:bullseye 

RUN apt update
RUN apt install -y vim
RUN apt install -y apache2
RUN apt install -y apache2-utils
RUN apt install -y libapache2-mod-auth-openidc
RUN apt clean

WORKDIR /etc/apache2/

COPY sites-available/* sites-available/

RUN a2enmod ssl
RUN a2enmod proxy
RUN a2enmod proxy_http
RUN a2ensite jenkins.conf
RUN a2ensite pve.conf
RUN a2ensite router.conf

EXPOSE 80
CMD ["apachectl", "-D", "FOREGROUND"]
