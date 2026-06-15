# Master Middleware Service

A Spring Boot middleware service using a dynamic multi-datasource architecture supporting multiple databases, custom transactions, Flyway migrations, and standardized REST API responses.

---

## 1. Konfigurasi Database di `application.yaml`

Aplikasi ini menggunakan konfigurasi multi-datasource yang dinamis. Semua database yang didefinisikan di bawah prefix `spring.datasource` akan secara otomatis didaftarkan sebagai bean `DataSource`, `JdbcTemplate`, `EntityManagerFactory` (JPA), dan `JpaTransactionManager`.

### Contoh Konfigurasi di `application.yaml`

```yaml
spring:
  datasource:
    # Nama Database Pertama (e.g., master-db)
    master-db:
      jdbc-url: jdbc:postgresql://localhost:5432/master
      username: admin
      password: admin
      driver-class-name: org.postgresql.Driver
      flyway:
        enabled: true
        locations: classpath:db/migration/master

    # Nama Database Kedua (e.g., testing-db)
    testing-db:
      jdbc-url: jdbc:postgresql://localhost:5433/testing
      username: admin
      password: admin
      driver-class-name: org.postgresql.Driver
      flyway:
        enabled: true
        locations: classpath:db/migration/secondary
```

### Aturan Penamaan & Penggunaan Bean:
- **DataSource**: Nama bean-nya adalah `{nama-db}DataSource` (misal: `master-dbDataSource`).
- **JdbcTemplate**: Nama bean-nya adalah `{nama-db}JdbcTemplate` (misal: `testing-dbJdbcTemplate`).
- **JPA EntityManager**: Secara default, semua datasource dapat menggunakan JPA. Untuk menggunakannya pada Service/Repository, gunakan `@PersistenceContext` atau `@Qualifier("{nama-db}EntityManagerFactory")`.
- **Flyway Migrations**: Jika `flyway.enabled` diset `true`, migrasi database akan dijalankan otomatis pada saat startup aplikasi menggunakan SQL script dari direktori yang ditentukan di `flyway.locations`.

---

## 2. Struktur Folder Lengkap

Berikut adalah struktur folder dan komponen utama proyek ini:

```text
MASTER/
├── .mvn/                            # Maven Wrapper configurations
├── logs/                            # Direktori penyimpanan log file aplikasi
├── src/
│   └── main/
│       ├── java/com/pancaran/master/
│       │   ├── config/              # Konfigurasi sistem core
│       │   │   ├── DatabaseConfig.java   # Registrasi dynamic multi-datasource, JPA, & JdbcTemplate
│       │   │   ├── DatabaseHelper.java   # Utility penolong koneksi database
│       │   │   └── OpenApiConfig.java    # Konfigurasi Swagger / SpringDoc
│       │   │
│       │   ├── common/              # Kelas pembantu global / shared utilities
│       │   │   └── APIResponse.java      # Wrapper generic untuk standarisasi JSON response
│       │   │
│       │   ├── feature/sample/      # Contoh implementasi fitur (Sample Feature)
│       │   │   ├── api/
│       │   │   │   └── SampleController.java  # Controller API (menggunakan JPA dan JDBC)
│       │   │   ├── dto/
│       │   │   │   └── SampleDto.java         # Data Transfer Object untuk Sample
│       │   │   ├── entity/
│       │   │   │   └── SampleEntity.java      # JPA Entity untuk tabel example_table
│       │   │   ├── repository/
│       │   │   │   └── SampleRepository.java  # Repository berisi logika JPA / JdbcTemplate
│       │   │   └── service/
│       │   │       └── SampleService.java     # Service Layer transaksi bisnis
│       │   │
│       │   ├── MasterApplication.java  # Main Application Entrypoint
│       │   └── status.json             # Dokumen standardisasi format JSON API
│       │
│       └── resources/
│           ├── config/
│           │   ├── application.yaml     # Konfigurasi utama aplikasi (Port, DB, dll.)
│           │   └── logback-spring.xml   # Konfigurasi logger system
│           └── db/migration/            # SQL DDL Migrasi Flyway
│               ├── master/              # SQL Script untuk database "master-db" (e.g., V1__master_init.sql)
│               └── secondary/           # SQL Script untuk database "testing-db"
│
├── mvnw                             # Maven wrapper execution script (Linux/macOS)
├── mvnw.cmd                         # Maven wrapper execution script (Windows)
└── pom.xml                          # Maven Project Object Model (Dependencies & Build config)
```

---

## 3. Cara Menjalankan Aplikasi (Running Instructions)

### Prerequisites:
1. Pastikan Anda memiliki **Java JDK 17** atau yang lebih baru terinstal.
2. Pastikan database PostgreSQL (sesuai port dan host di `application.yaml`) sudah berjalan.

### Langkah-Langkah Running:

1. **Jalankan Aplikasi via Terminal**:
   Buka terminal di root direktori proyek, lalu jalankan perintah berikut:

   - **Windows (Command Prompt / PowerShell)**:
     ```cmd
     mvnw.cmd spring-boot:run
     ```
     atau jika menggunakan terminal PowerShell/Bash di Windows:
     ```bash
     ./mvnw spring-boot:run
     ```

   - **Linux / macOS**:
     ```bash
     chmod +x mvnw
     ./mvnw spring-boot:run
     ```

2. **Akses Swagger UI**:
   Setelah aplikasi berhasil menyala (berjalan di port `8081` sesuai pengaturan default), Anda dapat mengakses dokumentasi API interaktif pada browser melalui tautan berikut:
   - **Swagger UI**: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
   - **OpenAPI Specs**: [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)
