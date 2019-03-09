package org.txlcn.demo.servicea;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Description:
 * Date: 19-3-5 下午1:07
 *
 * @author ujued
 */
@Entity
@Data
@Table(name = "t_demo")
public class JpaDemo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String demoField;
    private String kid;
    private String appName;
    private Date createTime = new Date();

}
