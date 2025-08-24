package system.flight.Flight.Management.System;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import system.flight.dto.PaymentsResponseDTO;
import system.flight.entities.Payment;
import system.flight.entities.User;
import system.flight.enums.PaymentStatus;
import system.flight.exception.ResourceNotFoundException;
import system.flight.mapper.PaymentMapper;
import system.flight.repository.PaymentRepository;
import system.flight.services.PaymentService;
import system.flight.services.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static system.flight.enums.PaymentStatus.SUCCESS;
import static system.flight.enums.PaymentStatus.REFUNDED;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserService userService;

    private Payment payment;
    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = new User();
        mockUser.setUserId(100); // Set required fields

        payment = new Payment();
        payment.setPaymentId(1);
        payment.setAmountPaid(250.0);
        payment.setPaymentStatus(SUCCESS);
        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        payment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        payment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        payment.setUser(mockUser); // Set owner for ownership validation
    }

    @Test
    void testGetAllPayments_Success() {
        when(paymentRepository.findAll()).thenReturn(List.of(payment));

        List<Payment> result = paymentService.getAllPayments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(SUCCESS, result.get(0).getPaymentStatus());
    }

    @Test
    void testGetPaymentById_Success() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(1);

        assertNotNull(result);
        assertEquals(250.0, result.getAmountPaid());
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
        when(paymentRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                paymentService.getPaymentById(99));
    }

    @Test
    void testGetPaymentsByStatus_Success() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
        when(paymentRepository.findByPaymentStatus(SUCCESS)).thenReturn(List.of(payment));

        PaymentsResponseDTO dto = new PaymentsResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setAmountPaid(payment.getAmountPaid());
        dto.setPaymentStatus(payment.getPaymentStatus());

        try (MockedStatic<PaymentMapper> mockedMapper = mockStatic(PaymentMapper.class)) {
            mockedMapper.when(() -> PaymentMapper.toDTO(payment)).thenReturn(dto);

            List<PaymentsResponseDTO> result = paymentService.getPaymentsByStatus("SUCCESS");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(SUCCESS, result.get(0).getPaymentStatus());
        }
    }

    @Test
    void testGetPaymentsByStatus_Empty() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(mockUser);
        when(paymentRepository.findByPaymentStatus(REFUNDED)).thenReturn(List.of());

        List<PaymentsResponseDTO> result = paymentService.getPaymentsByStatus("REFUNDED");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPaymentsByStatus_InvalidStatus() {
        assertThrows(RuntimeException.class, () ->
                paymentService.getPaymentsByStatus("INVALID_STATUS"));
    }
}