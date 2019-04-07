package team_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/*
 * try-catch for each method to try setting list and tScript
 * 1. output(filename, var): if all output is standard
 * 2. output(type, var): if all output is categorized
 */
public class StatsAndAnalysis {
  
	private static TranscriptHandler tScriptList;

	
  
	protected static int getNumStudetsPerYear(String Year){
		try
		{
			ArrayList<Transcript> list = tScriptList.getList();
			int output = 0;
			for( Transcript t: list) {
				ArrayList<Course> tScript = t.getCourses();
				for(Course c: tScript) {
					if(c.getTerm().contains(Year)) {
						output++;
						break;
					}
				}
			}
			return output;
		}
		catch(NullPointerException e)
		{
			return -1;
		}
		
	}
	
	protected static int getNumStudentsPerLocation(String Location) {
		try
		{
			ArrayList<Transcript> list = tScriptList.getList();
			int output = 0;
			for( Transcript t: list) {
				ArrayList<Course> tScript = t.getCourses();
				for(Course c: tScript) {
					if(c.getSectionCode().contains(Location)) {
						output++;
						break;
					}
				}
			}
			return output;
		}
		catch(NullPointerException e)
		{
			return -1;
		}
	}
	
	protected static int getNumStudentsInCoursePerYear(String courseCode, String Year) {
		try
		{
			ArrayList<Transcript> list = tScriptList.getList();
			int output = 0;
			for( Transcript t: list) {
				ArrayList<Course> tScript = t.getCourses();
				for(Course c: tScript) {
					if(c.getCourseCode().equals(courseCode)&&c.getTerm().contains(Year)) {
						output++;
						break;
					}
				}
			}
			return output;
		}
		catch(NullPointerException e)
		{
			return -1;
		}
	}
	
	protected static int getNumStudentsInLocationPerYear(String Location, String Year) {
		try
		{
			ArrayList<Transcript> list = tScriptList.getList();
			int output = 0;
			for( Transcript t: list) {
				ArrayList<Course> tScript = t.getCourses();
				for(Course c: tScript) {
					if(c.getSectionCode().contains(Location)&&c.getTerm().contains(Year)) {
						output++;
						break;
					}
				}
			}
			return output;
		}
		catch(NullPointerException e)
		{
			return -1;
		}
	}
	
	
	
	
	/*
	 * GUI Component
	 */
	protected static void showStatistics(TranscriptHandler cohort)
	{
		ConfigGUI.openConfig();
		tScriptList = cohort;
		
		Stage window = new Stage();
		Scene scene;
		
		// ----- Config Menu -----
		
		Text genStats = new Text("Generate Statistics");
		genStats.setFont(Font.font(20));
		
		
		Button back = new Button("Back");
		back.setOnAction(e ->
		{
			window.close();
		});
		
		
		Button stat1 = new Button("Students/Year");
		stat1.setMinWidth(175);
		stat1.setMaxWidth(175);
		stat1.setOnAction(e ->
		{
			studentsPerYearWindow();
		});
		
		
		Button stat2 = new Button("Students/Location");
		stat2.setMinWidth(175);
		stat2.setMaxWidth(175);
		stat2.setOnAction(e ->
		{
			studentsPerLocationWindow();
		});
		
		
		Button stat3 = new Button("Students/Course/Year");
		stat3.setMinWidth(175);
		stat3.setMaxWidth(175);
		stat3.setOnAction(e ->
		{
			studentsPerCoursePerYearWindow();
		});
		
		
		Button stat4 = new Button("Students/Location/Year");
		stat4.setMinWidth(175);
		stat4.setMaxWidth(175);
		stat4.setOnAction(e ->
		{
			studentsInLocationByYearWindow();
		});
		
		Button raw = new Button("Get Raw Distribution");
		raw.setMinWidth(175);
		raw.setMaxWidth(175);
		raw.setOnAction(e ->
		{
			//SystemGUI.initializeRawDistributionsList();
		});
		
		
		
		HBox title = new HBox();
		title.setPadding(new Insets(15, 15, 15, 15));
		title.setSpacing(15);
		title.setAlignment(Pos.CENTER);
		title.getChildren().addAll(genStats);
		
		
		VBox stats = new VBox();
		stats.setPadding(new Insets(15, 15, 15, 15));
		stats.setSpacing(15);
		stats.setAlignment(Pos.CENTER);
		stats.getChildren().addAll(stat1, stat2, stat3, stat4, raw);
		
		
		HBox bottomButtons = new HBox();
		bottomButtons.setPadding(new Insets(15, 15, 15, 15));
		bottomButtons.setSpacing(15);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.getChildren().addAll(back);
		
		
		BorderPane layout = new BorderPane();
		layout.setTop(title);
		layout.setCenter(stats);
		layout.setBottom(bottomButtons);
		
		scene = new Scene(layout, 250, 350);
		
		// -------------------------------------
		
		
		
		// ----- Global Window Properties -----
		
		window.setOnCloseRequest(e -> 
		{
			ConfigGUI.closeConfig();
		});
		
		window.setOnShowing(e -> 
		{
			
		});
		
		window.setScene(scene);
		window.setTitle("Statistics");
		window.show();
		
		// ------------------------------------
	}
	
	
	
