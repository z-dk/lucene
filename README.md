# lucene
## 计划：
### 实现文档修改而被动的触发索引更新
* 使用WatchService，完善该功能，实现对文件夹及其子文件夹的监控，包含新创建文件夹及其子文件夹
* 编写索引修改的方法，对文档的增删改，对相应的索引同样操作    （已完成）
* 索引的更新与删除功能测试不通过，须修复；索引的添加功能待测试 （已完成）
### 下一步任务：将索引的增删改加入到listener中
* 继续完成上一步任务
* 完成索引模块功能与监听器功能融合，并测试
* 优化整体代码
### 以上任务已完成，下一步界面优化
* 优化显示界面
### 解决一些已知BUG
* 尚未解决BUG：对于空白word文档，创建索引时POI抛出异常
