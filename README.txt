Збірка (потрібні JDK 21 і Gradle 8.10+):

  gradle wrapper --gradle-version 8.10.2
  ./gradlew build        (Windows: gradlew.bat build)

Готовий мод: build/libs/ezvisual-1.0.0.jar
Кинь його в .minecraft/mods разом із Fabric Loader 0.16.10+ і Fabric API для 1.21.4.
Меню відкривається на Right Shift.

Або відкрий папку в IntelliJ IDEA - вона сама все підтягне, потім Gradle -> build.
