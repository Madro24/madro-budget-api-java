# GRPC Server
## Dependencies
```
implementation "net.devh:grpc-spring-boot-starter:${grpcSpringVersion}"
```

## Gradle Plugin to generate java classes from protobuf
```
plugins {
  id 'com.google.protobuf' version '0.9.4'
  ...
}

protobuf {
  protoc {
    artifact = "com.google.protobuf:protoc:3.25.5"
  }
  plugins {
    grpc {
      artifact = 'io.grpc:protoc-gen-grpc-java:1.63.0'
    }
  }
  generateProtoTasks {
    all().each { task ->
        task.plugins {
          grpc {}
        }
    }
  }
}

sourceSets {
    main {
        java {
            srcDirs 'build/generated/source/proto/main/grpc', 'build/generated/source/proto/main/java'
        }
    }
}

```
Classes are generated under build/generated/source/proto/main/java path

## GRPC Service
BudgetGrpcService

## GRPC Configuration
application-local.yaml
```
grpc:
  server:
    port: 9090
    inProcessName: test
```