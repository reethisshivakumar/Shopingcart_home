package com.slidingmenusample2.slidingmenu2;

/**
 * Created by rdm-09 on 25-Jan-16.
 */
public class Cartdata {
    public String cid,cname,imageid;
    public int cost,cquantity;
    public Cartdata(String cid,String cname,String imageid,int cost,int cquantity)
    {
        this.cid=cid;
        this.cname=cname;
        this.imageid=imageid;
        this.cost = cost;
        this.cquantity=cquantity;
    }
}
