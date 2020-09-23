# 瑞华大数据中台统一接口文档
目录

一. 查询自动化点表实时数据 \
二. 查询自动化点表历史数据 \
三. 数据中台通用写数接口<待完善> \
四. 数据中台通用读数接口<待完善>

---

**1\. 查询自动化点表实时数据**
######  接口功能
> 获取自动化点表数据的实时值

###### URL
> http://10.238.255.252:9008/docs/index

###### 参数支持格式
> JSON


###### 返回值类型
> JSON

###### HTTP请求方式
> GET

###### 请求参数
> |参数|必选|类型|说明|
|:-----  |:-------|:-----|-----                               |
|name    |ture    |string|请求的项目名                          |
|type    |true    |int   |请求项目的类型。1：类型一；2：类型二 。|

###### 返回字段
> |返回字段|字段类型|说明                              |
|:-----   |:------|:-----------------------------   |
|status   |int    |返回结果状态。0：正常；1：错误。   |
|company  |string | 所属公司名                      |
|category |string |所属类型                         |

###### 接口示例
> 地址：[http://www.api.com/index.php?name="可口可乐"&type=1](http://www.api.com/index.php?name="可口可乐"&type=1)



<details>
  <summary>折叠代码块</summary>
  <pre><code> 
     System.out.println("虽然可以折叠代码块");
     System.out.println("但是代码无法高亮");
  </code></pre>
</details>