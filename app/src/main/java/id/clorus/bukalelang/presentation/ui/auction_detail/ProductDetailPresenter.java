package id.clorus.bukalelang.presentation.ui.auction_detail;

import android.content.Context;

/**
 * Created by mirza on 23/05/17.
 */

public class ProductDetailPresenter {

    ProductDetailView view;
    Context context;

    public ProductDetailPresenter(ProductDetailView view, Context context){

        this.view = view;
        this.context = context;


    }
}
