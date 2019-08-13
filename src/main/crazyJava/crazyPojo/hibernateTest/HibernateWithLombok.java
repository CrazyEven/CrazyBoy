package main.crazyJava.crazyPojo.hibernateTest;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * lombok真垃圾
 */
//@Table(name = "hibernate_lombok")
public class HibernateWithLombok {

    @Id
    @Column(name = "ID_", length = 55)
    @GeneratedValue(generator = "hlGenerator")
    @GenericGenerator(name = "hlGenerator", strategy = "uuid")
    @Setter @Getter private String id;
    @Column(name = "NAME_", length = 55)
    @Setter @Getter private String name;
    @Column(name = "CREATE_TIME", length = 55)
    @Setter @Getter private Date createTime;
}
