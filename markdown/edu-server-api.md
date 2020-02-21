# 质量评价系统


<a name="overview"></a>
## Overview
质量评价模块


### Version information
*Version* : 1.0


### Contact information
*Contact* : molamola


### URI scheme
*Host* : localhost:8080  
*BasePath* : /


### Tags

* 实例操作接口 : 实例操作接口
* 用户 : 用户
* 用户权限 : 用户权限
* 用户角色 : 用户角色
* 算法实例接口 : 算法实例接口
* 算法数据接口 : 算法数据接口
* 评价结果接口 : 评价结果接口




<a name="paths"></a>
## Paths

<a name="startusingpost"></a>
### 运行实例
```
POST /action/{action}/{instanceId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**action**  <br>*required*|action|string|
|**Path**|**instanceId**  <br>*required*|instanceId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[DeferredResult«CommonResult»](#dc724b3870b2069532fbc47cfca7ebb8)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 实例操作接口


<a name="uploadusingpost"></a>
### 上传数据
```
POST /pmsData/upload
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**FormData**|**file**  <br>*optional*|file|file|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `multipart/form-data`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="createusingpost"></a>
### 用户新建数据
```
POST /pmsData/user
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**data**  <br>*required*|data|[PmsData](#pmsdata)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="listusingget"></a>
### 列表分页查询
```
GET /pmsData/user
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="findbyidusingget"></a>
### 根据id查找
```
GET /pmsData/user/{dataId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**dataId**  <br>*required*|dataId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="updateusingput"></a>
### 更新数据
```
PUT /pmsData/user/{dataId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**dataId**  <br>*required*|dataId|string|
|**Query**|**desc**  <br>*required*|desc|string|
|**Query**|**name**  <br>*required*|name|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="deleteusingdelete"></a>
### 删除数据
```
DELETE /pmsData/user/{dataId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**dataId**  <br>*required*|dataId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法数据接口


<a name="saveusingpost"></a>
### 保存实例（用户限定）
```
POST /pmsInstance/user
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**instance**  <br>*required*|instance|[PmsInstance](#pmsinstance)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="listbyuseridusingget"></a>
### 列表分页查询（用户限定）
```
GET /pmsInstance/user
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="catallrunninginstanceusingget"></a>
### 查看所有正在运行中的实例id
```
GET /pmsInstance/user/running
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="findbyidanduseridusingget"></a>
### 根据实例id查找（用户限定）
```
GET /pmsInstance/user/{instanceId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**instanceId**  <br>*required*|instanceId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="updateusingput_1"></a>
### 更新实例（用户限定）
```
PUT /pmsInstance/user/{instanceId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**instanceId**  <br>*required*|instanceId|string|
|**Body**|**instance**  <br>*required*|instance|[PmsInstance](#pmsinstance)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="deleteusingdelete_1"></a>
### 删除实例（用户限定）
```
DELETE /pmsInstance/user/{instanceId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**instanceId**  <br>*required*|instanceId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 算法实例接口


<a name="saveusingpost_1"></a>
### 保存数据
```
POST /pmsResult
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**result**  <br>*required*|result|[PmsResult](#pmsresult)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 评价结果接口


<a name="listusingget_1"></a>
### 列表分页查询
```
GET /pmsResult
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 评价结果接口


<a name="findbyidusingget_1"></a>
### 根据id查找
```
GET /pmsResult/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 评价结果接口


<a name="updateusingput_2"></a>
### 更新数据
```
PUT /pmsResult/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|
|**Query**|**content**  <br>*optional*||string|
|**Query**|**id**  <br>*optional*||string|
|**Query**|**instanceId**  <br>*optional*||string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 评价结果接口


<a name="deleteusingdelete_2"></a>
### 删除数据
```
DELETE /pmsResult/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 评价结果接口


<a name="saveusingpost_2"></a>
### 保存数据
```
POST /umsPermission
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**permission**  <br>*required*|permission|[UmsPermission](#umspermission)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="listusingget_2"></a>
### 列表分页查询
```
GET /umsPermission
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="addrolepermissionrelationinbatchusingpost"></a>
### 添加角色与权限的对应关系（批量）
```
POST /umsPermission/relation/{roleId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**roleId**  <br>*required*|roleId|string|
|**Body**|**permissionIdList**  <br>*required*|permissionIdList|< string > array|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="deleterolepermissionrelationinbatchusingdelete"></a>
### 删除角色与权限的对应关系（批量）
```
DELETE /umsPermission/relation/{roleId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**roleId**  <br>*required*|roleId|string|
|**Body**|**permissionIdList**  <br>*required*|permissionIdList|< string > array|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="addrolepermissionrelationusingpost"></a>
### 添加角色与权限的对应关系
```
POST /umsPermission/relation/{roleId}/{permissionId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**permissionId**  <br>*required*|permissionId|string|
|**Path**|**roleId**  <br>*required*|roleId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="deleterolepermissionrelationusingdelete"></a>
### 删除角色与权限的对应关系
```
DELETE /umsPermission/relation/{roleId}/{permissionId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**permissionId**  <br>*required*|permissionId|string|
|**Path**|**roleId**  <br>*required*|roleId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="listbyroleidusingget"></a>
### 根据角色id列出权限
```
GET /umsPermission/role/{roleId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**roleId**  <br>*required*|roleId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="listbyuseridusingget_1"></a>
### 根据用户id列出权限
```
GET /umsPermission/user/{userId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**userId**  <br>*required*|userId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="findbyidusingget_2"></a>
### 根据id查找
```
GET /umsPermission/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="updateusingput_3"></a>
### 更新数据
```
PUT /umsPermission/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|
|**Query**|**createTime**  <br>*optional*||string (date-time)|
|**Query**|**description**  <br>*optional*||string|
|**Query**|**id**  <br>*optional*||string|
|**Query**|**name**  <br>*optional*||string|
|**Query**|**updateTime**  <br>*optional*||string (date-time)|
|**Query**|**url**  <br>*optional*||string|
|**Query**|**value**  <br>*optional*||string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="deleteusingdelete_3"></a>
### 删除数据
```
DELETE /umsPermission/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户权限


<a name="saveusingpost_3"></a>
### 保存数据
```
POST /umsRole
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**role**  <br>*required*|role|[UmsRole](#umsrole)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="listusingget_3"></a>
### 列表分页查询
```
GET /umsRole
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="adduserrolerelationinbatchusingpost"></a>
### 为用户添加角色（批量）
```
POST /umsRole/relation/{userId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**userId**  <br>*required*|userId|string|
|**Body**|**roleIdList**  <br>*required*|roleIdList|< string > array|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="deleteuserrolerelationinbatchusingdelete"></a>
### 删除用户与角色的对应关系（批量）
```
DELETE /umsRole/relation/{userId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**userId**  <br>*required*|userId|string|
|**Body**|**roleIdList**  <br>*required*|roleIdList|< string > array|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="adduserrolerelationusingpost"></a>
### 为用户添加角色
```
POST /umsRole/relation/{userId}/{roleId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**roleId**  <br>*required*|roleId|string|
|**Path**|**userId**  <br>*required*|userId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="deleteuserrolerelationusingdelete"></a>
### 删除用户与角色的对应关系
```
DELETE /umsRole/relation/{userId}/{roleId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**roleId**  <br>*required*|roleId|string|
|**Path**|**userId**  <br>*required*|userId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="listbyuseridusingget_2"></a>
### 根据用户id列出角色
```
GET /umsRole/user/{userId}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**userId**  <br>*required*|userId|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="findbyidusingget_3"></a>
### 根据id查找
```
GET /umsRole/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="updateusingput_4"></a>
### 更新数据
```
PUT /umsRole/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|
|**Query**|**createTime**  <br>*optional*||string (date-time)|
|**Query**|**description**  <br>*optional*||string|
|**Query**|**id**  <br>*optional*||string|
|**Query**|**name**  <br>*optional*||string|
|**Query**|**updateTime**  <br>*optional*||string (date-time)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="deleteusingdelete_4"></a>
### 删除数据
```
DELETE /umsRole/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户角色


<a name="saveusingpost_4"></a>
### 保存数据
```
POST /umsUser
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**user**  <br>*required*|user|[UmsUser](#umsuser)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="listusingget_4"></a>
### 列表分页查询
```
GET /umsUser
```


#### Parameters

|Type|Name|Description|Schema|Default|
|---|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string||
|**Query**|**pageNum**  <br>*optional*|pageNum|integer (int32)|`1`|
|**Query**|**pageSize**  <br>*optional*|pageSize|integer (int32)|`100`|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonPage](#commonpage)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="loginusingpost"></a>
### 用户登录
```
POST /umsUser/login
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Body**|**form**  <br>*required*|form|[LoginForm](#loginform)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="refreshtokenusingget"></a>
### 用户刷新token
```
GET /umsUser/refreshToken
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="userinfousingget"></a>
### 列出当前用户信息（包括权限与角色)
```
GET /umsUser/userInfo
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="findbyidusingget_4"></a>
### 根据id查找
```
GET /umsUser/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="updateusingput_5"></a>
### 更新数据
```
PUT /umsUser/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|
|**Body**|**user**  <br>*required*|user|[UmsUser](#umsuser)|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**201**|Created|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|
|**404**|Not Found|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户


<a name="deleteusingdelete_5"></a>
### 删除数据
```
DELETE /umsUser/{id}
```


#### Parameters

|Type|Name|Description|Schema|
|---|---|---|---|
|**Header**|**Authorization**  <br>*optional*|token|string|
|**Path**|**id**  <br>*required*|id|string|


#### Responses

|HTTP Code|Description|Schema|
|---|---|---|
|**200**|OK|[CommonResult](#commonresult)|
|**204**|No Content|No Content|
|**401**|Unauthorized|No Content|
|**403**|Forbidden|No Content|


#### Consumes

* `application/json`


#### Produces

* `*/*`


#### Tags

* 用户




<a name="definitions"></a>
## Definitions

<a name="commonpage"></a>
### CommonPage

|Name|Schema|
|---|---|
|**list**  <br>*optional*|< object > array|
|**pageNum**  <br>*optional*|integer (int32)|
|**pageSize**  <br>*optional*|integer (int32)|
|**total**  <br>*optional*|integer (int64)|
|**totalPage**  <br>*optional*|integer (int32)|


<a name="commonresult"></a>
### CommonResult

|Name|Schema|
|---|---|
|**code**  <br>*optional*|integer (int64)|
|**data**  <br>*optional*|object|
|**message**  <br>*optional*|string|


<a name="dc724b3870b2069532fbc47cfca7ebb8"></a>
### DeferredResult«CommonResult»

|Name|Schema|
|---|---|
|**result**  <br>*optional*|object|
|**setOrExpired**  <br>*optional*|boolean|


<a name="loginform"></a>
### LoginForm

|Name|Schema|
|---|---|
|**password**  <br>*optional*|string|
|**username**  <br>*optional*|string|


<a name="pmsdata"></a>
### PmsData

|Name|Schema|
|---|---|
|**createTime**  <br>*optional*|string (date-time)|
|**dataPath**  <br>*optional*|string|
|**dataSize**  <br>*optional*|string|
|**dataType**  <br>*optional*|string|
|**description**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**uid**  <br>*optional*|string|
|**updateTime**  <br>*optional*|string (date-time)|


<a name="pmsinstance"></a>
### PmsInstance

|Name|Schema|
|---|---|
|**createTime**  <br>*optional*|string (date-time)|
|**dataId**  <br>*optional*|string|
|**description**  <br>*optional*|string|
|**finishTime**  <br>*optional*|string (date-time)|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**startTime**  <br>*optional*|string (date-time)|
|**state**  <br>*optional*|string|
|**type**  <br>*optional*|string|
|**uid**  <br>*optional*|string|
|**updateTime**  <br>*optional*|string (date-time)|


<a name="pmsresult"></a>
### PmsResult

|Name|Schema|
|---|---|
|**content**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**instanceId**  <br>*optional*|string|


<a name="umspermission"></a>
### UmsPermission

|Name|Schema|
|---|---|
|**createTime**  <br>*optional*|string (date-time)|
|**description**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**updateTime**  <br>*optional*|string (date-time)|
|**url**  <br>*optional*|string|
|**value**  <br>*optional*|string|


<a name="umsrole"></a>
### UmsRole

|Name|Schema|
|---|---|
|**createTime**  <br>*optional*|string (date-time)|
|**description**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**updateTime**  <br>*optional*|string (date-time)|


<a name="umsuser"></a>
### UmsUser

|Name|Schema|
|---|---|
|**createTime**  <br>*optional*|string (date-time)|
|**description**  <br>*optional*|string|
|**email**  <br>*optional*|string|
|**headerIcon**  <br>*optional*|string|
|**id**  <br>*optional*|string|
|**name**  <br>*optional*|string|
|**password**  <br>*optional*|string|
|**updateTime**  <br>*optional*|string (date-time)|





