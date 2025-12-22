import { useState, useEffect } from 'react'
import './App.css'

const services = [
  {
    name: 'ElasticSearch',
    url: 'http://localhost:9200',
    description: '검색 엔진 및 데이터 저장소',
    icon: '🔍',
    color: '#005571'
  },
  {
    name: 'Kibana',
    url: 'http://localhost:5601',
    description: 'ElasticSearch 시각화 도구',
    icon: '📊',
    color: '#00BFB3'
  },
  {
    name: 'Redis',
    url: 'http://localhost:6379',
    description: '캐싱 및 세션 스토리지',
    icon: '⚡',
    color: '#DC382D'
  },
  {
    name: 'RedisInsight',
    url: 'http://localhost:5540',
    description: 'Redis GUI 관리 도구',
    icon: '🗄️',
    color: '#FF4438'
  },
  {
    name: 'Kafka UI',
    url: 'http://localhost:8090',
    description: 'Kafka 토픽 및 메시지 관리',
    icon: '📨',
    color: '#231F20'
  },
  {
    name: 'Prometheus',
    url: 'http://localhost:9090',
    description: '메트릭 수집 및 저장',
    icon: '📈',
    color: '#E6522C'
  },
  {
    name: 'Grafana',
    url: 'http://localhost:3000',
    description: '메트릭 시각화 대시보드',
    icon: '📉',
    color: '#F46800'
  },
  {
    name: 'Spring Boot',
    url: 'http://localhost:8080/actuator',
    description: 'Application Actuator',
    icon: '🍃',
    color: '#6DB33F'
  }
]

function App() {
  const [serviceStatus, setServiceStatus] = useState({})

  useEffect(() => {
    checkAllServices()
  }, [])

  const checkAllServices = async () => {
    const statusChecks = {}

    for (const service of services) {
      try {
        const response = await fetch(service.url, {
          mode: 'no-cors',
          signal: AbortSignal.timeout(2000)
        })
        statusChecks[service.name] = 'online'
      } catch (error) {
        statusChecks[service.name] = 'offline'
      }
    }

    setServiceStatus(statusChecks)
  }

  const openService = (url) => {
    window.open(url, '_blank', 'noopener,noreferrer')
  }

  return (
    <div className="app">
      <header className="header">
        <h1>🚀 DevLab Service Dashboard</h1>
        <p>모니터링 및 관리 도구 모음</p>
        <button className="refresh-btn" onClick={checkAllServices}>
          🔄 새로고침
        </button>
      </header>

      <div className="services-grid">
        {services.map((service) => (
          <div
            key={service.name}
            className="service-card"
            onClick={() => openService(service.url)}
            style={{ borderLeftColor: service.color }}
          >
            <div className="service-icon">{service.icon}</div>
            <div className="service-info">
              <h3>{service.name}</h3>
              <p>{service.description}</p>
              <code className="service-url">{service.url}</code>
            </div>
            <div className={`status-indicator ${serviceStatus[service.name] || 'checking'}`}>
              {serviceStatus[service.name] === 'online' ? '✓' :
                serviceStatus[service.name] === 'offline' ? '✗' : '⋯'}
            </div>
          </div>
        ))}
      </div>

      <footer className="footer">
        <p>모든 서비스는 Docker Compose로 실행됩니다</p>
        <code>docker-compose up -d</code>
      </footer>
    </div>
  )
}

export default App
