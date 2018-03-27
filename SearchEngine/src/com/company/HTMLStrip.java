package com.company;

/**
 * Created by Jessica on 11/30/17.
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class HTMLStrip {

    public static String delHTMLTag(String htmlStr){

        //String regEx_head = "<head[^>]*?>*<\\/head>";
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html = "<[^>]+>";

        /*Pattern p_head =Pattern.compile(regEx_head,Pattern.CASE_INSENSITIVE);
        Matcher m_head = p_head.matcher(htmlStr);
        htmlStr = m_head.replaceAll("");*/

        Pattern p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");

        return htmlStr.trim();
    }

    public static String removeHTMLInString(String content) {

        content = content.replaceAll("<p .*?>", "\r\n");

        content = content.replaceAll("<br\\s*/?>", "\r\n");

        content = content.replaceAll("\\<.*?>", "");

        String regs="([^\\u4e00-\\u9fa5\\w\\(\\)（）])+?";

        Pattern pattern=Pattern.compile(regs);
        Matcher matcher=pattern.matcher(content);

        matcher.replaceAll("");

        //content = HTMLDecoder.decode(content);
        return content;
    }

}
