#  Yapay Zeka Entegreli Kütüphane Yönetim Sistemi (Library Management System)

Bu proje, Spring Boot Framework kullanılarak geliştirilmiş, katmanlı mimariye (Layered Architecture) sahip, modern ve akıllı bir **Kütüphane Yönetim Sistemi** REST API uygulamasıdır. Proje bünyesinde klasik kütüphane yönetim süreçlerinin (Kitap, Üye, Ödünç Alma/İade) yanı sıra, **Groq API (Llama 3.3)** entegrasyonu kullanılarak doğal dille akıllı kitap arama ve öneri sistemi barındırmaktadır.

---

##  Kullanılan Teknolojiler ve Altyapı

Projede kullanılan temel teknolojiler, kütüphaneler ve üstlendikleri roller şu şekildedir:

* **Java 17 & Spring Boot:** Uygulamanın ana iskeleti ve backend altyapısı.
* **Spring Data JPA & Hibernate:** Veritabanı tablolarının Java sınıfları ile eşleştirilmesi (ORM) ve veritabanı CRUD işlemleri.
* **H2 Database (veya MySQL/PostgreSQL):** Verilerin güvenli ve performanslı şekilde saklandığı ilişkisel veritabanı.
* **Groq API (Llama 3.3 Model):** Kullanıcıların ruh haline, kitap temasına ve doğal dil isteklerine göre akıllı analiz yapan yapay zeka motoru.
* **Spring Security:** Uygulamanın giriş, yetkilendirme ve endpoint güvenliği altyapısı.
* **Jakarta Validation:** DTO katmanında veri girişlerinin doğruluğunu (boş olamaz, eksi değer alamaz vb.) kapıda denetleyen sistem.
* **RestTemplate:** Groq AI sunucularına güvenli ve hafif REST HTTP istekleri göndermek için kullanılan istemci.

---

##  Proje Mimarisi (Katmanlı Yapı)

Proje, yazılım mühendisliği standartlarına uygun olarak 5 ana katman halinde tasarlanmıştır:

1.  **Entity (Veri Tabanı Katmanı):** Veritabanındaki `books`, `members` ve `loans` tablolarımızın Java'daki karşılıklarıdır.
2.  **DTO (Veri Taşıma & Validation Katmanı):** Dışarıdan gelen verilerin güvenle karşılandığı, `@NotBlank`, `@PositiveOrZero` gibi anotasyonlarla doğrulandığı paketlerdir.
3.  **Repository (Veri Erişim Katmanı):** Veritabanına doğrudan sorgu atan kısımdır. Spring Data JPA'in gücüyle `Derived Query` (Türetilmiş Sorgu) yapısı kullanılmıştır.
4.  **Service (İş Mantığı / Beyin Katmanı):** Projenin kalbidir. Stok kontrolleri, limitler, yapay zeka prompt hazırlıkları ve `@Transactional` yönetimleri burada işletilir.
5.  **Controller (Dış Kapı / API Katmanı):** Dış dünyayla (Front-end) konuşan, istekleri DTO ile alıp işleyen ve geriye standart HTTP Durum Kodları (201 Created, 200 OK, 204 No Content) dönen REST API merkezidir.
6.  **Exception (Hata Yönetim Merkezi):** `@RestControllerAdvice` ve `GlobalExceptionHandler` ile sistemde fırlatılan tüm hatalar (Örn: `ResourceNotFoundException`) yakalanır ve front-end'e temiz JSON paketleri olarak fırlatılır.

---

##  Kurulum ve Çalıştırma Kılavuzu

Projeyi kendi yerel bilgisayarınızda indirip çalıştırmak için aşağıdaki adımları sırasıyla uygulayabilirsiniz:

