package main.crazyJava.crazyPojo.JavaReflection;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "JAVA_METHOD")
//@Table(name = "JAVA_METHOD")
public class JavaMethodEntity implements Serializable{

    private String id;
    private String methodName;
    private String explain; //说明

    @Id
    @Column(name = "ID_", length = 55)
    @GeneratedValue(generator = "javaMethodGenerator")
    @GenericGenerator(name = "javaMethodGenerator", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "METHOD_NAME", length = 55)
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Column(name = "EXPLAIN_")
    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
