# Rodaki

O Rodaki é uma plataforma web e mobile-first desenvolvida para otimizar a gestão de motoristas de transporte fretado, substituindo controles manuais por um sistema digital centralizado.

## 🚀 Como Rodar o Projeto

### Pré-requisitos
- Docker
- Docker Compose

### Instruções de Execução

1. Clone o repositório e navegue até a pasta do projeto

2. Configure as variáveis de ambiente:
   - Copie o arquivo .env.example para .env
   - Preencha as variáveis necessárias

3. Execute o comando:
```bash
docker-compose up --build -d
```

4. Aguarde a inicialização dos containers. O sistema estará disponível em:
   - **Front-end**: http://localhost:4200
   - **Back-end API**: http://localhost:8080
   - **MinIO (Storage)**: http://localhost:9000

## 📁 Estrutura de Diretórios

```
.
├── Rodaki-Back/           # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/Rodaki/
│   │   │   │   ├── controller/    # Endpoints REST
│   │   │   │   ├── service/       # Lógica de negócio
│   │   │   │   ├── entity/        # Entidades JPA
│   │   │   │   ├── repository/    # Repositórios
│   │   │   │   └── config/        # Configurações
│   │   │   └── resources/
│   │   └── test/
│   ├── Dockerfile
│   └── pom.xml
│
├── Rodaki-Front/          # Frontend Angular
│   ├── src/
│   │   ├── app/
│   │   │   ├── components/        # Componentes da UI
│   │   │   ├── services/          # Serviços (alguns mockados)
│   │   │   ├── guards/            # Guards de autenticação
│   │   │   ├── interceptors/      # Interceptors HTTP
│   │   │   └── models/            # Modelos de dados
│   │   └── assets/
│   ├── Dockerfile
│   └── package.json
│
├── docker-compose.yml     # Configuração dos containers
├── .env                   # Variáveis de ambiente (criar a partir do .env.example)
└── README.md
```

## 🎯 Acesso às Telas do Front-end

O sistema possui as seguintes rotas (definidas em `app.routes.ts`):

| Rota | Descrição | Componente |
|------|-----------|------------|
| `/login` | Tela de login | `LoginComponent` |
| `/register` | Cadastro de novos usuários | `RegisterComponent` |
| `/dashboard` | Dashboard principal (requer autenticação) | `DashboardComponent` |
| `/passenger-home` | Home do passageiro | `PassengerHome` |
| `/weekly-schedule` | Agenda semanal do passageiro | `WeeklyScheduleComponent` |
| `/upload-payment-proof` | Upload de comprovante de pagamento | `UploadPaymentProof` |
| `/daily-checkin` | Lista de check-in diário | `DailyCheckinList` |

**Nota importante**: Alguns dados do front-end são atualmente **mockados** (ex: [`PassengerList`](Rodaki-Front/src/app/services/passenger-list.ts), [`PassengerProfileService`](Rodaki-Front/src/app/services/passenger-profile.ts), [`CheckinService`](Rodaki-Front/src/app/services/checkin.ts)). A integração completa com o backend está em desenvolvimento.

## 🔌 Endpoints da API Backend

O backend possui diversos endpoints implementados e funcionais:

### Autenticação e Usuários
- **POST** `/api/auth/register` - Registrar novo usuário
- **POST** `/api/auth/login` - Fazer login
- **GET** `/api/users/me` - Obter dados do usuário logado

### Motoristas
- **POST** `/api/drivers` - Criar motorista
- **GET** `/api/drivers` - Listar todos os motoristas
- **GET** `/api/drivers/{id}` - Buscar motorista por ID
- **PUT** `/api/drivers/{id}` - Atualizar motorista
- **DELETE** `/api/drivers/{id}` - Deletar motorista

### Passageiros
- **POST** `/api/passengers` - Criar passageiro
- **GET** `/api/passengers` - Listar todos os passageiros
- **GET** `/api/passengers/{id}` - Buscar passageiro por ID
- **PUT** `/api/passengers/{id}` - Atualizar passageiro
- **DELETE** `/api/passengers/{id}` - Deletar passageiro

