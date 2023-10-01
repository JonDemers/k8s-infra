#!/bin/bash

set -e

for website in $(cat /etc/apache2/sites-enabled/* | grep -E '^ +(ServerName|ServerAlias) ' | awk '{print $2}' | envsubst); do
  echo "<a href='https://$website/'>$website</a><br/>"
done >> /var/www/ROOT_DOMAIN_02/index.html

apachectl -D FOREGROUND
