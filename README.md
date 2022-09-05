# TaskManager-app
_____________________________
This web application is a simple task manager.

Project structure
-----------
Application is designed according to SOLID, REST principles, using DI and N-Tier Architecture patterns with next layers:
1. controllers layer;
2. services layer;
3. repositories layer;

Features
-----------
1. Register;
2. Login;
3. Create/update/delete user (available for admin only);
4. Display all users (available for admin only);
5. Display user by id (available for admin only);
6. Create/update/delete tasklist;
7. Display all tasklists;
8. Display tasklist by id;
9. Create/update/delete task;
10. Display all tasks;
11. Display task by id;

About the app
-----------
* All incoming and outgoing data converted to JSON format;
* List of embedded roles:

      1. ADMIN;
      2. USER;
* List of embedded tasks/tasklists statuses:

      1. TO_DO;
      2. IN_PROGRESS;
      3. DONE;
* List of embedded tasks priorities:

      1. LOW;
      2. MEDIUM;
      3. HIGH;
* USER can create/update/read/delete his own tasklists and tasks only, but ADMIN can create/update/read/delete tasklists and tasks for any user;
* Task and/or tasklist has "TO_DO" status by default, but user can set other status for task and/or tasklist when creating or anytime after;
* Tasklist changes it's status to "IN_PROGRESS" automatically when at least one of the tasks got status, different from "TO_DO";
* Tasklist changes it's status to "DONE" automatically when all the tasks got status "DONE";
* User can't add a new tsk to tasklist in "DONE" status;
* Finish date sets to task automatically when "DONE" status has been gotten;
* Task's "terminated" getting "true" automatically if the finish date is after than tasklist's deadline(if present);
* Tasklist's "terminated" getting "true" automatically when at least one of the task got "terminated: true";
* Task has "MEDIUM" priority by default, but user can set other priority for task when creating or anytime after;

Technologies
-----------
* Java 11
* Spring Boot
* Spring Data JPA
* Spring Security
* Lombok
* Apache Maven
* MySQL

Usage
-----------
1. Install IntelliJ IDEA;
2. Clone this project from GitHub and make sure that an absolute path doesn't include any white spaces and/or non-latin
   symbols;
3. Install MySQL and MySQL Workbench;
4. Configure application.properties file to make a connection to you DB;
5. Run application;
6. Test application using postman and/or your browser address bar
   * Use login: userjohn@i.ua and password: user1234 to authenticate as user;
             or userjane@i.ua and password: user1234 to authenticate as user;
   * USE login: admin@i.ua and password: admin1234 to authenticate as admin.

List of allowed http methods with available endpoints and authorities
-----------
```
POST: /register/ALL
POST: /login/ALL
POST: /users/ADMIN
PUT: /users/{id}/ADMIN
GET: /users/ADMIN
GET: /users/{id}/ADMIN
DELETE: /users/{id}/ADMIN
POST: /tasklists/ADMIN, USER
GET: /tasklists/ADMIN, USER
PUT: /tasklists/{id}/ADMIN, USER
GET: /tasklists/{id}/ADMIN, USER
DELETE: /tasklists/{id}/ADMIN, USER
POST: /tasks/ADMIN, USER
GET: /tasks/ADMIN, USER
PUT: /tasks/{id}/ADMIN, USER
GET: /tasks/{id}/ADMIN, USER
DELETE: /tasks/{id}/ADMIN, USER
```

