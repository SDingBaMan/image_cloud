分布式 图像存储

config文件：
image.cloud.config的值（用于解决处理分布式图像存储选择的）
<pre><code>
{
           	"imagecloud": [{
           		"id": 111,
           		"db": "db1",
           		"config": {
           		    "zone":"zone0",
           			"table": "table1",
           			"ak": "ak",
           			"sk": "sk",
           			"baseUrl": "url"
           		},
                "weight":1
           	}, {
           		"id": 222,
           		"db": "db2",
           		"config": {
           		    "zone":"zone0",
           			"table": "table2",
           			"ak": "ak",
           			"sk": "sk",
           			"baseUrl": "url"
           		},
                "weight":1
           	}, {
           		"id": 333,
           		"db": "db3",
           		"config": {
           		    "zone":"zone0",
           			"table": "table3",
           			"ak": "ak",
           			"sk": "sk",
           			"baseUrl": "url"
           		},
                "weight":2
           	}]
           }
</code></pre>

配置参数

      id 仓库id 必填   
      account 建议账户名称    
      table 对于云 bucket name 必填   
      ak 必填   
      sk 必填 
      baseUrl bucket 请求前缀url
      db 必填，相当于数据库，用于分组，主要解决一个应用程序多个bucket的分布式处理，
              多个不同类型的bucket属于不同的db, 一个db下为相同的bucket（包括分布式空间）
               如果 bd不设置 默认为 null (建议设置值)
      zone 必须填写, 参考Enum
   
返回参数
   
     id 仓库id 
     account 返回填写值
     table 对于云 bucket name 
     baseUrl bucket 请求前缀url   
     url 真实url
     hashCode 云返回的hashkey
     key 对于云的文件key
     db 填写值
{"imageResult":{"baseUrl":"http://oqdzomx3f.bkt.clouddn.com","db":"db3","fsize":"63021","hashCode":"FmsHApRZ3NOzkFHLfcWsJGN7mfwU","id":333,"key":"nihaowoshixinren3","table":"news-image","zone":"zone0"}}
{"imageResult":{"baseUrl":"http://oqdzomx3f.bkt.clouddn.com","db":"db3","fsize":"63021","hashCode":"FmsHApRZ3NOzkFHLfcWsJGN7mfwU","id":333,"key":"nihaowoshixinren4","table":"news-image","zone":"zone0"}}


注意： 分 布 式处理图片存储的话，hashCode可能不唯一，需要联合索引。
      如果 bd不设置 默认为 null 

预期 ： 1 实现分布式数据配置 ok
       2 实现多bucket分布式配置 ok  (通过设置db,来实现多个项目（一个项目多个仓库（多个table））的功能)
       
下一步：
       1 修改QiniuyunServer 成为一个 统一的入口,接入更多的不同类型的图片仓库