package cn.aogo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

//        原处理对象
@Slf4j
@Component  //交给spring管理
public class MyMetaObjectHandler implements MetaObjectHandler {
    //    使用mybatis-plus实现添加操作，执行该方法
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
//        添加操作初始化version
        this.setFieldValByName("version", 1, metaObject);
        this.setFieldValByName("isDelete", 0, metaObject);
    }

    //    使用mybatis-plus实现更新操作，执行该方法
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }


}
