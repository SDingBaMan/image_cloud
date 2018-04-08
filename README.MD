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