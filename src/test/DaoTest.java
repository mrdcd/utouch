import com.utouch.dao.UserDao;
import com.utouch.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:config/spring_mybatis.xml"})
public class DaoTest {
    @Autowired
    UserDao userDao;
    @Test
    public void testInsert(){
        User user=new User();
        user.setUsername("11987654321");
        user.setPassword("33333");
        user.setNickname("小丁");
        user.setSex(1);
        user.setGrade("大三");
        user.setMajor("计算机");
        int i=userDao.register(user);
        System.out.println(i);
    }
}
