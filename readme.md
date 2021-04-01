## Prerequisites
- java 8
- node 
- npm
- gradle

## How to run the application?

`gradle` should be the only build tool for developing the application.

- `./gradlew run` - builds both front and back-end components 
                        and starts the application

### Front-end devs

- in one console run `./gradlew bootRun` (to run spring app)
- and in another run `./gradlew startDev` 

### Back-end devs

- run `./gradlew bootJar` to make sure that front-end components 
    are up-to-date 
  
- run `./gradlew bootRun` to recompile (if needed) and run spring application

Alternatively, you could run `./gradlew run` but it runs webpack
each time which takes a couple of seconds.










