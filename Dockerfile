
FROM amazoncorretto:22-alpine-jdk
WORKDIR /app
COPY ./app/build/libs/madro-budget-api.jar madro-budget-api.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-Xlog:gc*:file=/var/log/gc-%t.log:time,uptime,tid,tags,level:filecount=10,filesize=10M", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/var/log/oom.hprof", "-XX:OnOutOfMemoryError=/app/upload_heapdump_gc_s3.sh", "-XshowSettings:vm", "-XX:+PrintFlagsFinal", "-XX:+UnlockDiagnosticVMOptions", "-jar", "madro-budget-api.jar"]

