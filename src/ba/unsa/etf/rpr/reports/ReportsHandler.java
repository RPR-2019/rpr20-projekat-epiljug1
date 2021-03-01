package ba.unsa.etf.rpr.reports;

import ba.unsa.etf.rpr.enums.ReportsEnum;
import ba.unsa.etf.rpr.model.Developer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ReportsHandler extends JFrame {
    public void showReport(Connection conn, int id) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/all_Developers.jrxml").getFile();
      //  String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
     //   parameters.put("reportsDirPath", reportsDir);
        parameters.put("creator_id", id);
        parameters.put("list_developers", ReportsEnum.LIST_DEVELOPERS.toString());
        parameters.put("name", ReportsEnum.NAME_DEV.toString());
        parameters.put("surname", ReportsEnum.SURNAME_DEV.toString());
        parameters.put("username", ReportsEnum.SURNAME_DEV.toString());
        parameters.put("email", ReportsEnum.EMAIL.toString());
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }
    public void showReport(Connection conn, int id, Developer developer) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/listProjects.jrxml").getFile();
        //  String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        //   parameters.put("reportsDirPath", reportsDir);
        parameters.put("creator_id", id);
        parameters.put("name_of_creator",developer.getName()+" " + developer.getSurname() + "("+developer.getUsername()+")");


        if(Locale.getDefault().getCountry().equals("US"))
            parameters.put("project_created","Project created by: ");
        else
            parameters.put("project_created","Kreator projekata: ");


        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }

    public void showReport(Connection conn, int id, String name) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/listOtherProjectss.jrxml").getFile();
        //  String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("developer_id", id);
        parameters.put("name_of_developer", name);
        parameters.put("assigned_projects",ReportsEnum.ASSIGNED.toString());
        parameters.put("name", ReportsEnum.NAME.toString());
        parameters.put("creator",ReportsEnum.CREATOR.toString());
        parameters.put("date",ReportsEnum.DATE.toString());
        parameters.put("client_name",ReportsEnum.CLIENT_NAME.toString());
        parameters.put("client_email",ReportsEnum.CLIENT_EMAIL.toString());

        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}
