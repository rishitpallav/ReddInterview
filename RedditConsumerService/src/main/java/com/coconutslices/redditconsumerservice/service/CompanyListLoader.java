package com.coconutslices.redditconsumerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CompanyListLoader {

    private final Set<String> companies;

    public CompanyListLoader (@Value("${file-path.company-names}") String companyNamesFilePath) {

        Set<String> tempCompaniesSet = new HashSet<>();

        try {
            InputStream inputStream = new ClassPathResource(companyNamesFilePath).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                tempCompaniesSet.add(line.trim().toLowerCase());
            }

            reader.close();

            log.info("✅ Loaded {} unique companies from {}", tempCompaniesSet.size(), companyNamesFilePath);
        } catch (Exception e) {
            log.error("❗ Error loading companies from {} : {}", companyNamesFilePath, e.getMessage());
        }

        companies = Collections.unmodifiableSet(tempCompaniesSet);

    }

    public Set<String> getCompanies() {
        return companies;
    }
}
