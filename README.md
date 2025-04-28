# Distributed File Storage System

A lightweight and efficient distributed file storage system built using **Spring Boot**.  
Files are **split into chunks**, stored across **multiple storage nodes**, and can be **reassembled** for retrieval.  
It provides a simple REST API for uploading, retrieving, and managing files in a distributed way.

---

## 🚀 Features
- Split large files into smaller chunks automatically.
- Distribute chunks across two or more storage nodes.
- Store metadata (file info, chunk info) in a **PostgreSQL** database.
- Reassemble chunks into original files on download.
- Designed to be lightweight, modular

---

## 🛠 Tech Stack
- **Backend:** Java, Spring Boot
- **Database:** PostgreSQL
- **Others:** Base64 encoding for chunk transfer

---

## 📦 Architecture Overview
1. **Upload Flow:**
   - File is divided into chunks on upload.
   - Each chunk is stored on separate nodes.
   - Metadata (file name, chunk mappings, storage info) saved into PostgreSQL.

2. **Download Flow:**
   - Metadata is used to fetch all chunks.
   - Chunks are decoded and combined to recreate the original file.

---

## ⚙️ Installation & Setup

```bash
# 1. Clone the repository
git clone https://github.com/your-username/distributed-file-storage.git
cd distributed-file-storage

# 2. Spin up PostgreSQL instance
docker-compose up

# 3. Build the project
./mvnw clean install

# 4. Run the application
./mvnw spring-boot:run
```
