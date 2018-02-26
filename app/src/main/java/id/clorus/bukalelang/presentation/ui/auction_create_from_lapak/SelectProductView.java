package id.clorus.bukalelang.presentation.ui.auction_create_from_lapak;

import java.util.List;

import id.clorus.bukalelang.data.entity.response.lapak.Product;

/**
 * Created by mirza on 04/06/17.
 */

public interface SelectProductView {

    void onLapakLoadded(List<Product> products);
    void onLapakSelected(Product product);
}
