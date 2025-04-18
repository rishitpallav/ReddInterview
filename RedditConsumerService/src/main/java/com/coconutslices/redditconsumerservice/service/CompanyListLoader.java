package com.coconutslices.redditconsumerservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class CompanyListLoader {

    private final Set<String> companies = new HashSet<>();

    public CompanyListLoader (@Value("${file-path.company-names}") String companyNamesFilePath) {
        try {
            InputStream inputStream = new ClassPathResource(companyNamesFilePath).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                companies.add(line.trim().toLowerCase());
            }

            reader.close();

            log.info("✅ Loaded {} companies from {}", companies.size(), companyNamesFilePath);
        } catch (Exception e) {
            log.error("❗ Error loading companies: {}", e.getMessage());
        }
    }

    public Set<String> getCompanies() {
        return companies;
    }
}
