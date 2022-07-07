package pl.pomoku.loginsystem.models;

import java.util.Date;

public class Players {

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_confirm() {
        return email_confirm;
    }

    public void setEmail_confirm(boolean email_confirm) {
        this.email_confirm = email_confirm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSign_in_date() {
        return sign_in_date;
    }

    public void setSign_in_date(Date sign_in_date) {
        this.sign_in_date = sign_in_date;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }

    public double getLast_x() {
        return last_x;
    }

    public void setLast_x(double last_x) {
        this.last_x = last_x;
    }

    public double getLast_y() {
        return last_y;
    }

    public void setLast_y(double last_y) {
        this.last_y = last_y;
    }

    public double getLast_z() {
        return last_z;
    }

    public void setLast_z(double last_z) {
        this.last_z = last_z;
    }

    public Players(String uuid, String player_name, String ip, String email, boolean email_confirm, String password, Date sign_in_date, boolean ban, double last_x, double last_y, double last_z) {
        this.uuid = uuid;
        this.player_name = player_name;
        this.ip = ip;
        this.email = email;
        this.email_confirm = email_confirm;
        this.password = password;
        this.sign_in_date = sign_in_date;
        this.ban = ban;
        this.last_x = last_x;
        this.last_y = last_y;
        this.last_z = last_z;
    }

    private String uuid;
    private String player_name;
    private String ip;
    private String email;
    private boolean email_confirm;
    private String password;
    private Date sign_in_date;
    private boolean ban;
    private double last_x;
    private double last_y;
    private double last_z;
}
