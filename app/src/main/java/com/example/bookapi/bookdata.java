package com.example.bookapi;

public class bookdata {
    private String book;
    private String url;
    public bookdata(String s1,String s2)
    {
        book=s1;
        url=s2;
    }
    public String getbook()
    {
        return book;
    }
    public String geturl()
    {
        return url;
    }
}
