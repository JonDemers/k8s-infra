
FROM debian:bullseye 

RUN apt update
RUN apt install -y vim
RUN apt install -y apache2
RUN apt install -y apache2-utils
RUN apt install -y libapache2-mod-auth-openidc
RUN apt clean

RUN cp /etc/apache2/mods-available/auth_openidc.conf /etc/apache2/mods-available/auth_openidc.conf.original
RUN sed -i -r 's/.*OIDCStateMaxNumberOfCookies.*/OIDCStateMaxNumberOfCookies 7 true/' /etc/apache2/mods-available/auth_openidc.conf

WORKDIR /etc/apache2/

COPY sites-available/* sites-available/

RUN a2enmod ssl
RUN a2enmod proxy
RUN a2enmod proxy_http
RUN a2enmod proxy_wstunnel
RUN a2enmod rewrite

RUN a2ensite jellyfin.conf
RUN a2ensite jenkins.conf
RUN a2ensite k8s.conf
RUN a2ensite mern.conf
RUN a2ensite pve.conf
RUN a2ensite router.conf
RUN a2ensite webtop.conf

EXPOSE 80
CMD ["apachectl", "-D", "FOREGROUND"]
