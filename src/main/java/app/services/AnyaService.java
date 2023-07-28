package app.services;


import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface AnyaService {
	
	String handleCsv(MultipartFile file) throws IOException;
	
	String handleExcel(MultipartFile file);
}
