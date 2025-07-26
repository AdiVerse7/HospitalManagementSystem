USE HospitalManagement;

CREATE TABLE doctor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctorName VARCHAR(255) NOT NULL,
    doctorAge INT,
    specialist VARCHAR(50),
    availableTime VARCHAR(50)
);


CREATE TABLE patient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patientName VARCHAR(255) NOT NULL,
    patientAge INT,
    patientGender VARCHAR(20),
    patientPassword VARCHAR(20),
    bookID INT
);


CREATE TABLE bookappointment (
    bookId INT PRIMARY KEY AUTO_INCREMENT,
    patientName VARCHAR(255),
    patientAge INT,
    patientGender VARCHAR(10),
    contactNumber VARCHAR(20),
    doctorID INT,
    dateOfAppointment DATE,
    FOREIGN KEY (doctorID) REFERENCES doctor(id)
);
