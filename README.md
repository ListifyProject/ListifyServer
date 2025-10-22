<div align="center">
    <h1>Listify Server</h1>
    <p>- the backend part of the <a href="https://github.com/ListifyProject">Listify Project</a></p>
    <a href="#dependencies">Dependencies</a> •
    <a href="#how-to-build">Build</a> •
    <a href="#testing">Testing</a>
    <h2></h2>
</div>

## Dependencies

- JDK 21
- PostgreSQL

All the required databases and services can be started with Docker Compose using the provided `docker-compose.yml` file

Alternatively, you can use your own running instances, but ensure that all services are accessible using the connection settings in `application.properties`.

## How to build

1. Clone the repository:
```bash
git clone https://github.com/ListifyProject/ListifyServer.git
cd ListifyServer
```

2. Fix `application.properties`, edit secrets and DB settings

3. Build and run:
```bash
./gradlew build
./gradlew bootRun
```

## Testing

1. Copy your `application.properties` to `application-test.properties`

2. Run the tests with:

```bash
./gradlew test
```
