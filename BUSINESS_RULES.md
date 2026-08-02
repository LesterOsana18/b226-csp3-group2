# 🎓 Automated University Enrollment System

## 📋 Business Rules & Functional Requirements

> **📌 Purpose**
>
> This document defines the **official business rules**, **functional requirements**, and **development guidelines** for the University Enrollment System. It serves as the project's **source of truth** to ensure consistency throughout development.

---

# 1. 🔐 User Authentication

## Login

* Users **must** log in using a valid username and password.
* Passwords **must** be stored as hashed values. 🔒
* ⚠️ **Plain-text passwords must never be stored in the database.**
* Every user account must belong to **exactly one role**.
* Only authenticated users may access the system.

### Available Roles

* 🛠️ Administrator
* 🗂️ Registrar
* 🎒 Student

---

## Logout

* Users may log out at any time.
* Logging out **must destroy** the current session.
* Users must be redirected back to the login screen after logging out. ↩️

---

# 2. 👥 User Management

**Access:** 🛠️ Administrator

## Features

* 👀 View all users
* 🔍 Search users
* 🗄️ Archive users
* ♻️ Restore archived users

## Business Rules

* Username **must be unique**.
* Every employee or student must have **exactly one** user account.
* ⛔ Archived users **cannot log in**.
* User roles are assigned **automatically** based on account type.

---

# 3. 🏫 Department Management

**Access:** 🛠️ Administrator

## Features

* 👀 View departments
* 🔍 Search departments
* ➕ Create department
* ✏️ Update department
* 🗄️ Archive department
* ♻️ Restore archived department

## Business Rules

* Department names **must be unique**.
* Department name **cannot be empty**.
* ⛔ Archived departments must **not** appear during course creation or updates.
* Departments should only contain **active** courses.

---

# 4. 📚 Course Management

**Access**

* 🛠️ Administrator
* 🗂️ Registrar

## Features

* 👀 View courses
* 🔍 Search courses
* ➕ Create course
* ✏️ Update course
* 🗄️ Archive course
* ♻️ Restore archived course

## Business Rules

* Course code **must be unique**.
* Course name **cannot be empty**.
* Units **must be greater than zero**.
* Every course must belong to **one** department.
* ⛔ Courses **cannot exist** without a valid department.
* ⛔ Archived courses **cannot be enrolled in**.
* ⛔ Courses under **archived departments** cannot be created.

---

# 5. 🔗 Prerequisite Management

**Access**

* 🛠️ Administrator
* 🗂️ Registrar

## Features

* 👀 View prerequisites
* ➕ Add prerequisite
* ✏️ Update prerequisite
* ❌ Delete prerequisite

## Business Rules

* A course may have **zero or more** prerequisites.
* Every prerequisite must reference an **existing course**.
* 🚫 A course **cannot be its own prerequisite**.
* 🚫 **Duplicate** prerequisite pairs are not allowed.
* 🚫 **Circular** prerequisite chains should not be allowed.

### ✅ Valid Example

Programming 2

requires

Programming 1

### ❌ Invalid Example

Programming 1

requires

Programming 1

---

# 6. 🧑‍💼 Employee Management

**Access:** 🛠️ Administrator

## Features

* 👀 View employees
* 🔍 Search employees
* ➕ Create employee
* ✏️ Update employee
* 🗄️ Archive employee
* ♻️ Restore archived employee

## Business Rules

* Employee ID **must be unique**.
* Username **must be unique**.
* Every employee **automatically** receives a user account.
* Employee position **determines** the assigned system role.

### Position Mapping

Registrar Employee
↓
**REGISTRAR**

Administrator Employee
↓
**ADMIN**

---

# 7. 🎒 Student Management

**Access**

* 🗂️ Registrar
* 🛠️ Administrator *(viewing purposes only)*

## Features

* 👀 View students
* 🔍 Search students
* ➕ Register student
* ✏️ Update student
* 🗄️ Archive student
* ♻️ Restore archived student

## Business Rules

* Student Number **must be unique**.
* Email address **should be unique**.
* Every registered student **automatically** receives:

  * 👤 User account
  * 🔤 Username
  * 🔑 Password
* Every student account is assigned the **STUDENT** role.
* ⚠️ **Only students can register for courses.**

---

# 8. 🖥️ Student Dashboard

**Access:** 🎒 Student

## Features

* 👤 View personal profile
* 📖 View available courses
* 🧾 View enrollment history
* ✅ Enroll in a course
* ❌ Drop an enrolled course
* 🔗 View prerequisite information

## 🚫 Restrictions

Students **cannot**:

* Create courses
* Update departments
* Manage employees
* Manage users
* Manage other students
* Manage prerequisites

---

# 9. 📝 Enrollment Management

## Registrar Features

