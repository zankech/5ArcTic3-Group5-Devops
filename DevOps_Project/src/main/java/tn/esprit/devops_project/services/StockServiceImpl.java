package tn.esprit.devops_project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.devops_project.services.Iservices.IStockService;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

   private final StockRepository stockRepository;

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock retrieveStock(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new NullPointerException("Stock not found"));
    }

    @Override
    public List<Stock> retrieveAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public double calculateTotalStockValue(Long stockId) {
        Stock stock = retrieveStock(stockId);  // Récupération du stock

        // Calculer la valeur totale des produits du stock
        return stock.getProducts().stream()
                .mapToDouble(product -> product.getPrice() * product.getQuantity())
                .sum();
    }


}
