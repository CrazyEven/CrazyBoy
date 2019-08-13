package main.crazyJava.crazyCommonUtils.springORM;


import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class BasicDao {

    private String name;

    public BasicDao(){}

    //private String password;
    private BasicDao(String name){
        System.out.println(name);
    }

    public String getName() {
        return name;
    }
}
