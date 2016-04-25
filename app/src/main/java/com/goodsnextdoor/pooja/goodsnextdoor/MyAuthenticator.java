package com.goodsnextdoor.pooja.goodsnextdoor;

import java.net.PasswordAuthentication;

/**
 * Created by Rath on 2/21/2016.
 */
public class MyAuthenticator {
    private String user;
    private String passwd;

    public MyAuthenticator(String user, String passwd)
    {
        this.user = user;
        this.passwd = passwd;
    }

    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(user, passwd.toCharArray());
    }

}
