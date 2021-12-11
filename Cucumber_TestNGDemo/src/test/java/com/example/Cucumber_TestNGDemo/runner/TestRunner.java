package com.example.Cucumber_TestNGDemo.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		tags="", 
		features = "src/test/resources/features", 
		glue = "com.example.Cucumber_TestNGDemo.definitions", 
		plugin = { "pretty","html:target/cucumber-reports"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
	@Override
    @DataProvider(parallel = true)
	public Object[][] scenarios() {
        return super.scenarios();
    }
}