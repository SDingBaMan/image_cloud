分布式 图像存储

config文件：
image.cloud.config的值（用于解决处理分布式图像存储选择的）
<pre><code>
{
           	"imagecloud": [{
           		"id": 111,
           		"config": {
           			"db": "db1",
           			"table": "table1",
           			"ak": "ak",
           			"sk": "sk",
           			"baseUrl": "url"
           		},
                "weight":1
           	}, {
           		"id": 222,
           		"config": {
           			"db": "db2",
           			"table": "table2",
           			"ak": "ak",
           			"sk": "sk",
           			"baseUrl": "url"
           		},
                "weight":1
           	}, {
           		"id": 333,
           		"config": {
           			"db": "db3",
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
      db 建议账户名称    
      table 对于云 bucket name 必填   
      ak 必填   
      sk 必填 
      baseUrl bucket 请求前缀url
   
   
   
返回参数
   
     id 仓库id 
     db 返回填写值
     table 对于云 bucket name 
     baseUrl bucket 请求前缀url   
     url 真实url
     hashCode 云返回的hashkey
     key 对于云的文件key


注意： 分 布 式处理图片存储的话，hashCode可能不唯一，需要联合索引。

预期 ： 1 实现分布式数据配置
       2 实现多bucket分布式配置