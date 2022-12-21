FROM debian:bullseye 

RUN echo 'Acquire::http { Proxy "http://apt-cacher-ng.k8s.lan:30142"; }' > /etc/apt/apt.conf.d/00aptproxy

RUN apt -y update && \
    apt -y install \
        apache2 \
        apache2-utils \
        libapache2-mod-auth-openidc && \
    apt clean

WORKDIR /etc/apache2/
COPY www/ /var/www/
COPY sites-available/ sites-available/

RUN cp /etc/apache2/mods-available/auth_openidc.conf /etc/apache2/mods-available/auth_openidc.conf.original && \
    sed -i -r 's/.*OIDCStateMaxNumberOfCookies.*/OIDCStateMaxNumberOfCookies 7 true/' /etc/apache2/mods-available/auth_openidc.conf && \
    a2enmod \
        ssl \
        proxy \
        proxy_http \
        proxy_wstunnel \
        rewrite && \
    a2ensite \
        apt-cacher-ng.conf \
        blogs.conf \
        docker-proxy.conf \
        docker-registry.conf \
        jellyfin.conf \
        jenkins.conf \
        k8s.conf \
        mern.conf \
        pve.conf \
        router.conf \
        webtop.conf

EXPOSE 80

CMD ["apachectl", "-D", "FOREGROUND"]
