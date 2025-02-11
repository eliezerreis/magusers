package com.magmutual.magusers.file;

import com.magmutual.magusers.entity.User;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Similarly of what EntityManager do, this class manage the csv file persistence;
 */
@Component
public class CsvFileManager {

    @Value("${csv.file.path}")
    public String filePath;

    public List<User> readAll() throws IOException {
        return new CsvToBeanBuilder<User>(new FileReader(filePath))
            .withType(User.class).build().parse();
    }

    public void writeAll(List<User> entities) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        /*
         * Direct from the OpenCSV documentation:
         * https://opencsv.sourceforge.net/#upgrading_from_3_x_to_4_x
         *
         * "We have rewritten the bean code to be multi-threaded so that writing from a list of beans is significantly
         * faster. Performance benefits depend largely on your data and hardware, but our non-rigorous tests indicate
         * that writing now takes half of the time it used to."
         *
         * It means that OpenCSV is thread-safe while writing to files. So, isn't necessary to handle
         * concurrency
         */

        Writer writer = new FileWriter(filePath);
        StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(writer).build();
        beanToCsv.write(entities);
        writer.close();
    }
}
