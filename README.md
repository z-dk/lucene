# lucene
计划：
    实现文档修改而被动的触发索引更新
    1.使用WatchService，完善该功能，实现对文件夹及其子文件夹的监控，包含新创建文件夹及其子文件夹
    2.编写索引修改的方法，对文档的增删改，对相应的索引同样操作
    
    3.索引的更新与删除功能测试不通过，须修复；索引的添加功能待测试
