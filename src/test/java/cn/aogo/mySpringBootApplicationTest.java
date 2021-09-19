package cn.aogo;

import cn.aogo.entity.User;
import cn.aogo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils.gt;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = mySpringBootApplication.class)
public class mySpringBootApplicationTest {
    @Autowired
    private UserMapper userMapper;

    //    查询所有
    @Test
    public void queryAll() {
        List<User> usersList = userMapper.selectList(null);
        System.out.println(usersList);
    }

    //    根据id查询
    @Test
    public void queryById() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    //    通过多个id批量查询
    @Test
    public void batchQueryByIds() {
        //    数组转化成List集合
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        userList.forEach(System.out::println);

    }

    //简单的查询条件
    @Test
    public void queryByMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "onestar");
        map.put("age", 18);
        List<User> userList = userMapper.selectByMap(map);
        userList.forEach(System.out::println);
    }

//    -----------高级查询----------------------

    //    根据wrapper查询 between & not null
    @Test
    public void wrapperQuery() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("version").isNotNull("id").between("age", 18, 19);
        userMapper.selectObjs(queryWrapper).forEach(System.out::println);
    }

    //    根据wrapper查询  等于 、not between
    @Test
    public void wrapper() {
        QueryWrapper<User> wrappper = new QueryWrapper<>();
        wrappper.eq("name", "aogo").notBetween("age", 17, 18);
        userMapper.selectList(wrappper).forEach(System.out::println);
    }

    //    模糊匹配查询
    @Test
    public void likeQuery() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("email", "qq").likeRight("name", "t").likeLeft("name", "star");

        userMapper.selectList(wrapper).forEach(System.out::println);
        userMapper.selectObjs(wrapper).forEach(System.out::println);
    }

    //    连接查询
    @Test
    public void connectQuery() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("id", "select id from user where id <4");
        List<Object> userList = userMapper.selectObjs(wrapper);
        userList.forEach(System.out::println);

    }

    //    查询结果排序
    @Test
    public void orderQuery() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);

    }

    //    添加user
    @Test
    public void addUser() {
        User user = new User();
        user.setName("13star");
        user.setAge(6);
        user.setEmail("thirteen@136.com");
        int affexted = userMapper.insert(user);
        if (affexted > 0) {
            System.out.println("insert success");
        } else {
            System.out.println("insert failed");
        }


    }

    //    通过id更新user
    @Test
    public void updateUserById() {
        User user = new User();
        user.setAge(8);
        user.setEmail("aogo15@qq.com");
        user.setId(17L);
        int affected = userMapper.updateById(user);
        if (affected > 0) {
            System.out.println("update success");
        } else {
            System.out.println("update failed");
        }
    }

    //  根据id删除
    @Test
    public void deleteById() {
        int affected = userMapper.deleteById(14L);
        if (affected > 0) {
            System.out.println("delete success");
        } else {
            System.out.println("delete failed");
        }
    }

    //    批量删除
    @Test
    public void batchDeleteByIds() {
        int affected = userMapper.deleteBatchIds(Arrays.asList(5L, 6L));
        if (affected > 0) {
            System.out.println("delete success");
        } else {
            System.out.println("delete failed");
        }

    }

    //    根据条件删除
    @Test
    public void deleteByMap() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", "fourstar");
        map.put("age", 18);
        int affected = userMapper.deleteByMap(map);
        if (affected > 0) {
            System.out.println("deleteByMap success");
        } else {
            System.out.println("deleteByMap failed");
        }
    }

    //    分页查询
    @Test
    public void selectByPage() {
//        创建page对象，当前页（2）、每页显示对象（4）
        Page<User> page = new Page<>(2, 4);
        userMapper.selectPage(page, null);
//        获取当前页
        System.out.println("currentPage:" + page.getCurrent());
//        每页数据list集合
        System.out.println("records:" + page.getRecords());

//        获取总量
        System.out.println("total:" + page.getTotal());
        //每页显示记录书
        System.out.println("size:" + page.getSize());
//        是否有下一页
        System.out.println("next:" + page.hasNext());
//        是否有当前页
        System.out.println("previous:" + page.hasPrevious());
    }


    //    乐观锁 模拟更新失败
    @Test
    public void optimisticLocker() {
        User user = userMapper.selectById(17L);
        System.out.println(user);

//        修改数据
        user.setAge(1);

//        模拟数据库中的version比取出来的值大，即其他线程更改了数据
        user.setVersion(user.getVersion() - 1);

        int affected = userMapper.updateById(user);
        if (affected > 0) {
            System.out.println("update success");
        } else {
            System.out.println("update failed");
        }

    }

    //    乐观锁 模拟更新成功 注意：这种更新version需要先查在update  不然会造成version为null
    @Test
    public void lockerSuccess() {
        User user = userMapper.selectById(16L);
        user.setAge(16);
        user.setEmail("16@163.com");
        int affected = userMapper.updateById(user);
        if (affected > 0) {
            System.out.println("update success");
        } else {
            System.out.println("update failed");
        }
    }

    //    逻辑删除 将is_delete字段置为1  0：未删除  1：已删除
//    删除语句变成了 UPDATE user SET is_delete=1 WHERE id=? AND is_delete=0
//    查询语句加了is_delete=0的限制
    @Test
    public void logicDelete() {
        User user = new User();
        user.setAge(100);
        user.setEmail("100@163.com.cn");
        user.setName("one zero zero");
        userMapper.insert(user);
        System.out.println("插入的数据：" + user);
        Long id = user.getId();
        System.out.println("id：" + id);
        Integer affected = userMapper.deleteById(id);
        if (affected > 0) {
            System.out.println("logic delete success");
        } else {
            System.out.println("logic delete failed");
        }
    }

}

