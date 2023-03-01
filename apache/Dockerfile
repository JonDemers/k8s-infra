FROM debian:bullseye 

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update && \
    apt -y install \
        gettext-base \
        apache2 \
        apache2-utils \
        libapache2-mod-auth-openidc && \
    apt clean

WORKDIR /etc/apache2/
COPY www/ /var/www/
COPY sites-available/ sites-available/
COPY start-apache.sh /root/

RUN cp /etc/apache2/mods-available/auth_openidc.conf /etc/apache2/mods-available/auth_openidc.conf.original && \
    sed -i -r 's/.*OIDCStateMaxNumberOfCookies.*/OIDCStateMaxNumberOfCookies 7 true/' /etc/apache2/mods-available/auth_openidc.conf && \
    a2enmod \
        ssl \
        proxy \
        proxy_http \
        proxy_wstunnel \
        rewrite && \
    cd /etc/apache2/sites-available/ && \
    a2ensite * && \
    a2dissite default-ssl.conf

EXPOSE 80

CMD ["/root/start-apache.sh"]
