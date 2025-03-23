# subscriber-data-tracking

Выполненное [тестовое задание](./task.md) для "Nexign Bootcamp'25 - Инженерные практики"

Выполнены все 3 задачи, использована H2 Database, покрытие тестов более 75%.

## Оглавление
1. [Запуск проекта](#run)
2. [Тестирование](#tests)
3. [API Endpoints](#api-endpoints)
4. [Лицензия](#license)

## Запуск проекта <a name="run"></a>

1. Клонируйте репозиторий:
    ```shell
    git clone https://github.com/petrovviacheslav/subscriber-data-tracking.git
    cd subscriber-data-tracking
    ```
2. Запустите приложение:
   ```shell
   ./gradlew bootRun
   ```

3. API будет доступно по адресу: http://localhost:8080/api.
4. Консоль H2 Database: http://localhost:8080/h2-console.

## Тестирование <a name="tests"></a>

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
${path-to-project}/build/reports/jacoco/test/html/index.html
```

## API Endpoints <a name="api-endpoints"></a>

1. CDR (CdrReportController)
   - POST ```/api/cdr-reports``` - создать отчёт по номеру абонента за промежуток времени 
2. UDR (UsageDataReportController)
   - GET ```/api/udr/by-msisdn``` - вернуть UDR запись абонента за нужный месяц или весь период по номеру телефона
   - GET ```/api/udr/all``` - вернуть UDR записи всех абонентов за определённый месяц

[Примеры http-запросов и ответов](./http.md)

## Лицензия <a name="license"></a>

Проект доступен с открытым исходным кодом на условиях [MIT license](./LICENSE).<br>
*Авторские права 2025 Вячеслав Петров*<br>

<a href="https://github.com/petrovviacheslav/subscriber-data-tracking/graphs/contributors">
  <img alt="contributors" src="https://contrib.rocks/image?repo=petrovviacheslav/subscriber-data-tracking" />
</a><br>
