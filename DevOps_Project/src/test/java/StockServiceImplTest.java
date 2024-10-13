import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.StockServiceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    private Stock stock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les mocks

        // Produit 1 : Smartphone, 800.0 * 3 = 2400.0
        Product product1 = Product.builder()
                .title("Smartphone")
                .price(800.0f)
                .quantity(3)
                .category(ProductCategory.ELECTRONICS)
                .build();

        // Produit 2 : Livre, 15.0 * 10 = 150.0
        Product product2 = Product.builder()
                .title("Novel")
                .price(15.0f)
                .quantity(10)
                .category(ProductCategory.BOOKS)
                .build();

        // Ajouter les produits au stock
        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        stock = new Stock(1L, "Central Stock", products);
    }


    @Test
    public void testCalculateTotalStockValue() {
        // Simuler le comportement du repository
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        // Appeler la fonction à tester
        double totalValue = stockService.calculateTotalStockValue(1L);

        // Vérifier le résultat attendu : (800 * 3) + (15 * 10) = 2550
        assertEquals(2550.0, totalValue, 0.01);
    }

}


