package eu.artofcoding.app1.web.mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@ManagedBean
public class MyBean {

    String username;
    String password;
    List<String> aList = new ArrayList<String>();
    Date aDate = new Date();
    String editorValue;

    public MyBean() {
        aList.add("Hello 1");
        aList.add("Hello 2");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getaList() {
        return aList;
    }

    public void setaList(List<String> aList) {
        this.aList = aList;
    }

    public Date getaDate() {
        return aDate;
    }

    public void setaDate(Date aDate) {
        this.aDate = aDate;
    }

    public String getEditorValue() {
        return editorValue;
    }

    public void setEditorValue(String editorValue) {
        this.editorValue = editorValue;
        System.out.println("Got editorValue=" + editorValue);
    }

    public void sayHello() {
        sayHello("Helloooo");
    }

    public void sayHello(String what) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, what, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
