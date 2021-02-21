package ba.unsa.etf.rpr.reports;

import ba.unsa.etf.rpr.model.Developer;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportsListDevelopersEN extends JFrame {
    public void showReport(Connection conn, int id) throws JRException {
        String reportSrcFile = getClass().getResource("/reports/all_Developers_EN.jrxml").getFile();
      //  String reportsDir = getClass().getResource("/reports/").getFile();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
     //   parameters.put("reportsDirPath", reportsDir);
        parameters.put("creator_id", id);
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
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        //   parameters.put("reportsDirPath", reportsDir);
        parameters.put("developer_id", id);
        parameters.put("name_of_developer", name);

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
