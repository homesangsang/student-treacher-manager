package cn.edu.qlu.studentteachermanager.entity;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2018-05-23
 * Time: 13:59
 */
public class PasswordContent {
    private String oldPassword; // 旧密码
    private String newPassword; // 新密码

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