	protected static void studentsPerYearWindow()
	{
		Stage window = new Stage();
		Scene scene;
		
		
		
		Text enterYear = new Text("Enter Year:");
		enterYear.setFont(Font.font(14));
		
		TextField yearField = new TextField();
		yearField.setMinWidth(75);
		yearField.setMaxWidth(75);
		
		Button ok = new Button("OK");
		ok.setOnAction(e ->
		{
			int numStudents = getNumStudetsPerYear(yearField.getText());
			
			output("Students Per Year", yearField.getText(), ("" + numStudents), "");
			
			window.close();
		});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e ->
		{
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		
		
		VBox contents = new VBox();
		contents.setPadding(new Insets(10,10,10,10));
		contents.setSpacing(10);
		contents.setAlignment(Pos.CENTER);
		contents.getChildren().addAll(enterYear, yearField);
		
		VBox bottomButtons = new VBox();
		bottomButtons.setPadding(new Insets(10,10,10,10));
		bottomButtons.setSpacing(10);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.getChildren().addAll(ok, cancel);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(contents);
		layout.setBottom(bottomButtons);
		
		
		
		
		//define scene
		scene = new Scene(layout, 225, 310);
		
		
		
		// ----- Window Properties -----
		
		window.setOnCloseRequest(e -> 
		{
			e.consume();
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		window.setScene(scene);
		window.setTitle("Students By Year");
		window.showAndWait();
		
	}
	
	
	
	protected static void studentsPerLocationWindow()
	{
		Stage window = new Stage();
		Scene scene;
		
		
		
		Text enterLocation = new Text("Enter Location:");
		enterLocation.setFont(Font.font(14));
		
		TextField locationField = new TextField();
		locationField.setMinWidth(75);
		locationField.setMaxWidth(75);
		
		Button ok = new Button("OK");
		ok.setOnAction(e ->
		{
			int numStudents = getNumStudentsPerLocation(locationField.getText());
			
			output("Students Per Location", locationField.getText(), ("" + numStudents), "");
			
			window.close();
		});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e ->
		{
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		
		
		VBox contents = new VBox();
		contents.setPadding(new Insets(10,10,10,10));
		contents.setSpacing(10);
		contents.setAlignment(Pos.CENTER);
		contents.getChildren().addAll(enterLocation, locationField);
		
		VBox bottomButtons = new VBox();
		bottomButtons.setPadding(new Insets(10,10,10,10));
		bottomButtons.setSpacing(10);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.getChildren().addAll(ok, cancel);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(contents);
		layout.setBottom(bottomButtons);
		
		
		
		
		//define scene
		scene = new Scene(layout, 225, 310);
		
		
		
		// ----- Window Properties -----
		
		window.setOnCloseRequest(e -> 
		{
			e.consume();
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		window.setScene(scene);
		window.setTitle("Students By Location");
		window.showAndWait();
		
	}
	
	
	
	protected static void studentsPerCoursePerYearWindow()
	{
		Stage window = new Stage();
		Scene scene;
		
		
		
		Text enterCourse = new Text("Enter Course Code:");
		enterCourse.setFont(Font.font(14));
		
		TextField courseField = new TextField();
		courseField.setMinWidth(75);
		courseField.setMaxWidth(75);
		
		Text enterYear = new Text("Enter Year:");
		enterYear.setFont(Font.font(14));
		
		TextField yearField = new TextField();
		yearField.setMinWidth(75);
		yearField.setMaxWidth(75);
		
		Button ok = new Button("OK");
		ok.setOnAction(e ->
		{
			int numStudents = getNumStudentsInCoursePerYear(courseField.getText(), yearField.getText());
			
			output("Students Per Course Per Year", courseField.getText(), yearField.getText(), ("" + numStudents));
			
			window.close();
		});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e ->
		{
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		
		
		VBox contents = new VBox();
		contents.setPadding(new Insets(10,10,10,10));
		contents.setSpacing(10);
		contents.setAlignment(Pos.CENTER);
		contents.getChildren().addAll(enterCourse, courseField, enterYear, yearField);
		
		VBox bottomButtons = new VBox();
		bottomButtons.setPadding(new Insets(10,10,10,10));
		bottomButtons.setSpacing(10);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.getChildren().addAll(ok, cancel);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(contents);
		layout.setBottom(bottomButtons);
		
		
		
		
		//define scene
		scene = new Scene(layout, 225, 310);
		
		
		
		// ----- Window Properties -----
		
		window.setOnCloseRequest(e -> 
		{
			e.consume();
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		window.setScene(scene);
		window.setTitle("Students Per Course Per Year");
		window.showAndWait();
		
	}
	
	
	
	protected static void studentsInLocationByYearWindow()
	{
		Stage window = new Stage();
		Scene scene;
		
		
		
		Text enterLocation = new Text("Enter Location:");
		enterLocation.setFont(Font.font(14));
		
		TextField locationField = new TextField();
		locationField.setMinWidth(75);
		locationField.setMaxWidth(75);
		
		Text enterYear = new Text("Enter Year:");
		enterYear.setFont(Font.font(14));
		
		TextField yearField = new TextField();
		yearField.setMinWidth(75);
		yearField.setMaxWidth(75);
		
		Button ok = new Button("OK");
		ok.setOnAction(e ->
		{
			int numStudents = getNumStudentsInLocationPerYear(locationField.getText(), yearField.getText());
			
			output("Students In Location By Year", yearField.getText(), locationField.getText(), ("" + numStudents));
			
			window.close();
		});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e ->
		{
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
	
		
		
		VBox contents = new VBox();
		contents.setPadding(new Insets(10,10,10,10));
		contents.setSpacing(10);
		contents.setAlignment(Pos.CENTER);
		contents.getChildren().addAll(enterLocation, locationField, enterYear, yearField);
		
		VBox bottomButtons = new VBox();
		bottomButtons.setPadding(new Insets(10,10,10,10));
		bottomButtons.setSpacing(10);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.getChildren().addAll(ok, cancel);
		
		BorderPane layout = new BorderPane();
		layout.setCenter(contents);
		layout.setBottom(bottomButtons);
		
		
		
		
		//define scene
		scene = new Scene(layout, 225, 310);
		
		
		
		// ----- Window Properties -----
		
		window.setOnCloseRequest(e -> 
		{
			e.consume();
			boolean answer = ConfirmBox.display("Cancel", "Are you sure you want to go back?");
			if(answer)
			{
				window.close();
			}
		});
		
		window.setScene(scene);
		window.setTitle("Students In Location By Year");
		window.showAndWait();
		
	}

	
	
	protected static void output(String sheetName, String rowValueOne, String rowValueTwo, String rowValueThree)
	{
		boolean fileFound = false;
		int currentRow = 0;
		
		XSSFWorkbook workbook = null;
		XSSFSheet spreadsheet = null;
		XSSFRow row;
		XSSFCell cell;
		
		File file = new File(ConfigGUI.getOutputFolderPath() + "output.xlsx");
		FileOutputStream out = null;
		
		
		//check for file
		try
		{
			FileInputStream fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			fileFound = true;
		}
		catch(Exception e)
		{
			AlertBox.displayAlert("Attention", "Output file does not exist. New file will be created.");
			workbook = new XSSFWorkbook();
			
			spreadsheet = workbook.createSheet("Students Per Year");
			spreadsheet = workbook.createSheet("Students Per Location");
			spreadsheet = workbook.createSheet("Students Per Course Per Year");
			spreadsheet = workbook.createSheet("Students In Location By Year");
		} 
		
		
		try
		{
			out = new FileOutputStream(file);
		}
		catch(Exception e)
		{
			AlertBox.displayAlert("Error", "Could not set File Output Stream.");
		}
		
		
		//file now exists. get proper sheet
		spreadsheet = workbook.getSheet(sheetName);
		
		try
		{
			currentRow = spreadsheet.getLastRowNum() + 1;
		}
		//sheet is empty
		catch(Exception e)
		{
			row = spreadsheet.createRow(currentRow);
			cell = row.createCell(0);
			cell.setCellValue(sheetName);
			currentRow = 2;
		}
		
		//create cells
		row = spreadsheet.createRow(currentRow);
		cell = row.createCell(0);
		cell.setCellValue(rowValueOne);
		cell = row.createCell(1);
		cell.setCellValue(rowValueTwo);
		cell = row.createCell(2);
		cell.setCellValue(rowValueThree);
		
		//write new data to excel file
		try
		{
			workbook.write(out);
			
			out.close();
			workbook.close();
		}
		catch(Exception e)
		{
			AlertBox.displayAlert("Error", "Could not write to excel file.");
		}
	}
	
}