### 1. Ön Gereksinimler
Bilgisayarınızda aşağıdaki araçların kurulu olduğundan emin olun:
* Java JDK 17 veya üzeri
* Maven (Genellikle IDE'ler ile gömülü gelir)
* Git

### 2. Projeyi Klonlayın
Öncelikle terminali veya Git Bash'i açarak projeyi bilgisayarınıza indirin:
```bash

git clone [https://github.com/KULLANICI_ADINIZ/PROJE_REPO_ADINIZ.git](https://github.com/KULLANICI_ADINIZ/PROJE_REPO_ADINIZ.git)
cd PROJE_REPO_ADINIZ

### 3. Yapay Zeka (Groq) API Anahtarını Tanımlayın
Projenin akıllı arama yapabilmesi için src/main/resources/application.properties dosyasını açın ve Groq platformundan aldığınız API anahtarını ilgili alana yapıştırın:

groq.api.key=YOUR_GROQ_API_KEY_HERE

### 4. Projeyi Derleyin ve Çalıştırın
Projenin ana dizinindeyken terminalden aşağıdaki komutla uygulamayı ayağa kaldırın:

mvn spring-boot:run

Uygulama varsayılan olarak http://localhost:8080 portunda çalışmaya başlayacaktır.




[EN]

# 📚 AI-Integrated Library Management System

This project is a modern and smart **Library Management System** REST API application developed using the Spring Boot Framework, featuring a Layered Architecture. In addition to classic library management processes (Book, Member, Loan/Return), the project incorporates a smart book search and recommendation system using natural language via **Groq API (Llama 3.3)** integration.

---

## 🛠️ Used Technologies and Infrastructure

The core technologies, libraries, and their respective roles used in the project are as follows:

* **Java 17 & Spring Boot:** The main skeleton and backend infrastructure of the application.
* **Spring Data JPA & Hibernate:** Mapping database tables with Java classes (ORM) and database CRUD operations.
* **H2 Database (or MySQL/PostgreSQL):** Relational database where data is stored securely and performantly.
* **Groq API (Llama 3.3 Model):** The artificial intelligence engine that performs smart analysis based on users' mood, book theme, and natural language requests.
* **Spring Security:** The login, authorization, and endpoint security infrastructure of the application.
* **Jakarta Validation:** The system that validates data entries at the door (cannot be empty, cannot take negative values, etc.) in the DTO layer.
* **RestTemplate:** The client used to send secure and lightweight REST HTTP requests to Groq AI servers.

---

## 🏗️ Project Architecture (Layered Structure)

The project is designed in 5 main layers in accordance with software engineering standards:

1.  **Entity (Database Layer):** Java counterparts of our `books`, `members`, and `loans` tables in the database.
2.  **DTO (Data Transfer & Validation Layer):** Packages where incoming data is safely met and validated with annotations like `@NotBlank`, `@PositiveOrZero`.
3.  **Repository (Data Access Layer):** The part that throws direct queries to the database. Utilizing the power of Spring Data JPA, a `Derived Query` structure is used.
4.  **Service (Business Logic / Brain Layer):** The heart of the project. Stock controls, limits, AI prompt preparations, and `@Transactional` managements are executed here.
5.  **Controller (Outer Door / API Layer):** The REST API center that speaks with the outer world (Front-end), processes requests by taking them with DTO, and returns standard HTTP Status Codes (201 Created, 200 OK, 204 No Content).
6.  **Exception (Error Management Center):** With `@RestControllerAdvice` and `GlobalExceptionHandler`, all errors thrown in the system (e.g., `ResourceNotFoundException`) are caught and thrown to the front-end as clean JSON packages.

---

## 🚀 Installation and Running Guide

You can apply the following steps in order to download and run the project on your local computer:

### 1. Prerequisites
Make sure that the following tools are installed on your computer:
* Java JDK 17 or higher
* Maven (Usually comes embedded with IDEs)
* Git

### 2. Clone the Project
First, download the project to your computer by opening the terminal or Git Bash:
```bash
git clone [https://github.com/KULLANICI_ADINIZ/PROJE_REPO_ADINIZ.git](https://github.com/KULLANICI_ADINIZ/PROJE_REPO_ADINIZ.git)
cd PROJE_REPO_ADINIZ

3. Define the Artificial Intelligence (Groq) API Key
In order for the project to perform smart search, open the src/main/resources/application.properties file and paste the API key you received from the Groq platform into the relevant field:

groq.api.key=YOUR_GROQ_API_KEY_HERE

4. Compile and Run the Project
While in the main directory of the project, raise the application up with the following command from the terminal:

mvn spring-boot:run