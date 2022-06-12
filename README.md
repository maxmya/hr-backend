# HR APIS - Simple 💻

## 🤖 Build Project

---
I used docker to contain the project to build it run current command

```
 docker-compose build
```

## 🚀🚀 Run HR-Backend

---

```
 docker-compose up
```

## 🧑‍💻 Swagger URL

--- 
http://localhost:8080/swagger-ui/index.html

## 👾 APIS

```
- Register Employee : POST /api/v1/employee/register
- Change Employee State : PUT /api/v1/employee/{id}/state/{event}
- Get Employee Details : GET /api/vi/employee/{id} 

Supported State Change Events : BEGIN_CHECK,APPROVE,UNAPPROVE,ACTIVATE

```