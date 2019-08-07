package main.crazyJava.crazyPojo.common;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "CRAZY_TECHNOLOGY")  //无则自动创建表 -> hibernate.dialect
@Table(name = "CRAZY_TECHNOLOGY")
public class TechnologyEntity implements Serializable {
    private String id;
    private String name;
    private String describe;
    private String notes;
    private String remark;
    private Date createTime;
    private Date updateTime;

    @Id
    @Column(name = "ID_", length = 55)
    @GeneratedValue(generator = "technologyGenerator")
    @GenericGenerator(name = "technologyGenerator", strategy = "uuid")
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

    @Column(name = "DESCRIBE_", length = 500)
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Column(name = "NOTES_", length = 500)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Column(name = "REMARK_", length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
