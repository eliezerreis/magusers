package com.magmutual.magusers.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @CsvBindByName
    private int id;

    @CsvBindByName
    private String firstName;

    @CsvBindByName
    private String lastName;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String profession;

    @CsvBindByName
    @CsvDate("yyyy-MM-dd")
    private LocalDate dateCreated;

    @CsvBindByName
    private String country;

    @CsvBindByName
    private String city;

}