### Endereços
- **POST** `/api/addresses/passenger/{passengerId}` - Criar endereço para passageiro
- **GET** `/api/addresses/passenger/{passengerId}` - Listar endereços do passageiro
- **GET** `/api/addresses/{id}` - Buscar endereço por ID
- **PUT** `/api/addresses/{id}` - Atualizar endereço
- **DELETE** `/api/addresses/{id}` - Deletar endereço

### Associação Motorista-Passageiro
- **POST** `/api/driver-passenger/assign` - Associar passageiro a motorista
- **DELETE** `/api/driver-passenger/remove` - Remover associação
- **GET** `/api/driver-passenger/driver/{driverId}/passengers` - Listar passageiros de um motorista
- **GET** `/api/driver-passenger/passenger/{passengerId}/drivers` - Listar motoristas de um passageiro

### Rotas e Otimização
- **GET** `/api/routes/driver/{driverId}/optimized?period={period}&tripType={tripType}` - Obter rota otimizada
- **GET** `/api/routes/driver/{driverId}/passengers` - Listar endereços dos passageiros
- **POST** `/api/routes/calculate-distance` - Calcular distância entre pontos

### Comprovantes de Pagamento
- **POST** `/api/payment-proofs/upload` - Upload de comprovante
- **GET** `/api/payment-proofs/passenger/{passengerId}` - Comprovantes de um passageiro
- **GET** `/api/payment-proofs/driver/{driverId}` - Comprovantes de um motorista
- **GET** `/api/payment-proofs/status/{status}` - Buscar por status
- **GET** `/api/payment-proofs/driver/{driverId}/status/{status}` - Filtrar por motorista e status

### CEP e Geolocalização
- **GET** `/api/cep/{cep}` - Buscar dados de endereço por CEP (integração ViaCEP + Google Maps)
- **GET** `/api/cep/validar?cep={cep}` - Validar formato do CEP

### Teste
- **GET** `/api/test` - Endpoint de teste da API

## 🛠️ Funcionalidades Implementadas no Backend

O backend possui arquitetura com as seguintes funcionalidades:

- ✅ **Autenticação JWT** - Sistema completo de autenticação e autorização
- ✅ **Integração com Google Maps API** - Geocodificação, autocomplete, cálculo de distâncias
- ✅ **Integração com ViaCEP** - Busca automática de endereços
- ✅ **Upload de arquivos com MinIO** - Armazenamento de comprovantes de pagamento
- ✅ **Otimização de rotas** - Cálculo de rotas otimizadas para motoristas
- ✅ **Gestão de cronogramas** - Controle de horários e dias da semana
- ✅ **Sistema de pagamentos** - Controle de comprovantes e status
- ✅ **CORS configurado** - Comunicação segura com o frontend

## 📝 Configurações Importantes

### Variáveis de Ambiente Necessárias

```env
MYSQL_ROOT_PASSWORD=__CHANGEME__
MYSQL_DATABASE=rodakidb
MYSQL_USER=user
MYSQL_PASSWORD=__CHANGEME__

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/rodakidb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=${MYSQL_USER}
SPRING_DATASOURCE_PASSWORD=${MYSQL_PASSWORD}

FRONT_PORT=4200

MINIO_ACCESS_KEY=__CHANGEME__
MINIO_SECRET_KEY=__CHANGEME__

JWT_SECRET=__CHANGEME__
JWT_EXPIRATION_MS=86400000

GOOGLE_MAPS_API_KEY=__CHANGEME__
```

## 📦 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3
- Spring Security + JWT
- JPA/Hibernate
- MySQL
- MinIO
- Google Maps API
- ViaCEP API

### Frontend
- Angular 20
- TypeScript
- Bootstrap 5
- Bootstrap Icons

---

Desenvolvido por Gabriel dos Santos Campelo, Gabriella Oliveira Iglesias, João Gabriel Lira Nunes, João Paulo de Oliveira Pereira, Kauê Felippe Tiburcio e Natan Henrique Pereira da Silva, como parte do curso de Análise e Desenvolvimento de Sistemas no Centro Universitário Facens.

