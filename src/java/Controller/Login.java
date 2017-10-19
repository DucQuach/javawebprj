package Controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import DataModel.QueryData;
import Entity.User;
import Entity.SessionUtils;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class Login implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String pwd;
    private String msg;
    private String uname;
    private String draft;
    private int sign = 0;
    private User user;

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //validate login
    public String validateUsernamePassword() throws ClassNotFoundException {
        boolean valid = QueryData.validate(uname, pwd);
        if (valid) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", uname);
            user = (User) session.getAttribute("user");
            return "admin";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "index";
        }
    }

    //logout event, invalidate session
    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "index";
    }

    public void mna() {
        draft = user.getUname();
        sign = 1;
    }

    public void mho() {
        draft = user.getHobby();
        sign = 2;
    }

    public void mst() {
        draft = user.getStudy();
        sign = 3;
    }

    public void mr() {
        draft = user.getResearch();
        sign = 4;
    }

    public void mre() {
        draft = user.getRecord();
        sign = 5;
    }

    public void msk() {
        draft = user.getSkill();
        sign = 6;
    }

    public void mdr() {
        draft = user.getDream();
        sign = 7;
    }

    public void min() {
        draft = user.getIntroduce();
        sign = 8;
    }

    public void update() throws SQLException, ClassNotFoundException {
        if (sign == 0) return;
        if (sign == 1) {
            user.setUname(draft);
        } else if (sign == 2) {
            user.setHobby(draft);
        } else if (sign == 3) {
            user.setStudy(draft);
        } else if (sign == 4) {
            user.setResearch(draft);
        } else if (sign == 5) {
            user.setRecord(draft);
        } else if (sign == 6) {
            user.setSkill(draft);
        } else if (sign == 7) {
            user.setDream(draft);
        } else if (sign == 8) {
            user.setIntroduce(draft);
        };
        
        sign = 0;
        
        QueryData.updateUser(user);
        
    }
}
