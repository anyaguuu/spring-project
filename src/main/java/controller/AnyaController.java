package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Deque;
import java.util.ArrayDeque;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.beans.User;
import app.beans.Repo;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AnyaController {

	private final Repo repo;

	@Autowired
	public AnyaController(Repo repo) {
		this.repo = repo;
	}

	@GetMapping("/getUsers")
	public List<User> getUsers() {
		return (List<User>) repo.findAll();
	}

	@PostMapping("/addUser")
	public ResponseEntity<String> addUser(@RequestBody User user) {
		repo.save(user);
		return new ResponseEntity<>("Added user", HttpStatus.OK);
	}

	@GetMapping("/getUserByParams")
	public ResponseEntity<User> getUserByParams(@RequestParam String name, @RequestParam int year,
			@RequestParam Long version) {

		User user = new User(name, year, version);

		// user.setName(name);
		// user.setYear(year);
		// user.setVersion(version);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/saveMyFirstUserList")
	public ResponseEntity<List<User>> saveMyFirstUserList(@RequestBody List<User> users) {

		List<User> lst = new ArrayList<>();
		for (User user : users) {
			lst.add(new User(user.getName(), user.getYearNum(), user.getVersion()));
		}

		return new ResponseEntity<>(lst, HttpStatus.OK);
	}

	// post: receive json object; get: receive params
	// different datatypes, calc on those numbers, etc.

	@PostMapping("/useHashMap")
	public ResponseEntity<Map<String, Integer>> useHashMap(@RequestBody List<User> users) {
		// will map name to year

		Map<String, Integer> map = new HashMap<>();

		for (User user : users) {
			map.put(user.getName(), user.getYearNum());
		}
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@GetMapping("/anyaTest")
	public ResponseEntity<String> anyaTest(@RequestParam String name, @RequestParam int year) {
		String str = name + " was born in " + year;
		return new ResponseEntity<>(str, HttpStatus.OK);
	}

	@PostMapping("/saveListWithStreams")
	public ResponseEntity<List<User>> saveListWithStreams(@RequestBody List<User> users) {
		List<User> lst2 = users.stream()
				.map(user -> new User(user.getName(), user.getYearNum(), user.getVersion())).toList();
		System.out.println("list made");
		return new ResponseEntity<>(lst2, HttpStatus.OK);
	}

	@GetMapping("/getUniqueNames")
	public ResponseEntity<Set<String>> getUniqueNames(@RequestBody List<User> users) {
		// get unique names
		Set<String> uniqueNames = users.stream().map(b -> b.getName()).collect(Collectors.toSet());
		return new ResponseEntity<>(uniqueNames, HttpStatus.OK);
	}

	@PostMapping("/useDequeAsQueue")
	public ResponseEntity<Deque<String>> useDequeAsQueue(@RequestBody List<User> users) {
		// FIFO
		Deque<String> names = new ArrayDeque<>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			names.add(user.getName());
		}
		return new ResponseEntity<>(names, HttpStatus.OK);
	}

	@GetMapping("/useDequeAsStack")
	public ResponseEntity<Deque<String>> useDequeAsStack(@RequestBody List<User> users) {
		// LIFO
		Deque<String> names = new ArrayDeque<>();
		for (User user : users) {
			names.push(user.getName());
		}
		return new ResponseEntity<>(names, HttpStatus.OK);
	}

	// also used set as input

	/* Below is reading files */

	@PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			// Check if uploaded file is not empty
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("Please select a file to upload");
			}

			return ResponseEntity.ok("File uploaded successfully");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Failed to upload the file: " + e.getMessage());
			// 500 is internal server error
		}
	}

	@PostMapping(value = "/uploadToDirectory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadToDirectory(@RequestParam("file") MultipartFile file) {
		try {
			// Specify target directory to save uploaded file
			String uploadDirectory = "/Users/anyagu/Developer/Citi/";

			// Check if directory exists, create if not
			File directory = new File(uploadDirectory);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			// Get file name
			String filename = file.getOriginalFilename();

			// Define path where file will be saved
			Path targetLocation = Path.of(uploadDirectory + filename);

			// Save file to target location
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return ResponseEntity.ok("File uploaded successfully");
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to upload the file: " + e.getMessage());
		}
	}

	@PostMapping(value = "/readFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> readFile(@RequestParam("file") MultipartFile file) {
		try {
			// Read the file
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			String line;
			String text = "";

			// Process each line, split into list
			while ((line = reader.readLine()) != null) {
				String[] lineArr = line.split(" ");
				for (String word : lineArr)
					text += (word);
			}

			reader.close();

			return new ResponseEntity<>(text, HttpStatus.OK);
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Failed to upload the file: " + e.getMessage());
		}
	}

	/* Handle different types of files! */

	@PostMapping(value = "/handleFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> handleFiles(@RequestParam("file") MultipartFile file) throws IOException {
		String filename = file.getOriginalFilename();

		if (filename.endsWith(".csv")) {
			return new ResponseEntity<>(handleCsv(file), HttpStatus.OK);
		} else if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
			return new ResponseEntity<>(handleExcel(file), HttpStatus.OK);
		}
		return ResponseEntity.status(500).body("Cannot process non .csv/.xlsx files");
	}

	private String handleCsv(MultipartFile file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
		String line;
		String removeSpaces = "";

		while ((line = reader.readLine()) != null) {
			String[] vals = line.split(","); // may have to adjust this
			for (String val : vals) {
				removeSpaces += val;
			}
		}
		reader.close();
		return removeSpaces;
	}

	private String handleExcel(MultipartFile file) {
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

	@PostMapping("/listToMap")
	public ResponseEntity<Map<String, User>> listToMap(@RequestBody List<User> lst) {
		Map<String, User> map = lst.stream().collect(Collectors.toMap(user -> user.getName(), user -> user));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
