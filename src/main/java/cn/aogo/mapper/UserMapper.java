package cn.aogo.mapper;

import cn.aogo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

//UserMapper接口

@Repository
public interface UserMapper extends BaseMapper<User> {
}
