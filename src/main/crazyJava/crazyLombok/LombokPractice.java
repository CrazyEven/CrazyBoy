package main.crazyJava.crazyLombok;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Lombok能以简单的注解形式来简化java代码，提高开发人员的开发效率。
 *
 @Data注解在类上，会为类的所有属性自动生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。
 */
@Data public class LombokPractice {

    @Setter @Getter private int age;
}
