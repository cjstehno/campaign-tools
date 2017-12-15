# D&D Campaign Tools

This is a simple spring-boot application I created to help manage my D&D campaign. It is meant to be run on a 
local server or on your local machine during a game.

> WARNING: this is just an application to get stuff done for my personal use - it's pretty raw and not at all ready for prime time.
 
![alt text](/dmt-screen-encounter.png "Encounter Screen")

## Build

It's a Spring-Boot app built with Kotlin and Gradle, you can build it using:

    ./gradlew clean build
    
## Running

You can run the application using:

    ./gradle bootRun
    
or by running the built application jar.

## Database 

Since this is meant to be a local single-user application, the data is stored in an H2 database file at `~/dmtools.db`.