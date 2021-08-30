package com.pom.common.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReportWriter {
	
	 File junitReport;
	 BufferedWriter junitWriter;
	 String fname;
	
	public ReportWriter(String fname) throws IOException {
		// TODO Auto-generated constructor stub
		this.fname = fname;
	}
	
	public File getJunitReport() {
		String junitReportFile = System.getProperty("user.dir") + "\\resources\\output\\" + fname;
		junitReport = new File(junitReportFile);
		return junitReport;
	}
	
	public  void writeReport(String text) throws IOException {
		String junitReportFile = System.getProperty("user.dir") + "\\resources\\output\\" + fname;
		junitReport = new File(junitReportFile);
		junitWriter = new BufferedWriter(new FileWriter(junitReport, true));
		
		junitWriter.write(text);
		
		junitWriter.close();
	}
	
}
