# hse-zoo-manager

## Сборка

Для того чтобы протестировать в Swagger клиенте выполните следующие команды:

```bash
mvn clean install
cd presentation
mvn spring-boot:run
```


## Функционал системы

Я реализовал все основные use cases, которые требовались в задании (и даже больше!):

*   **a. Добавить, удалить, посмотреть животное:**
    *   Добавление: Обрабатывается в `AnimalController` методом `addAnimal` (это `POST /api/animals`). Он вызывает `AnimalManagementService`.
    *   Удаление: Обрабатывается в `AnimalController` методом `deleteAnimal` (`DELETE /api/animals/{id}`). Тоже вызывает `AnimalManagementService`.
    *   Просмотр: Есть методы `getAllAnimals` (`GET /api/animals`) и `getAnimalById` (`GET /api/animals/{id}`) в `AnimalController`.
*   **b. Добавить или удалить вольер:**
    *   Добавление: Метод `addEnclosure` (`POST /api/enclosures`) в `EnclosureController`, использует `EnclosureManagementService`.
    *   Удаление: Метод `deleteEnclosure` (`DELETE /api/enclosures/{id}`) в `EnclosureController`. Там есть проверка, чтобы нельзя было удалить вольер с животными.
    *   Просмотр: Методы `getAllEnclosures` и `getEnclosureById` в `EnclosureController`.
*   **c. Переместить животное между вольерами:**
    *   Этим занимается `AnimalTransferController`. Метод `moveAnimalToEnclosure` (`POST /api/animals/{animalId}/move-to-enclosure`) перемещает животное в вольер, а `removeAnimalFromEnclosure` (`POST /api/animals/{animalId}/remove-from-enclosure`) убирает животное из вольера. Оба вызывают `AnimalTransferService`.
*   **d. Просмотреть расписание кормления:**
    *   Метод `getFeedingSchedules` (`GET /api/feeding-schedules`) в `FeedingController`. Он может показывать все расписания или только для одного животного (если передать `animalId` как параметр). Вызывает `FeedingOrganizationService`.
*   **e. Добавить новое кормление в расписание:**
    *   Метод `addFeedingSchedule` (`POST /api/feeding-schedules`) в `FeedingController`. Использует `FeedingOrganizationService`.
*   **f. Просмотреть статистику зоопарка:**
    *   Метод `getStatistics` (`GET /api/statistics`) в `StatisticsController`. Вызывает `ZooStatisticsService`, который собирает данные (кол-во животных, вольеров и т.д.).

## Принципы Clean Architecture

Я старался следовать основным прицнипам чистой архитектуры:

*   Проект разбит на 4 модуля Maven, которые представляют слои:
    *   `domain`: Тут самая суть - классы животных (`Animal`), вольеров (`Enclosure`), расписаний (`FeedingSchedule`), разные вспомогательные типы (`Gender`, `FoodType`) и интерфейсы репозиториев (`AnimalRepository`). Это ядро, оно ни от чего не зависит.
    *   `application`: Здесь логика приложения - сервисы (`AnimalManagementService`, `AnimalTransferService` и их реализации `...Impl`). Они используют классы и интерфейсы из `domain`. Этот слой зависит только от `domain`.
    *   `infrastructure`: Тут детали реализации - как мы храним данные. Сейчас там `inmemory` репозитории (`InMemoryAnimalRepository`), которые реализуют интерфейсы из `domain`. Зависит от `application` и `domain`.
    *   `presentation`: Слой для общения с внешним миром. Здесь REST контроллеры (`AnimalController`, `EnclosureController`, ...), которые принимают HTTP запросы и вызывают сервисы из `application`. Зависит от `application`.
*   Зависимости идут только внутрь: `presentation` -> `application` -> `domain`. Слой `infrastructure` тоже зависит от `application` и `domain`, потому что реализует их интерфейсы. `domain` не знает ни о каких других слоях.
*   Общение между слоями идет через интерфейсы. Например, `AnimalManagementService` (интерфейс) определен в `application`, а его реализация `AnimalManagementServiceImpl` находится в `application` (в пакете `impl`). Контроллер в `presentation` зависит от интерфейса `AnimalManagementService`. Точно так же репозитории: интерфейс `AnimalRepository` в `domain`, а реализация `InMemoryAnimalRepository` в `infrastructure`. Сервисы в `application` используют интерфейс `AnimalRepository`, а Spring во время запуска подставляет нужную реализацию (`InMemory...`).
*   Правила предметной области (как кормить животное, можно ли добавить его в вольер) находятся внутри классов в `domain` (например, `Animal.feed`, `Enclosure.canAddAnimal`). Логика конкретных действий пользователя, типа "переместить животное", находится в сервисах `application` слоя (например, `AnimalTransferService` проверяет всё, вызывает методы у `Animal` и `Enclosure`, сохраняет через репозитории). Слой `presentation` просто принимает запросы и передает их сервисам, а `infrastructure` занимается только хранением данных.

## 3. Концепции Domain-Driven Design (DDD)

Я применил некоторые концепции DDD:

*   **Rich Domain Model:** Классы `Animal`, `Enclosure`, `FeedingSchedule` - это не просто структуры с данными. У них есть методы, которые содержат бизнес-логику (`Animal.feed()`, `Animal.treat()`, `Enclosure.canAddAnimal()`, `FeedingSchedule.markAsCompleted()`). Это помогает держать логику, связанную с этими объектами, внутри них самих.
*   **Value Objects:** Вместо простых типов (вроде `String` или `int`) я использовал специальные классы (enum) для некоторых полей, чтобы придать им больше смысла и ограничить возможные значения. Например:
    *   `Gender` (вместо строки "MALE"/"FEMALE")
    *   `AnimalStatus` ("Здоров"/"Болен")
    *   `EnclosureType` (тип вольера)
    *   `FoodType` (тип еды)

    Они находятся в `domain.model`.
*   **Сущности:** `Animal`, `Enclosure`, `FeedingSchedule` - это сущности. У них есть уникальный ID (`UUID`), и они имеют свой жизненный цикл (их можно создавать, изменять, удалять). Равенство сущностей определяется по их ID.
*   **Агрегаты:** Можно считать `Animal` и `Enclosure` корнями агрегатов. Это значит, что операции с ними (или с тем, что тесно с ними связано) должны проходить через них, чтобы поддерживать целостность данных (например, перемещение животного меняет и `Animal`, и `Enclosure`, и это координируется через сервис, который работает с этими агрегатами).
*   **Репозитории:** Интерфейсы `AnimalRepository`, `EnclosureRepository`, `FeedingScheduleRepository` в слое `domain` определяют, как мы можем получать и сохранять наши сущности, но не говорят, *как именно* это делается. Это скрывает детали хранения данных от доменной логики.
*   **События домена:** Я определил классы `AnimalMovedEvent` и `FeedingTimeEvent` в `domain.event`. Они представляют важные события, которые произошли в системе. Пока они явно не публикуются через Spring (только вывод в консоль в `AnimalTransferService`), но их можно использовать, чтобы другие части системы реагировали на эти события (например, обновить статистику при перемещении животного).
*   **Сервисы приложения** Сервисы вроде `AnimalTransferService`, `FeedingOrganizationService` реализуют сценарии использования (use cases). Они координируют работу: находят нужные объекты через репозитории, вызывают методы у доменных объектов, сохраняют изменения через репозитории и (в будущем) публикуют события.

## Заключение

Вроде бы, основную функциональность и структуру проекта по требованиям задания я сделал. Получилось разделить логику по слоям согласно Clean Architecture и использовать некоторые идеи из DDD для построения модели предметной области.