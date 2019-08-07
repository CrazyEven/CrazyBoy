package main.crazyJava.crazyPojo.JavaReflection;

import main.crazyJava.crazyEnums.ReflectionType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * java反射机制
 */
@Entity(name = "JAVA_REFLECTION")
//@Table(name = "JAVA_REFLECTION")
public class JavaReflectionEntity implements Serializable{

    private String id;
    private String name;
    private String describe;
    private String remark;
    private ReflectionType reflectionType;
    private Set<JavaMethodEntity> set = new HashSet<>();
    private Date createDate;
    private Date updateDate;

    @Id
    @Column(name = "ID_", length = 55)
    @GeneratedValue(generator = "reflectionGenerator")
    @GenericGenerator(name = "reflectionGenerator", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME_", length = 60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIBE_", length = 600)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Column(name = "REMARK_", length = 600)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "REFLECTION_TYPE")
    @Enumerated(EnumType.STRING)
    public ReflectionType getReflectionType() {
        return reflectionType;
    }

    public void setReflectionType(ReflectionType reflectionType) {
        this.reflectionType = reflectionType;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="reflection_id")
    public Set<JavaMethodEntity> getSet() {
        return set;
    }

    public void setSet(Set<JavaMethodEntity> set) {
        this.set = set;
    }

    @Column(name = "CREATE_DATE", length = 60)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(name = "UPDATE_DATE", length = 60)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
