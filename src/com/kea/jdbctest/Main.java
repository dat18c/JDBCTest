package com.kea.jdbctest;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        //query info
        String query = "SELECT *"
                + " FROM vendors "
                + " ORDER BY vendor_name ASC";
        String dbUrl = "jdbc:mysql://localhost:3306/ap";
        String username = "ap_tester";
        String password = "sesame";


        //Define JDBC objects
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);//must be updateable
            ResultSet res = st.executeQuery(query);

            //udskriv resultatsættet
            printRes(res);

            //update row 1
            System.out.println("\nUpdate row 1...");
            res.absolute(1);//moves cursor to first row - res.first(); gør det samme
            res.updateString("vendor_name", "Abbey Office Furnishings");
            res.updateRow();
            //Abbey Office Furnishings
            //AAA Barneby Housing

            //udskriv resultatsættet
            printRes(res);

            //insert row
            System.out.println("\nInsert row...");
            //move to insertrow
            res.moveToInsertRow();
            //res.updateInt("vendor_id", autoincrement);
            res.updateString("vendor_name", "ZZZ Top inc");
            res.updateString("vendor_address1", "Svinget 7");
            res.updateString("vendor_address2", "Holeby");
            res.updateString("vendor_city", "Odense");
            res.updateString("vendor_state", "DK");
            res.updateString("vendor_zip_code", "6000");
            res.updateString("vendor_phone", "(666) 123 4567");
            res.updateString("vendor_contact_last_name", "Hansen");
            res.updateString("vendor_contact_first_name", "Kurt");
            res.updateInt("default_terms_id", 3);
            res.updateInt("default_account_number", 521);
            //commit
            res.insertRow();

            System.out.println("Viser ny række i resultatsættet...");
            printRes(res);

            //delete new row
            System.out.println("\nSletter sidste række...");
            //res.afterLast();
            //res.previous();
            res.last(); //i stedet
            res.deleteRow();
            printRes(res);

            //luk JDBC-objekter
            if (res!=null) res.close();
            if (st!=null) st.close();
            if (conn!=null) conn.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void printRes(ResultSet rs) throws SQLException{
        //Udskriv  resultatsæt
        System.out.println("\nLeverandørdata");
        //flyt cursor til start
        rs.beforeFirst();
        //kør igennem resultatsættet
        while (rs.next()){
            String v_name = rs.getString("vendor_name");
            String v_address1 = rs.getString("vendor_address1");
            System.out.println(v_name + " " + v_address1);
        }
    }
}
