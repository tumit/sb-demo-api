#!/bin/bash
#env OTEL_EXPORTER_OTLP_ENDPOINT=http://127.0.0.1:4317\
#  OTEL_EXPORTER_OTLP_PROTOCOL=grpc\
#  OTEL_SERVICE_NAME="sb-demo"\
#  OTEL_RESOURCE_ATTRIBUTES=service.name=sb-demo-api,service.instance.id=localhost:8080,env=dev\
#  OTEL_LOGS_EXPORTER="otlp"\
#  OTEL_METRIC_EXPORT_INTERVAL=500\
#  OTEL_BSP_SCHEDULE_DELAY=500\
#  java -javaagent:opentelemetry-javaagent.jar\
#  -Dotel.traces.exporter=logging\
#  -Dotel.metrics.exporter=logging\
#  -Dotel.logs.exporter=logging\
#  -Dotel.service.name=sb-demo-api\
#  -jar ./build/libs/sb-demo-api-0.0.1-SNAPSHOT.jar


env\
  OTEL_SERVICE_NAME="sb-demo"\
  OTEL_METRIC_EXPORT_INTERVAL=500\
  java -javaagent:opentelemetry-javaagent.jar\
  -Dotel.resource.attributes=service.instance.id=$HOSTNAME\
  -jar ./build/libs/sb-demo-api-0.0.1-SNAPSHOT.jar