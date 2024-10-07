package tn.esprit.devops_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InvoiceServiceImplTest {
    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Arrange
    }

    @Test
    public void testRetrieveInvoice_Success() {
        // Arrange
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setIdInvoice(invoiceId);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Act
        Invoice result = invoiceService.retrieveInvoice(invoiceId);

        // Assert
        assertNotNull(result);
        assertEquals(invoiceId, result.getIdInvoice());
        verify(invoiceRepository, times(1)).findById(invoiceId);
    }

    @Test
    public void testRetrieveInvoice_NotFound() {
        // Arrange
        Long invoiceId = 1L;
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, () -> {
            invoiceService.retrieveInvoice(invoiceId);
        });
        assertEquals("Invoice not found", exception.getMessage());
        verify(invoiceRepository, times(1)).findById(invoiceId);
    }
    @Test
    public void testCancelInvoice() {
        // Arrange
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        invoice.setIdInvoice(invoiceId);
        invoice.setArchived(false);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        // Act
        invoiceService.cancelInvoice(invoiceId);

        // Assert
        assertTrue(invoice.getArchived());
        verify(invoiceRepository, times(1)).save(invoice);
        verify(invoiceRepository, times(1)).updateInvoice(invoiceId); // Si méthode JPQL utilisée
    }
}