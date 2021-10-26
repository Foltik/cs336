# CS336 Project

## Running Locally

You will need OpenJDK 17, and MySQL or MariaDB running locally on port 3306.

Make sure you've created a database 'cs336' and given access to the user 'cs336' with password 'cs336'.

To start the app, run:

```console
./gradlew bootRun
```

Or if you're running Windows:

```console
./gradlew.bat bootRun
```

Access the app at [http://localhost:8080](https://localhost:8080).

## Running in Docker

Docker automates all of the setup and runs everything in containers, meaning you don't have to install Java or any database locally. You'll just need to install Docker CE itself.

To start the app, run:

```console
docker-compose up --build
```

Access the app at [http://localhost:8080](https://localhost:8080).

To get a SQL prompt, run:

```console
docker-compose exec db mysql -u root --password="" -D cs336
```
