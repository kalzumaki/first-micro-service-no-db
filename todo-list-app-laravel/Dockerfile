FROM php:8.3-cli

WORKDIR /var/www

COPY . .

# Install Composer globally
RUN curl -sS https://getcomposer.org/installer | php -- --install-dir=/usr/local/bin --filename=composer

# Install Laravel dependencies (ignore dev dependencies if you want, but for dev usually include them)
RUN composer install

EXPOSE 8000

CMD [ "php", "artisan", "serve", "--host=0.0.0.0", "--port=8000" ]