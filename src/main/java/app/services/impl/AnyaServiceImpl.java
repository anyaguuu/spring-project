package app.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.services.AnyaService;

@Service("AnyaService")
public class AnyaServiceImpl implements AnyaService{

	@Override
	public String handleCsv(MultipartFile file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		String removeSpaces = "";
		
		while ((line = reader.readLine()) != null) {
			String[] vals = line.split(","); // may have to adjust this
			for (String val: vals) {
				removeSpaces += val;
			}
		}
		reader.close();
		return removeSpaces;
	}

	@Override
	public String handleExcel(MultipartFile file) {
		try {
	        Workbook workbook = WorkbookFactory.create(file.getInputStream());

	        // Read data from the Excel file
	        Sheet sheet = workbook.getSheetAt(0); // read the first sheet
	        StringBuilder data = new StringBuilder();
	        for (Row row : sheet) {
	            for (Cell cell : row) {
	                data.append(cell.toString()).append(" ");
	            }
	            data.append("\n");
	        }

	        workbook.close();

	        return data.toString();
	    } catch (IOException e) {
	        return "Failed to process the file: " + e.getMessage();
	    }
	}
	
	
}
