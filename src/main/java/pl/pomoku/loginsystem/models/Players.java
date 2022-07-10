package pl.pomoku.loginsystem.models;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Players {
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
    private boolean rem_password;
    private Date expiry_date;
}
