# AcmeFresh-Hydroponic-Farming Service API

REST API for a Hydroponic-Farming Service

## Tech Stack
- Java
- Spring Framework
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Validation
- MySQL
- Lambok
- Swagger Ui

## Admin-controller

```
POST ​/admin​/add​/category addCategoryHandler

POST ​/admin​/add​/payment addPaymentHandler

POST ​/admin​/add​/produce addProduceHandler

GET /admin​/all​/orders findAllOrdersHandler

DELETE ​/admin​/delete deleteAdminByIdHandler

DELETE ​/admin​/delete​/category​/{categoryId} deleteCategoryHandler

DELETE ​/admin​/delete​/payment​/{paymentId} deletePaymentHandler

DELETE /admin​/delete​/produce​/{produceId} deleteProduceHandler

POST ​/admin​/login loginAdminHandler

POST ​/admin​/logout logoutAdminHandler

PATCH ​/admin​/produce​/addCategory​/{produceId}​/{categoryId} addCategoryToProduceHandler

POST ​/admin​/register registerAdminHandler

PATCH ​/admin​/toggle​/payemnt​/{paymentId} togglePaymentHandler

PUT ​/admin​/update updateAdminHandler

PUT ​/admin​/update​/category updateCategoryHandler

PUT /admin​/update​/payment updatePaymentHandler

PUT ​/admin​/update​/produce updateProduceHandler

```


## User-controller



```
PATCH /user​/cancel​/order​/{orderId} cancelOrderHandler

DELETE ​/user​/delete deleteUserByIdHandler

POST ​/user​/login loginUserHandler

DELETE ​/user​/logout logoutUserHandler

POST ​/user​/order​/{produceId}​/{paymentId}​/{quantity} orderProduceHandler

GET ​/user​/produce findAllProduceHandler

POST ​/user​/register registerUser

PUT ​/user​/update updateUserHandler

GET ​/user​/user​/orders findAllUserOrdersHandler

```