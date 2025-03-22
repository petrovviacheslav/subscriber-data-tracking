# subscriber-data-tracking

Выполненное [тестовое задание](./task.md) для "Nexign Bootcamp'25 - Инженерные практики"

## Запуск проекта

1. Клонируйте репозиторий:
    ```shell
    git clone https://github.com/petrovviacheslav/subscriber-data-tracking.git
    cd subscriber-data-tracking
    ```
2. Запустите приложение:
   ```shell
   ./gradlew bootRun
   ```

3. API будет доступно по адресу: http://localhost:8080.
4. Консоль H2 Database: http://localhost:8080/h2-console.

## Тестирование

Для запуска тестов выполните команду:
```shell
./gradlew test
```

Покрытие кода проверяется с помощью JaCoCo. Отчет о покрытии доступен в директории:
```
build/reports/jacoco/test/html/
``` 
или по адресу 
```
${path-to-dir}/build/reports/jacoco/test/html/index.html
```

## API Endpoints

1. CDR
   - POST ```/api/cdr-reports``` - создать отчёт по номеру абонента за промежуток времени
   - GET ```/{requestId}/status``` - проверка статуса по UUID (у всех базово COMPLETE, но можно доработать) 
2. UDR
   - GET ```/api/udr/by-msisdn``` - вернуть UDR запись абонента за нужный месяц или весь период по номеру телефона
   - GET ```/api/udr/all``` - вернуть UDR записи всех абонентов за определённый месяц

## Лицензия

