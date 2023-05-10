package com.sashaus;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUploadExampleTest {

    private ClassLoader cl = FileUploadExampleTest.class.getClassLoader();


    @Test
    void checkPDFFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("examples.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry file;

            while ((file = zis.getNextEntry()) != null) {
                if (file.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertTrue(file.getName().contains("youarenotdoneyet.pdf"));
                    Assertions.assertTrue(pdf.title.equals("Это еще не конец!"));
                }
            }
        }
    }

        @Test
        void checkXLSFromZipTest () throws Exception {
            try (InputStream is = cl.getResourceAsStream("examples.zip");
                 ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xls")) {
                        XLS file = new XLS(zs);
                        Assertions.assertEquals("file.xls", entry.getName());
                        Assertions.assertEquals("Great Britain", file.excel.getSheetAt(0).getRow(2).getCell(4).getStringCellValue());
                    }
                }
            }
        }

        @Test
        void checkCSVFromZipTest () throws Exception {
            try (InputStream is = cl.getResourceAsStream("examples.zip");
                 ZipInputStream zs = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zs.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".csv")) {
                        CSVReader reader = new CSVReader(new InputStreamReader(zs));
                        List<String[]> content = reader.readAll();
                        Assertions.assertEquals("email.csv", entry.getName());
                        Assertions.assertArrayEquals(new String[]{"laura@example.com;2070;Laura;Grey"}, content.get(1));
                    }
                }
            }
        }

        @Test
        void checkJsonContentTest () throws Exception {
            List<String> dogsCharacterValues = List.of("Clever", "Funny", "Silly", "Grumpy", "Loyal", "Kind", "Mischievous");
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = cl.getResourceAsStream("dog.json");
                 InputStreamReader isr = new InputStreamReader(is)) {
                Dog dog = objectMapper.readValue(isr, Dog.class);

                Assertions.assertEquals("Bilbo", dog.name);
                Assertions.assertEquals(5, dog.age);
                Assertions.assertEquals(36, dog.ageInHumanYears);
                Assertions.assertLinesMatch(dogsCharacterValues, dog.characterValues);
            }
        }
    }