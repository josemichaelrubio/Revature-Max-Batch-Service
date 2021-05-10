service {
  name = "batch-service"
  id = "batch-service-1"
  port = 8083

  connect {
    sidecar_service {}
  }

  check {
    id       = "batch-health-check"
    http     = "http://40.76.1.66:8083/actuator/health"
    method   = "GET"
    interval = "10s"
    timeout  = "1s"
  }
}