package com.pom.common.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.pom.pages.depositweb.CalcPageTest;

public class Watcher implements TestWatcher {
	
	public Watcher() throws IOException {
		// TODO Auto-generated constructor stub
	}
    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        // do something
    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
        // do something
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
    	try {
    		ReportWriter writer = new ReportWriter("junitReportFile.html");

    		writer.writeReport("<tr>");
    		writer.writeReport("<td>" + extensionContext.getDisplayName() + "</td>");
    		writer.writeReport("<td style='background-color: red; text-align: center'>FAIL</td>");
    		writer.writeReport("</tr>");
			
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
    	try {
    		ReportWriter writer = new ReportWriter("junitReportFile.html");

    		writer.writeReport("<tr>");
    		writer.writeReport("<td>" + extensionContext.getDisplayName() + "</td>");
    		writer.writeReport("<td style='background-color: lightgreen; text-align: center'>SUCCESS!</td>");
    		writer.writeReport("</tr>");

		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
    }
}