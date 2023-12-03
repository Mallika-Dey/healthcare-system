package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.request.MedicineRequestDTO;
import com.example.inventoryservice.exception.CustomException;
import com.example.inventoryservice.service.impl.MedicineServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MedicalServiceTest {
    private MedicineServiceImpl medicineService;

    @BeforeEach
    public void setUp() {
        medicineService = new MedicineServiceImpl(null);
    }

    @Test
    public void testValidateMedicineDatesWithFutureProductionDate() {
        MedicineRequestDTO medicineRequestDTO = MedicineRequestDTO
                .builder()
                .productionDate(LocalDate.of(2024, 01, 01))
                .expirationDate(LocalDate.of(2025, 12, 31))
                .build();
        CustomException thrown = assertThrows(
                CustomException.class,
                () -> medicineService.addMedicine(medicineRequestDTO),
                "Production date should be past.."
        );

        assertTrue(thrown.getMessage().contains("Production date should be past"));
        assertTrue(thrown.getStatus() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testValidateMedicineDatesWithPastExpirationDate() {
        MedicineRequestDTO medicineRequestDTO = MedicineRequestDTO
                .builder()
                .productionDate(LocalDate.of(2023, 01, 03))
                .expirationDate(LocalDate.of(2023, 01, 01))
                .build();

        CustomException thrown = assertThrows(
                CustomException.class,
                () -> medicineService.addMedicine(medicineRequestDTO),
                "Expected validateMedicineDates to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Production date should be before Expiration date"));
        assertTrue(thrown.getStatus() == HttpStatus.BAD_REQUEST);
    }

}
