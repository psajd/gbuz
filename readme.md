# Spring Boot + PostgreSQL Dockerized Application

Это приложение на базе Spring Boot и PostgreSQL, которое можно запускать в Docker-контейнерах. Приложение разработано с
использованием Java 21 (Amazon Corretto).

## Содержание

- [Требования](#требования)
- [Установка и запуск](#установка-и-запуск)
- [Использование](#использование)
- [Связь](#связь)

## Требования

Для запуска приложения на вашей системе должны быть установлены:

- **Docker**: [Установить Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Установить Docker Compose](https://docs.docker.com/compose/install/)
- **Maven** (только для сборки): [Установить Maven](https://maven.apache.org/install.html)

## Установка и запуск

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/psajd/gbuz/
    ```
2. **Соберите JAR-файл с помощью Maven:**
    ```bash
    ./mvnw clean package -DskipTests
    ```

3. Поднимите приложение в докер контейнере
   ```bash
    docker-compose up -d
   ```


## Использование

- API-приложения доступно по адресу http://localhost:8080.
Конкретные эндпоинты зависят от реализации вашего приложения.
- PostgreSQL работает на порту 5432.
Вы можете подключиться к базе данных с помощью настроек, указанных в файле docker-compose.yml:
  * Имя пользователя: your_username
  * Пароль: your_password
  * Имя базы данных: your_database

## Связь

Контакт для связи, при возникновении вопросов: 
- tg: [@psajd](https://t.me/psajd)