/**
 * FileName: User
 * Author:   linwd
 * Date:     2021/5/3 19:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoMail.entity;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author linwd
 * @create 2021/5/3
 * @since 1.0.0
 */
public class User {

    private String username;

    private String sex;

    public User(String username, String sex) {
        this.username = username;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
