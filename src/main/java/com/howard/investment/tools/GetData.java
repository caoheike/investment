package com.howard.investment.tools;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


import java.io.IOException;

public class GetData {
    public static void main(String[] args) throws IOException, InterruptedException {
        WebClient WebClients = new WebClient();
        HtmlPage page = WebClients.getPage("http://www.hyxwqy.com/qytj/login.asp");
        HtmlTextInput username = page.getElementByName("username");
        HtmlPasswordInput username2 = page.getElementByName("password");
        username.setValueAttribute("tjjfl");
        username2.setValueAttribute("111111");
        page.executeJavaScript("javascript:chkLoginFrm()");
        Thread.sleep(3000);
        HtmlPage pageInfo= WebClients.getPage("http://www.hyxwqy.com/qytj/tzxmsj.asp");//进入查询页面
        System.out.print(pageInfo.asText());


    }
}
