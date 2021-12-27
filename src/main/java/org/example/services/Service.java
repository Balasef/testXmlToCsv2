package org.example.services;

import org.example.model.Banknote;

import java.util.List;

public interface Service {

    void startProcess();

    List<Banknote> readDataFromXml();

    void createCsvFile(List<Banknote> dataForCsv);
}