* 👀 View all enrollments
* 🔍 Search enrollments

## Student Features

* ✅ Enroll in available courses
* ❌ Drop enrolled courses
* 🧾 View enrollment history

## Business Rules

⚠️ A student **cannot enroll**:

* in the **same course**
* during the **same semester**
* within the **same school year**

...more than once.

Each enrollment record stores:

* 🎒 Student
* 📚 Course
* 📅 School Year
* 🔢 Semester
* 🗓️ Date Enrolled

🚫 Archived students **cannot enroll**.

🚫 Archived courses **cannot accept enrollments**.

---

# 10. ✅ Prerequisite Validation

Before enrollment, the system **must verify** that the student satisfies all prerequisites.

If a prerequisite has **not** been completed →
❌ **Enrollment must be denied.**

### Example

Programming 2

requires

Programming 1

If Programming 1 has not been completed →
🚫 **Enrollment is rejected.**

---

# 11. 🗄️ Archive System

The system uses **Soft Delete**. 🧩

Archive → `is_archived = TRUE` ✅
Restore → `is_archived = FALSE` ♻️

## Business Rules

* Archived records **must not** appear in normal listings.
* Archived records **remain stored** in the database.
* ⚠️ Physical `DELETE` operations should only be used **when explicitly required**.

---

# 12. 🔍 Search

Every management module **should support searching**.

Search should:

* 🔡 Ignore case
* 🧩 Support partial matching

### Example

Searching:

```
comp
```

should match:

* Computer Science
* Computer Engineering
* Introduction to Computing

---

# 13. ✅ Validation Rules

The system **must reject**:

* 🚫 Empty required fields
* 🚫 Duplicate usernames
* 🚫 Duplicate student numbers
* 🚫 Duplicate employee IDs
* 🚫 Duplicate course codes
* 🚫 Duplicate department names
* 🚫 Duplicate prerequisite pairs
* 🚫 Invalid email addresses
* 🚫 Invalid foreign keys
* 🚫 Invalid unit values
* 🚫 Self-referencing prerequisites
* 🚫 Duplicate enrollments

---

# 14. 🔐 Session Management

* Only **one** user may be logged in at a time.
* The current user is stored inside the **Session**.
* Logging out **clears** the current session.
* 🚫 Unauthenticated users **cannot access** application menus.

---

# 15. 🧭 Navigation Flow

```
Login
    ↓
Main Menu
    ↓
Role Menu
    ↓
Module
    ↓
CRUD Operations
    ↓
Back
    ↓
Main Menu
    ↓
Logout
```

---

# 16. 🏗️ Application Architecture

The project follows a **layered architecture**.

```
View
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## Responsibilities

### 🖥️ View

* Handles user interaction.
* Displays menus and messages.
* Receives user input.

### 🎮 Controller

* Bridges the View and Service layers.
* Converts user input into service requests.

### ⚙️ Service

* Contains **all business logic**.
* Performs validations.
* Coordinates repository operations.

### 🗄️ Repository

* Performs database operations **only**.
* Contains SQL queries.
* Maps database records into model objects.

---

# 17. 🗃️ Database Rules

* Every table uses `id` as the **primary key**.
* Soft-delete tables contain an `is_archived` column.
* Foreign keys **must always** reference valid records.
* Referential integrity **must always** be maintained.

---

# 18. 📐 Coding Standards

The project follows these **design principles**:

* 🏗️ Constructor Dependency Injection
* 🧩 Interface-based architecture
* 🗄️ Repository Pattern
* ⚙️ Service Layer Pattern
* 🎯 MVC-inspired separation of concerns
* 1️⃣ Single Responsibility Principle
* 🔤 Consistent naming conventions
* 🎨 Consistent code formatting
* 📝 JavaDoc-style file comments where applicable

---

# 19. 🚀 Future Improvements

The following features are **outside the current project scope**:

* 🔑 Change Password
* ❓ Forgot Password
* 🔄 Password Reset
* 🎓 Student Grades
* 📋 Curriculum Checklist
* 📜 Transcript of Records
* 🧩 Section Management
* 📅 Subject Scheduling
* 🧑‍🏫 Faculty Management
* 🏫 Classroom Management
* 📊 Audit Logs
* 🕓 Activity History
* 📄 PDF Reports
* 📈 Excel Export
* 📉 Dashboard Analytics
* 💾 Database Backup & Restore

---

# 🎯 Project Goal

The University Enrollment System aims to provide a **simple, secure, and maintainable** console-based application for managing university enrollment records while following **clean software architecture** and **object-oriented programming principles**.

This project emphasizes **maintainability**, **readability**, **modularity**, and **proper separation of concerns** to serve as both a functional enrollment system and a demonstration of **professional Java development practices**. ✨