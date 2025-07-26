# 🏥 Hospital Management System (Java + MySQL)

This project is a **console-based Hospital Management System** built using Java and MySQL. It allows you to manage doctors, patients, and appointment bookings with database support.

---

## 🗂 Project Structure

- `hospital_schema.sql`: SQL file to create the database tables.
- Java files: Contains the backend logic for managing patients and appointments.
- `dbconfig.properties`: Stores database connection info.

---

## 🛠 Setup Instructions

### ✅ Requirements:
- Java (JDK 8 or above)
- MySQL Server
- MySQL Workbench (optional)
- JDBC Connector (added to classpath)

### 🚀 How to Run:

1. **Import the database:**
   - Open MySQL Workbench or your preferred tool.
   - Run the SQL script `hospital_schema.sql` to create the tables.

2. **Update `dbconfig.properties`:**
   Replace with your actual credentials:
   ```properties
   url=jdbc:mysql://localhost:3306/HospitalManagement
   user=your_mysql_username
   pass=your_mysql_password
