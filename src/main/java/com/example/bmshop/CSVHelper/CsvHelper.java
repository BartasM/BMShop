package com.example.bmshop.CSVHelper;


import com.example.bmshop.entity.Customer;
import com.example.bmshop.service.CustomerService;
import org.apache.commons.csv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvHelper {

    private static Logger logger = LoggerFactory.getLogger(CsvHelper.class);

    private CustomerService customerService;
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"Name", "Email"};

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static ByteArrayInputStream customersToCsv(List<Customer> customerList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            for (Customer customer : customerList) {
                List<? extends Serializable> data = Arrays.asList(
                        String.valueOf(customer.getId()),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getDateOfBirth(),
                        String.valueOf(customer.isActive())
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV File: " + e.getMessage());
        }
    }

    public static List<Customer> csvToCustomers(InputStream is) {

        logger.info("Start loading Customer file");

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreEmptyLines().withIgnoreHeaderCase().withTrim());) {

            List<Customer> customerList = new ArrayList<Customer>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {

                Customer customer = new Customer(
                        csvRecord.get("name"),
                        csvRecord.get("email"),
                        LocalDate.parse(csvRecord.get("dateOfBirth")));
                customerList.add(customer);
            }

            logger.info("Customers saved into a list in csvToCustomers method");

            return customerList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
